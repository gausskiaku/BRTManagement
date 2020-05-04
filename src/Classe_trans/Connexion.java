/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Classe_trans;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;

/**
 *
 * @author Gauss
 */
public class Connexion {
    private static String url ="jdbc:postgresql://localhost:5432/Bd_tfc";
    private static String user ="postgres";
    private static String passwd = "gausskiaku05";
    private static Connection connect;
    ResultSet rs ;
    Statement st;
    public static Connection getInstance(){
        if(connect== null){
            try {
                connect = DriverManager.getConnection(url, user, passwd);
                System.out.println("INSTANCIATION DE LA CONNEXION SQL ! ");
            } catch (SQLException e) {
               JOptionPane.showMessageDialog(null, e.getMessage(), "ERREUR", JOptionPane.ERROR_MESSAGE);
            }
        }
        return connect;
    }
}
