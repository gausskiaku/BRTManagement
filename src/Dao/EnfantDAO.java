/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Dao;

import Entites.Agent;
import Entites.Enfant;
import Entites.Materiel;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;

/**
 *
 * @author Gauss
 */
public class EnfantDAO extends Dao<Enfant>{

    
     public ResultSet PourTable(){
        ResultSet result = null;
        try {
            result = this.connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE).executeQuery(
            "SELECT id_enfant as \"Numero\", nom_enfant as \"Nom enfant\", post_nom_enfant as \"PostNom enfant\", prenom as \"Prenom enfant\", t_enfant.id_agent as \"Num agent\", t_agent.nom_agent as \"Nom agent\", t_agent.post_nom_agent as \"PostNom agent\", t_agent.prenom_agent as \"Prenom agent\" from t_enfant INNER JOIN t_agent ON t_enfant.id_agent = t_agent.id_agent");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
        return result;
    }
    @Override
    public Enfant find(long id) {
     Enfant enfant = new Enfant();
        try {
            ResultSet result = this.connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY).executeQuery(
            "SELECT * FROM t_enfant WHERE id_enfant = " +id);
            if(result.first())
           enfant = new Enfant((int) id, result.getString("nom_enfant"), result.getString("post_nom_enfant"), result.getString("prenom"), new AgentDAO().find(result.getInt("id_agent")));
         
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e+" DAO Enfant");
        }
        return enfant;
    
    }

    @Override
    public Enfant create(Enfant obj) {
       Enfant enfant = new Enfant();
       try {
            ResultSet result = this.connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE).executeQuery(
            "SELECT MAX(id_enfant) FROM t_enfant as id");
            if(result.first()){
            long id =result.getLong("max");
            PreparedStatement prepare = this.connect.prepareStatement(
            "INSERT INTO t_enfant (nom_enfant, post_nom_enfant, prenom, id_agent)"+"VALUES(?, ?, ?, ?)");
            prepare.setString(1, obj.getNom_enfant());
            prepare.setString(2, obj.getPost_nom_enfant());
            prepare.setString(3, obj.getPrenom());
            prepare.setInt(4, obj.getAgent().getId_agent());
            
            prepare.executeUpdate();
            obj = this.find(id); }
            JOptionPane.showMessageDialog(null, "Enregistrement de l'enfant de l'agent effectué avec reussi","Brt Appli", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e+ " DAO Enfant");
        }
        return obj;
    }

    @Override
    public Enfant update(Enfant obj) {
        try {
            int p = JOptionPane.showConfirmDialog(null, "Voulez vous modifier?", "Modifier", JOptionPane.YES_NO_OPTION);
            if (p==0){
            this.connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE).executeUpdate(
            "UPDATE t_enfant SET nom_enfant = '" +obj.getNom_enfant()+ "',"+" post_nom_enfant = '" +obj.getPost_nom_enfant() + "', prenom ='"+obj.getPrenom()+"', id_agent='"+obj.getAgent().getId_agent()+"'"+
            " WHERE id_enfant = " +obj.getId_enfant());
            obj = this.find(obj.getId_enfant());  
            }
            } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e+ " DAO Enfant");
        }
        return obj;
    }

    @Override
    public void delete(Enfant obj) {
     try { int p = JOptionPane.showConfirmDialog(null, "Voulez vous supprimer?", "Supprimer", JOptionPane.YES_NO_OPTION);
            if (p==0){
            this.connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE).executeUpdate(
            "DELETE FROM t_enfant WHERE id_enfant = " +obj.getId_enfant());
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e+ " DAO Enfant");
        }   
    }
    
}
