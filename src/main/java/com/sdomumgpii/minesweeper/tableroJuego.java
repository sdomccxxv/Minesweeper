package com.sdomumgpii.minesweeper;

/**
 *
 * @author Samuel David Ortiz
 */
public class tableroJuego {
    Casilla[][] casillas;
    
    int numfila, numcol, numMinas;
    
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
    }
    
    public void pruebas() {
        for (int i = 0; i < casillas.length; i++) {
            for (int j = 0; j < casillas[i].length; j++) {
                System.out.print(casillas[i][j].isMina()?"*":"0");
            }
            System.out.println("");
        }
    }
    
    public static void main(String[] args) {
        tableroJuego tablero = new tableroJuego(6, 10, 10);
        tablero.pruebas();
    }
}
