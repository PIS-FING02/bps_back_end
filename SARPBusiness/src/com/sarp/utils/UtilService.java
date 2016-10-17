package com.sarp.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class UtilService {

	private static final String propertiesPath = UtilService.class.getProtectionDomain().getCodeSource().getLocation().getPath().toString().split("/standalone")[0] + "/modules/conf/sarp.properties";  

	public static Integer getIntegerProperty(String key){    
		return  Integer.valueOf(getProperty().getProperty(key));
	}
	
	public static String getStringProperty(String key){    
		return  getProperty().getProperty(key);
	}
	
	private static Properties getProperty(){
		Properties prop = new Properties(); 
		  InputStream input; 
		  try { 
			  input = new FileInputStream(propertiesPath);
			  prop.load(input);  
		  } catch(FileNotFoundException e) { 
			  e.printStackTrace(); 
		  } catch (IOException e){
			  e.printStackTrace(); 
		  }
		  return prop;
	}
	 
}
