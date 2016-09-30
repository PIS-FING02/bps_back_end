package com.sarp.services;
import java.util.List;


import com.sarp.classes.BusinessPuesto;
import com.sarp.classes.BusinessNumero;
import com.sarp.classes.BusinessSector;
import com.sarp.dao.controllers.DAOPuestoController;
import com.sarp.dao.factory.DAOFactory;
import com.sarp.dao.factory.DAOServiceFactory;
import com.sarp.enumerados.EstadoPuesto;
import com.sarp.exceptions.ContextException;
import com.sarp.factory.Factory;

public class AttentionService {
	
	public void abrirPuesto(String nombreMaquina,String usuarioId) throws Exception{
		DAOServiceFactory daoServiceFactory = DAOServiceFactory.getInstance();
		DAOPuestoController controladorPuesto = daoServiceFactory.getDAOPuestoController();
		BusinessPuesto puesto = controladorPuesto.obtenerPuesto(nombreMaquina);
		if(puesto.getEstado() == EstadoPuesto.CERRADO){
			puesto.setEstado(EstadoPuesto.DIPONIBLE);
			puesto.setUsuarioId(usuarioId);
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
			puesto.setUsuarioId(null);// se lo seteo como vacio al puesto
			//Se delega a DaoService
			controladorPuesto.modificarPuesto(puesto);
		}else{
			throw new ContextException("YaCerrado");
		}		
	}
	public void comenzarAtencion(String nombreMaquina) throws Exception{
		DAOServiceFactory daoServiceFactory = DAOServiceFactory.getInstance();
		DAOPuestoController controladorPuesto = daoServiceFactory.getDAOPuestoController();
		BusinessPuesto puesto = controladorPuesto.obtenerPuesto(nombreMaquina);
		if(puesto.getEstado() == EstadoPuesto.LLAMANDO){
			if(puesto.getNumeroAsignado() != null){
				puesto.setEstado(EstadoPuesto.ATENDIENDO);
				//Se delega a DaoService
				controladorPuesto.modificarPuesto(puesto);
			}else{
				throw new ContextException("PuestoSinNumeroAsignado");
			}
		}else{
			throw new ContextException("PuestoNoLlamando");
		}		
	}

	public void finalizarAtencion(String nombreMaquina) throws Exception{
		DAOServiceFactory daoServiceFactory = DAOServiceFactory.getInstance();
		DAOPuestoController controladorPuesto = daoServiceFactory.getDAOPuestoController();
		BusinessPuesto puesto = controladorPuesto.obtenerPuesto(nombreMaquina);
		if(puesto.getEstado() != EstadoPuesto.CERRADO){
			puesto.setEstado(EstadoPuesto.CERRADO);
			puesto.setUsuarioId(null);// se lo seteo como vacio al puesto
			//Se delega a DaoService
			controladorPuesto.modificarPuesto(puesto);
		}else{
			throw new ContextException("YaCerrado");
		}		
	}
}
