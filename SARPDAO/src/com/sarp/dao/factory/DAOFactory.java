package com.sarp.dao.factory;

import javax.persistence.EntityManager;

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
	   
	public DAONumero getNumeroRepository(EntityManager em){
		if(numeroRepository == null){
			numeroRepository = new com.sarp.dao.repository.DAONumero();
		}
		numeroRepository.setEM(em);
		return numeroRepository;
	}
	
	public DAOSector getSectorRepository(EntityManager em){
		if(sectorRepository == null){
			sectorRepository = new com.sarp.dao.repository.DAOSector();
		}
		sectorRepository.setEM(em);
		return sectorRepository;
	}
	
	public DAOTramite getTramiteRepository(EntityManager em){
		if(tramiteRepository == null){
			tramiteRepository = new com.sarp.dao.repository.DAOTramite();
		}
		tramiteRepository.setEM(em);
		return tramiteRepository;
	}
	
	public DAOPuesto getPuestoRepository(EntityManager em){
		if(puestoRepository == null){
			puestoRepository = new com.sarp.dao.repository.DAOPuesto();
		}
		puestoRepository.setEM(em);
		return puestoRepository;
	}
	
	public DAODisplay getDisplayRepository(EntityManager em){
		if(displayRepository == null){
			displayRepository = new com.sarp.dao.repository.DAODisplay();
		}
		displayRepository.setEM(em);
		return displayRepository;
	}
}
