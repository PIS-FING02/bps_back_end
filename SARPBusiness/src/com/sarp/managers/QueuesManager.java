package com.sarp.managers;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sarp.classes.BusinessSector;
import com.sarp.classes.BusinessSectorQueue;

public class QueuesManager {
	
	private Map<String,BusinessSectorQueue> manejadorDeColas;
	private static QueuesManager instancia;
	
	private QueuesManager(){
		//Es imporante crear las colas con la cantidad inicial bastante cercano a la cantidad 
		// de sectores del arbol de GAFU, para un buen hashing.
		
		this.manejadorDeColas = new HashMap<String,BusinessSectorQueue>();
	}
	
	public static synchronized QueuesManager getInstance(){
		if (instancia == null){
			instancia = new QueuesManager();
			return instancia;
		}else{
			return instancia;
		}
	}
	
	public synchronized void crearColaSector(String idSector) throws IOException{
		if (!this.manejadorDeColas.containsKey(idSector)){
			BusinessSectorQueue cola = new BusinessSectorQueue(idSector);
			manejadorDeColas.put(idSector, cola);
		}
//		else{
//			IOException e = new IOException("El sector " + idSector + "  ya tiene una cola asociada");
//			throw e;
//		}
	}
	
	public synchronized void borrarColaSector(String idSector) throws IOException {
		if (this.manejadorDeColas.containsKey(idSector)){
			manejadorDeColas.remove(idSector);
		}
		else{
			IOException e = new IOException("El sector no tenia una cola asociada");
			throw e;
		}
	}
	
	public synchronized void quitarColaSector(String idSector) throws IOException{
		if (this.manejadorDeColas.containsKey(idSector)){
			manejadorDeColas.remove(idSector);
		}
		else{
			IOException e = new IOException("No existe cola asociada para ese sector");
			throw e;
		}
	}
	
	public synchronized BusinessSectorQueue obtenerColaSector(String idSector){
		if (this.manejadorDeColas.containsKey(idSector)){
			return manejadorDeColas.get(idSector);
		}
		else{
			return null;
		}
	}
	
	public synchronized void reinicializarColas(List<BusinessSector> listaSectores) throws IOException{
		this.manejadorDeColas = new HashMap<String,BusinessSectorQueue>();
		for(BusinessSector sec : listaSectores){
			if (!this.manejadorDeColas.containsKey(sec.getSectorId())){
				BusinessSectorQueue cola = new BusinessSectorQueue(sec.getSectorId());
				manejadorDeColas.put(sec.getSectorId(), cola);
			}
			else{
				this.manejadorDeColas = new HashMap<String,BusinessSectorQueue>();
				throw new IOException("No se pudieron reinicializar las colas");
			}
		}
	}

}
