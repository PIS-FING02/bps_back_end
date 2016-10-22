package com.sarp.enumerados;

//Posibles estados por los que puede pasar un puesto
public enum EstadoNumero {
	PENDIENTE, LLAMADO, ATRASADO, NOATENDIDO, ATENDIENDO, PAUSADO, FINALIZADO;
	
	public static EstadoNumero getEnum(String s){
        if(PENDIENTE.name().equals(s)){
            return PENDIENTE;
        }else if(LLAMADO.name().equals(s)){
            return LLAMADO;
        }else if(ATRASADO.name().equals(s)){
            return ATRASADO;
        }else if (NOATENDIDO.name().equals(s)){
            return NOATENDIDO;
        }else if(ATENDIENDO.name().equals(s)){
            return ATENDIENDO;
        }else if (PAUSADO.name().equals(s)){
            return PAUSADO;
        }else if (FINALIZADO.name().equals(s)){
            return FINALIZADO;
        }
        throw new IllegalArgumentException("No se pudo machear ningun Estado de numero ");
    }
}
