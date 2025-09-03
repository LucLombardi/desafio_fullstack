package com.accenture.desafio_fullstack.app.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.accenture.desafio_fullstack.app.model.Empresa;
import com.accenture.desafio_fullstack.app.model.Fornecedor;

public interface FornecedorRepository extends JpaRepository<Fornecedor, Long> {
	
	Optional<Fornecedor> findByCnpjOuCpf(String cnpjOuCpf);
	
	List<Fornecedor>findByNomeContainingAllIgnoreCase(String nome);

}
