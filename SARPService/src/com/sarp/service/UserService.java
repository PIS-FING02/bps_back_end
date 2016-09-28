package com.sarp.service;

import javax.enterprise.context.RequestScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

import org.jboss.resteasy.spi.BadRequestException;

import com.sarp.controllers.UserController;
import com.sarp.factory.Factory;
import com.sarp.json.modeler.JSONPuesto;

@RequestScoped
@Path("/userService")
public class UserService {

}
