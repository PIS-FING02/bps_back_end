package com.sarp.dao.controllers;

import java.util.LinkedList;
import java.util.List;

import com.sarp.classes.BusinessSector;
import com.sarp.dao.factory.DAOFactory;
import com.sarp.dao.model.Sector;
import com.sarp.dao.repository.DAOSector;



public class DAOSectorController {
	
	public void crearSector(Integer codigo, String nom) throws Exception{
		DAOFactory factory = DAOFactory.getInstance();
		DAOSector sectorRepository = factory.getSectorRepository();
		sectorRepository.crearSector(codigo, nom);
	}

	public List<BusinessSector> listarSectores() {
		DAOFactory factory = DAOFactory.getInstance();
		DAOSector sectorRepository = factory.getSectorRepository();
		List<Sector> list = sectorRepository.listarSectores();
		
		List<BusinessSector> ret = new LinkedList<BusinessSector>();
		for (Sector s : list){
			BusinessSector dt = new BusinessSector(s.getCodigo(),s.getNombre());
			ret.add(dt);
		}
		return ret;
	}
	

}
