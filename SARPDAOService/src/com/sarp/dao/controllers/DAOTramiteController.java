package com.sarp.dao.controllers;

import java.util.LinkedList;
import java.util.List;

import com.sarp.classes.BusinessTramite;
import com.sarp.dao.factory.DAOFactory;
import com.sarp.dao.model.Puesto;
import com.sarp.dao.model.Sector;
import com.sarp.dao.model.Tramite;
import com.sarp.dao.repository.DAOPuesto;
import com.sarp.dao.repository.DAOSector;
import com.sarp.dao.repository.DAOTramite;

//import org.dao.repository.NumeroRepository;

public class DAOTramiteController {
	
	public Integer crearTramite(BusinessTramite tramite) throws Exception{
		DAOFactory factory = DAOFactory.getInstance();
		DAOTramite tramiteRepository = factory.getTramiteRepository();
		
		Integer id = tramiteRepository.insertTramite(tramite.getNombre());
		return id;
	}
	
	public List<BusinessTramite> listarTramites() {
		DAOFactory factory = DAOFactory.getInstance();
		DAOTramite tramiteRepository = factory.getTramiteRepository();
		
		List<Tramite> list = tramiteRepository.selectTramites();
		List<BusinessTramite> ret = new LinkedList<BusinessTramite>();
		for (Tramite t : list){
			BusinessTramite dt = new BusinessTramite(t.getCodigo(), t.getNombre());
			ret.add(dt);
		}	
		return ret;
	}
	
	public BusinessTramite obtenerTramite(int codigo) throws Exception{
		DAOFactory factory = DAOFactory.getInstance();
		DAOTramite tramiteRepository = factory.getTramiteRepository();
		
		Tramite t = tramiteRepository.selectTramite(codigo);
		BusinessTramite ret = new BusinessTramite(t.getCodigo(),t.getNombre());
		return ret;	
	}
	
	public void modificarTramite(BusinessTramite t) throws Exception{
		DAOFactory factory = DAOFactory.getInstance();
		DAOTramite tramiteRepository = factory.getTramiteRepository();
		
		tramiteRepository.updateTramite(t.getCodigo(),t.getNombre());
	}
	
	public void eliminarTramite(int codigo) throws Exception{
		DAOFactory factory = DAOFactory.getInstance();
		DAOTramite tramiteRepository = factory.getTramiteRepository();
		
		tramiteRepository.deleteTramite(codigo);
	}

	public void asociarTramiteSector(int codigoTramite, int codigoSector) throws Exception{
		DAOFactory factory = DAOFactory.getInstance();
		DAOTramite tramiteRepository = factory.getTramiteRepository();
		DAOSector sectorRepository = factory.getSectorRepository();
		
		Sector s = sectorRepository.selectSector(codigoSector);
		Tramite t = tramiteRepository.selectTramite(codigoTramite);
		tramiteRepository.asociarTramiteSector(s, t);
	}
	
	public void asociarTramitePuesto(int codigoTramite, String nombreMaquina) throws Exception{
		DAOFactory factory = DAOFactory.getInstance();
		DAOTramite tramiteRepository = factory.getTramiteRepository();
		DAOPuesto puestoRepository = factory.getPuestoRepository();
		
		Puesto p = puestoRepository.selectPuesto(nombreMaquina);
		Tramite t = tramiteRepository.selectTramite(codigoTramite);
		tramiteRepository.asociarTramitePuesto(p, t);
	}

}