package com.sarp.thread;

import java.util.Calendar;
import java.util.GregorianCalendar;


public class ThreadManager {

    private static ThreadManager instance;
    private Thread th;
    
    private ThreadManager() {
        GregorianCalendar gc = new GregorianCalendar();
        this.th = new Thread(new Batch(gc.get(Calendar.HOUR_OF_DAY), gc.get(Calendar.MINUTE)+1));
        this.th.start();
    }

    public static ThreadManager getInstance() {
        instance = instance != null ? instance : new ThreadManager();
        return instance;
    }

    public void lanzarBatch(int h, int m){
        this.th.interrupt();
        this.th = new Thread(new Batch(h,m));
        this.th.start();
    }
    
}