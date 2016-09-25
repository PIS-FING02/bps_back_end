package com.sarp.dao.controllers;

import java.util.ArrayList;
import java.util.List;
import com.sarp.classes.BusinessDisplay;
import com.sarp.dao.factory.DAOFactory;
import com.sarp.dao.model.Display;
import com.sarp.dao.model.Numero;
import com.sarp.dao.repository.DAODisplay;
import com.sarp.dao.repository.DAONumero;

public class DAODisplayController {
			
	/* Dada una ruta, creo en la base una entidad Display con esa ruta */
	public Integer crearDisplay(BusinessDisplay d){
		DAOFactory factory = DAOFactory.getInstance();
		DAODisplay displayRepository = factory.getDisplayRepository();	
		
		Integer id = displayRepository.insertDisplay(d.getRutaArchivo());
		return id;
	}
	
	public BusinessDisplay obtenerDisplay(int codigo) throws Exception{		
		DAOFactory factory = DAOFactory.getInstance();
		DAODisplay displayRepository = factory.getDisplayRepository();
		
		Display d = displayRepository.selectDisplay(codigo);
		BusinessDisplay ret = new BusinessDisplay(d.getCodigo(),d.getRutaArchivo());
		return ret;	
	}
	
	public List<BusinessDisplay> listarDisplays(){
		DAOFactory factory = DAOFactory.getInstance();
		DAODisplay displayRepository = factory.getDisplayRepository();
		
		List<Display> lista = displayRepository.selectDisplays();
		List<BusinessDisplay> ret = new ArrayList<BusinessDisplay>();
		for(Display md : lista){
			BusinessDisplay d = new BusinessDisplay(md.getCodigo(),md.getRutaArchivo());
			ret.add(d);
		}		
		return ret;
	}
	
	public void modificarDisplay(BusinessDisplay d) throws Exception{
		DAOFactory factory = DAOFactory.getInstance();
		DAODisplay displayRepository = factory.getDisplayRepository();
		
		displayRepository.updateDisplay(d.getCodigo(), d.getRutaArchivo());
	}
	
	public void eliminarDisplay(int codigo) throws Exception{
		DAOFactory factory = DAOFactory.getInstance();
		DAODisplay displayRepository = factory.getDisplayRepository();
		
		displayRepository.deleteDisplay(codigo);
	}

}
