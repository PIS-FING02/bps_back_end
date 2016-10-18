package com.sarp.services;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.util.ArrayList;
import java.util.List;

import com.sarp.classes.BusinessDisplay;
import com.sarp.dao.controllers.DAOSectorController;
import com.sarp.dao.factory.DAOServiceFactory;
import com.sarp.json.modeler.JSONNumero;
import com.sarp.utils.UtilService;

public class DisplayService {

	private static DisplayService instance = null;

	private DisplayService() {
	};

	public static DisplayService getInstance() {
		instance = (instance != null) ? instance : new DisplayService();

		return instance;
	}

	public void llamarEnDisplay( String numPuesto, JSONNumero numero) {
		try {

			DAOServiceFactory daoServiceFactory = DAOServiceFactory.getInstance();
			DAOSectorController controladorSector = daoServiceFactory.getDAOSectorController();

			List<BusinessDisplay> displaysSector = controladorSector.obtenerDisplaysSector(numero.getIdSector());
			
			//Saco el path desde archivo property 
			String localPath = UtilService.getStringProperty("DISPLAYS_PATH");
			
			String hora = numero.getHora();
			String prioridad;
			
			//Me fijo el tipo de prioridad
			if(numero.getPrioridad() == 1){
				prioridad = "SAE";
			}else{
				prioridad = "ATRIL";
			}
			
			//Recorro todos los displays que tiene asociado el sector del numero que quiero mostrar
			for (BusinessDisplay display : displaysSector) {

				String idDisplay = display.getIdDisplay();

				String absolutePath = localPath + idDisplay + ".txt";

				File displayFile = new File(absolutePath);
				
				if (!displayFile.exists()) {
					displayFile.getParentFile().mkdirs();
					displayFile.createNewFile();
				}
				
				//Se utiliza para bloquear el exceso al archivo .txt evitando que dos escriban al mismo tiempo
				FileChannel channel = new RandomAccessFile(displayFile, "rw").getChannel();
				// Use the file channel to create a lock on the file.
				// This method blocks until it can retrieve the lock.
				FileLock lock = channel.lock();

				BufferedReader in = new BufferedReader(new FileReader(absolutePath));

				String line;
				boolean found = false;
				String nuevoArchivo = "";
				while ((line = in.readLine()) != null) {
					String[] parteLine = line.split("\\*");
					if (parteLine[4].equals(numPuesto)) {
						nuevoArchivo = nuevoArchivo + "|260216123226*4*5*"+prioridad+"*" + numPuesto + "*D*Juan*5*" + hora + "*"
								+ numero.getExternalId() + "\n";
						found = true;
					} else {
						nuevoArchivo = nuevoArchivo + line + "\n";
					}
				}
				in.close();

				System.out.println(nuevoArchivo);
				PrintWriter writer = new PrintWriter(displayFile);
				writer.print(nuevoArchivo);
				if (!found) {
					writer.println("|260216123226*4*5*"+prioridad+"*" + numPuesto + "*D*Juan*5*" + hora + "*" + numero.getExternalId());
				}
				writer.close();
				
		        //se libera el lock
		        if( lock != null ) {
		            lock.release();
		        }

		        // Cierro el archivo
		        channel.close();

			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}
}
