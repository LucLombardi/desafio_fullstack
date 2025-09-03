package com.accenture.desafio_fullstack.app.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.accenture.desafio_fullstack.app.model.Empresa;

import java.util.List;

public interface EmpresaRepository  extends JpaRepository<Empresa, Long>{
	
	Optional<Empresa>findByCnpj(String cnpj);
	List<Optional<Empresa>>findByNomeFantasiaContainingAllIgnoreCase(String nome);
	

}
