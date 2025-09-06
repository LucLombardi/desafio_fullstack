package com.accenture.desafio_fullstack.app.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.accenture.desafio_fullstack.app.model.Empresa;
import com.accenture.desafio_fullstack.app.model.Fornecedor;

import feign.Param;

import java.util.List;

public interface EmpresaRepository  extends JpaRepository<Empresa, Long>{
	
	Optional<Empresa>findByCnpj(String cnpj);
	List<Optional<Empresa>>findByNomeFantasiaContainingAllIgnoreCase(String nome);
	
    
  
    @Query(value = "SELECT COUNT(*) FROM tb_empresa_fornecedor ef WHERE ef.empresa_id = :empresaId", 
           nativeQuery = true)
    Long contarFornecedores(@Param("empresaId") Long empresaId);
    
    @Query("SELECT f FROM Fornecedor f JOIN f.empresas e WHERE e.id = :empresaId")
    Page<Fornecedor> findFornecedoresByEmpresaId(@Param("empresaId") Long empresaId, Pageable pageable);

}
