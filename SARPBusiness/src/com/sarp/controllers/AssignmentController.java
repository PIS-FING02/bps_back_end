package com.sarp.controllers;

public class AssignmentController {

	private static  AssignmentController instance;
	private AssignmentController(){};
	
	public static  AssignmentController getInstance(){
		instance = instance != null ? instance : new AssignmentController();
		return instance;
	}
	
}
