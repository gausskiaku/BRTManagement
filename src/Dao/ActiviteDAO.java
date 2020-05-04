/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Dao;

import Entites.Activite;
import Entites.Emplacement;
import Entites.Programme;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;

/**
 *
 * @author Gauss
 */
public class ActiviteDAO extends Dao<Activite>{

    public int VerifierId (String nom){
int id = 0;
    try {
        ResultSet result = this.connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE).executeQuery(
                "SELECT id_act FROM t_activite WHERE nom_act = '"+nom+"'");
        if(result.next())
        id = result.getInt("id_act");
    } catch (Exception e) {
        JOptionPane.showMessageDialog(null, e + " Voir VerifierId : Activité");
    }
return id;
}
    public ResultSet PourTable(String departement){
        ResultSet result = null;
        try {
            result = this.connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE).executeQuery(
            "SELECT id_act as \"Numero\",nom_act as \"Nom de l'activité\", type_act as \"Type de l'activité\", description_act as \"Description\" FROM t_activite WHERE departement = '"+departement+"'");
            
        } catch (Exception e) {
        }
        return result;
    }
    public ResultSet Combo(String departement){
        ResultSet result = null;
        try {
            result = this.connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE).executeQuery(
            "SELECT id_act as \"Numero\",nom_act as \"Nom de l'activité\", type_act as \"Type de l'activité\", description_act as \"Description\" FROM t_activite WHERE departement = '"+departement+"'");
            
        } catch (Exception e) {
        }
        return result;
    }
    public ResultSet AutreInfo (String nom, String departement){
        ResultSet result = null;
        try {
            result = this.connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE).executeQuery(
            "SELECT * FROM t_activite WHERE nom_act ='"+nom+"' and departement='"+departement+"' ");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    return result;
    }
    @Override
    public Activite find(long id) {
        Activite activite = new Activite();
        try {
            ResultSet result = this.connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE).executeQuery(
            "SELECT * FROM t_activite WHERE id_act = " +id);
            if(result.first())
            activite = new Activite((int) id,result.getString("nom_act"), result.getString("type_act"), result.getString("description_act"), result.getString("departement"));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e+ " DAO Activité");
        }
        return activite;
    }

    @Override
    public Activite create(Activite obj) {
        try {
            ResultSet result = this.connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE).executeQuery(
            "SELECT MAX(id_act) FROM t_activite as id");
       if(result.first()){
       long id =result.getLong("max");
            PreparedStatement prepare = this.connect.prepareStatement(
            "INSERT INTO t_activite (nom_act,type_act,description_act, departement) VALUES(?,?,?,?)");
            prepare.setString(1, obj.getNom_act());
            prepare.setString(2, obj.getType_act());
            prepare.setString(3, obj.getDescription_act());
            prepare.setString(4, obj.getDepartement());
            prepare.executeUpdate();
            obj = this.find(id); }
        JOptionPane.showMessageDialog(null, "Enregistrement de l'activité effectué avec reussi","Brt Appli", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage() + " DAO Activité");
        }
        return obj;
    }

    @Override
    public Activite update(Activite obj) {
       try {
           int p = JOptionPane.showConfirmDialog(null, "Voulez vous modifier?", "Modifier", JOptionPane.YES_NO_OPTION);
            if (p==0){
            this.connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE).executeUpdate(
            "UPDATE t_activite SET nom_act = '" +obj.getNom_act() +"', type_act ='"+obj.getType_act()+"', description_act='"+obj.getDescription_act()+"', departement = '"+obj.getDepartement()+"'"+" WHERE id_act = " +obj.getId_act());
            obj = this.find(obj.getId_act());
            JOptionPane.showMessageDialog(null, "Modification de l'activité effectué avec reussi","Brt Appli", JOptionPane.INFORMATION_MESSAGE);
            }
            } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage() + " DAO Activité : Update");
        }
        return obj;
    }

    @Override
    public void delete(Activite obj) {
        try {
             int p = JOptionPane.showConfirmDialog(null, "Voulez vous supprimer?", "Supprimer", JOptionPane.YES_NO_OPTION);
            if (p==0){
            this.connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE).executeUpdate(
            "DELETE FROM t_activite WHERE id_act = " +obj.getId_act());
            JOptionPane.showMessageDialog(null, "Suppression de l'activité effectué avec reussi","Brt Appli", JOptionPane.INFORMATION_MESSAGE);
            }
            } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage() + " DAO Activité"); 
        }
    }
    }

