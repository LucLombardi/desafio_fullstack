import { TipoPessoa } from "../../shared/type/pessoa";

export interface FornecedorRequest {
    tipoPessoa: string;
    nome: string;
    email: string;
    cnpjOuCpf: string;
    rg?: string | null;
    dataNascimento?: string | null;
    cep: string;
    logradouro: string;
    numero: string;
    complemento?: string | null;
    bairro: string;
    localidade: string;
    uf: string;
    estado: string;
}
