package com.accenture.desafio_fullstack.app.exception;

public class RecursoNaoEncontradoException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public RecursoNaoEncontradoException(String mensagem) {
		super(mensagem);
	}

	public RecursoNaoEncontradoException(String recurso, Long id) {
		super(String.format("%s com ID %d não encontrado", recurso, id));
	}
	
	public RecursoNaoEncontradoException(String recurso, String nome) {
		super(String.format("%s com Nome %s não encontrado", recurso, nome));
	}

}
