package com.sarp.service.rest;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import com.sarp.controllers.AdminActionsController;
import com.sarp.factory.Factory;
import com.sarp.thread.ThreadManager;

@ApplicationPath("/")
public class RestApplication extends Application {
	
	public RestApplication() throws Exception{
		
		try{
			Factory fac = Factory.getInstance();
			AdminActionsController ctrl = fac.getAdminActionsController();
			System.out.println("Inicializando colas...");
			ctrl.reinicializarColas();
			System.out.println("Colas inicializadas...");			
			/*ThreadManager tm = */ThreadManager.getInstance();
		}catch(Exception e){
			throw e;
		}
	}
	
}