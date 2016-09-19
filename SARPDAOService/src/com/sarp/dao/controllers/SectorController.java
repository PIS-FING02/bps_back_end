package com.sarp.dao.controllers;

import java.util.LinkedList;
import java.util.List;

import com.sarp.classes.BusinessNumero;
import com.sarp.classes.BusinessSector;
import com.sarp.classes.BusinessTramite;
import com.sarp.dao.factory.DAOFactory;
import com.sarp.dao.model.Sector;
import com.sarp.dao.repository.DAONumero;
import com.sarp.dao.repository.DAOSector;
import com.sarp.dao.repository.DAOTramite;
import com.sun.corba.se.impl.orbutil.RepositoryIdFactory;

//import org.dao.repository.NumeroRepository;

public class SectorController {
	
	public void crearSector(String nom) throws Exception{
		System.out.println("hola desde crearSector");
		DAOFactory factory = DAOFactory.getInstance();
		DAOSector sectorRepository = factory.getSectorRepositoryInstance();
		sectorRepository.crearSector(nom);
	}

	public List<BusinessSector> listarSectores() {
		DAOFactory factory = DAOFactory.getInstance();
		DAOSector sectorRepository = factory.getSectorRepositoryInstance();
		List<Sector> list = sectorRepository.listarSectores();
		
		List<BusinessSector> ret = new LinkedList<BusinessSector>();
		for (Sector s : list){
			BusinessSector dt = new BusinessSector(s.getSectorid(),s.getNombre());
			ret.add(dt);
		}
		
		return ret;
	}
	

}
