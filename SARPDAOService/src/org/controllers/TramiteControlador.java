package org.controllers;

import java.util.LinkedList;
import java.util.List;

import com.sun.corba.se.impl.orbutil.RepositoryIdFactory;

import dao.repository.NumeroRepository;
import dao.repository.SectorRepository;
import dao.repository.TramiteRepository;
import dataTypes.DtSector;
import dataTypes.DtTramite;
import model.Numero;
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
	public List<DtTramite> listarTramites() {
		RepositoryFactory factory = RepositoryFactory.getInstance();
		TramiteRepository tramiteRepository = factory.getTramiteRepositoryInstance();
		List<Tramite> list = tramiteRepository.listarTramites();
		List<DtTramite> ret = new LinkedList<DtTramite>();
		for (Tramite t : list){
			DtTramite dt = new DtTramite(t.getCodigo(), t.getNombre());
			ret.add(dt);
		}
		
		return ret;
	}

}
