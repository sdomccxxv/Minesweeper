package com.sdomumgpii.minesweeper;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Samuel David Ortiz
 */
public class clock implements Runnable{
    
    int hora, minuto, segundo;
    Thread h1;
    
    public clock() {
        h1 = new Thread(this);
        h1.start();
    }
    
    public String hora(){
        
        
        Calendar calendario = Calendar.getInstance();
        hora = calendario.get(Calendar.HOUR_OF_DAY);
        minuto = calendario.get(Calendar.MINUTE);
        segundo = calendario.get(Calendar.SECOND);
        
        String hr;
        hr = null;

        hr = (hora<=9?"0"+hora:hora) + ":" + (minuto<=9?"0"+minuto:minuto) + ":" + (segundo<=9?"0"+segundo:segundo);
            
        return hr;
    }
    
    

    @Override
    public void run() {
       Thread ct = Thread.currentThread();
       
       while(ct==h1){
           hora();
           
           try{
               Thread.sleep(1000);
           } catch (InterruptedException ex) {
               Logger.getLogger(clock.class.getName()).log(Level.SEVERE, null, ex);
           }
       }
    }
    
}
