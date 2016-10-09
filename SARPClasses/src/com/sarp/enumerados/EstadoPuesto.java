package com.sarp.enumerados;

//Posibles estados por los que puede pasar un puesto
public enum EstadoPuesto {
	CERRADO, DISPONIBLE, LLAMANDO, ATENDIENDO;
	
	public static EstadoPuesto getEnum(String s){
        if(CERRADO.name().equals(s)){
            return CERRADO;
        }else if(DISPONIBLE.name().equals(s)){
            return DISPONIBLE;
        }else if(LLAMANDO.name().equals(s)){
            return LLAMANDO;
        }else if (ATENDIENDO.name().equals(s)){
            return ATENDIENDO;
        }
        throw new IllegalArgumentException("No se pudo machear ningun Estado de puesto ");
    }
}
