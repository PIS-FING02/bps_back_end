package com.sarp.test.colas;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import org.junit.Before;
import org.junit.Test;

import com.sarp.classes.BusinessNumero;
import com.sarp.classes.BusinessSectorQueue;
import com.sarp.classes.BusinessTramite;

public class TestBusinessSectorQueue {
	
	private BusinessSectorQueue cola;
	
	@Before
	public void armarAmbiente(){
		this.cola = new BusinessSectorQueue();
	}
	
	
	@Test
	public void pruebaAgregarBatch(){
		BusinessNumero nro;
		GregorianCalendar date;
		ArrayList<BusinessNumero> listaNros = new ArrayList<BusinessNumero>();
		for(int i=1; i<11; i++){
			date = new GregorianCalendar();
			date.set(Calendar.HOUR_OF_DAY, 23);
			date.set(Calendar.MINUTE, 0);
			date.add(Calendar.MINUTE, i);
			nro = new BusinessNumero(i, Integer.toString(i), date, "estado", 1, 1, "1");
			nro.setLastUpdated(new Timestamp(1));
			listaNros.add(nro);
		}
		
		this.cola.agregarNumeroColaBatch(listaNros);
		
		ArrayList<BusinessTramite> tramites = new ArrayList<BusinessTramite>(); 
		BusinessTramite tramite = new BusinessTramite(1, "codT1");
		tramites.add(tramite);
		System.out.println("---------TRAMITES");
		for(BusinessTramite t : tramites){
			System.out.println("--Codigo tramite "+t.getCodigo()+ ", Nombre Tramite "+ t.getNombre());
		}
		
		nro = this.cola.llamarNumeroCola(tramites);
		while(nro != null){			
			System.out.println("Numero: "+nro.getExternalId()+", hora:"+nro.getHora().getTime().toString());
			nro = this.cola.llamarNumeroCola(tramites);
		}
	}
	
}
