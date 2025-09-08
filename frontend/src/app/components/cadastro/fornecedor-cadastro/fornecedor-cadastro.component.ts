import { TipoPessoa } from './../../../shared/type/pessoa';
import { FornecedorResponse } from './../../../core/interfaces/fornecedor-response';
import { FornecedorRequest } from './../../../core/interfaces/fornecedor-request';
import { Component, OnInit, Output, EventEmitter, Input } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { FornecedorService } from '../../../core/services/fornecedor.service';
import { CepService } from '../../../core/services/cep.service';
import { CepResponse } from '../../../core/interfaces/cep-response';
import { HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

@Component({
  selector: 'app-fornecedor-cadastro',
  templateUrl: './fornecedor-cadastro.component.html',
  styleUrls: ['./fornecedor-cadastro.component.css'],
  standalone:false
})
export class FornecedorCadastroComponent implements OnInit {
  @Input() fornecedorId?: number; // Para ser usado em modo de edição via input
  @Output() formSubmitted = new EventEmitter<FornecedorResponse>();
  @Output() formCanceled = new EventEmitter<void>();

  fornecedorForm!: FormGroup;
  isEditMode: boolean = false;
  isSaving: boolean = false;
  cepLoading: boolean = false;
  cepError: string | null = null;
  errorMessage: string | null = null;
  loading: boolean = false;

  tiposPessoa: TipoPessoa[] = ['FISICA', 'JURIDICA'];

  constructor(
    private fb: FormBuilder,
    private fornecedorService: FornecedorService,
    private cepService: CepService,
    private route: ActivatedRoute,
    private router: Router
  ) { }

  ngOnInit(): void {
    this.initializeForm();

    // Verifica se há um ID na rota para determinar o modo de edição
    this.route.paramMap.subscribe(params => {
      const idParam = params.get('id');
      if (idParam) {
        this.fornecedorId = +idParam; // Converte para número
        this.isEditMode = true;
        this.loadFornecedorForEditing(this.fornecedorId);
      }
    });

    // Observa mudanças no tipoPessoa para aplicar validações condicionais
    this.fornecedorForm.get('tipoPessoa')?.valueChanges.subscribe(value => {
      this.onTipoPessoaChange(value);
    });
  }

  /**
   * Inicializa o formulário com os controles e validações.
   */
  initializeForm(): void {
    this.fornecedorForm = this.fb.group({
      tipoPessoa: ['JURIDICA', Validators.required], // Valor padrão para PJ
      nome: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      cnpjOuCpf: ['', Validators.required],
      rg: [null], // Inicialmente null, validação condicional
      dataNascimento: [null], // Inicialmente null, validação condicional
      cep: ['', [Validators.required, Validators.pattern(/^\d{5}-?\d{3}$/)]],
      logradouro: ['', Validators.required],
      numero: ['', Validators.required],
      complemento: [null],
      bairro: ['', Validators.required],
      localidade: ['', Validators.required],
      uf: ['', [Validators.required, Validators.maxLength(2), Validators.minLength(2)]],
      estado: ['', Validators.required], // Campo para nome completo do estado
    });

    // Aplica as validações condicionais iniciais com base no valor padrão 'JURIDICA'
    this.onTipoPessoaChange('JURIDICA');
  }

  /**
   * Ajusta as validações dos campos RG e Data de Nascimento com base no Tipo de Pessoa.
   * @param tipo O tipo de pessoa selecionado (FISICA ou JURIDICA).
   */
  onTipoPessoaChange(tipo: TipoPessoa): void {
    const rgControl = this.fornecedorForm.get('rg');
    const dataNascimentoControl = this.fornecedorForm.get('dataNascimento');

    if (tipo === 'FISICA') {
      rgControl?.setValidators([Validators.required]);
      dataNascimentoControl?.setValidators([Validators.required]);
    } else {
      rgControl?.clearValidators();
      dataNascimentoControl?.clearValidators();
      // Limpa os valores se não forem mais necessários
      rgControl?.setValue(null);
      dataNascimentoControl?.setValue(null);
    }
    rgControl?.updateValueAndValidity();
    dataNascimentoControl?.updateValueAndValidity();
  }

  /**
   * Carrega os dados de um fornecedor existente para edição.
   * @param id O ID do fornecedor a ser carregado.
   */
  loadFornecedorForEditing(id: number): void {
    this.loading = true; //
    this.fornecedorService.getFornecedorById(id).subscribe({
      next: (fornecedor: FornecedorResponse) => {


  
        const dataNascimentoFormatada = fornecedor.dataNascimento ?
          new Date(fornecedor.dataNascimento).toISOString().split('T')[0] : null;

        this.fornecedorForm.patchValue({
          tipoPessoa: fornecedor.tipoPessoa,
          nome: fornecedor.nome,
          email: fornecedor.email,
          cnpjOuCpf: fornecedor.cnpjOuCpf,
          rg: fornecedor.rg,
          dataNascimento: dataNascimentoFormatada,
          cep: fornecedor.endereco.cep,
          logradouro: fornecedor.endereco.logradouro,
          numero: fornecedor.endereco.numero,
          complemento: fornecedor.endereco.complemento,
          bairro: fornecedor.endereco.bairro,
          localidade: fornecedor.endereco.localidade,
          uf: fornecedor.endereco.uf,
          estado: fornecedor.endereco.estado,
        });
        this.loading = false;
      },
      error: (error) => {
        console.error('Erro ao carregar fornecedor para edição:', error);
        this.errorMessage = 'Não foi possível carregar os dados do fornecedor para edição.';
        this.loading = false;
        // Opcional: Redirecionar ou mostrar mensagem e manter o form vazio
        // this.router.navigate(['/fornecedores']);
      }
    });
  }

  /**
   * Busca o endereço automaticamente ao sair do campo CEP.
   */
  onCepBlur(): void {
    const cep = this.fornecedorForm.get('cep')?.value;
    if (cep && cep.length === 8) { // Verifica se o CEP tem 8 dígitos (sem máscara, se houver)
      this.cepLoading = true;
      this.cepError = null;
      this.cepService.buscarEnderecoPorCep(cep).subscribe({
        next: (data: CepResponse) => {
          if (!data.erro) {
            this.fornecedorForm.patchValue({
              logradouro: data.logradouro,
              bairro: data.bairro,
              localidade: data.localidade,
              uf: data.uf,
              // O campo 'estado' não vem diretamente do ViaCEP, precisaria de um mapeamento
              // Por enquanto, vamos deixar a critério do usuário preencher ou buscar externamente se necessário.
            });
            // Opcional: Desabilitar campos de endereço se vierem do CEP
            // this.fornecedorForm.get('logradouro')?.disable();
          } else {
            this.cepError = 'CEP não encontrado.';
            this.clearAddressFields();
          }
          this.cepLoading = false;
        },
        error: (error) => {
          console.error('Erro ao buscar CEP:', error);
          this.cepError = 'Erro ao buscar CEP. Verifique o número e tente novamente.';
          this.clearAddressFields();
          this.cepLoading = false;
        }
      });
    } else {
      this.clearAddressFields();
      this.cepError = null;
    }
  }

  /**
   * Limpa os campos de endereço no formulário.
   */
  clearAddressFields(): void {
    this.fornecedorForm.patchValue({
      logradouro: '',
      numero: '',
      complemento: '',
      bairro: '',
      localidade: '',
      uf: '',
      estado: '',
    });
  }

  /**
   * Marca todos os campos do formulário como 'touched' para exibir mensagens de validação.
   */
  markAllAsTouched(formGroup: FormGroup) {
    Object.values(formGroup.controls).forEach(control => {
      control.markAsTouched();
      if (control instanceof FormGroup) {
        this.markAllAsTouched(control);
      }
    });
  }

  /**
   * Lida com o envio do formulário.
   */
  onSubmit(): void {
    this.markAllAsTouched(this.fornecedorForm);
    if (this.fornecedorForm.invalid) {
      this.errorMessage = 'Por favor, corrija os erros no formulário.';
      return;
    }

    this.isSaving = true;
    this.errorMessage = null;

    // Habilita temporariamente campos desabilitados para que seus valores sejam incluídos
    // const originalState = {};
    // Object.keys(this.fornecedorForm.controls).forEach(key => {
    //   if (this.fornecedorForm.controls[key].disabled) {
    //     originalState[key] = true;
    //     this.fornecedorForm.controls[key].enable();
    //   }
    // });

    const fornecedorData: FornecedorRequest = this.fornecedorForm.value;

    let operation: Observable<FornecedorResponse>;

    if (this.isEditMode && this.fornecedorId) {
      operation = this.fornecedorService.updateFornecedor(this.fornecedorId, fornecedorData);
    } else {
      operation = this.fornecedorService.createFornecedor(fornecedorData);
    }

    operation.subscribe({
      next: (response) => {
        this.isSaving = false;
        this.formSubmitted.emit(response);
        alert(`Fornecedor ${this.isEditMode ? 'atualizado' : 'cadastrado'} com sucesso!`);
        this.router.navigate(['/fornecedores']); // Redireciona para a lista
      },
      error: (error: HttpErrorResponse) => {
        this.isSaving = false;
        console.error('Erro ao salvar fornecedor:', error);
        // Exemplo de tratamento de erro mais específico do backend
        if (error.error && error.error.message) {
          this.errorMessage = `Erro ao salvar fornecedor: ${error.error.message}`;
        } else {
          this.errorMessage = 'Ocorreu um erro inesperado ao salvar o fornecedor. Tente novamente.';
        }
        // Re-desabilita campos se necessário
        // Object.keys(originalState).forEach(key => {
        //   if (originalState[key]) {
        //     this.fornecedorForm.controls[key].disable();
        //   }
        // });
      }
    });
  }

  /**
   * Cancela a operação e retorna para a lista.
   */
  onCancel(): void {
    this.formCanceled.emit();
    this.router.navigate(['/fornecedores']);
  }
}