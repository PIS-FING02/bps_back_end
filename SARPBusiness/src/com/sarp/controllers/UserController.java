package com.sarp.controllers;


public class UserController {
	
	private static  UserController instance;
	private UserController(){};
	
	public static UserController getInstance(){
		instance = instance != null ? instance : new UserController();
		return instance;
	}
	
	
}
