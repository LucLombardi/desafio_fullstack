import { Endereco } from "./endereco";

export interface EmpresaResponse {
    id: number;
    cnpj: string;
    nomeFantasia: string;
    endereco: Endereco;
    _links?: any; 
}
