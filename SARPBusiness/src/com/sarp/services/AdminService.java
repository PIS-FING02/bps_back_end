package com.sarp.services;


import java.util.List;


import com.sarp.classes.BusinessPuesto;
import com.sarp.classes.BusinessSector;
import com.sarp.dao.controllers.DAOPuestoController;
import com.sarp.dao.factory.DAOFactory;
import com.sarp.dao.factory.DAOServiceFactory;
import com.sarp.enumerados.EstadoPuesto;
import com.sarp.factory.Factory;


public class AdminService {
	
	
	public void altaPuesto(String nombreMaquina) throws Exception{	
		DAOServiceFactory daoServiceFactory = DAOServiceFactory.getInstance();
		DAOPuestoController controladorPuesto = daoServiceFactory.getDAOPuestoController();
		BusinessPuesto puesto = new BusinessPuesto(nombreMaquina);
		//INSERT en DaoService
		controladorPuesto.insertPuesto(puesto);
		
	}
	
	public void bajaPuesto(String nombreMaquina) throws Exception{
		DAOServiceFactory daoServiceFactory = DAOServiceFactory.getInstance();
		DAOPuestoController controladorPuesto = daoServiceFactory.getDAOPuestoController();
		//DELETE en DaoService
		controladorPuesto.deletePuesto(nombreMaquina);
	}
		
	public void modificarUsuarioPuesto(String nombreMaquina,String usuarioId) throws Exception{
		DAOServiceFactory daoServiceFactory = DAOServiceFactory.getInstance();
		DAOPuestoController controladorPuesto = daoServiceFactory.getDAOPuestoController();
		BusinessPuesto puesto = controladorPuesto.selectPuesto(nombreMaquina);
		puesto.setUsuarioId(usuarioId);
		//UPDATE en DaoService
		controladorPuesto.deletePuesto(puesto);
	}

	public void modificarEstadoPuesto(String nombreMaquina, EstadoPuesto estado) throws Exception{
		DAOServiceFactory daoServiceFactory = DAOServiceFactory.getInstance();
		DAOPuestoController controladorPuesto = daoServiceFactory.getDAOPuestoController();
		BusinessPuesto puesto = controladorPuesto.selectPuesto(nombreMaquina);
		puesto.setEstado(estado);
		//UPDATE en DaoService
		controladorPuesto.deletePuesto(puesto);
		
	}
	
	
	public List<BusinessPuesto> listarPuestos(BusinessSector sector) throws Exception{
		DAOServiceFactory daoServiceFactory = DAOServiceFactory.getInstance();
		DAOPuestoController controladorPuesto = daoServiceFactory.getDAOPuestoController();
		List<BusinessPuesto> puestos;
		//Traigo los puestos de un sector desde DaoService
		//si sector es null entonces traigo todos los puestos del sistema		
		if(sector != null){
			puestos = controladorPuesto.selectPuestoSector(sector);
		}else{
			puestos = controladorPuesto.listarPuestos();
		}
		
		return puestos;
		
	}
	
}
