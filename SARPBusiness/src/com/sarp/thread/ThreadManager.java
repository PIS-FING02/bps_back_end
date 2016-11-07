package com.sarp.thread;

import java.util.Calendar;
import java.util.GregorianCalendar;


public class ThreadManager {

    private static ThreadManager instance;
    private Thread th;
    private int hora;
    private int min;
    
    private ThreadManager() {
        this.th = new Thread(new Batch(5, 0));
        this.hora = 5;
        this.min = 0;
        this.th.start();
    }

    public static ThreadManager getInstance() {
        instance = instance != null ? instance : new ThreadManager();
        return instance;
    }

    public void cambiarHoraLimpiadoColas(int h, int m){
    	this.hora = h;
    	this.min = m;
        this.th.interrupt();
        this.th = new Thread(new Batch(h,m));
        this.th.start();
    }
    
    public String obtenerHoraLimpiadoColas(){
    	String horaStr = "";
    	if(this.hora < 10)
    		horaStr = "0"+Integer.toString(this.hora);
    	else
    		horaStr = Integer.toString(this.hora);
    	if(this.min < 10)
    		horaStr = horaStr + ":0"+Integer.toString(this.min);
    	else
    		horaStr = horaStr + ":"+Integer.toString(this.min);
    	return horaStr;
    }
    
}