// src/app/core/services/empresa.service.ts
import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment'; // Assumindo que você tem um arquivo environment.ts
import { EmpresaResponse } from '../interfaces/empresa-response';
import { EmpresaRequest } from '../interfaces/empresa-request';

@Injectable({
  providedIn: 'root'
})
export class EmpresaService {
  private apiUrl = `${environment.apiUrl}/empresas`; // URL base da API de empresas

  constructor(private http: HttpClient) { }

  /**
   * Cria uma nova empresa.
   * @param empresa Os dados da empresa a ser criada.
   * @returns Um Observable com a empresa criada.
   */
  createEmpresa(empresa: EmpresaRequest): Observable<EmpresaResponse> {
    return this.http.post<EmpresaResponse>(this.apiUrl, empresa);
  }

  /**
   * Obtém todas as empresas com paginação.
   * @param page O número da página (base 0).
   * @param size O tamanho da página.
   * @returns Um Observable com a lista paginada de empresas.
   */
  getEmpresas(page: number = 0, size: number = 10): Observable<any> {
    const params = new HttpParams()
      .set('page', page.toString())
      .set('size', size.toString());
    return this.http.get<any>(this.apiUrl, { params });
  }

  /**
   * Obtém uma empresa pelo seu ID.
   * @param id O ID da empresa.
   * @returns Um Observable com os dados da empresa.
   */
  getEmpresaById(id: number): Observable<EmpresaResponse> {
    return this.http.get<EmpresaResponse>(`${this.apiUrl}/${id}`);
  }

  /**
   * Obtém uma empresa pelo seu CNPJ.
   * @param cnpj O CNPJ da empresa.
   * @returns Um Observable com os dados da empresa.
   */
  getEmpresaByCnpj(cnpj: string): Observable<EmpresaResponse> {
    return this.http.get<EmpresaResponse>(`${this.apiUrl}/cnpj/${cnpj}`);
  }

  /**
   * Atualiza uma empresa existente.
   * @param id O ID da empresa a ser atualizada.
   * @param empresa Os novos dados da empresa.
   * @returns Um Observable com a empresa atualizada.
   */
  updateEmpresa(id: number, empresa: EmpresaRequest): Observable<EmpresaResponse> {
    return this.http.put<EmpresaResponse>(`${this.apiUrl}/${id}`, empresa);
  }

  /**
   * Exclui uma empresa pelo seu ID.
   * @param id O ID da empresa a ser excluída.
   * @returns Um Observable que completa quando a exclusão é bem-sucedida.
   */
  deleteEmpresa(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }
}