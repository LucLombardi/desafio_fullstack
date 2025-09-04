package com.accenture.desafio_fullstack.app.controller;

import com.accenture.desafio_fullstack.app.dto.EmpresaRequestDTO;
import com.accenture.desafio_fullstack.app.dto.EmpresaResponseDto;
import com.accenture.desafio_fullstack.app.service.EmpresaService;
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
@RequestMapping("/api/empresas")
@RequiredArgsConstructor
@Tag(name = "Empresas", description = "API para gerenciamento de empresas")
public class EmpresaController {

    private final EmpresaService empresaService;

    @PostMapping
    @Operation(summary = "Criar nova empresa", description = "Cria uma nova empresa no sistema")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Empresa criada com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos"),
        @ApiResponse(responseCode = "409", description = "CNPJ já cadastrado")
    })
    public ResponseEntity<EmpresaResponseDto> criarEmpresa(
            @Valid @RequestBody EmpresaRequestDTO empresaRequest) {
        
        EmpresaResponseDto empresaCriada = empresaService.criarEmpresa(empresaRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(empresaCriada);
    }

    @GetMapping
    @Operation(summary = "Listar empresas", description = "Lista todas as empresas com paginação")
    @ApiResponse(responseCode = "200", description = "Lista de empresas retornada com sucesso")
    public ResponseEntity<Page<EmpresaResponseDto>> listarEmpresas(
            @PageableDefault(size = 10, sort = "id") 
            @Parameter(description = "Parâmetros de paginação e ordenação") Pageable pageable) {
        
        Page<EmpresaResponseDto> empresas = empresaService.listarEmpresas(pageable);
        return ResponseEntity.ok(empresas);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar empresa por ID", description = "Retorna uma empresa específica pelo seu ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Empresa encontrada"),
        @ApiResponse(responseCode = "404", description = "Empresa não encontrada")
    })
    public ResponseEntity<EmpresaResponseDto> buscarEmpresaPorId(
            @PathVariable @Parameter(description = "ID da empresa") Long id) {
        
        EmpresaResponseDto empresa = empresaService.buscarEmpresaPorId(id);
        return ResponseEntity.ok(empresa);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar empresa", description = "Atualiza os dados de uma empresa existente")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Empresa atualizada com sucesso"),
        @ApiResponse(responseCode = "404", description = "Empresa não encontrada"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos"),
        @ApiResponse(responseCode = "409", description = "CNPJ já cadastrado para outra empresa")
    })
    public ResponseEntity<EmpresaResponseDto> atualizarEmpresa(
            @PathVariable @Parameter(description = "ID da empresa") Long id,
            @Valid @RequestBody EmpresaRequestDTO empresaRequest) {
        
        EmpresaResponseDto empresaAtualizada = empresaService.atualizarEmpresa(id, empresaRequest);
        return ResponseEntity.ok(empresaAtualizada);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar empresa", description = "Remove uma empresa do sistema")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Empresa deletada com sucesso"),
        @ApiResponse(responseCode = "404", description = "Empresa não encontrada"),
        @ApiResponse(responseCode = "409", description = "Empresa possui relacionamentos que impedem a exclusão")
    })
    public ResponseEntity<Void> deletarEmpresa(
            @PathVariable @Parameter(description = "ID da empresa") Long id) {
        
        empresaService.deletarEmpresa(id);
        return ResponseEntity.noContent().build();
    }
}