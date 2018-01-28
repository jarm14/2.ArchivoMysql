/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.espe.distribuidas.Mysql;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.UIManager;

/**
 *
 * @author joel
 */
public class Mysql extends javax.swing.JFrame implements ActionListener {

    /**
     * Creates new form Mysql
     */
    public Mysql() {

        //Para poder cerrar la ventana
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Se agrega un layout
        setLayout(new BorderLayout());

        //Se crea el editor de texto y se agrega a un scroll
        txp = new JTextPane();
        JScrollPane jsp = new JScrollPane();
        jsp.setViewportView(txp);

        add(jsp, BorderLayout.CENTER);

        //Se crea un boton para abrir el archivo
        JButton btn = new JButton("Abrir");
        btn.addActionListener(this);
        //btn.setIcon(new ImageIcon(getClass().getResource("Abrir.png")));

        add(btn, BorderLayout.NORTH);

        //Tamaño de la ventana
        setSize(500, 500);

        //Esto sirve para centrar la ventana
        setLocationRelativeTo(null);

        //Hacemos visible la ventana
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {

        JButton btn = (JButton) e.getSource();
        if (btn.getText().equals("Abrir")) {
            if (abrirArchivo == null) {
                abrirArchivo = new JFileChooser();
            }
            //Con esto solamente podamos abrir archivos
            abrirArchivo.setFileSelectionMode(JFileChooser.FILES_ONLY);

            int seleccion = abrirArchivo.showOpenDialog(this);

            if (seleccion == JFileChooser.APPROVE_OPTION) {
                File f = abrirArchivo.getSelectedFile();
                try {
                    String nombre = f.getName();
                    String path = f.getAbsolutePath();
                    String contenido = getArchivo(path);
                    //Colocamos en el titulo de la aplicacion el 
                    //nombre del archivo
                    this.setTitle(nombre);

                    //En el editor de texto colocamos su tiempo
                    txp.setText(contenido);

                } catch (Exception exp) {
                }
            }
        }
    }

    public String getArchivo(String ruta) {
        long tiempoInicial, tiempoFinal;

        System.out.println();
        FileReader fr = null;
        BufferedReader br = null;
        //Cadena de texto donde se guardara el tiempo del archivo
        String tiempo = "";
        String query = "";
        ArrayList<String> querys = new ArrayList<>();
        
        try {
            //ruta puede ser de tipo String o tipo File
            fr = new FileReader(ruta);
            br = new BufferedReader(fr);

            String linea;
            String[] separada;

            //Obtenemos el tiempo del archivo linea por linea
            while ((linea = br.readLine()) != null) {
                separada = new String[4];
                separada = linea.split("\\|");
                query = "INSERT INTO persona VALUES('" + separada[0] + "','" + separada[1] + "','" + separada[2] + "','" + separada[3] + "');";
                querys.add(query);
            }

        } catch (Exception e) {
        } //finally se utiliza para que si todo ocurre correctamente o si ocurre 
        //algun error se cierre el archivo que anteriormente abrimos
        finally {
            try {
                br.close();
            } catch (Exception ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        Conexion db = new Conexion();
        try {
            db.MySQLConnection("root", "joelram5635726", "TercerParcial");
        } catch (Exception ex) {
            Logger.getLogger(Mysql.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        tiempoInicial = System.currentTimeMillis();

        for(int i = 0; i<querys.size();i++)
        {
            db.insertData(querys.get(i).toString());
        }
        
        db.closeConnection();
        tiempoFinal = System.currentTimeMillis();
        tiempo = "Tiempo de ejecucion: " + (tiempoFinal - tiempoInicial) + " milisegundos";
        return tiempo;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    public static void main(String args[]) {
        try {
            //Cambiamos el Look&Feel
            JFrame.setDefaultLookAndFeelDecorated(true);
            UIManager.setLookAndFeel(new com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel());
        } catch (Exception e) {
        }
        new Mysql();
    }

    JTextPane txp;
    JFileChooser abrirArchivo;
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
