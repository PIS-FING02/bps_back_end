package com.sarp.dao.controllers;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import com.sarp.classes.BusinessNumero;
import com.sarp.classes.BusinessTramite;
import com.sarp.dao.factory.DAOFactory;
import com.sarp.dao.model.Numero;
import com.sarp.dao.model.Sector;
import com.sarp.dao.model.Tramite;
import com.sarp.dao.model.Display;
import com.sarp.dao.repository.DAODisplay;
import com.sarp.dao.repository.DAONumero;
import com.sarp.dao.repository.DAOSector;
import com.sarp.dao.repository.DAOTramite;
import com.sun.corba.se.impl.orbutil.RepositoryIdFactory;

import com.sarp.classes.*;


//import org.dao.repository.NumeroRepository;

public class DAODisplayController {
		
	
	/* Dada una ruta, creo en la base una entidad Display con esa ruta */
	public void crearDisplay(Display d){
		DAOFactory factory = DAOFactory.getInstance();
		DAODisplay displayRepository = factory.getDisplayReository();
		displayRepository.insertDisplay(d.getRutaArchivo());
	}
	
	public BusinessDisplay obtenerDisplay(int codigo) throws Exception{
		DAOFactory factory = DAOFactory.getInstance();
		DAODisplay displayRepository = factory.getDisplayReository();
		com.sarp.dao.model.Display d = displayRepository.selectDisplay(codigo);
		BusinessDisplay ret = new BusinessDisplay(d.getCodigo(),d.getRutaArchivo(),d.getDateCreated(),d.getLastUpdated());
		return ret;	
	}
	
	public List<Display> listarDisplays(){
		DAOFactory factory = DAOFactory.getInstance();
		DAODisplay displayRepository = factory.getDisplayReository();
		List<com.sarp.dao.model.Display> lista = displayRepository.selectDisplays();
		List<Display> ret = new ArrayList<Display>();
		for(com.sarp.dao.model.Display md : lista){
			Display d = new Display();
			d.setRutaArchivo(md.getRutaArchivo());
			d.setLastUpdated(md.getLastUpdated());
			ret.add(d);
		}		
		return ret;
	}
	
	public void modificarDisplay(Display d) throws Exception{
		DAOFactory factory = DAOFactory.getInstance();
		DAODisplay displayRepository = factory.getDisplayReository();
		displayRepository.updateDisplay(d.getCodigo(), d.getRutaArchivo());
	}

}
