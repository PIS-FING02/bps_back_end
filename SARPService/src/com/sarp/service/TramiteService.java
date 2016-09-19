package com.sarp.service;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.jboss.resteasy.spi.BadRequestException;

import com.sarp.classes.BusinessTramite;
import com.sarp.factory.Factory;


@RequestScoped
@Path("/tramites")
public class TramiteService {
	
	@GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<BusinessTramite> listTramites(String rol) {
		try{
			Factory factory = Factory.getInstance();
			return factory.getAdminActionsController().listarTramites();
		}catch(Exception e){
			throw new BadRequestException("Error obtiendo tramites");
		}
    }
	
	@POST
	@Path("/{sector}/{nombre}")
	public String altaTramite( @PathParam("sector") int sector, @PathParam("nombre") String nombre){		
		try {
			Factory factory = Factory.getInstance();
			return "OK";
		} catch (Exception e) {
			throw new BadRequestException("Error en alta tramite");
		}
	}
	
}
