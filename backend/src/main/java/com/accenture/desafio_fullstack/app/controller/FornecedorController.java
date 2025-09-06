package com.accenture.desafio_fullstack.app.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.accenture.desafio_fullstack.app.dto.FornecedorRequestDTO;
import com.accenture.desafio_fullstack.app.dto.FornecedorResponseDto;
import com.accenture.desafio_fullstack.app.service.FornecedorService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/api/fornecedores")
@Tag(name = "Fornecedores", description = "API para gerenciamento de fornecedores")
public class FornecedorController {

    private final FornecedorService fornecedorService;

    public FornecedorController(FornecedorService fornecedorService) {
        this.fornecedorService = fornecedorService;
    }
    
    
    @GetMapping()
    @Operation(summary = "Listar fornecedores ", description = "Retorna uma lista paginada de fornecedores")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de fornecedores retornada com sucesso",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = PagedModel.class))),
        @ApiResponse(responseCode = "400", description = "Parâmetros de requisição inválidos",
                content = @Content),
        @ApiResponse(responseCode = "500", description = "Erro interno do servidor",
                content = @Content)
    })
    public ResponseEntity<PagedModel<EntityModel<FornecedorResponseDto>>> ListarFornecedores(Pageable pageable) {
        
        	
    	Page<FornecedorResponseDto> fornecedores  = fornecedorService.ListarTodosOsFornecedores(pageable);

        PagedModel<EntityModel<FornecedorResponseDto>> pagedModel = PagedModel.of(
            fornecedores.getContent().stream()
                .map(this::toEntityModel)
                .toList(),
            new PagedModel.PageMetadata(
                fornecedores.getSize(),
                fornecedores.getNumber(),
                fornecedores.getTotalElements(),
                fornecedores.getTotalPages()
            )
        );
        
        pagedModel.add(linkTo(methodOn(FornecedorController.class).ListarFornecedores(pageable)).withSelfRel());
        
        return ResponseEntity.ok(pagedModel);
    }

    

    @GetMapping("/nome/{nome}")
    @Operation(summary = "Listar fornecedores por Nome", description = "Retorna uma lista paginada de fornecedores com opção de filtro por nome")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de fornecedores retornada com sucesso",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = PagedModel.class))),
        @ApiResponse(responseCode = "400", description = "Parâmetros de requisição inválidos",
                content = @Content),
        @ApiResponse(responseCode = "500", description = "Erro interno do servidor",
                content = @Content)
    })
    public ResponseEntity<PagedModel<EntityModel<FornecedorResponseDto>>> listarFornecedoresPorNome(
            @PathVariable String nome,
            @Parameter(description = "Parâmetros de paginação")
            Pageable pageable) {
        
        	
    	Page<FornecedorResponseDto> fornecedores  = fornecedorService.listNome(nome, pageable);

        PagedModel<EntityModel<FornecedorResponseDto>> pagedModel = PagedModel.of(
            fornecedores.getContent().stream()
                .map(this::toEntityModel)
                .toList(),
            new PagedModel.PageMetadata(
                fornecedores.getSize(),
                fornecedores.getNumber(),
                fornecedores.getTotalElements(),
                fornecedores.getTotalPages()
            )
        );
        
        pagedModel.add(linkTo(methodOn(FornecedorController.class).listarFornecedoresPorNome(nome, pageable)).withSelfRel());
        
        return ResponseEntity.ok(pagedModel);
    }

    @GetMapping("/id/{id}")
    @Operation(summary = "Buscar fornecedor por ID", description = "Retorna um fornecedor específico pelo seu ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Fornecedor encontrado com sucesso",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = FornecedorResponseDto.class))),
        @ApiResponse(responseCode = "400", description = "ID inválido fornecido",
                content = @Content),
        @ApiResponse(responseCode = "404", description = "Fornecedor não encontrado",
                content = @Content),
        @ApiResponse(responseCode = "500", description = "Erro interno do servidor",
                content = @Content)
    })
    public ResponseEntity<EntityModel<FornecedorResponseDto>> buscarFornecedorPorId(
            @Parameter(description = "ID do fornecedor", required = true)
            @PathVariable Long id) {
        
        FornecedorResponseDto fornecedor = fornecedorService.buscarFornecedorPorId(id);
        EntityModel<FornecedorResponseDto> entityModel = toEntityModel(fornecedor);
        
        return ResponseEntity.ok(entityModel);
    }

    @GetMapping("/cpf/{cpf}")
    @Operation(summary = "Buscar fornecedor por CPF", description = "Retorna um fornecedor específico pelo seu CPF")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Fornecedor encontrado com sucesso",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = FornecedorResponseDto.class))),
        @ApiResponse(responseCode = "400", description = "CPF inválido fornecido",
                content = @Content),
        @ApiResponse(responseCode = "404", description = "Fornecedor não encontrado",
                content = @Content),
        @ApiResponse(responseCode = "500", description = "Erro interno do servidor",
                content = @Content)
    })
    public ResponseEntity<EntityModel<FornecedorResponseDto>> buscarFornecedorPorCpf(
            @Parameter(description = "CPF do fornecedor", required = true)
            @PathVariable String cpf) {
        
        FornecedorResponseDto fornecedor = fornecedorService.buscarCpfOrCnpj(cpf);
        EntityModel<FornecedorResponseDto> entityModel = toEntityModel(fornecedor);
        
        return ResponseEntity.ok(entityModel);
    }

    @PostMapping
    @Operation(summary = "Criar novo fornecedor", description = "Cria um novo fornecedor no sistema")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Fornecedor criado com sucesso",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = FornecedorResponseDto.class))),
        @ApiResponse(responseCode = "400", description = "Dados de entrada inválidos",
                content = @Content),
        @ApiResponse(responseCode = "500", description = "Erro interno do servidor",
                content = @Content)
    })
    public ResponseEntity<EntityModel<FornecedorResponseDto>> criarFornecedor(
            @Parameter(description = "Dados do fornecedor a ser criado", required = true)
            @Valid @RequestBody FornecedorRequestDTO fornecedorRequestDTO) {
        
        FornecedorResponseDto fornecedorCriado = fornecedorService.criarFornecedor(fornecedorRequestDTO);
        EntityModel<FornecedorResponseDto> entityModel = toEntityModel(fornecedorCriado);
        
        return ResponseEntity.status(HttpStatus.CREATED).body(entityModel);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar fornecedor", description = "Atualiza os dados de um fornecedor existente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Fornecedor atualizado com sucesso",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = FornecedorResponseDto.class))),
        @ApiResponse(responseCode = "400", description = "Dados de entrada inválidos",
                content = @Content),
        @ApiResponse(responseCode = "404", description = "Fornecedor não encontrado",
                content = @Content),
        @ApiResponse(responseCode = "500", description = "Erro interno do servidor",
                content = @Content)
    })
    public ResponseEntity<EntityModel<FornecedorResponseDto>> atualizarFornecedor(
            @Parameter(description = "ID do fornecedor", required = true)
            @PathVariable Long id,
            @Parameter(description = "Dados atualizados do fornecedor", required = true)
            @Valid @RequestBody FornecedorRequestDTO fornecedorRequestDTO) {
        
        FornecedorResponseDto fornecedorAtualizado = fornecedorService.atualizarFornecedor(id, fornecedorRequestDTO);
        EntityModel<FornecedorResponseDto> entityModel = toEntityModel(fornecedorAtualizado);
        
        return ResponseEntity.ok(entityModel);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar fornecedor", description = "Remove um fornecedor do sistema")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Fornecedor deletado com sucesso"),
        @ApiResponse(responseCode = "400", description = "ID inválido fornecido",
                content = @Content),
        @ApiResponse(responseCode = "404", description = "Fornecedor não encontrado",
                content = @Content),
        @ApiResponse(responseCode = "500", description = "Erro interno do servidor",
                content = @Content)
    })
    public ResponseEntity<Void> deletarFornecedor(
            @Parameter(description = "ID do fornecedor", required = true)
            @PathVariable Long id) {
        
        fornecedorService.deletarFornecedor(id);
        return ResponseEntity.noContent().build();
    }

    private EntityModel<FornecedorResponseDto> toEntityModel(FornecedorResponseDto fornecedor) {
        return EntityModel.of(fornecedor)
            .add(linkTo(methodOn(FornecedorController.class).buscarFornecedorPorId(fornecedor.getId())).withSelfRel())
            .add(linkTo(methodOn(FornecedorController.class).ListarFornecedores(Pageable.unpaged())).withRel("Fornecedores"))
            .add(linkTo(methodOn(FornecedorController.class).listarFornecedoresPorNome(null, Pageable.unpaged())).withRel("Nome"))
            .add(linkTo(methodOn(FornecedorController.class).atualizarFornecedor(fornecedor.getId(), null)).withRel("update"))
            .add(linkTo(methodOn(FornecedorController.class).deletarFornecedor(fornecedor.getId())).withRel("delete"));
    }
}