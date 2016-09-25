package com.sarp.dao.controllers;

import java.util.ArrayList;
import java.util.List;

import com.sarp.classes.BusinessPuesto;
import com.sarp.classes.BusinessSector;
import com.sarp.dao.factory.DAOFactory;
import com.sarp.dao.model.Puesto;
import com.sarp.dao.repository.DAOPuesto;

public class DAOPuestoController {

	public void crearPuesto(BusinessPuesto p){
		DAOFactory factory = DAOFactory.getInstance();
		DAOPuesto puestoRepository = factory.getPuestoRepository();	
		
		puestoRepository.insertPuesto(p.getNombreMaquina());
	}

	public BusinessPuesto obtenerPuesto(String nombreMaquina) throws Exception {
		DAOFactory factory = DAOFactory.getInstance();
		DAOPuesto puestoRepository = factory.getPuestoRepository();
		
		Puesto p = puestoRepository.selectPuesto(nombreMaquina);
		BusinessPuesto ret = new BusinessPuesto(p.getNombreMaquina(),p.getUsuarioId(),p.getEstado());
		return ret;	
	}

	public void eliminarPuesto(String nombreMaquina) throws Exception {
		DAOFactory factory = DAOFactory.getInstance();
		DAOPuesto puestoRepository = factory.getPuestoRepository();
				
		puestoRepository.deletePuesto(nombreMaquina);
		
	}

	public List<BusinessPuesto> listarPuestos() {
		DAOFactory factory = DAOFactory.getInstance();
		DAOPuesto puestoRepository = factory.getPuestoRepository();
		
		List<Puesto> lista = puestoRepository.selectPuestos();
		List<BusinessPuesto> ret = new ArrayList<BusinessPuesto>();
		for(Puesto p : lista){
			BusinessPuesto bp = new BusinessPuesto(p.getNombreMaquina(), p.getUsuarioId(), p.getUsuarioId());
			ret.add(bp);
		}		
		return ret;
	}

	public void modificarPuesto(BusinessPuesto puesto) throws Exception {
		DAOFactory factory = DAOFactory.getInstance();
		DAOPuesto puestoRepository = factory.getPuestoRepository();
		
		puestoRepository.updatePuesto(puesto.getNombreMaquina(), puesto.getEstado().toString());
		
	}

}