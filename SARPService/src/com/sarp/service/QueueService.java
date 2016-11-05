package com.sarp.service;

import javax.enterprise.context.RequestScoped;

import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.jboss.resteasy.spi.BadRequestException;
import com.sarp.controllers.QueueController;
import com.sarp.factory.Factory;
import com.sarp.json.modeler.JSONNumero;
import com.sarp.json.modeler.JSONSector;

@RequestScoped
@Path("/queueService")
public class QueueService {

	
}
