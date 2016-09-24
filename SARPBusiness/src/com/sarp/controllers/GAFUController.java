package com.sarp.controllers;

import com.sarp.managers.GAFUManager;

public class GAFUController {
	
	private GAFUManager gafumgr;
	
	public GAFUController(){
		this.gafumgr = GAFUManager.getInstance();

	}
	
	public void crearArbolGAFU(){
		this.gafumgr.crearArbolGAFU();
	}
	
	
}

