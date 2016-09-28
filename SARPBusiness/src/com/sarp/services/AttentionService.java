package com.sarp.services;
import java.util.List;


import com.sarp.classes.BusinessPuesto;
import com.sarp.classes.BusinessSector;
import com.sarp.dao.controllers.DAOPuestoController;
import com.sarp.dao.factory.DAOFactory;
import com.sarp.dao.factory.DAOServiceFactory;
import com.sarp.enumerados.EstadoPuesto;
import com.sarp.exceptions.ContextException;
import com.sarp.factory.Factory;

public class AttentionService {
	
	public void abrirPuesto(String nombreMaquina) throws Exception{
		DAOServiceFactory daoServiceFactory = DAOServiceFactory.getInstance();
		DAOPuestoController controladorPuesto = daoServiceFactory.getDAOPuestoController();
		BusinessPuesto puesto = controladorPuesto.obtenerPuesto(nombreMaquina);
		if(puesto.getEstado() == EstadoPuesto.CERRADO){
			puesto.setEstado(EstadoPuesto.DIPONIBLE);
			//Se delega a DaoService
			controladorPuesto.modificarPuesto(puesto);
		}else{
			throw new ContextException("YaAbierto");
		}	
	}
	
	public void cerrarPuesto(String nombreMaquina) throws Exception{
		DAOServiceFactory daoServiceFactory = DAOServiceFactory.getInstance();
		DAOPuestoController controladorPuesto = daoServiceFactory.getDAOPuestoController();
		BusinessPuesto puesto = controladorPuesto.obtenerPuesto(nombreMaquina);
		if(puesto.getEstado() != EstadoPuesto.CERRADO){
			puesto.setEstado(EstadoPuesto.CERRADO);
			//Se delega a DaoService
			controladorPuesto.modificarPuesto(puesto);
		}else{
			throw new ContextException("YaCerrado");
		}		
	}
}
