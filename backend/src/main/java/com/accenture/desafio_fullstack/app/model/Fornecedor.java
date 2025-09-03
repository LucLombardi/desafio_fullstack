package com.accenture.desafio_fullstack.app.model;

import java.time.LocalDate;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name="tb_fornecedor")
public class Fornecedor {
	
	  @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;

	    @Column(unique = true, nullable = false, length = 15)
	    private String cnpjOuCpf; 
	    @Column(nullable = false, length = 120)
	    private String nome;
	    @Column(length = 120)
	    private String email;
	    @Column(length = 20)
	    private String cep;
	    @Column(length = 20)
	    private String rg; 
	    private LocalDate dataNascimento; 
	    
	    @ManyToMany(mappedBy = "fornecedores")
	    private Set<Empresa> empresas;
	    
	   
	    

}
