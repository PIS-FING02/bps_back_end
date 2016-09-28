package com.sarp.exceptions;

//Esta clase es utilizada para indicar errores en el contexto de las acciones
//Ej: Quiero setear el estado de un puesto en abierto y el puesto ya se encuentra en este estado

public class ContextException extends Exception {
	
	private static final long serialVersionUID = 1L;

	public ContextException(){}
	
	public ContextException(String message){
		//set the message to the Exception
		super(message);
	}
}
