package com.sdomumgpii.minesweeper;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import static java.lang.Thread.sleep;
import java.util.Calendar;
import java.util.List;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JToggleButton;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

/**
 *
 * @author Samuel David Ortiz
 */
public class frmTablero extends javax.swing.JFrame {

    int numFilas, numCol, numMinas, miliseg, seg, min, hora, minuto, segundo;
    boolean estado, isClicked = false;
    Thread hilo, hilo2;
    
    JToggleButton[][] casillasTablero;
    
    tableroJuego tableroMines;
    splashScreen splash;
    
    private Icon errorIcon = UIManager.getIcon("OptionPane.errorIcon");
    private Icon infoIcon = UIManager.getIcon("OptionPane.informationIcon");
    private Icon warnIcon = UIManager.getIcon("OptionPane.warningIcon");
    //private final Icon mine = (Icon) ImageIO.read(getClass().getResource("mina3.png");
    /**
     * Creates new form frmTablero
     */
    public frmTablero() {
        initComponents();
        Nuevo();
        reloj();
    }
    
    void borrarControles(){
        if(casillasTablero != null){
            for (int i = 0; i < casillasTablero.length; i++) {
                for (int j = 0; j < casillasTablero[i].length; j++) {
                    if(casillasTablero[i][j] != null){
                        getContentPane().remove(casillasTablero[i][j]);
                    }
                }
            }
        }
    }
    
    private void Nuevo(){
        borrarControles();
        crearCasillas(numFilas, numCol, numMinas);
        crearTablero(numFilas, numCol, numMinas);
        repaint();
    }
    
    public void crearTablero(int filas, int col, int minas) {
        this.numFilas = filas;
        this.numCol = col;
        this.numMinas = minas;
               
        tableroMines = new tableroJuego(numFilas, numCol, numMinas);
        tableroMines.setPartidaPerdida(new Consumer<List<Casilla>>() {
            @Override
            public void accept(List<Casilla> t) {
                for(Casilla casillaConMina: t){
                    //casillasTablero[casillaConMina.getPosfila()][casillaConMina.getPoscol()].setText("*");
                    
                    casillasTablero[casillaConMina.getPosfila()][casillaConMina.getPoscol()].setDisabledIcon(new ImageIcon("mina3.png"));

                    if (casillasTablero[casillaConMina.getPosfila()][casillaConMina.getPoscol()].isEnabled()) {
                        casillasTablero[casillaConMina.getPosfila()][casillaConMina.getPoscol()].setEnabled(false);
                    } else {
                        casillasTablero[casillaConMina.getPosfila()][casillaConMina.getPoscol()].setEnabled(true);
                    }
                    casillasTablero[casillaConMina.getPosfila()][casillaConMina.getPoscol()].setEnabled(false);

                }
                JOptionPane.showMessageDialog(null, "HAS PERDIDO", "Fin del Juego", JOptionPane.ERROR_MESSAGE);
            }
        });
        
        tableroMines.setPartidaGanada(new Consumer<List<Casilla>>() {
            @Override
            public void accept(List<Casilla> t) {
                for(Casilla casillaConMina: t){
                    //casillasTablero[casillaConMina.getPosfila()][casillaConMina.getPoscol()].setText(":)");
                    
                    casillasTablero[casillaConMina.getPosfila()][casillaConMina.getPoscol()].setDisabledIcon(new ImageIcon("feliz.png"));

                    if (casillasTablero[casillaConMina.getPosfila()][casillaConMina.getPoscol()].isEnabled()) {
                        casillasTablero[casillaConMina.getPosfila()][casillaConMina.getPoscol()].setEnabled(false);
                    } else {
                        casillasTablero[casillaConMina.getPosfila()][casillaConMina.getPoscol()].setEnabled(true);
                    }
                    casillasTablero[casillaConMina.getPosfila()][casillaConMina.getPoscol()].setEnabled(false);
                    hilo.stop();
                }
                JOptionPane.showMessageDialog(null, "HAS GANADO", "Fin del Juego", JOptionPane.DEFAULT_OPTION);
            }
        });
        
        tableroMines.setCasillaAbierta(new Consumer<Casilla>(){
            @Override
            public void accept(Casilla t){
                casillasTablero[t.getPosfila()][t.getPoscol()].setEnabled(false);
                casillasTablero[t.getPosfila()][t.getPoscol()]
                        .setText(t.getNumMinasA() == 0 ? "" : t.getNumMinasA() + "");
            }
        });
        
        
        
        tableroMines.pruebas();
    }
    
    public void crearCasillas(int filas, int col, int minas) {
        this.numFilas = filas;
        this.numCol = col;
        this.numMinas = minas;

        jLabel1.setText(Integer.toString(minas));

        int posX = 25, posY = 100, ancho = 50, alto = 50;

        casillasTablero = new JToggleButton[numFilas][numCol];
        for (int i = 0; i < casillasTablero.length; i++) {
            for (int j = 0; j < casillasTablero[i].length; j++) {
                final int k = i;
                final int l = j;

                casillasTablero[i][j] = new JToggleButton();
                casillasTablero[i][j].setName(i + "," + j);
                casillasTablero[i][j].setBorder(null);
                //casillasTablero[i][j].setIcon((errorIcon));
                //casillasTablero[i][j].setSelectedIcon(infoIcon);
                //casillasTablero[i][j].setDisabledIcon(errorIcon);

                if (i == 0 && j == 0) {
                    casillasTablero[i][j].setBounds(posX, posY, ancho, alto);
                } else if (i == 0 && j != 0) {
                    casillasTablero[i][j].setBounds(
                            casillasTablero[i][j - 1].getX() + casillasTablero[i][j - 1].getWidth(),
                            posY,
                            ancho,
                            alto);
                } else {
                    casillasTablero[i][j].setBounds(
                            casillasTablero[i - 1][j].getX(),
                            casillasTablero[i - 1][j].getY() + casillasTablero[i - 1][j].getHeight(),
                            ancho,
                            alto);
                }
                casillasTablero[i][j].addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        btnClick(e);
                    }
                });

                casillasTablero[i][j].addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {

                        if (SwingUtilities.isRightMouseButton(e) && e.getClickCount() == 1) {
                            //casillasTablero[k][l].setIcon(infoIcon);
                            casillasTablero[k][l].setIcon(new ImageIcon("flag.png")); // bandera
                            /*if (casillasTablero[k][l].isEnabled()) {
                                casillasTablero[k][l].setEnabled(false);
                                } else {
                                casillasTablero[k][l].setEnabled(true);
                                }*/
                            //JOptionPane.showMessageDialog(null, "Click derecho", "Fin del Juego", JOptionPane.ERROR_MESSAGE);

                        } else if (SwingUtilities.isLeftMouseButton(e) && e.getClickCount() == 1) {
                            casillasTablero[k][l].addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    casillasTablero[k][l].setIcon(null);
                                    btnClick(e);
                                }
                            });
                        }
                    }
                });
                getContentPane().add(casillasTablero[i][j]);
            }
        }
        /*this.setSize(casillasTablero[filas-1][col-1].getX() + 
                casillasTablero[filas-1][col-1].getWidth() + 40,
                casillasTablero[filas-1][col-1].getY() + 
                casillasTablero[filas-1][col-1].getHeight() + 50);*/
    }
    
    public void tempo(){
        estado = true;
        
        hilo = new Thread(){
          public void run()  {
              for(;;){
                  if(estado == true){
                      try{
                          sleep(1);
                          if(miliseg >= 1000){
                              miliseg = 0;
                              seg++;
                          }
                          if(seg >= 60){
                              miliseg = 0;
                              seg = 0;
                              min++;
                          }
                          if(min >= 60){
                              miliseg = 0;
                              seg = 0;
                              min = 0;
                          }
                          lbTempo.setText(((min<=9)?"0"+min:min)+":"+(seg<=9?"0"+seg:seg));
                          miliseg++;
                      }catch(Exception e) {
                          
                      }
                  }else{
                     break; 
                  }
              }
          }
        };
        hilo.start();
    }
    
    private void btnClick(ActionEvent e) {
        JToggleButton btn = (JToggleButton)e.getSource();
        String[] coordenada = btn.getName().split(",");
        
        int Fila = Integer.parseInt(coordenada[0]);
        int Col = Integer.parseInt(coordenada[1]);
        
        //JOptionPane.showMessageDialog(rootPane, Fila+","+Col);
        if(!isClicked){
            isClicked = true;
            tempo();
        }
        
        if(tableroMines.casillas[Fila][Col].isMina()){
            hilo.stop();
            
            sound soundsT = new sound();
           
            soundsT.tiposonido(1);
            soundsT.start();
            
            if (casillasTablero[Fila][Col].isEnabled()) {
                casillasTablero[Fila][Col].setEnabled(false);
            } else {
                casillasTablero[Fila][Col].setEnabled(true);
            }
        }
        tableroMines.selCasilla(Fila, Col);
    }

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The
     * content of this method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        lbReloj = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        lbTempo = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jLabel1.setIcon(new javax.swing.ImageIcon("D:\\OneDrive - Universidad Mariano G??lvez\\U\\4to Semestre\\2 - Programacion II\\Primer Parcial\\mina3.png")); // NOI18N
        jLabel1.setText("99");

        lbReloj.setBackground(new java.awt.Color(0, 0, 0));
        lbReloj.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        lbReloj.setForeground(new java.awt.Color(153, 0, 0));
        lbReloj.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbReloj.setText("00:00");

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setIcon(new javax.swing.ImageIcon("D:\\OneDrive - Universidad Mariano G??lvez\\U\\4to Semestre\\2 - Programacion II\\Primer Parcial\\Minesweeper\\umg.png")); // NOI18N

        lbTempo.setBackground(new java.awt.Color(0, 0, 0));
        lbTempo.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        lbTempo.setForeground(new java.awt.Color(153, 0, 0));
        lbTempo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbTempo.setText("00:00");

        jMenu1.setText("Juego");

        jMenuItem1.setText("Nuevo");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);

        jMenuItem2.setText("Reglas");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem2);

        jMenuBar1.add(jMenu1);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(118, 118, 118)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 152, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(17, 17, 17))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(lbReloj, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(16, 16, 16)
                    .addComponent(lbTempo, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(278, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 158, Short.MAX_VALUE)
                .addComponent(lbReloj, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(16, 16, 16)
                    .addComponent(lbTempo, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(204, Short.MAX_VALUE)))
        );

        setSize(new java.awt.Dimension(418, 309));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        Nuevo();
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        welcomePage welcome = new welcomePage();
        
        welcome.show();
        this.dispose();
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    public void reloj(){
        estado = true;
        
        hilo2 = new Thread(){
          public void run()  {
              for(;;){
                  if(estado == true){
                      try{
                          sleep(1);
                          lbReloj.setText(hora());
                      }catch(Exception e) {
                          
                      }
                  }else{
                     break; 
                  }
              }
          }
        };
        hilo2.start();
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
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(frmTablero.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(frmTablero.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(frmTablero.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(frmTablero.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new frmTablero().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    public javax.swing.JLabel lbReloj;
    public javax.swing.JLabel lbTempo;
    // End of variables declaration//GEN-END:variables
}
