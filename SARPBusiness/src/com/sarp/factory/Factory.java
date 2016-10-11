package com.sarp.factory;

import com.sarp.controllers.AdminActionsController;
import com.sarp.controllers.AssignmentController;
import com.sarp.controllers.AttentionsController;
import com.sarp.controllers.UserController;
import com.sarp.controllers.WaitingController;
import com.sarp.controllers.GAFUController;
import com.sarp.controllers.QueueController;

public class Factory {
	private static Factory instance;
	
	private Factory(){}
	
	public static Factory getInstance(){
		instance = instance != null ? instance : new Factory(); 
		return instance;
	}
	
	public AttentionsController getAttentionsController(){
		return AttentionsController.getInstance();
	}
	
	public AdminActionsController getAdminActionsController(){
		return  AdminActionsController.getInstance();
	}
	
	public UserController getUserController(){
		return  UserController.getInstance();
	}
	
	public WaitingController getWaitingController(){
		return WaitingController.getInstance();
	}
	
	public QueueController getQueueController(String idSector){
		return new QueueController(idSector);
	}
	
	public AssignmentController getAssignmentController(){
		return AssignmentController.getInstance();
	}
	
	public GAFUController GAFUController(){
		return new GAFUController();
	}
	
}
