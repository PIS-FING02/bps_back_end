package com.sarp.dao.test;

import org.junit.Test;

import com.sarp.classes.BusinessPuesto;
import com.sarp.dao.controllers.DAONumeroController;
import com.sarp.dao.controllers.DAOPuestoController;


public class NumeroTest {

	DAONumeroController ctrlNumero = new DAONumeroController();
   
	@Test
	public void testNumero() throws Exception {	
	}
	
	
	@Test
	public void testAsignarPuestoActual() throws Exception {	
		ctrlNumero.asociarNumeroPuestoActual(2, "maquina3");		
	}
   
  
}