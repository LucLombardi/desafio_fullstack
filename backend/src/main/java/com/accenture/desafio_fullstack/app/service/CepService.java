package com.accenture.desafio_fullstack.app.service;

import com.accenture.desafio_fullstack.app.dto.EnderecoResponseDto;
import com.accenture.desafio_fullstack.app.exception.RegraDeNegocioException;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
@Slf4j
public class CepService {

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    public EnderecoResponseDto buscarEnderecoPorCep(String cep) {
        if (cep == null || cep.trim().isEmpty()) {
            throw new RegraDeNegocioException("CEP não pode ser vazio");
        }

        // Remove formatação do CEP (mantém apenas números)
        String cepLimpo = cep.replaceAll("[^0-9]", "");
        
        if (cepLimpo.length() != 8) {
            throw new RegraDeNegocioException("CEP deve conter 8 dígitos");
        }

        try {
            String url = String.format("http://cep.la/%s", cepLimpo);
            log.info("Consultando CEP: {}", cepLimpo);
            
            // Faz a requisição para a API
            String response = restTemplate.getForObject(url, String.class);
            
            if (response == null || response.trim().isEmpty()) {
                throw new RegraDeNegocioException("CEP não encontrado");
            }

            // Parse da resposta JSON
            CepResponse cepResponse = objectMapper.readValue(response, CepResponse.class);
            
            // Verifica se retornou erro
            if (cepResponse.getLogradouro() == null || cepResponse.getLogradouro().trim().isEmpty()) {
                throw new RegraDeNegocioException("CEP não encontrado ou inválido");
            }

            // Converte para o DTO de resposta
            return EnderecoResponseDto.builder()
                    .logradouro(cepResponse.getLogradouro())
                    .bairro(cepResponse.getBairro())
                    .localidade(cepResponse.getLocalidade())
                    .uf(cepResponse.getUf())
                    .cep(formatarCep(cepLimpo))
                    .build();

        } catch (Exception e) {
            log.error("Erro ao consultar CEP {}: {}", cepLimpo, e.getMessage());
            throw new RegraDeNegocioException("Erro ao consultar CEP: " + e.getMessage());
        }
    }

    public boolean validarCep(String cep) {
        try {
            buscarEnderecoPorCep(cep);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private String formatarCep(String cep) {
        if (cep.length() == 8) {
            return cep.substring(0, 5) + "-" + cep.substring(5);
        }
        return cep;
    }

    @Getter
    @Setter
    @JsonIgnoreProperties(ignoreUnknown = true)
    private static class CepResponse {
        private String logradouro;
        private String bairro;
        private String localidade;
        private String uf;
    }
}