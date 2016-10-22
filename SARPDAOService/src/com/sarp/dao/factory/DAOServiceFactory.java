package com.sarp.dao.factory;

import com.sarp.dao.controllers.DAOAdminController;
import com.sarp.dao.controllers.DAODisplayController;
import com.sarp.dao.controllers.DAONumeroController;
import com.sarp.dao.controllers.DAOPuestoController;
import com.sarp.dao.controllers.DAOSectorController;
import com.sarp.dao.controllers.DAOTramiteController;

public class DAOServiceFactory {

	private static DAOServiceFactory singleton = new DAOServiceFactory( );
	   
	private DAOServiceFactory(){ }
	   
	public static DAOServiceFactory getInstance( ) {
	    return singleton;
	}
	
	public DAONumeroController getDAONumeroController(){
		return new DAONumeroController();
	}
	
	public DAOAdminController getDAOAdminController(){
		return new DAOAdminController();
	}
	
	public DAOPuestoController getDAOPuestoController(){
		return new  DAOPuestoController();
	}
	
	public DAOSectorController getDAOSectorController(){
		return new  DAOSectorController();
	}
	
	public DAOTramiteController getDAOTramiteController(){
		return new  DAOTramiteController();
	}
	
	public DAODisplayController getDAODisplayController(){
		return new DAODisplayController();
	}
}
