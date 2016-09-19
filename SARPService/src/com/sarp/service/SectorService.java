package com.sarp.service;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.sarp.classes.BusinessSector;
import com.sarp.dao.controllers.NumeroController;
import com.sarp.dao.controllers.SectorController;



@RequestScoped
@Path("/sectores")
public class SectorService {
	
	@GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<BusinessSector> listAllMembers() {
		com.sarp.dao.controllers.SectorController ctrl = new SectorController();
		return ctrl.listarSectores();
    }
	
	@POST
	@Path("/{nombre}")
	public String altaSector(
			@PathParam("nombre") String nombre){
		System.out.println("hola desde altaSector");
		com.sarp.dao.controllers.SectorController ctrl = new SectorController();
		try {
			ctrl.crearSector(nombre);
			return "OK";
		} catch (Exception e) {
			return e.toString();
		}
	}
	
}
