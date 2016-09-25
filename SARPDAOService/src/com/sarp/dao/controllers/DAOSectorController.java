package com.sarp.dao.controllers;

import java.util.LinkedList;
import java.util.List;

import com.sarp.classes.BusinessPuesto;
import com.sarp.classes.BusinessSector;
import com.sarp.dao.factory.DAOFactory;
import com.sarp.dao.model.Puesto;
import com.sarp.dao.model.Sector;
import com.sarp.dao.repository.DAOSector;



public class DAOSectorController {
	
	public void crearSector(String rutaSector, String nombre) throws Exception{
		DAOFactory factory = DAOFactory.getInstance();
		DAOSector sectorRepository = factory.getSectorRepository();
		
		sectorRepository.insertSector(rutaSector, nombre);
	}

	public List<BusinessSector> listarSectores() {
		DAOFactory factory = DAOFactory.getInstance();
		DAOSector sectorRepository = factory.getSectorRepository();
		
		List<Sector> list = sectorRepository.selectSectores();	
		List<BusinessSector> ret = new LinkedList<BusinessSector>();
		for (Sector s : list){
			BusinessSector dt = new BusinessSector(s.getCodigo(),s.getNombre());
			ret.add(dt);
		}
		return ret;
	}
	
	public List<BusinessPuesto> selectPuestoSector(BusinessSector sector) throws Exception {
		DAOFactory factory = DAOFactory.getInstance();
		DAOSector sectorRepository = factory.getSectorRepository();
		
		Sector s = sectorRepository.selectSector(sector.getSectorId());
		List<Puesto> list = s.getPuestos();
		List<BusinessPuesto> ret = new LinkedList<BusinessPuesto>();
		for(Puesto p : list){
			BusinessPuesto bp = new BusinessPuesto(p.getNombreMaquina(), p.getUsuarioId(), p.getUsuarioId());
			ret.add(bp);
		}	
		return ret;
	}
	

}
