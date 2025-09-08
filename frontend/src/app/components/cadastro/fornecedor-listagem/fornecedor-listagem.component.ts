import { GenericEmbedded } from './../../../shared/type/pages';

import { FornecedorService } from './../../../core/services/fornecedor.service';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Page} from '../../../shared/type/pages'; 
import { FornecedorResponse } from '../../../core/interfaces/fornecedor-response';
import { take } from 'rxjs';


@Component({
  selector: 'app-fornecedor-listagem',
  templateUrl: './fornecedor-listagem.component.html',
  styleUrls: ['./fornecedor-listagem.component.css'],
  standalone: false
})
export class FornecedorListagemComponent implements OnInit {
  fornecedores: FornecedorResponse[] = [];
  loading: boolean = false;
  deleting: boolean = false;
  errorMessage: string | null = null;

  // Propriedades para busca
  searchCnpjOuCpf: string = ''; // O backend não tem endpoint de busca por CPF/CNPJ na lista, mas podemos simular ou preparar para o futuro
  searchNome: string = '';

  // Propriedades para paginação
  currentPage: number = 0;
  pageSize: number = 10;
  totalPages: number = 0;
  totalElements: number = 0;

  // Propriedades para o modal de exclusão
  showDeleteModal: boolean = false;
  fornecedorToDelete: FornecedorResponse | null = null;

  constructor(
    private fornecedorService: FornecedorService,
    private router: Router
  ) { }

  ngOnInit(): void {
    this.loadFornecedores();
  }

  /**
   * Carrega a lista de fornecedores com base nos filtros e paginação atuais.
   */
  loadFornecedores(): void {
    this.loading = true;
    this.errorMessage = null;
    this.fornecedorService.getFornecedores(this.currentPage, this.pageSize, this.searchNome)
    .pipe(take(1))  
    .subscribe({
        next: (response: Page<FornecedorResponse>) => {
         this.fornecedores = response._embedded?.['fornecedorResponseDtoList'] ?? [];
          this.totalPages = response.page.totalPages;
          this.totalElements = response.page.totalElements;
          this.loading = false;
        },
        error: (error) => {
          console.error('Erro ao carregar fornecedores:', error);
          this.errorMessage = 'Erro ao carregar a lista de fornecedores. Tente novamente mais tarde.';
          this.loading = false;
        }
      });
  }

  /**
   * Lida com a busca de fornecedores.
   * Reinicia a paginação e recarrega os dados.
   */
  onSearch(): void {
    this.currentPage = 0; // Volta para a primeira página ao realizar uma nova busca
    this.loadFornecedores();
  }

  /**
   * Altera a página atual da lista.
   * @param page O número da página para navegar.
   */
  goToPage(page: number): void {
    if (page >= 0 && page < this.totalPages) {
      this.currentPage = page;
      this.loadFornecedores();
    }
  }

  /**
   * Redireciona para a tela de cadastro/edição para editar um fornecedor.
   * @param id O ID do fornecedor a ser editado.
   */
  onEdit(id: number): void {
    this.router.navigate(['/fornecedores/cadastro', id]);
  }

  /**
   * Abre o modal de confirmação de exclusão para um fornecedor específico.
   * @param fornecedor O fornecedor a ser excluído.
   */
  onDelete(fornecedor: FornecedorResponse): void {
    this.fornecedorToDelete = fornecedor;
    this.showDeleteModal = true;
  }

  /**
   * Confirma a exclusão do fornecedor selecionado.
   */
  confirmDelete(): void {
    if (this.fornecedorToDelete && this.fornecedorToDelete.id !== undefined) {
      this.deleting = true;
      this.fornecedorService.deleteFornecedor(this.fornecedorToDelete.id)
        .subscribe({
          next: () => {
            console.log(`Fornecedor ${this.fornecedorToDelete?.nome} excluído com sucesso.`);
            this.showDeleteModal = false;
            this.fornecedorToDelete = null;
            this.deleting = false;
            this.loadFornecedores(); // Recarrega a lista após a exclusão
          },
          error: (error) => {
            console.error('Erro ao excluir fornecedor:', error);
            this.errorMessage = `Erro ao excluir fornecedor: ${error.error.message || error.message}`;
            this.deleting = false;
          }
        });
    }
  }

  /**
   * Cancela a operação de exclusão e fecha o modal.
   */
  cancelDelete(): void {
    this.showDeleteModal = false;
    this.fornecedorToDelete = null;
    this.errorMessage = null; // Limpa qualquer mensagem de erro ao cancelar
  }

  /**
   * Redireciona para a tela de cadastro de um novo fornecedor.
   */
  onAddFornecedor(): void {
    this.router.navigate(['/fornecedores/cadastro']);
  }
}