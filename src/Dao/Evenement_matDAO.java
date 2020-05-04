/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Dao;

import Entites.Evenement_mat;
import Entites.Materiel;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;

/**
 *
 * @author Gauss
 */
public class Evenement_matDAO extends Dao<Evenement_mat>{

    
    
    public ResultSet info(String nom){
    ResultSet result = null;
        try {
            result = this.connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE).executeQuery(
            "SELECT t_materiel.type_mat, t_evenement_mat.id_ev,t_evenement_mat.type_ev, t_evenement_mat.date_ev, t_evenement_mat.heure, t_agent.nom_agent, t_agent.prenom_agent "
            + "FROM t_materiel INNER JOIN t_evenement_mat ON t_materiel.id_mat = t_evenement_mat.id_mat INNER JOIN t_agent ON t_evenement_mat.id_agent = t_agent.id_agent "
            + "WHERE t_evenement_mat.type_ev = 'Sortie' and t_materiel.type_mat = '"+nom+"' ORDER BY id_ev ASC");
            result.last();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e+" DAO Evenement_Mat : Info");
        }
    return result;
    }
    @Override
    public Evenement_mat find(long id) {
    Evenement_mat evenement_mat = new Evenement_mat();
        try {
            ResultSet result = this.connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY).executeQuery(
            "SELECT * FROM t_evenement_mat WHERE id_ev = " +id);
            if(result.first())
            evenement_mat = new Evenement_mat((int) id, result.getString("type_ev"), result.getString("jour"), result.getString("date_ev"), result.getString("heure"), result.getString("etat_mat"), result.getInt("quantite"), new MaterielDAO().find(result.getInt("id_mat")), new AgentDAO().find(result.getInt("id_agent")));
           
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e+" DAO Evenement_Mat");
        }
        return evenement_mat;   
    }

    @Override
    public Evenement_mat create(Evenement_mat obj) {
        try {
            ResultSet result = this.connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE).executeQuery(
            "SELECT MAX(id_ev) FROM t_evenement_mat as id");
            if(result.first()){
            long id =result.getLong("max");
            PreparedStatement prepare = this.connect.prepareStatement(
            "INSERT INTO t_evenement_mat (type_ev, jour, date_ev, heure, etat_mat, quantite, id_mat, id_agent)"+"VALUES(?, ?, ?, ?, ?, ?, ?, ?)");
            prepare.setString(1, obj.getType_ev());
            prepare.setString(2, obj.getJour());
            prepare.setString(3, obj.getDate_ev());
            prepare.setString(4, obj.getHeure());
            prepare.setString(5, obj.getEtat_mat());
            prepare.setInt(6, obj.getQuantite());
            prepare.setInt(7, obj.getMateriel().getId_mat());
            prepare.setInt(8, obj.getAgent().getId_agent());
            prepare.executeUpdate();
            obj = this.find(id); }
            JOptionPane.showMessageDialog(null, "Enregistrement de l'evenement effectué avec succes","Brt Appli", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e+ " DAO Evenement");
        }
        return obj;
    }

    @Override
    public Evenement_mat update(Evenement_mat obj) {
     try {
         int p = JOptionPane.showConfirmDialog(null, "Voulez vous modifier?", "Modifier", JOptionPane.YES_NO_OPTION);
            if (p==0){
            this.connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE).executeUpdate(
            "UPDATE t_evenement_mat SET type_ev = '" +obj.getType_ev()+ "',"+" jour = '" +obj.getJour() + "',"+" date_ev = '" +obj.getDate_ev()+ "', "
                    + "heure = '"+obj.getHeure()+"', etat_mat = '"+obj.getEtat_mat()+"', quantite='"+obj.getEtat_mat()+"', id_mat = '"+obj.getMateriel().getId_mat()+"', "
                    + "id_agent = '"+obj.getAgent().getId_agent()+"'"+" WHERE id_ev = " +obj.getId_ev());
            obj = this.find(obj.getId_ev());  
            JOptionPane.showMessageDialog(null, "Modification de l'evenement effectué avec reussi","Brt Appli", JOptionPane.INFORMATION_MESSAGE);
            }
            } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e+ " DAO Evenement");
        }
        return obj;   
    }

    @Override
    public void delete(Evenement_mat obj) {
     try {
          int p = JOptionPane.showConfirmDialog(null, "Voulez vous supprimer?", "Supprimer", JOptionPane.YES_NO_OPTION);
            if (p==0){
            this.connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE).executeUpdate(
            "DELETE FROM t_evenement_mat WHERE id_ev = " +obj.getId_ev());
            JOptionPane.showMessageDialog(null, "Suppression de l'evenement effectué avec reussi","Brt Appli", JOptionPane.INFORMATION_MESSAGE);
            }
            } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e+ " DAO Evenement");
        }  
    }
    
}
