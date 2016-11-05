package com.sarp.services;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.util.Calendar;
import java.util.GregorianCalendar;
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

	public void llamarEnDisplay( String numPuesto, JSONNumero numero) throws Exception {

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
			RandomAccessFile file = new RandomAccessFile(displayFile, "rw");
			FileChannel channel = file.getChannel();
			// Use the file channel to create a lock on the file.
			// This method blocks until it can retrieve the lock.
			FileLock lock = channel.lock();

			BufferedReader in = new BufferedReader(new FileReader(absolutePath));

			String line;
			boolean found = false;
			String nuevoArchivo = "";
	
			String horaRefresh = CalendarToString(new GregorianCalendar());
			
			while ((line = in.readLine()) != null) {
				String[] parteLine = line.split("\\*");
				if (parteLine[4].equals(numPuesto)) {
					if(numero.getPrioridad() == 1){
						nuevoArchivo = nuevoArchivo + "|"+horaRefresh+"*4*5*"+prioridad+"*" + numPuesto + "*D*-*5*" + hora + "*"
								+ numero.getDatosComplementarios() != null?  numero.getDatosComplementarios().getNombreCompleto():numero.getExternalId() + "\n";
					}else{
						nuevoArchivo = nuevoArchivo + "|"+horaRefresh+"*4*5*"+prioridad+"*" + numPuesto + "*D*-*5**"
								+ numero.getExternalId() + "\n";
					}
					
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
				if(numero.getPrioridad() == 1){
					writer.println("|"+horaRefresh+"*4*5*"+prioridad+"*" + numPuesto + "*D*-*5*" + hora + "*"
							+ numero.getDatosComplementarios() != null?  numero.getDatosComplementarios().getNombreCompleto():numero.getExternalId());
				}else{
					writer.println(nuevoArchivo = nuevoArchivo + "|"+horaRefresh+"*4*5*"+prioridad+"*" + numPuesto + "*D*-*5**"
							+ numero.getExternalId());
				}
	
			}
			writer.close();
			
	        //se libera el lock
	        if( lock != null ) {
	            lock.release();
	        }

	        // Cierro el archivo
	        channel.close();
	        file.close();

		}

	}
	
	private static String CalendarToString(GregorianCalendar c){
		String fecha = Integer.toString(c.get(Calendar.DAY_OF_MONTH)).length() > 1 ? Integer.toString(c.get(Calendar.DAY_OF_MONTH)) : "0"+Integer.toString(c.get(Calendar.DAY_OF_MONTH));
		fecha = fecha +"/" + (Integer.toString(c.get(Calendar.MONTH)+1).length() > 1 ? Integer.toString(c.get(Calendar.MONTH)+1) : "0"+Integer.toString(c.get(Calendar.MONTH)+1));
		fecha = fecha + "/" + Integer.toString(c.get(Calendar.YEAR));
		fecha = fecha + "-";
		fecha = fecha + (Integer.toString(c.get(Calendar.HOUR_OF_DAY)).length() > 1 ? Integer.toString(c.get(Calendar.HOUR_OF_DAY)) : "0"+Integer.toString(c.get(Calendar.HOUR_OF_DAY)));
		fecha = fecha + ":" + (Integer.toString(c.get(Calendar.MINUTE)).length() > 1 ? Integer.toString(c.get(Calendar.MINUTE)) : "0"+Integer.toString(c.get(Calendar.MINUTE)));
		fecha = fecha + ":" + (Integer.toString(c.get(Calendar.SECOND)).length() > 1 ? Integer.toString(c.get(Calendar.SECOND)) : "0"+Integer.toString(c.get(Calendar.SECOND)));
		return fecha;
	}
}
