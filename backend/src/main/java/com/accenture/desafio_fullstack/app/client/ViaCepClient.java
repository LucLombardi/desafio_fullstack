package com.accenture.desafio_fullstack.app.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.accenture.desafio_fullstack.app.dto.CepResponseDto;

@FeignClient(name = "viacep-client",
url = "https://viacep.com.br/ws")
public interface ViaCepClient {
	
	@GetMapping("/{cep}/json")
	CepResponseDto buscaCep(@PathVariable("cep")String cep);
	

}
