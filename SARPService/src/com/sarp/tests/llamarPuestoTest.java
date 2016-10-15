package com.sarp.tests;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

import org.junit.Test;

public class llamarPuestoTest {
	
	@Test
	public void llamarNumeroTesteo(){
		try{
		
			
	String localPath = "/Users/facevedo/";//.property
			
			String puesto = "Puesto1";
			
			String absolutePath = localPath+"maruputita.txt";
			
			
			File displayFile = new File(absolutePath);
			

			        

					  displayFile.getParentFile().mkdirs(); 
					  displayFile.createNewFile();
					  
					  displayFile.getParentFile().mkdirs(); 
					  displayFile.createNewFile();

	
	
		}catch(IOException e){
			System.out.println(e.getMessage());
		}
	}
	
	/*@Test
	public void atrasarNumeroTesteo(){
		

				
		
	}
	@Test
	public void pausarNumeroTesteo(){
		
		
	
		
	}*/
}
