package com.sarp.service.response.maker;

public class ResponseMaker {

	private ResponseMaker(){};
	private static ResponseMaker instance;
	
	public static ResponseMaker getInstance(){
		instance = (instance!= null)? instance : new ResponseMaker(); 
		return instance;
	}
}
