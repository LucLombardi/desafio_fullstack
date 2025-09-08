import { TipoPessoa } from "../../shared/type/pessoa";

export interface FornecedorRequest {
    tipoPessoa: TipoPessoa;
    nome: string;
    email: string;
    cnpjOuCpf: string;
    rg?: string | null; // Opcional, apenas para pessoa física
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
