import { EmpresaResponse } from './../../../core/interfaces/empresa-response';
import { EmpresaRequest } from './../../../core/interfaces/empresa-request';
import { CepService } from './../../../core/services/cep.service';
import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { FormBuilder, FormGroup, Validators, AbstractControl } from '@angular/forms';
import { EmpresaService } from '../../../core/services/empresa.service'; 
import { take } from 'rxjs/operators'; // Para gerenciar subscriptions
import { Observable } from 'rxjs';

@Component({
  selector: 'app-empresa-cadastro',
  standalone: false,
  templateUrl: './empresa-cadastro.component.html',
  styleUrl: './empresa-cadastro.component.css'
})
export class EmpresaCadastroComponent {

  @Input() empresaId: number | null = null; // Para modo de edição
  @Output() formSubmitted = new EventEmitter<EmpresaResponse>();
  @Output() formCanceled = new EventEmitter<void>();

  empresaForm!: FormGroup; // 
  isEditing: boolean = false;
  isSaving: boolean = false;
  cepLoading: boolean = false;
  cepError: string | null = null;

  constructor(
    private fb: FormBuilder,
    private empresaService: EmpresaService,
    private cepService: CepService
  ) { }

  ngOnInit(): void {
    this.initializeForm();

    if (this.empresaId) {
      this.isEditing = true;
      this.loadEmpresaForEditing(this.empresaId);
    }
  }

  private initializeForm(): void {
    this.empresaForm = this.fb.group({
      cnpj: ['', [Validators.required, Validators.pattern(/^\d{14}$/)]], // Apenas números, 14 dígitos
      nomeFantasia: ['', Validators.required],
      cep: ['', [Validators.required, Validators.pattern(/^\d{8}$/)]], // Apenas números, 8 dígitos
      logradouro: [{ value: '', disabled: true }, Validators.required], // Desabilitado e required
      numero: ['', Validators.required],
      complemento: [''], // Opcional
      bairro: [{ value: '', disabled: true }, Validators.required], // Desabilitado e required
      localidade: [{ value: '', disabled: true }, Validators.required], // Desabilitado e required
      uf: [{ value: '', disabled: true }, Validators.required] // Desabilitado e required
    });
  }

  private loadEmpresaForEditing(id: number): void {
    this.empresaService.getEmpresaById(id).pipe(take(1)).subscribe({
      next: (empresa: EmpresaResponse) => {
        // Habilita os campos de endereço temporariamente para preenchimento
        this.empresaForm.get('logradouro')?.enable();
        this.empresaForm.get('bairro')?.enable();
        this.empresaForm.get('localidade')?.enable();
        this.empresaForm.get('uf')?.enable();

        // Preenche o formulário com os dados da empresa e endereço
        this.empresaForm.patchValue({
          cnpj: empresa.cnpj,
          nomeFantasia: empresa.nomeFantasia,
          cep: empresa.endereco.cep,
          logradouro: empresa.endereco.logradouro,
          numero: empresa.endereco.numero,
          complemento: empresa.endereco.complemento,
          bairro: empresa.endereco.bairro,
          localidade: empresa.endereco.localidade,
          uf: empresa.endereco.uf
        });

        // Desabilita novamente os campos que são preenchidos pelo CEP
        this.empresaForm.get('logradouro')?.disable();
        this.empresaForm.get('bairro')?.disable();
        this.empresaForm.get('localidade')?.disable();
        this.empresaForm.get('uf')?.disable();
      },
      error: (error) => {
        console.error('Erro ao carregar empresa para edição:', error);
        alert('Não foi possível carregar os dados da empresa para edição.');
        this.formCanceled.emit(); // Ou redirecionar
      }
    });
  }

  onCepBlur(): void {
    const cepControl = this.empresaForm.get('cep');
    const cep = cepControl?.value;

    this.cepError = null; // Limpa erros anteriores
    // Só busca se o CEP for válido e não estiver vazio
    if (cepControl?.valid && cep) {
      this.cepLoading = true;
      // Desabilita os campos de endereço antes de buscar
      this.setAddressFieldsEnabled(false);

      this.cepService.buscarEnderecoPorCep(cep).pipe(take(1)).subscribe({
        next: (data) => {
          this.cepLoading = false;
          // Habilita os campos para preencher, caso não estivessem habilitados pelo patchValue
          this.setAddressFieldsEnabled(true);
          this.empresaForm.patchValue({
            logradouro: data.logradouro,
            bairro: data.bairro,
            localidade: data.localidade,
            uf: data.uf,
            complemento: data.complemento // O complemento da ViaCEP pode ser preenchido aqui
          });
          // Desabilita novamente os campos após preenchimento (se não forem editáveis manualmente)
          this.setAddressFieldsEnabled(false);
          // O campo 'numero' é manual, então não o desabilitamos após o CEP.
          // Certifica-se de que o 'numero' é re-habilitado se foi desabilitado por algum motivo.
          this.empresaForm.get('numero')?.enable();
        },
        error: (err) => {
          this.cepLoading = false;
          this.cepError = err.message || 'Erro ao buscar CEP.';
          // Limpa e habilita campos de endereço para preenchimento manual caso erro ou não encontrado
          this.clearAddressFields();
          this.setAddressFieldsEnabled(true); // Habilita para possível preenchimento manual
          this.empresaForm.get('numero')?.enable(); // Garante que numero está habilitado
        }
      });
    } else if (cepControl?.invalid && cepControl?.touched) {
      this.clearAddressFields();
      this.setAddressFieldsEnabled(true);
      this.empresaForm.get('numero')?.enable();
    }
  }

  private clearAddressFields(): void {
    this.empresaForm.patchValue({
      logradouro: '',
      numero: '',
      complemento: '',
      bairro: '',
      localidade: '',
      uf: ''
    });
  }

  private setAddressFieldsEnabled(enable: boolean): void {
    const fields = ['logradouro', 'bairro', 'localidade', 'uf'];
    fields.forEach(field => {
      const control = this.empresaForm.get(field);
      if (control) {
        enable ? control.enable() : control.disable();
      }
    });
  }

  onSubmit(): void {
    // Re-habilita todos os campos desabilitados para que seus valores sejam incluídos no .value
    this.setAddressFieldsEnabled(true);
    this.empresaForm.get('numero')?.enable(); // Garante que numero está habilitado

    if (this.empresaForm.valid) {
      this.isSaving = true;
      const empresaData: EmpresaRequest = this.empresaForm.value;

      let operation$: Observable<EmpresaResponse>;

      if (this.isEditing && this.empresaId) {
        operation$ = this.empresaService.updateEmpresa(this.empresaId, empresaData);
      } else {
        operation$ = this.empresaService.createEmpresa(empresaData);
      }

      operation$.pipe(take(1)).subscribe({
        next: (response) => {
          console.log('Operação de empresa bem-sucedida:', response);
          this.isSaving = false;
          this.formSubmitted.emit(response);
          // Opcional: Resetar formulário ou redirecionar
          this.empresaForm.reset();
        },
        error: (error) => {
          console.error('Erro na operação de empresa:', error);
          this.isSaving = false;
          alert('Ocorreu um erro ao salvar a empresa. Verifique o console para detalhes.');
          // Re-desabilita campos que são preenchidos por CEP, após a tentativa de salvar
          this.setAddressFieldsEnabled(false);
          this.empresaForm.get('numero')?.enable();
        }
      });
    } else {
      console.log('Formulário inválido. Verifique os campos.');
      // Marca todos os campos como 'touched' para exibir mensagens de validação
      this.markAllAsTouched(this.empresaForm);
      // Re-desabilita campos que são preenchidos por CEP, caso tenham sido habilitados pelo onSubmit
      this.setAddressFieldsEnabled(false);
      this.empresaForm.get('numero')?.enable();
    }
  }

  onCancel(): void {
    this.formCanceled.emit();
  }

  // Função utilitária para marcar todos os campos como "touched"
  private markAllAsTouched(formGroup: FormGroup | AbstractControl): void {
    if (formGroup instanceof FormGroup) {
      Object.values(formGroup.controls).forEach(control => {
        control.markAsTouched();
        if (control instanceof FormGroup) {
          this.markAllAsTouched(control);
        }
      });
    } else {
      formGroup.markAsTouched();
    }
  }

  

}
