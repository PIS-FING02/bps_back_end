package org.controllers;

import java.util.LinkedList;
import java.util.List;

import classes.Sector;
import classes.Tramite;
import dao.repository.SectorRepository;
import dao.repository.TramiteRepository;

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
	public List<Tramite> listarTramites() {
		RepositoryFactory factory = RepositoryFactory.getInstance();
		TramiteRepository tramiteRepository = factory.getTramiteRepositoryInstance();
		List<Tramite> list = tramiteRepository.listarTramites();
		List<Tramite> ret = new LinkedList<Tramite>();
		for (Tramite t : list){
			Tramite dt = new Tramite(t.getCodigo(), t.getNombre());
			ret.add(dt);
		}
		
		return ret;
	}

}
