/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sdomumgpii.minesweeper;

import java.util.Calendar;

/**
 *
 * @author Samuel David Ortiz
 */
public class clock {
    
    int hora, minuto, segundo;
    
    public String hora(){
        Calendar calendario = Calendar.getInstance();
        hora = calendario.get(Calendar.HOUR_OF_DAY);
        minuto = calendario.get(Calendar.MINUTE);
        segundo = calendario.get(Calendar.SECOND);
        
        if(hora <= 9 || minuto <=9 || segundo <= 9){
            String h = "0" + hora;
            String m = "0" + minuto;
            String s = "0" + hora;
        }
        
        String hr = hora + ":" + minuto + ":" + segundo;
        
        return hr;
    }
    
}
