package com.sdomumgpii.minesweeper;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;

/**
 *
 * @author Samuel David Ortiz
 */
public class tableroJuego {
    Casilla[][] casillas;
    
    int numfila, numcol, numMinas, numCasAbiertas;
    boolean juegoTerminado;
    
    private Consumer<List<Casilla>> partidaPerdida;
    private Consumer<List<Casilla>> partidaGanada;
    
    private Consumer<Casilla> casillaAbierta;
    
    //constructor del tablero, recibe el numero de casillas y de minas
    public tableroJuego(int numfila, int numcol, int numMinas) {
        this.numfila = numfila;
        this.numcol = numcol;
        this.numMinas = numMinas;
        inicializar();
    }
    
    //genera las casillas del tablero
    public void inicializar(){
        casillas = new Casilla[this.numfila][this.numcol];
        for (int i = 0; i < casillas.length; i++) {
            for (int j = 0; j < casillas[i].length; j++) {
                casillas[i][j] = new Casilla(i, j);
            }
        }
        genMinas();
    }
    
    //este metodo genera las minas en las casillas de manera aleatoria
    private void genMinas(){
        int minas = 0;
        while (minas != numMinas) {            
            int posTmpFila = (int) (Math.random() * casillas.length);
            int posTmpCol = (int) (Math.random() * casillas[0].length);
            if(!casillas[posTmpFila][posTmpCol].isMina()){
                casillas[posTmpFila][posTmpCol].setMina(true);
                minas++;
            }
        }
        NumeroMinasAl();
    }
    
    public void pruebas() {
        for (int i = 0; i < casillas.length; i++) {
            for (int j = 0; j < casillas[i].length; j++) {
                System.out.print(casillas[i][j].isMina()?"*":"0");
            }
            System.out.println("");
        }
    }
    
    public void pruebasP() {
        for (int i = 0; i < casillas.length; i++) {
            for (int j = 0; j < casillas[i].length; j++) {
                System.out.print(casillas[i][j].getNumMinasA());
            }
            System.out.println("");
        }
    }
    
    public void NumeroMinasAl() {
        for (int i = 0; i < casillas.length; i++) {
            for (int j = 0; j < casillas[i].length; j++) {
                if(casillas[i][j].isMina()){
                    List<Casilla> casillasAl = obtenerCasillas(i, j);
                    casillasAl.forEach((c)->c.incrementarMinasA());
                }
            }
        }
    }
    
    private List<Casilla> obtenerCasillas (int posFila, int posCol){
        List<Casilla> listaCasillas = new LinkedList<>();
        for (int i = 0; i < 8; i++) {
            int tmpFila = posFila;
            int tmpCol = posCol;
            switch (i) {
                case 0: tmpFila--; break;           //arriba
                case 1: tmpFila--; tmpCol++; break; //arriba derecha
                case 2: tmpCol++; break;            //derecha
                case 3: tmpCol++; tmpFila++; break; //derecha abajo
                case 4: tmpFila++; break;           //abajo
                case 5: tmpFila++; tmpCol--; break; //abajo izquierda
                case 6: tmpCol--; break;            //izquierda
                case 7: tmpFila--; tmpCol--; break; //izquierda arriba
            }
            
            if(tmpFila >= 0 && tmpFila < this.casillas.length
                    && tmpCol >= 0 && tmpCol < this.casillas[0].length){
                listaCasillas.add(this.casillas[tmpFila][tmpCol]);
            }
        }

        return listaCasillas;
    }
    
    List<Casilla> casillasConMinas() {
        List<Casilla> casillasConMinas = new LinkedList<>();
        for (int i = 0; i < casillas.length; i++) {
            for (int j = 0; j < casillas[i].length; j++) {
                if (casillas[i][j].isMina()) {
                    casillasConMinas.add(casillas[i][j]);
                }
            }
        }
        return casillasConMinas;
    }
    
    public void selCasilla(int posFila, int posCol){
        casillaAbierta.accept(this.casillas[posFila][posCol]);
        if (this.casillas[posFila][posCol].isMina()) {
            partidaPerdida.accept(casillasConMinas());
        }else if(this.casillas[posFila][posCol].getNumMinasA()==0){
            marcaCasAbierta(posFila, posCol);
            List<Casilla> casillasAl = obtenerCasillas(posFila, posCol);
            for(Casilla casilla: casillasAl){
                if(!casilla.isCasAbierta()){
                    selCasilla(casilla.getPosfila(), casilla.getPoscol());
                }
            }
        }else{
            marcaCasAbierta(posFila, posCol);
        }
        if(Ganada()){
            partidaGanada.accept(casillasConMinas());
        }
    }
    
    void marcaCasAbierta (int posFila, int posCol){
        if(!this.casillas[posFila][posCol].isCasAbierta()){
            numCasAbiertas++;
            this.casillas[posFila][posCol].setCasAbierta(true);
        }
    }
    
    boolean Ganada(){
        return numCasAbiertas >= (numfila*numcol)-numMinas;
    }
    
    public static void main(String[] args) {
        tableroJuego tablero = new tableroJuego(6, 10, 10);
        tablero.pruebas();
        System.out.println("---");
        tablero.pruebasP();
    }

    public void setPartidaPerdida(Consumer<List<Casilla>> partidaPerdida) {
        this.partidaPerdida = partidaPerdida;
    }

    public void setCasillaAbierta(Consumer<Casilla> casillaAbierta) {
        this.casillaAbierta = casillaAbierta;
    }

    public void setPartidaGanada(Consumer<List<Casilla>> partidaGanada) {
        this.partidaGanada = partidaGanada;
    }
    
    
}
