package com.sarp.enumerados;

//Posibles estados por los que puede pasar un puesto
public enum EstadoNumero {
	DISPONIBLE, LLAMADO, ATRASADO, NOATENDIDO, ATENDIENDO, PAUSADO, FINALIZADO, DESVIADO;
	
	public static EstadoNumero getEnum(String s){
        if(DISPONIBLE.name().equals(s)){
            return DISPONIBLE;
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
        }else if (DESVIADO.name().equals(s)){
            return DESVIADO;
        }
        throw new IllegalArgumentException("No se pudo machear ningun Estado de numero ");
    }
}
