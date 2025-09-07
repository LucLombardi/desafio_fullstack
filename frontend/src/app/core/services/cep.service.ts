// src/app/core/services/cep.service.ts
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError, map } from 'rxjs/operators';
import { CepResponse } from '../interfaces/cep-response';

@Injectable({
  providedIn: 'root'
})
export class CepService {
  
  private viaCepApiUrl = 'https://viacep.com.br/ws';

  constructor(private http: HttpClient) { }

  /**
   * Busca um endereço completo a partir de um CEP utilizando a API ViaCEP.
   * @param cep O CEP a ser consultado (apenas dígitos).
   * @returns Um Observable com os dados do endereço.
   */
  buscarEnderecoPorCep(cep: string): Observable<CepResponse> {
    const cleanedCep = cep.replace(/\D/g, ''); 
    if (cleanedCep.length !== 8) {
      return throwError(() => new Error('CEP inválido. Deve conter 8 dígitos numéricos.'));
    }

    return this.http.get<CepResponse>(`${this.viaCepApiUrl}/${cleanedCep}/json`).pipe(
      map(response => {
        if (response.erro) { 
          throw new Error('CEP não encontrado.');
        }
        return response;
      }),
      catchError(error => {
        console.error('Erro ao consultar ViaCEP:', error);
        return throwError(() => new Error('Não foi possível consultar o CEP. Tente novamente mais tarde.'));
      })
    );
  }
}