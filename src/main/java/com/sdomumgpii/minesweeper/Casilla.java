package com.sdomumgpii.minesweeper;

/**
 *
 * @author Samuel David Ortiz
 */
public class Casilla {
    private int posfila;
    private int poscol;
    private boolean mina;
    private int numMinasA;
    private boolean casAbierta;

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

    public int getNumMinasA() {
        return numMinasA;
    }

    public void setNumMinasA(int numMinasA) {
        this.numMinasA = numMinasA;
    }
    
    public void incrementarMinasA(){
        this.numMinasA++;
    }

    public boolean isCasAbierta() {
        return casAbierta;
    }

    public void setCasAbierta(boolean casAbierta) {
        this.casAbierta = casAbierta;
    }
    
    
}
