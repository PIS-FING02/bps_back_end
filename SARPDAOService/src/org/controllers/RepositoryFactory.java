package org.controllers;

import com.sun.corba.se.spi.activation.Repository;

import dao.repository.NumeroRepository;
import dao.repository.SectorRepository;
import dao.repository.TramiteRepository;

public class RepositoryFactory {
	
	private static RepositoryFactory singleton = new RepositoryFactory( );
	   
	private RepositoryFactory(){ }
	   
	public static RepositoryFactory getInstance( ) {
	    return singleton;
	}
	   
	public NumeroRepository numeroRepository;
	public SectorRepository sectorRepository;
	public TramiteRepository tramiteRepository;
	
	public NumeroRepository getNumeroRepositoryInstance(){
		if(numeroRepository == null){
			numeroRepository = new dao.repository.NumeroRepository();
		}
		return numeroRepository;
	}
	
	public SectorRepository getSectorRepositoryInstance(){
		if(sectorRepository == null){
			sectorRepository = new dao.repository.SectorRepository();
		}
		return sectorRepository;
	}
	
	public TramiteRepository getTramiteRepositoryInstance(){
		if(tramiteRepository == null){
			tramiteRepository = new dao.repository.TramiteRepository();
		}
		return tramiteRepository;
	}
	
}
