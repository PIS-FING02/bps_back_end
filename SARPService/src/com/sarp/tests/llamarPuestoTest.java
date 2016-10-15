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
			
			String absolutePath = localPath+"guguguguguugugu.txt";
			
			
			File displayFile = new File(absolutePath);
			
			  synchronized(displayFile) {
			        
				  if(!displayFile.exists()) { 
					  displayFile.getParentFile().mkdirs(); 
					  displayFile.createNewFile();
				  }
					
					
					BufferedReader in = new BufferedReader(new FileReader(absolutePath));
										
					String line;
					boolean found = false;
					String nuevoArchivo = "";
					while ((line = in.readLine()) != null) {
						String[] parteLine = line.split("\\*");
						if(parteLine[4].equals(puesto)){
							nuevoArchivo = nuevoArchivo + "|260216123226*4*5*ATRIL*"+puesto+"*D*Juan*5**\n";
							found = true;
						}else{
							nuevoArchivo = nuevoArchivo + line + "\n";					
						}
					}

		
					PrintWriter writer = new PrintWriter(absolutePath);
					writer.print(displayFile);
					if(!found){
						writer.println("|260216123226*4*5*ATRIL*"+puesto+"*D*Juan*5**\n");
					}
					writer.close();
					in.close();
				 
			  }
	
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
