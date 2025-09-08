import { HttpClient, HttpParams } from "@angular/common/http";
import { environment } from "../../../environments/environment";
import { FornecedorRequest } from "../interfaces/fornecedor-request";
import { FornecedorResponse } from "../interfaces/fornecedor-response";
import { Observable } from "rxjs";
import { Injectable } from "@angular/core";
import { Page } from "../../shared/type/pages";

@Injectable({
  providedIn: 'root'
})
export class FornecedorService {
  private apiUrl = `${environment.apiUrl}/fornecedores`; 

  constructor(private http: HttpClient) { }

  /**
   * Cria um novo fornecedor.
   * @param fornecedor Os dados do fornecedor a serem criados.
   * @returns Um Observable com o FornecedorResponse do fornecedor criado.
   */
  createFornecedor(fornecedor: FornecedorRequest): Observable<FornecedorResponse> {
    return this.http.post<FornecedorResponse>(this.apiUrl, fornecedor);
  }

  /**
   * Obtém uma lista paginada de fornecedores, com opção de filtro por nome.
   * @param page O número da página (baseado em zero).
   * @param size O número de elementos por página.
   * @param nome O nome para filtrar a busca (opcional).
   * @returns Um Observable com a página de FornecedorResponse.
   */
  getFornecedores(page: number, size: number, nome?: string): Observable<Page<FornecedorResponse>> {
    let params = new HttpParams()
      .set('page', page.toString())
      .set('size', size.toString());

    if (nome) {
      params = params.set('nome', nome);
    }

    return this.http.get<Page<FornecedorResponse>>(`${this.apiUrl}/nome/${nome || ''}`, { params });
   
   
  }

  /**
   * Obtém um fornecedor pelo seu ID.
   * @param id O ID do fornecedor.
   * @returns Um Observable com o FornecedorResponse correspondente.
   */
  getFornecedorById(id: number): Observable<FornecedorResponse> {
    return this.http.get<FornecedorResponse>(`${this.apiUrl}/id/${id}`);
  }

  /**
   * Obtém um fornecedor pelo seu CNPJ ou CPF.
   * @param cnpjOuCpf O CNPJ ou CPF do fornecedor.
   * @returns Um Observable com o FornecedorResponse correspondente.
   */
  getFornecedorByCnpjOuCpf(cnpjOuCpf: string): Observable<FornecedorResponse> {
    return this.http.get<FornecedorResponse>(`${this.apiUrl}/cpf/${cnpjOuCpf}`);
  }

  /**
   * Atualiza um fornecedor existente.
   * @param id O ID do fornecedor a ser atualizado.
   * @param fornecedor Os dados atualizados do fornecedor.
   * @returns Um Observable com o FornecedorResponse do fornecedor atualizado.
   */
  updateFornecedor(id: number, fornecedor: FornecedorRequest): Observable<FornecedorResponse> {
    return this.http.put<FornecedorResponse>(`${this.apiUrl}/${id}`, fornecedor);
  }

  /**
   * Exclui um fornecedor pelo seu ID.
   * @param id O ID do fornecedor a ser excluído.
   * @returns Um Observable de void.
   */
  deleteFornecedor(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }
}