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
import com.sarp.json.modeler.JSONPuesto;
import com.sarp.service.response.maker.RequestMaker;

public class AttentionService {

	public void abrirPuesto(JSONPuesto puesto) throws Exception{

		DAOServiceFactory daoServiceFactory = DAOServiceFactory.getInstance();
		DAOPuestoController controladorPuesto = daoServiceFactory.getDAOPuestoController();
		BusinessPuesto bPuesto = controladorPuesto.obtenerPuesto(puesto.getNombreMaquina());

		if(bPuesto.getEstado() == EstadoPuesto.CERRADO && puesto.getUsuarioId()!= null){
			bPuesto.setEstado(EstadoPuesto.DIPONIBLE);
			bPuesto.setUsuarioId(puesto.getUsuarioId());
			//Se delega a DaoService
			controladorPuesto.modificarPuesto(bPuesto);
		}else{
			throw new ContextException("YaAbierto");
		}
	}

	public void cerrarPuesto(JSONPuesto puesto) throws Exception{
		DAOServiceFactory daoServiceFactory = DAOServiceFactory.getInstance();
		DAOPuestoController controladorPuesto = daoServiceFactory.getDAOPuestoController();
		BusinessPuesto bPuesto = controladorPuesto.obtenerPuesto(puesto.getNombreMaquina());

		if(bPuesto.getEstado() != EstadoPuesto.CERRADO){
			bPuesto.setEstado(EstadoPuesto.DIPONIBLE);
			bPuesto.setUsuarioId(null);
			//Se delega a DaoService
			controladorPuesto.modificarPuesto(bPuesto);
		}else{
			throw new ContextException("YaCerrado");
		}
	}

	public void comenzarAtencion(JSONPuesto puesto) throws Exception{
		RequestMaker reqMaker = RequestMaker.getInstance();
		BusinessNumero bNumero = reqMaker.requestNumero(puesto.getNumeroAsignado());

		DAOServiceFactory daoServiceFactory = DAOServiceFactory.getInstance();
		DAOPuestoController controladorPuesto = daoServiceFactory.getDAOPuestoController();
		BusinessPuesto puestoSend = controladorPuesto.obtenerPuesto(puesto.getNombreMaquina());
		if(puestoSend.getEstado() == EstadoPuesto.LLAMANDO){
			if(puesto.getNumeroAsignado() != null){
				puestoSend.setEstado(EstadoPuesto.ATENDIENDO);
				//Se delega a DaoService
				controladorPuesto.modificarPuesto(puestoSend);
				controladorPuesto.asociarNumeroPuestoActual(puestoSend.getNombreMaquina(),bNumero.getInternalId());
			}else{
				throw new ContextException("PuestoSinNumeroAsignado");
			}
		}else{
			throw new ContextException("PuestoNoLlamando");
		}
	}


	public void finalizarAtencion(JSONPuesto puesto) throws Exception{
		RequestMaker reqMaker = RequestMaker.getInstance();
		BusinessPuesto bPuesto = reqMaker.requestPuesto(puesto);
		DAOServiceFactory daoServiceFactory = DAOServiceFactory.getInstance();
		DAOPuestoController controladorPuesto = daoServiceFactory.getDAOPuestoController();
		BusinessPuesto puestoSend = controladorPuesto.obtenerPuesto(puesto.getNombreMaquina());
		if(puestoSend.getEstado() == EstadoPuesto.ATENDIENDO){
			puestoSend.setEstado(EstadoPuesto.DIPONIBLE);
			//Se delega a DaoService
			controladorPuesto.modificarPuesto(bPuesto);
			controladorPuesto.desasociarNumeroPuestoActual(puestoSend.getNombreMaquina());
		}else{
			throw new ContextException("PuestoNoAtendiendo");
		}
	}
}
