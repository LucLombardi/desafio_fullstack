package com.accenture.desafio_fullstack.app.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.accenture.desafio_fullstack.app.dto.FornecedorEmpresaRequestDto;
import com.accenture.desafio_fullstack.app.dto.FornecedorEmpresaResponseDto;
import com.accenture.desafio_fullstack.app.service.FornecedorEmpresaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import lombok.RequiredArgsConstructor;

@RestController

@RequestMapping("/api/fornecedor-empresa")
@RequiredArgsConstructor
@Tag(name = "Fornecedor-Empresa", description = "Gerenciamento de vínculos entre Fornecedores e Empresas")
public class FornecedorEmpresaController {
	
    private final FornecedorEmpresaService fornecedorEmpresaService;
    
    
    @PostMapping("/vincular")
    @Operation(summary = "Vincular fornecedor a uma empresa", 
               description = "Cria um vínculo entre um fornecedor e uma empresa")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Vínculo criado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Empresa ou fornecedor não encontrado"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos ou fornecedor já vinculado")
    })
    public ResponseEntity<FornecedorEmpresaResponseDto> vincularFornecedorEmpresa(
            @Parameter(description = "Dados para vincular fornecedor à empresa")
            @Valid @RequestBody FornecedorEmpresaRequestDto requestDto) {
        

        
        FornecedorEmpresaResponseDto response = fornecedorEmpresaService.vincular(requestDto);
        
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    
    @DeleteMapping("/desvincular/{fornecedorId}/{empresaId}")
    @Operation(summary = "Desvincular fornecedor de sua empresa", 
               description = "Remove o vínculo entre um fornecedor e a empresa")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Desvinculação realizada com sucesso"),
        @ApiResponse(responseCode = "404", description = "Fornecedor não encontrado"),
        @ApiResponse(responseCode = "400", description = "Fornecedor não está vinculado a nenhuma empresa")
    })
    public ResponseEntity<String> desvincularFornecedor(
            @Parameter(description = "ID do fornecedor e ID da Empresa") @PathVariable Long fornecedorId , long empresaId) {
        
 
        
        fornecedorEmpresaService.desvincular(fornecedorId,empresaId);
        
        return ResponseEntity.ok("Fornecedor desvinculado com sucesso");
    }


}
