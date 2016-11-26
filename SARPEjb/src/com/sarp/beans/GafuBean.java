package com.sarp.beans;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import com.sarp.classes.BusinessNodeGAFU;
import com.sarp.classes.BusinessSectorRol;
import com.sarp.controllers.GAFUController;
import com.sarp.factory.Factory;

/**
 * Session Bean implementation class GafuBean
 */
@Stateless
@LocalBean
public class GafuBean {

    /**
     * Default constructor. 
     */
    private static GAFUController gafu;
	public GafuBean() {
        // TODO Auto-generated constructor stub
		Factory fac = Factory.getInstance();
		gafu = fac.GAFUController();
    }
    
 
	
	public void actualizarArbolGAFU() throws Exception{
		gafu.actualizarArbolGAFU();
	}
	
	public void imprimirArbol(String appender){
		gafu.imprimirArbol(appender);
	}

	public void imprimirSubArbol(BusinessNodeGAFU node, String appender){
		gafu.imprimirSubArbol(node, appender);
	}
	
	public BusinessNodeGAFU BusquedaNodo(String codigo){
		return gafu.BusquedaNodo(codigo);
	}
	public List<BusinessSectorRol>  obtenerSectorRolesUsuario ( String idUsuario ) throws Exception{
		System.out.println("entro al controler");
		return gafu.obtenerSectorRolesUsuario(idUsuario);
	}
	public List<BusinessSectorRol>  obtenerSectorRolesUsuario ( String idUsuario, String rol )throws Exception{
		return gafu.obtenerSectorRolesUsuario(idUsuario,rol);
	}

}
