package com.sarp.dao.controllers;

import java.util.LinkedList;
import java.util.List;

import com.sarp.classes.BusinessNumero;
import com.sarp.classes.BusinessSector;
import com.sarp.classes.BusinessTramite;
import com.sun.corba.se.impl.orbutil.RepositoryIdFactory;

import dao.repository.NumeroRepository;
import dao.repository.SectorRepository;
import dao.repository.TramiteRepository;
import model.Sector;

//import org.dao.repository.NumeroRepository;

public class SectorControlador {
	
	public void crearSector(String nom) throws Exception{
		System.out.println("hola desde crearSector");
		RepositoryFactory factory = RepositoryFactory.getInstance();
		SectorRepository sectorRepository = factory.getSectorRepositoryInstance();
		sectorRepository.crearSector(nom);
	}

	public List<BusinessSector> listarSectores() {
		RepositoryFactory factory = RepositoryFactory.getInstance();
		SectorRepository sectorRepository = factory.getSectorRepositoryInstance();
		List<Sector> list = sectorRepository.listarSectores();
		
		List<BusinessSector> ret = new LinkedList<BusinessSector>();
		for (Sector s : list){
			BusinessSector dt = new BusinessSector(s.getSectorid(),s.getNombre());
			ret.add(dt);
		}
		
		return ret;
	}
	

}
