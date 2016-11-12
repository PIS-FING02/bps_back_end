package com.sarp.thread;
import java.util.Calendar;
import java.util.GregorianCalendar;

import com.sarp.dao.controllers.DAOPuestoController;
import com.sarp.dao.factory.DAOServiceFactory;
import com.sarp.managers.QueuesManager;
import com.sarp.services.DisplayService;

public class Batch implements Runnable {
    
    private int hora;
    private int min;
    private int segsEspera;
    
    public Batch(int h, int m){
        this.hora = h;
        this.min = m;
        this.segsEspera = 24*60*60; // cada 1 dia se ejecuta
    }
    
    public void run(){
        int segsDelay = this.obtenerSegundosParaHoraIndicada(this.hora, this.min);
        System.out.println("segsDelay "+segsDelay);
        try{
            Thread.sleep(segsDelay * 1000);
            while(true){
            	QueuesManager qm = QueuesManager.getInstance();
            	qm.limpiarColas();
                GregorianCalendar gc = new GregorianCalendar();
                System.out.println("Se limpiaron las colas exitosamente a las: "+this.obtenerHora(gc));
                
                DAOServiceFactory daoFac = DAOServiceFactory.getInstance();
                DAOPuestoController ctrl = daoFac.getDAOPuestoController();
                ctrl.resetarPuestos();
                gc = new GregorianCalendar();
                System.out.println("Se resetearon los puestos a las: "+this.obtenerHora(gc));
                
                DisplayService dispServ = DisplayService.getInstance();
                dispServ.limpiarDisplays();
                gc = new GregorianCalendar();
                System.out.println("Se resetearon los displays a las: "+this.obtenerHora(gc));
                
                Thread.sleep((this.segsEspera * 1000) -gc.get(Calendar.MILLISECOND));
            }
        }catch(Exception e){
        	System.out.println(e.getMessage());
        }
    }
    
    private String obtenerHora(GregorianCalendar gc){
    	String horaStr = "";
    	int hora = gc.get(Calendar.HOUR_OF_DAY);
    	int min = gc.get(Calendar.MINUTE);
    	int segs = gc.get(Calendar.SECOND);
    	int milis = gc.get(Calendar.MILLISECOND);
    	if(hora < 10)
    		horaStr = "0" + Integer.toString(hora);
    	else
    		horaStr = Integer.toString(hora);
    	if(min < 10)
    		horaStr = horaStr + ":0" + Integer.toString(min);
    	else
    		horaStr = horaStr + ":" + Integer.toString(min);
    	if(segs < 10)
    		horaStr = horaStr + ":0" + Integer.toString(segs);
    	else
    		horaStr = horaStr + ":" + Integer.toString(segs);
    	horaStr = horaStr + ":" + Integer.toString(milis);
    	return horaStr;
    }
    
    /*private String obtenerHora(int hora, int min){
    	String horaStr = "";
    	if(hora < 10)
    		horaStr = "0" + Integer.toString(hora);
    	else
    		horaStr = Integer.toString(hora);
    	if(min < 10)
    		horaStr = horaStr + ":0" + Integer.toString(min);
    	else
    		horaStr = horaStr + ":" + Integer.toString(min);
    	return horaStr;
    }*/
    
    private int obtenerSegundosParaHoraIndicada(int hour, int min){
        GregorianCalendar horaActual = new GregorianCalendar();
        int horaHoy = horaActual.get(Calendar.HOUR_OF_DAY);
        int minHoy = horaActual.get(Calendar.MINUTE);
        int segsHoy = horaActual.get(Calendar.SECOND);
        
        int segundosHoy = horaHoy * 60 * 60 + minHoy * 60 + segsHoy;
        int segundosArrancar = hour * 60 * 60 + min * 60;
        int arrancoEn;
        if(segundosHoy > segundosArrancar)
            arrancoEn = segundosArrancar + (24*60*60 - segundosHoy);
        else
            arrancoEn = (segundosArrancar - segundosHoy);
        /*int h = arrancoEn / 3600;
        int m = (arrancoEn - h*60*60) / 60;
        int s = arrancoEn - h*60*60 - m*60;*/
        return arrancoEn;
    }
    
    /*private int obtenerSegundosParaHoraIndicada(int h, int m){
        GregorianCalendar horaActual = new GregorianCalendar();
        int hora = horaActual.get(Calendar.HOUR_OF_DAY);
        int min = horaActual.get(Calendar.MINUTE);
        h = (h - hora) % 24;
        if(hora < 0)
            h += 24;
        m = (m - min) % 60;
        if(min < 0)
            m += 60;
        return h*60*60 + m*60;
    }*/
    
}