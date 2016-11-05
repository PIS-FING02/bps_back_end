package com.sarp.beans;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import com.sarp.controllers.AttentionsController;
import com.sarp.factory.Factory;
import com.sarp.json.modeler.JSONEstadoPuesto;
import com.sarp.json.modeler.JSONFinalizarAtencion;
import com.sarp.json.modeler.JSONNumero;
import com.sarp.json.modeler.JSONPuesto;
import com.sarp.json.modeler.JSONSector;
import com.sarp.json.modeler.JSONTramiteSector;
import com.sarp.services.AttentionService;
import com.sarp.services.NumberService;

/**
 * Session Bean implementation class AttentionsBean
 */
@Stateless
@LocalBean
public class AttentionsBean {

    /**
     * Default constructor. 
     */
	private static AttentionsController ctrl;
	
    public AttentionsBean() {
        // TODO Auto-generated constructor stub
    	Factory fac = Factory.getInstance();
		ctrl = fac.getAttentionsController();
    }
    
    public String solicitarNumero(JSONNumero num) throws Exception {
		
		return ctrl.solicitarNumero(num);
	}
	
	public JSONEstadoPuesto abrirPuesto(JSONPuesto puesto) throws Exception{	
		return ctrl.abrirPuesto(puesto);
	}
	public void cerrarPuesto(JSONPuesto puesto) throws Exception{	
		ctrl.cerrarPuesto(puesto);
		
	}
	public void comenzarAtencion(JSONPuesto puesto) throws Exception{	
		//Se delega a AttentionService la implementacion
		ctrl.comenzarAtencion(puesto);
	}
	public void finalizarAtencion(JSONFinalizarAtencion finalizarAtencion) throws Exception{	
		ctrl.finalizarAtencion(finalizarAtencion);
	}
	
	public List<JSONTramiteSector> tramitesRecepcion(String puesto) throws Exception{
		return ctrl.tramitesRecepcion(puesto);
	}
	public JSONNumero llamarNumero(String puesto) throws Exception{
		return ctrl.llamarNumero(puesto);
	}
	
	public void atrasarNumero(JSONPuesto puesto) throws Exception{
		ctrl.atrasarNumero(puesto);
	}
	
	public void pausarNumero(JSONPuesto puesto) throws Exception{
		ctrl.pausarNumero(puesto);
	}
	
	public JSONNumero llamarNumeroPausado(Integer idNumero, String idPuesto) throws Exception{
		return ctrl.llamarNumeroPausado(idNumero, idPuesto);
	}
	
	public JSONNumero llamarNumeroAtrasado(Integer idNumero, String idPuesto) throws Exception{
		return ctrl.llamarNumeroAtrasado(idNumero, idPuesto);
	}
	
	public JSONNumero llamarNumeroDemanda(Integer idNumero, String idPuesto) throws Exception{
		return ctrl.llamarNumeroDemanda(idNumero, idPuesto);
	}
	
	public List<JSONSector> obtenerSectoresDesvio(String idSector) throws Exception {
		return ctrl.obtenerSectoresDesvio(idSector);
	
	}
	
	public void desviarNumero(String idSectorDesvio,JSONFinalizarAtencion finalizarAtencion) throws Exception {
		ctrl.desviarNumero(idSectorDesvio,finalizarAtencion);
	}
	
	public void reLlamarNumero(String idPuesto) throws Exception {
		ctrl.reLlamarNumero(idPuesto);	
	}

}
