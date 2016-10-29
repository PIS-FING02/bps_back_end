package com.sarp.beans;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import com.sarp.controllers.NumberController;
import com.sarp.factory.Factory;
import com.sarp.json.modeler.JSONNumero;

/**
 * Session Bean implementation class NumberBean
 */
@Stateless
@LocalBean
public class NumberBean {

    /**
     * Default constructor. 
     */
	
	public static NumberController ctrl;
	
    public NumberBean() {
        // TODO Auto-generated constructor stub
		Factory fac = Factory.getInstance();
		ctrl = fac.getNumberController();
    }
    
    public List<JSONNumero> listarNumerosSector(String idSector) throws Exception {
		
		return ctrl.listarNumerosSector(idSector);
	}
	
	public List<JSONNumero> listarNumerosPausados(String idPuesto) throws Exception {
		
		return ctrl.listarNumerosPausados(idPuesto);
	}
	
	public List<JSONNumero> listarNumerosPausadosSector(String idSector) throws Exception {
		return ctrl.listarNumerosPausadosSector(idSector);
	}
	
	public List<JSONNumero> listarNumerosAtrasados(String idPuesto) throws Exception {
	
		return ctrl.listarNumerosAtrasados(idPuesto);
	}
	
	public List<JSONNumero> listarNumerosAtrasadosSector(String idSector) throws Exception {
		
		return ctrl.listarNumerosAtrasadosSector(idSector);
	}
	
	public List<JSONNumero> listarNumerosEnEspera(String idPuesto) throws Exception {
		
		return ctrl.listarNumerosEnEspera(idPuesto);
	}
	
	public List<JSONNumero> listarNumerosEnEsperaSector(String idSector) throws Exception {
		
		return ctrl.listarNumerosEnEsperaSector(idSector);
	}
    
    
    
    
    
    

}
