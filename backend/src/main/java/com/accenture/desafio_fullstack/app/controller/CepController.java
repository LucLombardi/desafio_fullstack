package com.accenture.desafio_fullstack.app.controller;

import com.accenture.desafio_fullstack.app.dto.CepResponseDto;
import com.accenture.desafio_fullstack.app.dto.EnderecoResponseDto;
import com.accenture.desafio_fullstack.app.service.CepService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cep")
@RequiredArgsConstructor
@Tag(name = "CEP", description = "API para consulta de CEP via ViaCEP")
public class CepController {

    private final CepService cepService;

    @GetMapping("/{cep}")
    @Operation(summary = "Consultar CEP", description = "Consulta informações de endereço através do CEP via ViaCEP")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "CEP encontrado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos"),
        @ApiResponse(responseCode = "404", description = "Recurso não encontrado"),
        @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public ResponseEntity<EnderecoResponseDto> consultarCep(
            @PathVariable @Parameter(description = "CEP para consulta (formato: 12345678 ou 12345-678)") String cep) {
        
    	EnderecoResponseDto cepInfo = cepService.buscarEnderecoPorCep(cep);
        return ResponseEntity.ok(cepInfo);
    }
}