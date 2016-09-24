package com.sarp.factory;

import com.sarp.controllers.AdminActionsController;
import com.sarp.controllers.AssignmentController;
import com.sarp.controllers.AtentionsController;
import com.sarp.controllers.QueueController;
import com.sarp.controllers.UserController;
import com.sarp.controllers.WaitingController;
import com.sarp.controllers.GAFUController;

public class Factory {
	private static Factory instancia;
	
	private Factory(){}
	
	public static Factory getInstance(){
		if (instancia == null){
			instancia = new Factory();
			return instancia;
		}else{
			return instancia;
		}
	}
	
	public AtentionsController getAtentionsController(){
		return null;
	}
	
	public AdminActionsController getAdminActionsController(){
		return new AdminActionsController();
	}
	
	public UserController getUserController(){
		return new UserController();
	}
	
	public WaitingController getWaitingController(){
		return new WaitingController();
	}
	
	public QueueController getQueueController(){
		return new QueueController();
	}
	
	public AssignmentController getAssignmentController(){
		return new AssignmentController();
	}
	
	public GAFUController GAFUController(){
		return new GAFUController();
	}
	
}
