import { TipoPessoa } from "../../shared/type/pessoa";
import { Endereco } from "./endereco";

export interface FornecedorResponse {
id: number;
  tipoPessoa: TipoPessoa;
  nome: string;
  email: string;
  cnpjOuCpf: string;
  rg?: string | null;
  dataNascimento?: string | null; 
  endereco: Endereco;
  _links?: any; 

}
