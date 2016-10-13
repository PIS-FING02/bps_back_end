package com.sarp.controllers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;



public class DisplayController {
	
	private static DisplayController instance = null;
	private DisplayController(){};
	
	public static DisplayController getInstance(){
		  instance = (instance!= null)? instance : new DisplayController();
		  
		 return instance;
	}
	
	public void llamarEnDisplay(String idDisplay, String idSector, Integer numPuesto, String idNumero, String hora){
		try {
			

			String localPath = "/c/programas/accesorios'";//.property
			
			String absolutePath = localPath+idDisplay+".txt";
			
			
			File f = new File(absolutePath);

			f.getParentFile().mkdirs(); 
			f.createNewFile();
			
			
			BufferedReader in = new BufferedReader(new FileReader(absolutePath));
			
			String line;
			boolean found = false;
			String nuevoArchivo = "";
		
			while ((line = in.readLine()) != null) {
				String[] parteLine = line.split("\\*");
				if(parteLine[4].equals(idNumero)){
					nuevoArchivo = nuevoArchivo + "|260216123226*4*5*ATRIL*"+ numPuesto +"*D*Juan*5*"+ hora +"*"+ idNumero +"\n";
					found = true;
				}else{
					nuevoArchivo = nuevoArchivo + line + "\n";					
				}
			}

			File file = new File(absolutePath);
			PrintWriter writer = new PrintWriter(file);
			writer.print(nuevoArchivo);
			
			if(!found){
				writer.println("|260216123226*4*5*ATRIL*"+numPuesto+"*D*Juan*5*"+hora+"*"+ idNumero);
			}
			writer.close();
			in.close();
		
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
	}
}
