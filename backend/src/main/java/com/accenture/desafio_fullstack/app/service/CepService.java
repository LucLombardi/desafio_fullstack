package com.accenture.desafio_fullstack.app.service;

import com.accenture.desafio_fullstack.app.client.ViaCepClient;
import com.accenture.desafio_fullstack.app.dto.CepResponseDto;
import com.accenture.desafio_fullstack.app.dto.EnderecoResponseDto;
import com.accenture.desafio_fullstack.app.exception.RegraDeNegocioException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor 
public class CepService {

    private final ViaCepClient viaCepClient; 

    public EnderecoResponseDto buscarEnderecoPorCep(String cep) {

        

        validarCepEntrada(cep);
        

        String cepLimpo = limparCep(cep);
        

        validarFormatoCep(cepLimpo);
        
        try {
   
            CepResponseDto cepResponse = viaCepClient.buscaCep(cepLimpo);
            

            validarRespostaCep(cepResponse);
            

            return converterParaEnderecoResponse(cepResponse, cepLimpo);
            
        } catch (Exception e) {

            throw new RegraDeNegocioException("Erro ao consultar CEP: " + e.getMessage());
        }
    }


    
    private void validarCepEntrada(String cep) {
        if (cep == null || cep.trim().isEmpty()) {
            throw new RegraDeNegocioException("CEP não pode ser vazio");
        }
    }
    
    private String limparCep(String cep) {
        return cep.replaceAll("[^0-9]", "");
    }
    
    private void validarFormatoCep(String cepLimpo) {
        if (cepLimpo.length() != 8) {
            throw new RegraDeNegocioException("CEP deve conter exatamente 8 dígitos");
        }
    }
    
    private void validarRespostaCep(CepResponseDto cepResponse) {

        if (Boolean.TRUE.equals(cepResponse.getErro())) {
            throw new RegraDeNegocioException("CEP não encontrado");
        }
        

        if (cepResponse.getLogradouro() == null || cepResponse.getLogradouro().trim().isEmpty()) {
            throw new RegraDeNegocioException("CEP não encontrado ou inválido");
        }
    }
    
    private EnderecoResponseDto converterParaEnderecoResponse(CepResponseDto cepResponse, String cepLimpo) {
        return EnderecoResponseDto.builder()
                .logradouro(cepResponse.getLogradouro())
                .bairro(cepResponse.getBairro())
                .localidade(cepResponse.getLocalidade())
                .uf(cepResponse.getUf())
                .cep(formatarCep(cepLimpo))
                .build();
    }
    
    private String formatarCep(String cep) {
        if (cep.length() == 8) {
            return cep.substring(0, 5) + "-" + cep.substring(5);
        }
        return cep;
    }


    public boolean validarCep(String cep) {
        try {
            buscarEnderecoPorCep(cep);
            return true;
        } catch (Exception e) {

            return false;
        }
    }
}