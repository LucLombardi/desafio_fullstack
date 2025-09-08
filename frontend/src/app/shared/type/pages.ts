import { FornecedorResponse } from "../../core/interfaces/fornecedor-response";




export interface PageMetadata {
  size: number; 
  totalElements: number; 
  totalPages: number; 
  number: number; 
 
}

export interface GenericEmbedded<T> {
  [key: string]: T[]; 
}

export interface Page<T> {
  _embedded?: GenericEmbedded<T>; 
  _links?: any; 
  page: PageMetadata; 
}
