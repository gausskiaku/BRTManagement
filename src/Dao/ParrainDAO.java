/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Dao;

import Entites.Emplacement;
import Entites.Parrain;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;

/**
 *
 * @author Gauss
 */
public class ParrainDAO extends Dao<Parrain>{

    public ResultSet AutreInfo (String nom){
        ResultSet result = null;
        try {
            result = this.connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE).executeQuery(
            "SELECT * FROM t_parrain WHERE nom_parrain ='"+nom+"' ");
        } catch (Exception e) {
        }
    return result;
    }
    public ResultSet PourTable(){
        ResultSet result = null;
        try {
            result = this.connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE).executeQuery(
            "SELECT id_parrain as \"Numero\", nom_parrain as \"Nom du parrain\", prenom_parrain as \"Prenom du parrain\", num_tel_parrain as \"Numero telephone\" FROM t_parrain");
            
        } catch (Exception e) {
        }
        return result;
    }
    public int VerifierId (String nom, String prenom){
int id = 0;
    try {
        ResultSet result = this.connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE).executeQuery(
                "SELECT id_parrain FROM t_parrain WHERE nom_parrain = '"+nom+"' and prenom_parrain = '"+prenom+"'");
        if(result.next())
        id = result.getInt("id_parrain");
    } catch (Exception e) {
        JOptionPane.showMessageDialog(null, e + " Voir VerifierId : Parrain");
    }
return id;
}
    @Override
    public Parrain find(long id) {
        Parrain parrain = new Parrain();
        try {
            ResultSet result = this.connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE).executeQuery(
            "SELECT * FROM t_parrain WHERE id_parrain = " +id);
            if(result.first())
            parrain = new Parrain((int) id, result.getString("nom_parrain"), result.getString("prenom_parrain"), result.getString("num_tel_parrain"));     
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e+ " DAO Parrain");
        }
        return parrain; }

    @Override
    public Parrain create(Parrain obj) {
        try {
            ResultSet result = this.connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE).executeQuery(
            "SELECT MAX(id_parrain) FROM t_parrain as id");
       if(result.first()){
       long id =result.getLong("max");
            PreparedStatement prepare = this.connect.prepareStatement(
            "INSERT INTO t_parrain (nom_parrain,prenom_parrain,num_tel_parrain) VALUES(?,?,?)");
            prepare.setString(1, obj.getNom_parrain());
            prepare.setString(2, obj.getPrenom_parrain());
            prepare.setString(3, obj.getNum_tel_parrain());
            prepare.executeUpdate();
            obj = this.find(id); }
       JOptionPane.showMessageDialog(null, "Enregistrement du parrain effectué avec reussi","Brt Appli", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage() + " DAO Parrain");
        }
        return obj;
    }

    @Override
    public Parrain update(Parrain obj) {
       try {
           int p = JOptionPane.showConfirmDialog(null, "Voulez vous modifier?", "Modifier", JOptionPane.YES_NO_OPTION);
            if (p==0){
            this.connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE).executeUpdate(
            "UPDATE t_parrain SET nom_parrain = '" +obj.getNom_parrain() + "', prenom_parrain='"+obj.getPrenom_parrain()+"', num_tel_parrain='"+obj.getNum_tel_parrain()+"'"+" WHERE id_parrain = " +obj.getId_parrain());
            obj = this.find(obj.getId_parrain());
            JOptionPane.showMessageDialog(null, "Modification du parrain effectué avec reussi","Brt Appli", JOptionPane.INFORMATION_MESSAGE);
            }
            } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage() + " DAO Parrain");
        }
        return obj; }

    @Override
    public void delete(Parrain obj) {
        try {
            int p = JOptionPane.showConfirmDialog(null, "Voulez vous supprimer?", "Supprimer", JOptionPane.YES_NO_OPTION);
            if (p==0){
            this.connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE).executeUpdate(
            "DELETE FROM t_parrain WHERE id_parrain = " +obj.getId_parrain());
            JOptionPane.showMessageDialog(null, "Suppression du parrain effectué avec reussi","Brt Appli", JOptionPane.INFORMATION_MESSAGE);
            }
            } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage() + " DAO Parrain"); 
        }
    }
    
}
