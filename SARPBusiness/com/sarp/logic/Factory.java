package com.sarp.logic;

<<<<<<< HEAD

=======
>>>>>>> be7fa8d... arreglos
public class Factory {
	private static Factory instancia;
	
	public static Factory getInstance(){
		if (instancia == null){
			instancia = new Factory();
			return instancia;
		}else{
			return instancia;
		}
	}
	private Factory(){}
	
	public AtentionsController getAtentionsController(){
		return null;
	}
	
<<<<<<< HEAD
	public Admin_ActionsController getAdminActionsController(){
		
		return new Admin_ActionsController();
	}
=======
>>>>>>> be7fa8d... arreglos
	
}
