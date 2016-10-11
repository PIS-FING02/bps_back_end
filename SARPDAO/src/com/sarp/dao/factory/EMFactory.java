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

	private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("postgresUnit");
	/*
	 * Se trabaja con un �nico EntityManagerFactory que crea varios
	 * EntityManager, uno por transacci�n
	 */
	public static EntityManager getEntityManager() {
		
		/*String path_os;
		String[] result;
		String path;
		String location = EMFactory.class.getProtectionDomain().getCodeSource().getLocation().getPath().toString();
		result = location.split("/standalone");
		path_os = "/modules/conf/config_base.properties";
		path= result[0] + path_os;
		Properties prop = new Properties();
		InputStream input;
		try {
			input = new FileInputStream(path);
			prop.load(input);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		emf = Persistence.createEntityManagerFactory("postgresUnit", prop);*/
		
		return emf.createEntityManager();
	}

}
