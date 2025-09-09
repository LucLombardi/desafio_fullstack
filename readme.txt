# 🏢 Sistema de Gestão Empresarial Full-Stack

> **Sistema completo para gerenciamento de empresas, fornecedores e seus relacionamentos**

[![Java]
[![Spring Boot]
[![Angular]
[![MySQL]
[![License]

## 📋 Visão Geral

Sistema full-stack desenvolvido para o **Desafio Accenture**, oferecendo uma solução completa para gerenciamento de empresas e fornecedores com interface web moderna e API REST robusta.

### 🎯 Principais Características
- **Frontend Angular** com interface intuitiva e responsiva
- **Backend Spring Boot** com API REST documentada
- **Integração ViaCEP** para consulta automática de endereços
- **Validações avançadas** e regras de negócio específicas
- **Sistema de paginação** para grandes volumes de dados
- **Relacionamentos complexos** entre entidades

---

## 🏗️ Arquitetura do Sistema

```mermaid
graph TB
    subgraph "Frontend - Angular"
        A[Interface Web] --> B[Componentes]
        B --> C[Serviços]
        C --> D[HTTP Client]
    end
    
    subgraph "Backend - Spring Boot"
        E[Controllers] --> F[Services]
        F --> G[Repositories]
        G --> H[Database]
    end
    
    subgraph "Serviços Externos"
        I[ViaCEP API]
    end
    
    D --> E
    F --> I
    
    style A fill:#e1f5fe
    style E fill:#f3e5f5
    style H fill:#fff3e0
    style I fill:#e8f5e8
```

### 📁 Estrutura do Projeto

```
📦 desafio-fullstack/
├── 🗂️ backend/                      # Aplicação Spring Boot
│   ├── 📂 src/main/java/
│   │   └── 📂 com/accenture/desafio_fullstack/app/
│   │       ├── 🔌 client/           # Clientes Feign (ViaCEP)
│   │       ├── ⚙️ config/           # Configurações (CORS, Swagger)
│   │       ├── 🎮 controller/       # REST Controllers
│   │       ├── 📋 dto/              # Data Transfer Objects
│   │       ├── 🏢 model/            # Entidades JPA
│   │       ├── 📊 repository/       # Repositórios de Dados
│   │       ├── 🔧 service/          # Lógica de Negócio
│   │       └── ⚠️ exception/        # Tratamento de Exceções
│   ├── 📂 src/main/resources/
│   │   ├── 📄 application.yml       # Configurações
│   │   └── 🗃️ db/migration/        # Scripts Flyway
│   └── 📄 pom.xml
├── 🗂️ frontend/                     # Aplicação Angular
│   ├── 📂 src/app/
│   │   ├── 🧩 components/           # Componentes UI
│   │   │   ├── 📝 cadastro/         # Formulários CRUD
│   │   │   ├── 🏠 home/             # Página Inicial
│   │   │   ├── 🧭 nav-bar/          # Navegação
│   │   │   └── 🦶 footer/           # Rodapé
│   │   ├── 🔧 core/
│   │   │   ├── 🔗 interfaces/       # Interfaces TypeScript
│   │   │   └── 🛠️ services/         # Serviços HTTP
│   │   └── 🤝 shared/              # Tipos Compartilhados
│   ├── 📂 src/environments/         # Configurações
│   └── 📄 package.json
└── 📖 README.md
```

---

## 🚀 Stack Tecnológica



### Backend
![Java]
![Spring Boot]
![MySQL]
![Maven]

### Frontend
![Angular]
![TypeScript]
![HTML5]
![CSS3]



### 🛠️ Tecnologias Detalhadas

| Categoria | Backend | Frontend |
|-----------|---------|----------|
| **Core** | Java 17+, Spring Boot 3.x | Angular 18+, TypeScript |
| **Database** | MySQL 8, Spring Data JPA | - |
| **HTTP** | Spring Web, OpenFeign | HttpClient, RxJS |
| **Validação** | Bean Validation, Hibernate Validator | Angular Reactive Forms |
| **Documentação** | Swagger/OpenAPI 3 | TypeDoc |
| **Build** | Maven | Angular CLI, npm |
| **Migration** | Flyway | - |
| **Outros** | Lombok, HATEOAS | Angular Router, FormsModule |

---

## 🎯 Funcionalidades do Sistema

### 🏢 **Gestão de Empresas**
- ✅ Cadastro com validação de CNPJ
- ✅ Listagem paginada com busca
- ✅ Edição e exclusão segura
- ✅ Consulta automática de CEP
- ✅ Visualização de fornecedores vinculados

### 👥 **Gestão de Fornecedores**
- ✅ Cadastro PF/PJ com validações específicas
- ✅ Listagem com filtros avançados
- ✅ Busca por nome, CPF ou CNPJ
- ✅ Validação de idade para regra do Paraná
- ✅ Preenchimento automático de endereço

### 🔗 **Sistema de Relacionamentos**
- ✅ Vinculação empresa-fornecedor
- ✅ Desvinculação com confirmação
- ✅ Regras de negócio automatizadas
- ✅ Visualização de relacionamentos

### 🌐 **Integrações Externas**
- ✅ API ViaCEP para consulta de endereços
- ✅ Validação de CEP em tempo real
- ✅ Tratamento de erros de conectividade

---

## ⚡ Quick Start

### 📋 Pré-requisitos

```bash
# Verificar versões
java --version     # Java 17+
node --version     # Node.js 18+
npm --version      # npm 9+
mysql --version    # MySQL 8.0+
```

### 🗄️ **1. Preparação do Banco**

```sql
-- Conectar no MySQL e executar
CREATE DATABASE desafio_fullstack CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

### 🔧 **2. Configuração Backend**

```bash
# Clonar e configurar
git clone <repository-url>
cd desafio-fullstack/backend

# Configurar banco em src/main/resources/application-dev.yml
spring:
  datasource:
    username: seu_usuario
    password: sua_senha
    
# Executar aplicação
mvn spring-boot:run
```

✅ **Backend rodando em:** `http://localhost:8080`

### 🎨 **3. Configuração Frontend**

```bash
# Em nova aba do terminal
cd ../frontend

# Instalar dependências
npm install

# Executar aplicação
npm start
```

✅ **Frontend rodando em:** `http://localhost:4200`

### 🎉 **4. Primeira Execução**

1. **Abra** `http://localhost:4200`
2. **Navegue** pelo menu superior
3. **Teste** o cadastro de uma empresa
4. **Explore** todas as funcionalidades!

---

## 📖 Documentação da API

### 🔗 **Endpoints Principais**

👔 Empresas

```http
GET    /api/empresas                    # Listar com paginação
POST   /api/empresas                    # Criar nova empresa
GET    /api/empresas/{id}               # Buscar por ID
PUT    /api/empresas/{id}               # Atualizar empresa
DELETE /api/empresas/{id}               # Excluir empresa
GET    /api/empresas/cnpj/{cnpj}        # Buscar por CNPJ
GET    /api/empresas/{id}/fornecedores  # Fornecedores vinculados
```
👤 Fornecedores
```http
GET    /api/fornecedores                # Listar com paginação
POST   /api/fornecedores                # Criar fornecedor
GET    /api/fornecedores/id/{id}        # Buscar por ID
PUT    /api/fornecedores/{id}           # Atualizar fornecedor
DELETE /api/fornecedores/{id}           # Excluir fornecedor
GET    /api/fornecedores/nome/{nome}    # Buscar por nome
GET    /api/fornecedores/cpf/{cpf}      # Buscar por CPF/CNPJ
```

🔗 Relacionamentos
```http
POST   /api/fornecedor-empresa/vincular                    # Vincular
DELETE /api/fornecedor-empresa/desvincular/{fId}/{eId}     # Desvincular
```


### 📚 **Swagger UI**
Acesse a documentação interativa: **http://localhost:8080/swagger-ui.html**

---

## 🖥️ Interface do Sistema

### 📱 **Páginas Disponíveis**

| Rota | Componente | Descrição |
|------|------------|-----------|
| `/` | Home | Dashboard inicial |
| `/empresas` | Lista Empresas | Listagem paginada |
| `/empresas/cadastro` | Cadastro Empresa | Formulário de criação |
| `/empresas/cadastro/:id` | Edição Empresa | Formulário de edição |
| `/fornecedores` | Lista Fornecedores | Listagem paginada |
| `/fornecedores/cadastro` | Cadastro Fornecedor | Formulário de criação |
| `/fornecedores/cadastro/:id` | Edição Fornecedor | Formulário de edição |
| `/vinculacao` | Relacionamentos | Gestão de vínculos |

### ✨ **Recursos da Interface**

- 📱 **Design Responsivo** - Funciona perfeitamente em desktop e mobile
- 🔍 **Busca Inteligente** - Filtros por CNPJ, nome e outros campos
- 📄 **Paginação Avançada** - Navegação eficiente em grandes listas
- ✅ **Validação Real-time** - Feedback imediato em formulários
- 🌐 **Auto-complete CEP** - Integração automática com ViaCEP
- 🔄 **Loading States** - Indicadores visuais durante operações
- ⚠️ **Modais de Confirmação** - Proteção para ações críticas
- 🎨 **Interface Moderna** - Design clean e profissional

---

## 🧪 Exemplos Práticos

### 📝 **Cadastro de Empresa via API**

```json
POST /api/empresas
Content-Type: application/json

{
  "cnpj": "12345678000195",
  "nomeFantasia": "TechCorp Solutions Ltda",
  "cep": "01310100",
  "logradouro": "Av. Paulista",
  "numero": "1000",
  "complemento": "Conjunto 101",
  "bairro": "Bela Vista",
  "localidade": "São Paulo",
  "uf": "SP"
}
```

### 👤 **Cadastro de Fornecedor PF**

```json
POST /api/fornecedores
Content-Type: application/json

{
  "tipoPessoa": "FISICA",
  "nome": "Maria Silva Santos",
  "email": "maria.silva@email.com",
  "cnpjOuCpf": "12345678901",
  "rg": "123456789",
  "dataNascimento": "1985-03-15",
  "cep": "04567890",
  "logradouro": "Rua das Palmeiras",
  "numero": "456",
  "complemento": "Apt 203",
  "bairro": "Vila Madalena",
  "localidade": "São Paulo",
  "uf": "SP",
  "estado": "São Paulo"
}
```

### 🔗 **Vinculação Fornecedor-Empresa**

```json
POST /api/fornecedor-empresa/vincular
Content-Type: application/json

{
  "fornecedorId": 1,
  "empresaId": 1
}
```

---

## ⚙️ Configurações Avançadas

### 🔧 **Configuração de Desenvolvimento**

<details>
<summary><b>Backend - application-dev.yml</b></summary>

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/desafio_fullstack?allowPublicKeyRetrieval=true&createDatabaseIfNotExist=true&useSSL=false&serverTimezone=UTC
    username: root
    password: sua_senha
    driver-class-name: com.mysql.cj.jdbc.Driver

  jpa:
    hibernate:
      ddl-auto: update
    show-sql: true
    properties:
      hibernate:
        dialect: org.hibernate.dialect.MySQL8Dialect

  flyway:
    baseline-on-migrate: true
    locations: classpath:db/migration

  data:
    web:
      pageable:
        default-page-size: 10
        max-page-size: 100

server:
  port: 8080
```

</details>

<details>
<summary><b>Frontend - environment.ts</b></summary>

```typescript
export const environment = {
  production: false,
  apiUrl: 'http://localhost:8080/api',
  debugMode: true,
  version: '1.0.0'
};
```

</details>

### 🌐 **Configuração CORS**

O backend está configurado para aceitar requisições do frontend:

```java
@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")
                .allowedOriginPatterns("*")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH")
                .allowedHeaders("*")
                .allowCredentials(true)
                .maxAge(3600);
    }
}
```

---

## 📊 Regras de Negócio

### 🏢 **Empresas**
- CNPJ deve ser válido e único
- Nome fantasia é obrigatório
- Endereço completo é necessário
- Não pode ser excluída se tiver fornecedores vinculados

### 👤 **Fornecedores**
- CPF/CNPJ deve ser válido e único
- Pessoa Física: RG e data nascimento obrigatórios
- Pessoa Jurídica: RG e data nascimento opcionais
- Não pode ser excluído se tiver empresas vinculadas

### 🔗 **Relacionamentos**
- **Regra do Paraná**: Empresas do PR só podem vincular fornecedores PF maiores de idade
- Um fornecedor pode estar vinculado a múltiplas empresas
- Uma empresa pode ter múltiplos fornecedores

---

## 🚀 Deploy e Produção

### 📦 **Build para Produção**

<details>
<summary><b>Backend</b></summary>

```bash
# Build da aplicação
mvn clean package -DskipTests

# Executar com profile de produção
java -jar -Dspring.profiles.active=prod target/desafio-fullstack-1.0.0.jar
```

</details>

<details>
<summary><b>Frontend</b></summary>

```bash
# Build otimizado para produção
npm run build --configuration=production

# Arquivos gerados em dist/ prontos para deploy
# Pode ser servido por Nginx, Apache, ou qualquer servidor web
```

</details>

### 🐳 **Docker (Opcional)**

Dockerfile Backend
```dockerfile
FROM openjdk:17-jdk-slim

WORKDIR /app

COPY target/desafio-fullstack-1.0.0.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
```
Dockerfile Frontend

```dockerfile
FROM node:22-alpine AS build

WORKDIR /app
COPY package*.json ./
RUN npm ci --only=production

COPY . .
RUN npm run build --configuration=production

FROM nginx:alpine
COPY --from=build /app/dist/frontend /usr/share/nginx/html
COPY nginx.conf /etc/nginx/nginx.conf

EXPOSE 80
```



---

## 🔍 Troubleshooting

### ❗ **Problemas Comuns**

Erro de Conexão MySQL

```bash
# Verificar se o MySQL está rodando
systemctl status mysql  # Linux
brew services start mysql  # macOS

# Verificar credenciais no application-dev.yml
# Criar banco se não existir
mysql -u root -p -e "CREATE DATABASE desafio_fullstack;"
```

Erro CORS no Frontend

```bash
# Verificar se backend está rodando na porta 8080
curl http://localhost:8080/api/empresas

# Verificar configuração em environment.ts
# Limpar cache do navegador
```


Erro de Dependências Node

```bash
# Limpar cache e reinstalar
rm -rf node_modules package-lock.json
npm cache clean --force
npm install
```

</details>

### 📞 **Suporte**

Se encontrar problemas:

1. Verifique os logs da aplicação
2. Consulte a documentação do Swagger
3. Abra uma issue no repositório
4. Entre em contato: contato@accenture.com

---

## 🧪 Testes

### 🔬 **Executando Testes**

```bash
# Backend - Testes unitários
cd backend
mvn test

# Frontend - Testes unitários
cd frontend
npm test

# Frontend - Testes e2e
npm run e2e
```

### 📈 **Coverage**

```bash
# Gerar relatório de cobertura
mvn jacoco:report  # Backend
npm run test:coverage  # Frontend
```

---

## 🤝 Como Contribuir

### 1️⃣ **Fork e Clone**
```bash
git clone https://github.com/seu-usuario/desafio-fullstack.git
cd desafio-fullstack
```

### 2️⃣ **Criar Branch**
```bash
git checkout -b feature/minha-nova-feature
```

### 3️⃣ **Fazer Alterações**
- **Backend**: Siga padrões Spring Boot e Clean Code
- **Frontend**: Use Angular Style Guide
- **Commits**: Mensagens claras e descritivas

### 4️⃣ **Testar**
```bash
# Testar backend
cd backend && mvn test

# Testar frontend
cd frontend && npm test
```

### 5️⃣ **Submeter PR**
```bash
git add .
git commit -m "feat: adiciona nova funcionalidade X"
git push origin feature/minha-nova-feature
```

### 📋 **Checklist do PR**
- [ ] Código testado e funcionando
- [ ] Documentação atualizada
- [ ] Padrões de código seguidos
- [ ] Sem breaking changes
- [ ] Testes passando

---

## 📈 Status do Projeto

| Módulo | Status | Versão |
|--------|--------|---------|
| 🔧 Backend API | ✅ Completo | 1.0.0 |
| 🎨 Frontend Web | ✅ Completo | 1.0.0 |
| 🔗 Integração | ✅ Completo | - |
| 📚 Documentação | ✅ Completo | - |
| 🐳 Docker | ⏳ Planejado | - |
| 🚀 CI/CD | ⏳ Planejado | - |

---

## 📚 Recursos e Links

### 🔗 **Links do Projeto**
- 🌐 **Frontend:** http://localhost:4200
- 🔧 **Backend:** http://localhost:8080
- 📖 **API Docs:** http://localhost:8080/swagger-ui.html
- 📋 **OpenAPI:** http://localhost:8080/v3/api-docs

### 📖 **Documentação Oficial**
- [Spring Boot](https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/)
- [Angular](https://angular.io/docs)
- [MySQL](https://dev.mysql.com/doc/)
- [ViaCEP API](https://viacep.com.br/)

### 🛠️ **Ferramentas Úteis**
- [Postman Collection](docs/postman-collection.json)
- [Insomnia Workspace](docs/insomnia-workspace.json)
- [Database Schema](docs/database-schema.png)

---

## 📄 Licença

Este projeto está licenciado sob a **MIT License** - veja o arquivo [LICENSE](LICENSE) para detalhes.

```
MIT License

Copyright (c) 2024 Accenture

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software")...
```

---

## 👥 Equipe


### 🏢 **Desenvolvido para Accenture**

**Desafio Full-Stack 2024**

**Contato:** contato@accenture.com  
**Website:** [accenture.com](https://www.accenture.com)

---

### ⭐ **Se este projeto foi útil, considere dar uma estrela!**

[![GitHub stars](https://img.shields.io/github/stars/accenture/desafio-fullstack?style=social)](https://github.com/accenture/desafio-fullstack)


---

