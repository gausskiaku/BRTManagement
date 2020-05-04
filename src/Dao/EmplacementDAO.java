/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Dao;

import Entites.Emplacement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import tfc.Fenetre;

/**
 *
 * @author Gauss
 */
public class EmplacementDAO extends Dao<Emplacement>{

    public ResultSet PourTable(){
        ResultSet result = null;
        try {
            result = this.connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE).executeQuery(
            "SELECT id_emp as \"Numero\", nom_emp as \"Nom de l'Emplacement\" FROM t_emplacement");
            
        } catch (Exception e) {
        }
        return result;
    }
    @Override
    public Emplacement find(long id) {
       Emplacement emplacement = new Emplacement();
       String sql;
        try {
            if(id == 0){
            sql = "SELECT * FROM t_emplacement";
            }
            else {
            sql = "SELECT * FROM t_emplacement WHERE id_emp = " +id;}
            ResultSet result = this.connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE).executeQuery(sql);
            if(result.first())
            emplacement = new Emplacement((int) id, result.getString("nom_emp"));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e+ " DAO Emplacement : Find");
        }
        return emplacement;
    }

    @Override
    public Emplacement create(Emplacement obj) {
         try {
            ResultSet result = this.connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE).executeQuery(
            "SELECT MAX(id_emp) FROM t_emplacement as id");
       if(result.first()){
       long id =result.getLong("max");
            PreparedStatement prepare = this.connect.prepareStatement(
            "INSERT INTO t_emplacement (nom_emp) VALUES(?)");
            prepare.setString(1, obj.getNom_emp());
            prepare.executeUpdate();
            obj = this.find(id); 
       }
      JOptionPane.showMessageDialog(null, "Enregistrement de l'emplacement effectué avec reussi","Brt Appli", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage() + " DAO Emplacement");
        }
        return obj;
    }

    @Override
    public Emplacement update(Emplacement obj) {
        try {
            int p = JOptionPane.showConfirmDialog(null, "Voulez vous modifier?", "Modifier", JOptionPane.YES_NO_OPTION);
            if (p==0){
            this.connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE).executeUpdate(
            "UPDATE t_emplacement SET nom_emp = '" +obj.getNom_emp() + "'"+" WHERE id_emp = " +obj.getId_emp());
            obj = this.find(obj.getId_emp());
            JOptionPane.showMessageDialog(null, "Modification de l'emplacement effectué avec reussi","Brt Appli", JOptionPane.INFORMATION_MESSAGE);
            }
            } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage() + " DAO Emplacement");
        }
        return obj;
    }

    @Override
    public void delete(Emplacement obj) {
        try {
            int p = JOptionPane.showConfirmDialog(null, "Voulez vous supprimer?", "Supprimer", JOptionPane.YES_NO_OPTION);
            if (p==0){
                try {
                     this.connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE).executeUpdate(
            "DELETE FROM t_emplacement WHERE id_emp = " +obj.getId_emp());
            JOptionPane.showMessageDialog(null, "Suppression de l'emplacement effectué avec reussi","Brt Appli", JOptionPane.INFORMATION_MESSAGE);
                } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "L'emplacement ne peut pas etre supprimer parceque \nil ya les materiaux a cet endroit."
                    + " Pour supprimer cet \nemplacement veuillez relocalisez les Materiaux et reffectuer l'operation", "Brt Appli", JOptionPane.ERROR_MESSAGE);
                }
            }
            } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage() + " DAO Emplacement"); 
        }
    }
    public int VerifierId (String nom){
    int id = 0;
    try {
        ResultSet result = this.connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE).executeQuery(
                "SELECT id_emp FROM t_emplacement WHERE nom_emp = '"+nom+"'");
        if(result.next())
        id = result.getInt("id_emp");
    } catch (Exception e) {
        JOptionPane.showMessageDialog(null, e + " Voir VerifierId");
    }
return id;
}
    
    public ResultSet AutreInfo (String nom){
        ResultSet result = null;
        try {
            result = this.connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE).executeQuery(
            "SELECT * FROM t_emplacement WHERE nom_emp ='"+nom+"' ");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erreur ici ==> Autreinfo empl" + e);
        }
    return result;
    }
     public ResultSet AutreInfoImpo (String nom, String etat){
        ResultSet result = null;
        try {
            result = this.connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE).executeQuery(
            "SELECT * FROM t_emplacement INNER JOIN t_materiel ON t_emplacement.id_emp = t_materiel.id_emp WHERE t_emplacement.nom_emp ='"+nom+"' and t_materiel.disponibilite = '"+etat+"'");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erreur ici ==> Autreinfo empl" + e);
        }
    return result;
    }

}
