package com.sarp.thread;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class Batch implements Runnable {
    
    private int hora;
    private int min;
    
    public Batch(int h, int m){
        this.hora = h;
        this.min = m;
    }
    
    public void run(){
        int segsDelay = this.obtenerSegundosParaHoraIndicada(this.hora, this.min);
        System.out.println("segsDelay "+segsDelay);
        try{
            Thread.sleep(segsDelay * 1000);
            while(true){
                GregorianCalendar gc = new GregorianCalendar();
                System.out.println("Me limpio la cola con papel "+gc.get(Calendar.HOUR_OF_DAY)+":"+gc.get(Calendar.MINUTE)+":"+gc.get(Calendar.SECOND)+":"+gc.get(Calendar.MILLISECOND));
                Thread.sleep(10000 -gc.get(Calendar.MILLISECOND));
            }
        }catch(Exception e){
        	System.out.println("Error: "+e.getMessage());
        }
    }
    
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