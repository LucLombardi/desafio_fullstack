package com.br.accenture.desafio_fullstack.app.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.accenture.desafio_fullstack.app.dto.EmpresaRequestDTO;
import com.accenture.desafio_fullstack.app.dto.EmpresaResponseDto;
import com.accenture.desafio_fullstack.app.exception.RegraDeNegocioException;
import com.accenture.desafio_fullstack.app.model.Empresa;
import com.accenture.desafio_fullstack.app.model.Endereco;
import com.accenture.desafio_fullstack.app.repository.EmpresaRepository;
import com.accenture.desafio_fullstack.app.service.EmpresaService;

@ExtendWith(MockitoExtension.class)
@DisplayName("Testes do EmpresaService")
class EmpresaServiceTest {

    @Mock
    private EmpresaRepository empresaRepository;

    @InjectMocks
    private EmpresaService empresaService;

    private EmpresaRequestDTO empresaRequestDTO;
    private Empresa empresa;
    private Endereco endereco;

    @BeforeEach
    void setUp() {
        endereco = new Endereco();
        endereco.setId(1L);
        endereco.setLogradouro("Rua Teste");
        endereco.setNumero("123");
        endereco.setBairro("Bairro Teste");
        endereco.setLocalidade("Cidade Teste");
        endereco.setUf("SP");
        endereco.setCep("01234-567");

        empresa = new Empresa();
        empresa.setId(1L);
        empresa.setCnpj("12345678000195");
        empresa.setNomeFantasia("Empresa Teste");
        empresa.setEndereco(endereco);

        empresaRequestDTO = new EmpresaRequestDTO();
        empresaRequestDTO.setCnpj("12345678000195");
        empresaRequestDTO.setNomeFantasia("Empresa Teste");
        // Assume que empresaRequestDTO tem campos de endereco
    }

    @Test
    @DisplayName("Deve criar empresa com sucesso")
    void deveCriarEmpresaComSucesso() {
        // Given
        when(empresaRepository.findByCnpj(anyString())).thenReturn(Optional.empty());
        when(empresaRepository.save(any(Empresa.class))).thenReturn(empresa);

        // When
        EmpresaResponseDto resultado = empresaService.criarEmpresa(empresaRequestDTO);

        // Then
        assertNotNull(resultado);
        assertEquals(empresa.getCnpj(), resultado.getCnpj());
        assertEquals(empresa.getNomeFantasia(), resultado.getNomeFantasia());
        
        verify(empresaRepository, times(1)).findByCnpj(empresaRequestDTO.getCnpj());
        verify(empresaRepository, times(1)).save(any(Empresa.class));
    }

    @Test
    @DisplayName("Deve lançar exceção ao tentar criar empresa com CNPJ já existente")
    void deveLancarExcecaoAoTentarCriarEmpresaComCnpjExistente() {
        // Given
        when(empresaRepository.findByCnpj(anyString())).thenReturn(Optional.of(empresa));

        // When & Then
        RegraDeNegocioException exception = assertThrows(
            RegraDeNegocioException.class,
            () -> empresaService.criarEmpresa(empresaRequestDTO)
        );

        assertEquals("CNPJ já cadastrado no sistema", exception.getMessage());
        verify(empresaRepository, times(1)).findByCnpj(empresaRequestDTO.getCnpj());
        verify(empresaRepository, never()).save(any(Empresa.class));
    }

}