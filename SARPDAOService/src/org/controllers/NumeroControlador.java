package org.controllers;

import java.util.Iterator;
import java.util.List;

import com.sarp.clases.Numero;
import com.sarp.clases.Sector;
import com.sarp.clases.Tramite;
import com.sun.corba.se.impl.orbutil.RepositoryIdFactory;

import dao.repository.NumeroRepository;
import dao.repository.SectorRepository;
import dao.repository.TramiteRepository;

//import org.dao.repository.NumeroRepository;

public class NumeroControlador {
	
	public void crearNumero(int sec,int tram, String nombre) throws Exception{
		System.out.println("hola desde crearNumero");
		RepositoryFactory factory = RepositoryFactory.getInstance();
		NumeroRepository numeroRepository = factory.getNumeroRepositoryInstance();
		SectorRepository sectorRepository = factory.getSectorRepositoryInstance();
		TramiteRepository tramiteRepository = factory.getTramiteRepositoryInstance();
		System.out.println("hola desde crearNumero2");
		Sector s = sectorRepository.obtenerSector(sec);
		if(s != null){
			List<Tramite> tList = s.getTramites();
			boolean existe = false;
			for (Tramite t:tList){
				if (t.getCodigo() == tram){
					System.out.println("hola desde crearNumero3");
					numeroRepository.crearNumero(t, nombre);
					existe = true;
				}
			}
			if (!existe) {
				System.out.println("hola desde crearNumero4");
				throw new Exception("El sector no contiene al tramite con el codigo solicitado");
			}			
		}else{
			throw new Exception("No se encontro el sector solicitado");
		}
	}
	
	public List<Numero> listarNumeros(){
		RepositoryFactory factory = RepositoryFactory.getInstance();
		NumeroRepository numeroRepository = factory.getNumeroRepositoryInstance();
		return numeroRepository.listarNumeros();
	}
	public boolean existeNumero(int id){
		RepositoryFactory factory = RepositoryFactory.getInstance();
		NumeroRepository numeroRepository = factory.getNumeroRepositoryInstance();
		Numero n = numeroRepository.obtenerNumero(id);
		return n != null;
	}
	
	public void modificarNumero(int codigo, String estado, int prioridad){
		RepositoryFactory factory = RepositoryFactory.getInstance();
		NumeroRepository numeroRepository = factory.getNumeroRepositoryInstance();
		Numero n = numeroRepository.obtenerNumero(codigo);
		numeroRepository.modificarNumero(n, estado, prioridad);
	}
	
	public void eliminarNumero(int codigo){
		RepositoryFactory factory = RepositoryFactory.getInstance();
		NumeroRepository numeroRepository = factory.getNumeroRepositoryInstance();
		Numero n = numeroRepository.obtenerNumero(codigo);
		numeroRepository.eliminarNumero(n);
	}

}
