
CREATE TABLE tb_endereco (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    cep VARCHAR(255) NOT NULL,
    logradouro VARCHAR(255),
    complemento VARCHAR(255),
    numero VARCHAR(255),
    bairro VARCHAR(255),
    localidade VARCHAR(255),
    uf VARCHAR(255),
    estado VARCHAR(255)
);


CREATE TABLE tb_empresa (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    cnpj VARCHAR(15) UNIQUE NOT NULL,
    nome_fantasia VARCHAR(120),
    endereco_id BIGINT,
    FOREIGN KEY (endereco_id) REFERENCES tb_endereco(id)
);


CREATE TABLE tb_fornecedor (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    cnpj_ou_cpf VARCHAR(15) UNIQUE NOT NULL,
    nome VARCHAR(120) NOT NULL,
    email VARCHAR(120),
    rg VARCHAR(20),
    data_nascimento DATE,
    endereco_id BIGINT,
    FOREIGN KEY (endereco_id) REFERENCES tb_endereco(id)
);


CREATE TABLE tb_empresa_fornecedor (
    empresa_id BIGINT NOT NULL,
    fornecedor_id BIGINT NOT NULL,
    PRIMARY KEY (empresa_id, fornecedor_id),
    FOREIGN KEY (empresa_id) REFERENCES tb_empresa(id),
    FOREIGN KEY (fornecedor_id) REFERENCES tb_fornecedor(id)
);