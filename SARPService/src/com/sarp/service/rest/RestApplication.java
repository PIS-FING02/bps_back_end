package com.sarp.service.rest;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import com.sarp.thread.ThreadManager;

@ApplicationPath("/")
public class RestApplication extends Application {
	
	public RestApplication(){
		
		/*ThreadManager tm = */ThreadManager.getInstance();
		
	}
}
