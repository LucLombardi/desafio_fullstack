package com.accenture.desafio_fullstack.app.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.accenture.desafio_fullstack.app.model.Empresa;
import com.accenture.desafio_fullstack.app.model.Fornecedor;

import feign.Param;

public interface FornecedorRepository extends JpaRepository<Fornecedor, Long> {

	Optional<Fornecedor> findByCnpjOuCpf(String cnpjOuCpf);

	@Query(value = "SELECT COUNT(*) FROM tb_empresa_fornecedor ef WHERE ef.fornecedor_id = :fornecedorId", nativeQuery = true)
	Long contarEmpresas(@Param("id") Long fornecedorId);

	@Query("SELECT f FROM Fornecedor f WHERE LOWER(f.nome) LIKE LOWER(CONCAT('%', :nome, '%'))")
	Page<Fornecedor> findByLikeNome(@Param("nome") String nome, Pageable pageable);
	
	Page<Fornecedor> findByNomeContainingIgnoreCase(@Param("nome") String nome, Pageable pageable);

}
