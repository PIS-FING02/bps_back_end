package com.sarp.dao.factory;

import com.sarp.dao.repository.DAODisplay;
import com.sarp.dao.repository.DAONumero;
import com.sarp.dao.repository.DAOPuesto;
import com.sarp.dao.repository.DAOSector;
import com.sarp.dao.repository.DAOTramite;

public class DAOFactory {
	
	private static DAOFactory singleton = new DAOFactory( );
	   
	private DAONumero numeroRepository;
	private DAOSector sectorRepository;
	private DAOTramite tramiteRepository;
	private DAOPuesto puestoRepository;
	private DAODisplay displayRepository;
	
	private DAOFactory(){ }
	   
	public static DAOFactory getInstance( ) {
	    return singleton;
	}
	   
	public DAONumero getNumeroRepository(){
		if(numeroRepository == null){
			numeroRepository = new com.sarp.dao.repository.DAONumero();
		}
		return numeroRepository;
	}
	
	public DAOSector getSectorRepository(){
		if(sectorRepository == null){
			sectorRepository = new com.sarp.dao.repository.DAOSector();
		}
		return sectorRepository;
	}
	
	public DAOTramite getTramiteRepository(){
		if(tramiteRepository == null){
			tramiteRepository = new com.sarp.dao.repository.DAOTramite();
		}
		return tramiteRepository;
	}
	
	public DAOPuesto getPuestoRepository(){
		if(puestoRepository == null){
			puestoRepository = new com.sarp.dao.repository.DAOPuesto();
		}
		return puestoRepository;
	}
	
	public DAODisplay getDisplayRepository(){
		if(displayRepository == null){
			displayRepository = new com.sarp.dao.repository.DAODisplay();
		}
		return displayRepository;
	}
}
