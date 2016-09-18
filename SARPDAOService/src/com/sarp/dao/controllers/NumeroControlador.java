package com.sarp.dao.controllers;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import com.sarp.classes.BusinessNumero;
import com.sarp.classes.BusinessTramite;
import com.sun.corba.se.impl.orbutil.RepositoryIdFactory;

import dao.repository.NumeroRepository;
import dao.repository.SectorRepository;
import dao.repository.TramiteRepository;
import model.Numero;
import model.Sector;
import model.Tramite;

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
	
	public List<BusinessNumero> listarNumeros(){
		RepositoryFactory factory = RepositoryFactory.getInstance();
		NumeroRepository numeroRepository = factory.getNumeroRepositoryInstance();
		
		List<Numero> list = numeroRepository.listarNumeros();
		List<BusinessNumero> ret = new LinkedList<BusinessNumero>();
		for (Numero numero : list){
			BusinessNumero businessNumero = new BusinessNumero(numero.getInternalId(), numero.getTramite().getCodigo(), 1); // VER BIEN ACA QUE SE LE PASA
			ret.add(businessNumero);
		}
		return ret;
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
