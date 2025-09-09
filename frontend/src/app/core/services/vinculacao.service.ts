import { FornecedorEmpresaResponseDto } from './../interfaces/fornecedor-empresa-response-dto';
import { FornecedorEmpresaRequestDto } from './../interfaces/fornecedor-empresa-request-dto';
// src/app/core/services/vinculacao.service.ts

import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment'; 


@Injectable({
  providedIn: 'root'
})
export class VinculacaoService {
  private apiUrl = `${environment.apiUrl}/fornecedor-empresa`; // Base da URL da API de vinculação

  constructor(private http: HttpClient) { }

  /**
   * Vincula um fornecedor a uma empresa.
   * @param vinculacaoRequest DTO com os IDs do fornecedor e da empresa.
   * @returns Um Observable com a resposta da operação de vinculação.
   */
  vincularFornecedorEmpresa(vinculacaoRequest: FornecedorEmpresaRequestDto): Observable<FornecedorEmpresaResponseDto> {
    return this.http.post<FornecedorEmpresaResponseDto>(`${this.apiUrl}/vincular`, vinculacaoRequest);
  }

  /**
   * Desvincula um fornecedor de uma empresa.
   * @param fornecedorId O ID do fornecedor a ser desvinculado.
   * @param empresaId O ID da empresa.
   * @returns Um Observable indicando a conclusão da operação (normalmente um `void` ou um objeto de sucesso).
   */
  desvincularFornecedorEmpresa(fornecedorId: number, empresaId: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/desvincular/${fornecedorId}/${empresaId}`);
  }
}