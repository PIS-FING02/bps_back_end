package com.sarp.test.dao;

import static org.junit.Assert.assertEquals;

import java.util.GregorianCalendar;

import org.junit.Test;

import com.sarp.classes.BusinessDatoComplementario;
import com.sarp.classes.BusinessDisplay;
import com.sarp.classes.BusinessNumero;
import com.sarp.classes.BusinessPuesto;
import com.sarp.classes.BusinessTramite;
import com.sarp.dao.controllers.DAONumeroController;
import com.sarp.dao.controllers.DAOPuestoController;
import com.sarp.dao.controllers.DAOTramiteController;


public class NumeroTest {

	DAONumeroController ctrlNumero = new DAONumeroController();
	DAOTramiteController ctrlTramite = new DAOTramiteController();
   
	@Test
	public void testNumero() throws Exception {	
	    BusinessNumero n = ctrlNumero.obtenerNumero(1);
		
	}
	

   
  
}