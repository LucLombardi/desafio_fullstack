export interface Endereco {
   id?: number; 
  cep: string;
  logradouro: string;
  numero: string;
  complemento?: string; 
  bairro: string;
  localidade: string;
  uf: string;
  estado?: string; 
}
