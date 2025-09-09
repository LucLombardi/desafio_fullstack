import { FornecedorResponse } from './../../../core/interfaces/fornecedor-response';
import { EmpresaResponse } from './../../../core/interfaces/empresa-response';
import { VinculacaoService } from './../../../core/services/vinculacao.service';
import { FornecedorService } from './../../../core/services/fornecedor.service';
// src/app/features/vinculacao/vinculacao-fornecedor-empresa.component.ts

import { Component, OnInit, OnDestroy } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { EmpresaService } from '../../../core/services/empresa.service'; 

import { Subject, takeUntil, forkJoin } from 'rxjs'; // Usaremos Subject para desinscrever e forkJoin para requisições paralelas

@Component({
  selector: 'app-vinculacao-fornecedor-empresa',
  templateUrl: './vinculacao-fornecedor-empresa.component.html',
  styleUrls: ['./vinculacao-fornecedor-empresa.component.css'],
  standalone: false

})
export class VinculacaoFornecedorEmpresaComponent implements OnInit, OnDestroy {

  vinculacaoForm!: FormGroup;
  empresas: EmpresaResponse[] = [];
  fornecedoresDisponiveis: FornecedorResponse[] = [];
  fornecedoresVinculados: FornecedorResponse[] = []; // Fornecedores já vinculados à empresa selecionada

  isLoadingEmpresas = false;
  isLoadingFornecedoresDisponiveis = false;
  isLoadingFornecedoresVinculados = false;
  isSavingVinculacao = false;
  errorMessage: string | null = null;
  successMessage: string | null = null;

  private destroy$ = new Subject<void>(); // Para gerenciar a desinscrição de Observables

  constructor(
    private fb: FormBuilder,
    private empresaService: EmpresaService,
    private fornecedorService: FornecedorService,
    private vinculacaoService: VinculacaoService
  ) { }

  ngOnInit(): void {
    this.initializeForm();
    this.loadEmpresas();
    this.loadFornecedoresDisponiveis(); // Carrega todos os fornecedores para o dropdown
  }

  ngOnDestroy(): void {
    this.destroy$.next();
    this.destroy$.complete();
  }

  initializeForm(): void {
    this.vinculacaoForm = this.fb.group({
      empresaId: [null, Validators.required],
      fornecedorId: [null, Validators.required]
    });

    // Observa mudanças na empresa selecionada para carregar fornecedores vinculados
    this.vinculacaoForm.get('empresaId')?.valueChanges
      .pipe(takeUntil(this.destroy$))
      .subscribe(empresaId => {
        if (empresaId) {
          this.loadFornecedoresVinculados(empresaId);
          this.errorMessage = null; 
          this.successMessage = null;
        } else {
          this.fornecedoresVinculados = []; // Limpa a lista se nenhuma empresa estiver selecionada
        }
      });
  }

  loadEmpresas(): void {
    this.isLoadingEmpresas = true;
    this.errorMessage = null;
    this.empresaService.getEmpresas(0, 9999) // Buscar todas as empresas para o dropdown
      .pipe(takeUntil(this.destroy$))
      .subscribe({
        next: (data) => {
          this.empresas = data._embedded?.['empresaResponseDtoList'] ?? [];
          this.isLoadingEmpresas = false;
        },
        error: (error) => {
          this.errorMessage = 'Erro ao carregar empresas.';
          console.error('Erro ao carregar empresas:', error);
          this.isLoadingEmpresas = false;
        }
      });
  }

  loadFornecedoresDisponiveis(): void {
    this.isLoadingFornecedoresDisponiveis = true;
    this.errorMessage = null;
    this.fornecedorService.getFornecedores(0, 9999) // Buscar todos os fornecedores para o dropdown
      .pipe(takeUntil(this.destroy$))
      .subscribe({
        next: (data) => {
          this.fornecedoresDisponiveis = data._embedded?.['fornecedorResponseDtoList'] ?? [];
          this.isLoadingFornecedoresDisponiveis = false;
        },
        error: (error) => {
          this.errorMessage = 'Erro ao carregar fornecedores disponíveis.';
          console.error('Erro ao carregar fornecedores disponíveis:', error);
          this.isLoadingFornecedoresDisponiveis = false;
        }
      });
  }

  loadFornecedoresVinculados(empresaId: number): void {
    this.isLoadingFornecedoresVinculados = true;
    this.errorMessage = null;
    // O backend tem um endpoint GET /api/empresas/{id}/fornecedores
    this.empresaService.getFornecedoresByEmpresaId(empresaId)
      .pipe(takeUntil(this.destroy$))
      .subscribe({
        next: (data) => {
          this.fornecedoresVinculados = data.content; // Supondo que o backend retorna um Page de fornecedores
          this.isLoadingFornecedoresVinculados = false;
        },
        error: (error) => {
         // this.errorMessage = error.error?.message || 'Erro ao carregar fornecedores vinculados.';
          console.error('Erro ao carregar fornecedores vinculados:', error);
          this.isLoadingFornecedoresVinculados = false;
          this.fornecedoresVinculados = []; // Limpa a lista em caso de erro
        }
      });
  }

  onVincularFornecedor(): void {
    this.errorMessage = null;
    this.successMessage = null;

    if (this.vinculacaoForm.invalid) {
      this.vinculacaoForm.markAllAsTouched();
      this.errorMessage = 'Por favor, selecione uma empresa e um fornecedor.';
      return;
    }

    const { empresaId, fornecedorId } = this.vinculacaoForm.value;

    
    const fornecedorJaVinculado = this.fornecedoresVinculados.some(f => f.id === fornecedorId);
    if (fornecedorJaVinculado) {
      this.errorMessage = 'Este fornecedor já está vinculado a esta empresa.';
      return;
    }

    this.isSavingVinculacao = true;
    this.vinculacaoService.vincularFornecedorEmpresa({ empresaId, fornecedorId })
      .pipe(takeUntil(this.destroy$))
      .subscribe({
        next: () => {
          this.successMessage = 'Fornecedor vinculado com sucesso!';
          // Recarrega a lista de fornecedores vinculados para atualizar a tabela
          this.loadFornecedoresVinculados(empresaId);
          // Limpa a seleção do fornecedor no dropdown após a vinculação
          this.vinculacaoForm.get('fornecedorId')?.setValue(null);
          this.isSavingVinculacao = false;
        },
        error: (error) => {
          this.errorMessage = error.error?.message || 'Erro ao vincular fornecedor.';
          console.error('Erro ao vincular fornecedor:', error);
          this.isSavingVinculacao = false;
        }
      });
  }

  onDesvincularFornecedor(fornecedorId: number, fornecedorNome: string): void {
    this.errorMessage = null;
    this.successMessage = null;
    const empresaId = this.vinculacaoForm.get('empresaId')?.value;

    if (!empresaId) {
      this.errorMessage = 'Por favor, selecione uma empresa para desvincular o fornecedor.';
      return;
    }

    if (confirm(`Tem certeza que deseja desvincular o fornecedor "${fornecedorNome}" desta empresa?`)) {
      this.isSavingVinculacao = true; // Usamos a mesma flag para indicar operação em andamento
      this.vinculacaoService.desvincularFornecedorEmpresa(fornecedorId, empresaId)
        .pipe(takeUntil(this.destroy$))
        .subscribe({
          next: () => {
            this.successMessage = 'Fornecedor desvinculado com sucesso!';
            this.loadFornecedoresVinculados(empresaId); // Recarrega a lista
            this.isSavingVinculacao = false;
          },
          error: (error) => {
            this.errorMessage = error.error?.message || 'Erro ao desvincular fornecedor.';
            console.error('Erro ao desvincular fornecedor:', error);
            this.isSavingVinculacao = false;
          }
        });
    }
  }

  // --- Helpers para o template ---
  get empresaFormControl() {
    return this.vinculacaoForm.get('empresaId');
  }

  get fornecedorFormControl() {
    return this.vinculacaoForm.get('fornecedorId');
  }

}