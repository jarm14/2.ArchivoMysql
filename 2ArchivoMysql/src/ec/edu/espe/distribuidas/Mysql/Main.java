/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.espe.distribuidas.Mysql;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author joel
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            Conexion db = new Conexion();
            db.MySQLConnection("root", "joelram5635726", "TercerParcial");
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
