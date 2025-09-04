package com.accenture.desafio_fullstack.app.controller;

import com.accenture.desafio_fullstack.app.dto.FornecedorRequestDTO;
import com.accenture.desafio_fullstack.app.dto.FornecedorResponseDto;
import com.accenture.desafio_fullstack.app.service.FornecedorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/fornecedores")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
@Tag(name = "Fornecedores", description = "API para gerenciamento de fornecedores")
public class FornecedorController {

    private final FornecedorService fornecedorService;

    @PostMapping
    @Operation(summary = "Criar novo fornecedor", description = "Cria um novo fornecedor no sistema")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Fornecedor criado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos ou regra de negócio violada"),
        @ApiResponse(responseCode = "409", description = "CPF/CNPJ já cadastrado")
    })
    public ResponseEntity<FornecedorResponseDto> criarFornecedor(
            @Valid @RequestBody FornecedorRequestDTO fornecedorRequest) {
        
        FornecedorResponseDto fornecedorCriado = fornecedorService.criarFornecedor(fornecedorRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(fornecedorCriado);
    }

    @GetMapping
    @Operation(summary = "Listar fornecedores por nome", 
               description = "Lista fornecedores filtrados por nome (opcional) com paginação")
    @ApiResponse(responseCode = "200", description = "Lista de fornecedores retornada com sucesso")
    public ResponseEntity<Page<FornecedorResponseDto>> listarFornecedores(
            @RequestParam(required = false) 
            @Parameter(description = "Nome para filtrar (opcional)") String nome,
            @PageableDefault(size = 10, sort = "id") 
            @Parameter(description = "Parâmetros de paginação e ordenação") Pageable pageable) {
        
        Page<FornecedorResponseDto> fornecedores = fornecedorService.listNome(nome, pageable);
        return ResponseEntity.ok(fornecedores);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar fornecedor por ID", description = "Retorna um fornecedor específico pelo seu ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Fornecedor encontrado"),
        @ApiResponse(responseCode = "404", description = "Fornecedor não encontrado")
    })
    public ResponseEntity<FornecedorResponseDto> buscarFornecedorPorId(
            @PathVariable @Parameter(description = "ID do fornecedor") Long id) {
        
        FornecedorResponseDto fornecedor = fornecedorService.buscarFornecedorPorId(id);
        return ResponseEntity.ok(fornecedor);
    }

    @GetMapping("/buscar-por-documento/{cpfOrCnpj}")
    @Operation(summary = "Buscar fornecedor por CPF/CNPJ", 
               description = "Retorna um fornecedor específico pelo seu CPF ou CNPJ")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Fornecedor encontrado"),
        @ApiResponse(responseCode = "404", description = "Fornecedor não encontrado")
    })
    public ResponseEntity<FornecedorResponseDto> buscarFornecedorPorDocumento(
            @PathVariable @Parameter(description = "CPF ou CNPJ do fornecedor") String cpfOrCnpj) {
        
        FornecedorResponseDto fornecedor = fornecedorService.buscarCpfOrCnpj(cpfOrCnpj);
        return ResponseEntity.ok(fornecedor);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar fornecedor", description = "Atualiza os dados de um fornecedor existente")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Fornecedor atualizado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Fornecedor não encontrado"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos ou regra de negócio violada"),
        @ApiResponse(responseCode = "409", description = "CPF/CNPJ já cadastrado para outro fornecedor")
    })
    public ResponseEntity<FornecedorResponseDto> atualizarFornecedor(
            @PathVariable @Parameter(description = "ID do fornecedor") Long id,
            @Valid @RequestBody FornecedorRequestDTO fornecedorRequest) {
        
        FornecedorResponseDto fornecedorAtualizado = fornecedorService.atualizarFornecedor(id, fornecedorRequest);
        return ResponseEntity.ok(fornecedorAtualizado);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar fornecedor", description = "Remove um fornecedor do sistema")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Fornecedor deletado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Fornecedor não encontrado"),
        @ApiResponse(responseCode = "409", description = "Fornecedor possui relacionamentos que impedem a exclusão")
    })
    public ResponseEntity<Void> deletarFornecedor(
            @PathVariable @Parameter(description = "ID do fornecedor") Long id) {
        
        fornecedorService.deletarFornecedor(id);
        return ResponseEntity.noContent().build();
    }
}