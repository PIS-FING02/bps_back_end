package com.sarp.tests;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

import org.junit.Test;

public class testTxt {
	
	public static void main(String[] args) {
		
		try{
			
		
		String localPath = "/Users/facevedo/";//.property
		
		String puesto = "Puestopijingo";
		String hora = "asasa";
		String numero = "10";
		
		String absolutePath = localPath+"fedesape.txt";
		
		
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
						nuevoArchivo = nuevoArchivo + "|260216123226*4*5*ATRIL*"+puesto+"*D*Juan*5*"+hora+"*"+numero+"\n";
						found = true;
					}else{
						nuevoArchivo = nuevoArchivo + line + "\n";					
					}
				}
				in.close();
				
				System.out.println(nuevoArchivo);
				PrintWriter writer = new PrintWriter(displayFile);
				writer.print(nuevoArchivo);
				if(!found){
					writer.println("|260216123226*4*5*ATRIL*"+puesto+"*D*Juan*5*"+hora+"*"+numero);
				}
				writer.close();
				
			 
		  }

		}catch(Exception e){
			System.out.println(e.getMessage());
		}
		
		
	}
}
