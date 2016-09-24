package com.sarp.enumerados;

//Posibles estados por los que puede pasar un puesto
public enum EstadoPuesto {
	CERRADO, DIPONIBLE, LLAMANDO, ATENDIENDO;
	
	public static EstadoPuesto getEnum(String s){
        if(CERRADO.name().equals(s)){
            return CERRADO;
        }else if(DIPONIBLE.name().equals(s)){
            return DIPONIBLE;
        }else if(LLAMANDO.name().equals(s)){
            return LLAMANDO;
        }else if (ATENDIENDO.name().equals(s)){
            return ATENDIENDO;
        }
        throw new IllegalArgumentException("No se pudo machear ningun Estado de puesto ");
    }
}
