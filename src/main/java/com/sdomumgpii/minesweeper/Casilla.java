package com.sdomumgpii.minesweeper;

/**
 *
 * @author Samuel David Ortiz
 */
public class Casilla {
    private int posfila;
    private int poscol;
    private boolean mina;

    public Casilla(int posfila, int poscol) {
        this.posfila = posfila;
        this.poscol = poscol;
    }
    
    

    public int getPosfila() {
        return posfila;
    }

    public void setPosfila(int posfila) {
        this.posfila = posfila;
    }

    public int getPoscol() {
        return poscol;
    }

    public void setPoscol(int poscol) {
        this.poscol = poscol;
    }

    public boolean isMina() {
        return mina;
    }

    public void setMina(boolean mina) {
        this.mina = mina;
    }
    
    
}
