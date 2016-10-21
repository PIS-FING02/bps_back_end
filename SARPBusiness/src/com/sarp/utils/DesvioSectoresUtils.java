package com.sarp.utils;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Enumeration;
import java.util.Properties;
import java.util.Set;
 
public class DesvioSectoresUtils {
 
    private Properties prop = null;
     
    public DesvioSectoresUtils(){
         
        InputStream is = null;
        try {
            this.prop = new Properties();
            is = this.getClass().getResourceAsStream("/Users/facevedo/Documents/desvios.properties");
            prop.load(is);
            int a =2 ;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
     
    public Set<Object> getAllKeys(){
        Set<Object> keys = prop.keySet();
        return keys;
    }
     
    public String getPropertyValue(String key){
        return this.prop.getProperty(key);
    }
     
    public static void main(String a[]){
    	
    	try{
    		Properties pro = new Properties();
        	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        	  System.out.print("Enter file name for getting all properties : ");
        	  FileInputStream in = new FileInputStream("/Users/facevedo/Documents/desvios" + ".properties");
        	  pro.load(in);
        	  
        	  System.out.println("All keys of the property file : ");
        	  System.out.println(pro.keySet());
        	  System.out.println("All values of the property file : ");
        	  Enumeration em = pro.keys();
        	  
        	  while(em.hasMoreElements()){
    	    	  String str = (String)em.nextElement();
    	    	  System.out.println(str + ": " + pro.get(str));
        	  }
    	}catch(Exception e){
    		System.out.print(e.getMessage());
    	}
    	
   }
    

    
}