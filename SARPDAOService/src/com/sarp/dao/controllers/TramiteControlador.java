package com.sarp.dao.controllers;

import java.util.LinkedList;
import java.util.List;

import com.sarp.classes.BusinessSector;
import com.sarp.classes.BusinessTramite;

import dao.repository.SectorRepository;
import dao.repository.TramiteRepository;
import model.Sector;
import model.Tramite;

//import org.dao.repository.NumeroRepository;

public class TramiteControlador {
	
	public void crearTramite(int codigoSector, String nom) throws Exception{
		System.out.println("hola desde crearTramite");
		RepositoryFactory factory = RepositoryFactory.getInstance();
		TramiteRepository tramiteRepository = factory.getTramiteRepositoryInstance();
		SectorRepository sectorRepository = factory.getSectorRepositoryInstance();
		System.out.println("hola desde crearTramite1");
		Sector s = sectorRepository.obtenerSector(codigoSector);
		System.out.println("hola desde crearTramite2" + s.getNombre());
		tramiteRepository.crearTramite(s, nom);
	}
	public List<BusinessTramite> listarTramites() {
		RepositoryFactory factory = RepositoryFactory.getInstance();
		TramiteRepository tramiteRepository = factory.getTramiteRepositoryInstance();
		List<Tramite> list = tramiteRepository.listarTramites();
		List<BusinessTramite> ret = new LinkedList<BusinessTramite>();
		for (Tramite t : list){
			BusinessTramite dt = new BusinessTramite(t.getCodigo(), t.getNombre());
			ret.add(dt);
		}
		
		return ret;
	}

}
