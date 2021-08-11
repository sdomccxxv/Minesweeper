package com.sdomumgpii.minesweeper;;

import java.awt.Color;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;
import java.awt.font.*;
import java.util.Random;

import java.util.ResourceBundle;
public class Hilos extends Thread {
    int contador;
    private String cadena;
    private int sleep;
    private String MSG_ERROR = "";
    private JLabel lblCadena;
    private JLabel lblMensaje;
    private Font[] fuentes;
    private int tamanioMin = 10;
    private int tamanioMax = 30;
    
    final Random r=new Random();
    
    public Hilos(JLabel lblCadena, JLabel lblMensaje)
    {
        this.cadena = "Sin cadena ingresada...";
        this.sleep = 1000;
        this.lblCadena = lblCadena;
        this.lblMensaje = lblMensaje;
    }
    
    public Hilos(String cadena, int sleep, JLabel lblCadena, JLabel lblMensaje) throws Exception
    {
        try
        {
            this.cadena = cadena;
            this.sleep = sleep;
            
            this.cadena = this.cadena.trim();
            
            if(this.cadena.length() == 0) MSG_ERROR = "Ingrese una cadena no vacía. ";
            if(this.sleep < 0) MSG_ERROR = "Ingrese un valor para sleep mayor o igual que cero (0). ";
            if(!MSG_ERROR.equals("")) throw new Exception(MSG_ERROR);
            
            this.lblCadena = lblCadena;
            this.lblMensaje = lblMensaje;
            
        }catch(Exception ex)
        {
            throw new Exception("Hilos()." + ex.getMessage());
        }
    }
    
    public void setLabelCadena(JLabel lblCadena)
    {
        this.lblCadena = lblCadena;
    }
    
    public void setSleep(int sleep)
    {
        this.sleep = sleep;
    }
    public void setTamanioFuente(int minimo, int maximo)
    {
        this.tamanioMin = minimo;
        this.tamanioMax = maximo;
    }
    
    @Override
    public void run()
    {
        fuentes = GraphicsEnvironment.getLocalGraphicsEnvironment().getAllFonts();
        int indiceFuente = 0;
        int tamanioFuente = 0;
        String[] palabras = this.cadena.split(" ");
        contador = 0;
        while(true)
        {
            
            try {
                indiceFuente = (int) Math.round(Math.random() * fuentes.length);
                tamanioFuente = (int) Math.round(Math.random() * (this.tamanioMax - this.tamanioMin + 1) + this.tamanioMin);
                Font fuente = new Font(fuentes[indiceFuente].getFontName(), Font.PLAIN, tamanioFuente);
                
                lblCadena.setFont(fuente);
                lblCadena.setText(palabras[contador]);
                
                
                Color c = new Color(r.nextInt(256),r.nextInt(256),r.nextInt(256),r.nextInt(256));
                
                lblCadena.setForeground(c);
                lblMensaje.setText("Fuente: " + fuente.getName() + ", tamaño: " + String.valueOf(tamanioFuente));
                Thread.sleep(sleep);
                contador++;
                if(contador == palabras.length) contador = 0;
                
            } catch (InterruptedException ex) {
                Logger.getLogger(Hilos.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
