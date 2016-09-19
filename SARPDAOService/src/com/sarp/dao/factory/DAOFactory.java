package com.sarp.dao.factory;

import com.sarp.dao.repository.DAONumero;
import com.sarp.dao.repository.DAOSector;
import com.sarp.dao.repository.DAOTramite;
import com.sun.corba.se.spi.activation.Repository;

public class DAOFactory {
	
	private static DAOFactory singleton = new DAOFactory( );
	   
	private DAOFactory(){ }
	   
	public static DAOFactory getInstance( ) {
	    return singleton;
	}
	   
	public DAONumero numeroRepository;
	public DAOSector sectorRepository;
	public DAOTramite tramiteRepository;
	
	public DAONumero getNumeroRepositoryInstance(){
		if(numeroRepository == null){
			numeroRepository = new com.sarp.dao.repository.DAONumero();
		}
		return numeroRepository;
	}
	
	public DAOSector getSectorRepositoryInstance(){
		if(sectorRepository == null){
			sectorRepository = new com.sarp.dao.repository.DAOSector();
		}
		return sectorRepository;
	}
	
	public DAOTramite getTramiteRepositoryInstance(){
		if(tramiteRepository == null){
			tramiteRepository = new com.sarp.dao.repository.DAOTramite();
		}
		return tramiteRepository;
	}
	
}
