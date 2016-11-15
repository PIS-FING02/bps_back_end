package com.sarp.dao.factory;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class EMFactory {

	private static  final String propertiesPath = "/home/ubuntu/EAP-6.4.0/modules/conf/config_base.properties";
	private static EntityManagerFactory emf;
	//emf =Persistence.createEntityManagerFactory("postgresUnit");
	
	
	public static EntityManager getEntityManager() {
		Properties prop = getProperty();
		EMFactory.emf = Persistence.createEntityManagerFactory("postgresUnit", prop);
		return EMFactory.emf.createEntityManager();
		
	}
	
	private static Properties getProperty(){
		Properties prop = new Properties(); 
		  InputStream input; 
		  try { 
			  input = new FileInputStream(propertiesPath);
			  prop.load(input);  
		  } catch(FileNotFoundException e) { 
				  System.out.println("No se encontro la configuracion de la base de datos");
		  } catch (IOException e) {

			e.printStackTrace();
		}
		  return prop;
	}

}
