package com.sarp.dao.controllers;

import java.util.LinkedList;
import java.util.List;

import com.sarp.classes.BusinessDisplay;
import com.sarp.classes.BusinessPuesto;
import com.sarp.classes.BusinessSector;
import com.sarp.classes.BusinessTramite;
import com.sarp.dao.factory.DAOFactory;
import com.sarp.dao.model.Display;
import com.sarp.dao.model.Puesto;
import com.sarp.dao.model.Sector;
import com.sarp.dao.model.Tramite;
import com.sarp.dao.repository.DAOSector;



public class DAOSectorController {
	
	public void crearSector(BusinessSector sector) throws Exception{
		DAOFactory factory = DAOFactory.getInstance();
		DAOSector sectorRepository = factory.getSectorRepository();
		
		sectorRepository.insertSector(sector.getSectorId(), sector.getNombre(),sector.getRuta());
	}

	public List<BusinessSector> listarSectores() {
		DAOFactory factory = DAOFactory.getInstance();
		DAOSector sectorRepository = factory.getSectorRepository();
		
		List<Sector> list = sectorRepository.selectSectores();	
		List<BusinessSector> ret = new LinkedList<BusinessSector>();
		for (Sector s : list){

			BusinessSector dt = new BusinessSector(s.getCodigo(),s.getNombre(),s.getDisplay().getCodigo(),s.getRutaSector());
			ret.add(dt);
		}
		return ret;
	}
	
	public List<BusinessPuesto> selectPuestosSector(Integer sectorID) throws Exception {
		DAOFactory factory = DAOFactory.getInstance();
		DAOSector sectorRepository = factory.getSectorRepository();
		
		Sector s = sectorRepository.selectSector(sectorID);
		List<Puesto> list = s.getPuestos();
		List<BusinessPuesto> ret = new LinkedList<BusinessPuesto>();
		for(Puesto p : list){
			BusinessPuesto bp = new BusinessPuesto(p.getNombreMaquina(), p.getUsuarioId(), p.getUsuarioId());
			ret.add(bp);
		}	
		return ret;
	}
	
	public List<BusinessTramite> selectTramitesSector(Integer sectorID) throws Exception {
		DAOFactory factory = DAOFactory.getInstance();
		DAOSector sectorRepository = factory.getSectorRepository();
		
		Sector s = sectorRepository.selectSector(sectorID);
		List<Tramite> list = s.getTramites();
		List<BusinessTramite> ret = new LinkedList<BusinessTramite>();
		for(Tramite t : list){
			BusinessTramite bt = new BusinessTramite(t.getCodigo(), t.getNombre());
			ret.add(bt);
		}	
		return ret;
	}
	
	public BusinessDisplay selectDisplaySector(Integer sectorID) throws Exception {
		DAOFactory factory = DAOFactory.getInstance();
		DAOSector sectorRepository = factory.getSectorRepository();
		
		Sector s = sectorRepository.selectSector(sectorID);
		Display d = s.getDisplay();
		BusinessDisplay ret = new BusinessDisplay(d.getCodigo(), d.getRutaArchivo());
		return ret;
	}

}
