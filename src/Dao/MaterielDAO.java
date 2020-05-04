/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Dao;

import Entites.Materiel;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author Gauss
 */
public class MaterielDAO extends Dao<Materiel>{
    
    public ResultSet Recherche(String nom){
    ResultSet result = null;
        try {
            result = this.connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE).executeQuery(
                    "SELECT id_mat as \"Numero Materiel\", type_mat as \"Type Materiel\", caracteristique as \"Caracteristique\", disponibilite as \"Disponibilité\" FROM t_materiel WHERE type_mat LIKE '"+nom+"%'");
        } catch (Exception e) {
        }
        return result;
    }
    public ResultSet PourTable(){
        ResultSet result = null;
        try {
            result = this.connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE).executeQuery(
            "SELECT id_mat as \"Numero Materiel\", type_mat as \"Type Materiel\", caracteristique as \"Caracteristique\", disponibilite as \"Disponibilité\" FROM t_materiel");
            
        } catch (Exception e) {
        }
        return result;
    }
 public ResultSet Gestion(){
        ResultSet result = null;
        try {
            result = this.connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE).executeQuery(
            "SELECT id_mat as \"Numero Materiel\", type_mat as \"Type Materiel\", caracteristique as \"Caracteristique\", disponibilite as \"Disponibilité\",t_emplacement.id_emp, nom_emp as \"Nom de l'emplacement\" FROM t_materiel INNER JOIN t_emplacement ON t_materiel.id_emp = t_emplacement.id_emp");
            
        } catch (Exception e) {
        }
        return result;
    }
    public int VerifierId (String nom , int id_cle){
    int id = 0;
    try {
        ResultSet result = this.connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE).executeQuery(
                "SELECT id_mat FROM t_materiel WHERE type_mat = '"+nom+"' and id_emp = '"+id_cle+"'");
        if(result.next())
        id = result.getInt("id_mat");
    } catch (Exception e) {
        JOptionPane.showMessageDialog(null, e + " Voir VerifierId");
    }
return id;
}
    public ResultSet AutreInfo(String emplacement){
    ResultSet result = null;
        try {
           result = this.connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE).executeQuery(
                   "SELECT type_mat FROM t_materiel INNER JOIN t_emplacement ON t_materiel.id_mat = t_emplacement.id_emp WHERE  = '"+emplacement+"'");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    return result;
    }
    
    @Override
    public Materiel find(long id) {
        Materiel materiel = new Materiel();
        try {
            ResultSet result = this.connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY).executeQuery(
            "SELECT * FROM t_materiel WHERE id_mat = " +id);
            if(result.first())
            materiel = new Materiel((int) id, result.getString("type_mat"), result.getString("caracteristique"), result.getString("disponibilite"), new EmplacementDAO().find(result.getInt("id_emp")));

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e+" DAO Materiel");
        }
        return materiel;
    }

    @Override
    public Materiel create(Materiel obj) {
        try {
            ResultSet result = this.connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE).executeQuery(
            "SELECT MAX(id_mat) FROM t_materiel as id");
            if(result.first()){
            long id =result.getLong("max");
            PreparedStatement prepare = this.connect.prepareStatement(
            "INSERT INTO t_materiel (type_mat, caracteristique, disponibilite, id_emp)"+"VALUES(?, ?, ?, ?)");
            prepare.setString(1, obj.getType_mat());
            prepare.setString(2, obj.getCaracteristique());
            prepare.setString(3, obj.getDisponibilite());
            prepare.setInt(4, obj.getEmplacement().getId_emp());
            prepare.executeUpdate();
            obj = this.find(id); }
            JOptionPane.showMessageDialog(null, "Enregistrement du materiel effectué avec reussi","Brt Appli", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e+ " DAO Materiel");
        }
        return obj;
    }

    @Override
    public Materiel update(Materiel obj) {
        try {
            int p = JOptionPane.showConfirmDialog(null, "Voulez vous modifier?", "Modifier", JOptionPane.YES_NO_OPTION);
            if (p==0){
            this.connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE).executeUpdate(
            "UPDATE t_materiel SET type_mat = '" +obj.getType_mat()+ "',"+" caracteristique = '" +obj.getCaracteristique() + "', "
            + "disponibilite = '"+obj.getDisponibilite()+"', "+" id_emp = '" +obj.getEmplacement().getId_emp()+ "'"+
            " WHERE id_mat = " +obj.getId_mat());
            obj = this.find(obj.getId_mat());
            JOptionPane.showMessageDialog(null, "Modification du materiel effectué avec reussi","Brt Appli", JOptionPane.INFORMATION_MESSAGE);
            }
            } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e+ " DAO Materiel");
        }
        return obj;
    }
    public Materiel updateDisponibilite (Materiel obj){
        try {
            this.connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE).executeUpdate(
            "UPDATE t_materiel SET disponibilite = '"+obj.getDisponibilite()+"' WHERE id_mat = "+obj.getId_mat());
            obj = this.find(obj.getId_mat());
//            JOptionPane.showMessageDialog(null, "Modification du materiel effectué avec reussi","Brt Appli", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e+ " DAO Materiel : Disponibilité");
        }
        return obj;
}
    @Override
    public void delete(Materiel obj) {
        try {
             int p = JOptionPane.showConfirmDialog(null, "Voulez vous supprimer?", "Supprimer", JOptionPane.YES_NO_OPTION);
            if (p==0){
            this.connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE).executeUpdate(
            "DELETE FROM t_materiel WHERE id_mat = " +obj.getId_mat());
            JOptionPane.showMessageDialog(null, "Suppression du materiel effectué avec reussi","Brt Appli", JOptionPane.INFORMATION_MESSAGE);
            }
            } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e+ " DAO Materiel");
        }
    }
    
}
