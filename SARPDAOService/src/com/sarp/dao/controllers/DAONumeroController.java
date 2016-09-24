package com.sarp.dao.controllers;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import com.sarp.classes.BusinessNumero;
import com.sarp.classes.BusinessTramite;
import com.sarp.dao.factory.DAOFactory;
import com.sarp.dao.model.Numero;
import com.sarp.dao.model.Sector;
import com.sarp.dao.model.Tramite;
import com.sarp.dao.repository.DAONumero;
import com.sarp.dao.repository.DAOSector;
import com.sarp.dao.repository.DAOTramite;
import com.sun.corba.se.impl.orbutil.RepositoryIdFactory;

//import org.dao.repository.NumeroRepository;

public class DAONumeroController {
	
	public void crearNumero(int sec,int tram, String nombre, Integer codigo) throws Exception{
		DAOFactory factory = DAOFactory.getInstance();
		DAONumero numeroRepository = factory.getNumeroRepository();
		DAOSector sectorRepository = factory.getSectorRepository();
		DAOTramite tramiteRepository = factory.getTramiteRepository();
		Sector s = sectorRepository.obtenerSector(sec);
		if(s != null){
			List<Tramite> tList = s.getTramites();
			boolean existe = false;
			for (Tramite t:tList){
				if (t.getCodigo() == tram){
					numeroRepository.crearNumero(t, nombre, codigo);
					existe = true;
				}
			}
			if (!existe) {
				throw new Exception("El sector no contiene al tramite con el codigo solicitado");
			}			
		}else{
			throw new Exception("No se encontro el sector solicitado");
		}
	}
	
	public List<BusinessNumero> listarNumeros(){
		DAOFactory factory = DAOFactory.getInstance();
		DAONumero numeroRepository = factory.getNumeroRepository();
		
		List<Numero> list = numeroRepository.listarNumeros();
		List<BusinessNumero> ret = new LinkedList<BusinessNumero>();
		for (Numero numero : list){
			BusinessNumero businessNumero = new BusinessNumero(numero.getInternalId(), numero.getTramite().getCodigo(), 1); // VER BIEN ACA QUE SE LE PASA
			ret.add(businessNumero);
		}
		return ret;
	}
	
	public boolean existeNumero(int id){
		DAOFactory factory = DAOFactory.getInstance();
		DAONumero numeroRepository = factory.getNumeroRepository();
		Numero n = numeroRepository.obtenerNumero(id);
		return n != null;
	}
	
	public void modificarNumero(int codigo, String estado, int prioridad){
		DAOFactory factory = DAOFactory.getInstance();
		DAONumero numeroRepository = factory.getNumeroRepository();
		Numero n = numeroRepository.obtenerNumero(codigo);
		numeroRepository.modificarNumero(n, estado, prioridad);
	}
	
	public void eliminarNumero(int codigo){
		DAOFactory factory = DAOFactory.getInstance();
		DAONumero numeroRepository = factory.getNumeroRepository();
		Numero n = numeroRepository.obtenerNumero(codigo);
		numeroRepository.eliminarNumero(n);
	}

	public LinkedList<BusinessNumero> obtenerNumerosDelDia() {
		// Retornar todos los numeros con la fecha del dia actual
		return null;
	}

}
