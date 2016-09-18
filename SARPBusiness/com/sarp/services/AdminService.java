package com.sarp.services;


import java.util.List;

import com.sarp.controladores;
import com.sarp.classes.BusinessPuesto;
import com.sarp.enumerados.EstadoPuesto;
import com.sarp.logic.Estado;


public class AdminService {
	
	
	public void altaPuesto(String nombreMaquina) throws Exception{	
		Factory factoryServices = Factory.getInstance();
		PuestoControlador controladorPuesto =factoryServices.getPuestoControlador();
		BusinessPuesto puesto = new BusinessPuesto(nombreMaquina);
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
		BusinessPuesto puesto = controladorPuesto.selectPuesto(nombreMaquina);
		puesto.setUsuarioId(usuarioId);
		//UPDATE en DaoService
		controladorPuesto.updatePuesto(puesto);
	}

	public void modificarEstadoPuesto(String nombreMaquina, EstadoPuesto estado) throws Exception{
		Factory factoryServices = Factory.getInstance();
		PuestoControlador controladorPuesto =factoryServices.getPuestoControlador();
		BusinessPuesto puesto = controladorPuesto.selectPuesto(nombreMaquina);
		puesto.setEstado(estado);
		//UPDATE en DaoService
		controladorPuesto.updatePuesto(puesto);
		
	}
	
	
	public List<BusinessPuesto> listarPuestos(BusinessSector sector) throws Exception{
		Factory factoryServices = Factory.getInstance();
		PuestoControlador controladorPuesto =factoryServices.getPuestoControlador();
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
