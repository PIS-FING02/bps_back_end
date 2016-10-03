package com.sarp.controllers;

public class WaitingController {
	private static  WaitingController instance;
	private WaitingController(){};
	
	public static WaitingController getInstance(){
		instance = instance != null ? instance : new WaitingController();
		return instance;
	}

}
