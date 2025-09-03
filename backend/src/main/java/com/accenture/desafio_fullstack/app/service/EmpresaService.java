package com.accenture.desafio_fullstack.app.service;

import org.springframework.stereotype.Service;

import com.accenture.desafio_fullstack.app.repository.EmpresaRepository;

@Service
public class EmpresaService {
	
	final private EmpresaRepository empresaRepository;

	public EmpresaService(EmpresaRepository empresaRepository) {
	
		this.empresaRepository = empresaRepository;
	}
	
	
	
	
	

}
