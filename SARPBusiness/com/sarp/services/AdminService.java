package com.sarp.services;


import java.util.List;

import com.sarp.controladores;
import com.sarp.clases.Puesto;
import com.sarp.enumerados.EstadoPuesto;
import com.sarp.logic.Estado;


public class AdminService {
	
	
	public void altaPuesto(String nombreMaquina) throws Exception{	
		Factory factoryServices = Factory.getInstance();
		PuestoControlador controladorPuesto =factoryServices.getPuestoControlador();
		Puesto puesto = new Puesto(nombreMaquina);
		//INSERT en DaoService
		controladorPuesto.insertPuesto(puesto);
		
	}
	
	public void bajaPuesto(String nombreMaquina) throws Exception{
		Factory factoryServices = Factory.getInstance();
		PuestoControlador controladorPuesto =factoryServices.getPuestoControlador();
		//DELETE en DaoService
		controladorPuesto.deletePuesto(nombreMaquina);
	}
		
	public void modificarUsuarioPuesto(String nombreMaquina,String usuarioId) throws Exception{
		Factory factoryServices = Factory.getInstance();
		PuestoControlador controladorPuesto =factoryServices.getPuestoControlador();
		Puesto puesto = controladorPuesto.selectPuesto(nombreMaquina);
		puesto.setUsuarioId(usuarioId);
		//UPDATE en DaoService
		controladorPuesto.updatePuesto(puesto);
	}

	public void modificarEstadoPuesto(String nombreMaquina, EstadoPuesto estado) throws Exception{
		Factory factoryServices = Factory.getInstance();
		PuestoControlador controladorPuesto =factoryServices.getPuestoControlador();
		Puesto puesto = controladorPuesto.selectPuesto(nombreMaquina);
		puesto.setEstado(estado);
		//UPDATE en DaoService
		controladorPuesto.updatePuesto(puesto);
		
	}
	
	public void actualizarFechaModificacionPuesto(String nombreMaquina) throws Exception{
		Factory factoryServices = Factory.getInstance();
		PuestoControlador controladorPuesto =factoryServices.getPuestoControlador();
		Puesto puesto = controladorPuesto.selectPuesto(nombreMaquina);
		//fecha del momento
		Date fechaActual = new Date();
		puesto.setLastUpdate(fechaActual);
		//UPDATE en DaoService
		controladorPuesto.updatePuesto(puesto);
	}
	
	public List<Puesto> listarPuestos(Sector sector) throws Exception{
		Factory factoryServices = Factory.getInstance();
		PuestoControlador controladorPuesto =factoryServices.getPuestoControlador();
		List<Puesto> puestos;
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
