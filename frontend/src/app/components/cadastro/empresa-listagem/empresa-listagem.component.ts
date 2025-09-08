import { Component, inject, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router'; 
import { EmpresaService } from '../../../core/services/empresa.service'; 
import { EmpresaResponse } from '../../../core/interfaces/empresa-response';
import { take } from 'rxjs/operators';
import { Page} from '../../../shared/type/pages'; 

@Component({
  selector: 'app-empresa-listagem',
  standalone: false,
  templateUrl: './empresa-listagem.component.html',
  styleUrl: './empresa-listagem.component.css'
})
export class EmpresaListagemComponent implements OnInit{
  empresas: EmpresaResponse[] = [];
  searchCnpj: string = '';
  searchNomeFantasia: string = '';
  loading: boolean = false;
  deleting: boolean = false;
  errorMessage: string | null = null;

  // Paginação
  currentPage: number = 0;
  pageSize: number = 10; // Definir o tamanho da página, como no backend (default 10)
  totalPages: number = 0;
  totalElements: number = 0;

  // Modal de Exclusão
  showDeleteModal: boolean = false;
  empresaToDelete: EmpresaResponse | null = null;

  constructor(
    private empresaService: EmpresaService,
    private router: Router // Injeta o Router
  ) { }

  ngOnInit(): void {
    this.loadEmpresas(); // Carrega as empresas na inicialização
  }

  // --- Métodos de Busca e Carregamento ---
  loadEmpresas(): void {
    this.loading = true;
    this.errorMessage = null;

  

    this.empresaService.getEmpresas(this.currentPage, this.pageSize)
      .pipe(take(1)) 
      .subscribe({
        next: (response: Page<EmpresaResponse>) => {
          let filteredEmpresas = response._embedded?.['empresaResponseDtoList']??[];

          if (this.searchCnpj) {
            filteredEmpresas = filteredEmpresas.filter((e: EmpresaResponse) => e.cnpj.includes(this.searchCnpj));
          }
          if (this.searchNomeFantasia) {
            filteredEmpresas = filteredEmpresas.filter((e: EmpresaResponse) =>
              e.nomeFantasia.toLowerCase().includes(this.searchNomeFantasia.toLowerCase())
            );
          }

          this.empresas = filteredEmpresas; // Atribui as empresas filtradas
          this.totalPages = response.page.totalPages;
          this.totalElements = response.page.totalElements;
          this.loading = false;
        },
        error: (error) => {
          console.error('Erro ao carregar empresas:', error);
          this.errorMessage = 'Erro ao carregar empresas. Tente novamente mais tarde.';
          this.loading = false;
        }
      });
  }


  onSearch(): void {
    this.currentPage = 0; // Reinicia a paginação para a primeira página ao buscar
    this.loadEmpresas();
  }

  // --- Métodos de Paginação ---
  goToPage(page: number): void {
    if (page >= 0 && page < this.totalPages) {
      this.currentPage = page;
      this.loadEmpresas();
    }
  }

  // --- Métodos de Ação (Editar, Excluir) ---
  onEdit(id: number): void {
    // Redireciona para a rota de cadastro de empresa com o ID para edição
    this.router.navigate(['/empresas/cadastro', id]);
    // A rota '/empresas/cadastro/:id' deve ser configurada no seu módulo de rotas
  }

  onDelete(empresa: EmpresaResponse): void {
    this.empresaToDelete = empresa;
    this.showDeleteModal = true;
  }

  confirmDelete(): void {
    if (this.empresaToDelete && !this.deleting) {
      this.deleting = true;
      this.empresaService.deleteEmpresa(this.empresaToDelete.id)
        .pipe(take(1))
        .subscribe({
          next: () => {
            alert(`Empresa "${this.empresaToDelete?.nomeFantasia}" excluída com sucesso!`);
            this.showDeleteModal = false;
            this.empresaToDelete = null;
            this.deleting = false;
            this.loadEmpresas(); // Recarrega a lista após a exclusão
          },
          error: (error) => {
            console.error('Erro ao excluir empresa:', error);
            this.errorMessage = `Erro ao excluir empresa: ${error.message || 'Erro desconhecido.'}`;
            alert(`Erro ao excluir empresa: ${error.error?.message || 'Verifique o console para detalhes.'}`);
            this.deleting = false;
            this.showDeleteModal = false; // Fecha o modal mesmo com erro
          }
        });
    }
  }

  cancelDelete(): void {
    this.showDeleteModal = false;
    this.empresaToDelete = null;
  }

  onAddCompany(): void {
    this.router.navigate(['/empresas/cadastro']); // Redireciona para a tela de cadastro (sem ID)
  }

}
