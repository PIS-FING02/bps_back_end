package com.sarp.logic;

public class Factory {
	private static Factory instancia;
	
	public static Factory GetInstance(){
		if (instancia == null){
			instancia = new Factory();
			return instancia;
		}else{
			return instancia;
		}
	}
	private Factory(){}
	
	public AtentionsController GetAtentionsController(){
		return null;
	}
	
	
}
