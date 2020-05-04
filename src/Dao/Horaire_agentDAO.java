/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Dao;

import Entites.Agent;
import Entites.Horaire_agent;
import Entites.Materiel;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;

/**
 *
 * @author Gauss
 */
public class Horaire_agentDAO extends Dao<Horaire_agent>{

    @Override
    public Horaire_agent find(long id) {
        Horaire_agent horaire_agent = new Horaire_agent();
        try {
            ResultSet result = this.connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY).executeQuery(
            "SELECT * FROM t_horaire_agent WHERE id_horaire_agent = " +id);
            if(result.first())
            horaire_agent = new Horaire_agent((int)id, result.getDate("date_de_service"), result.getTime("heure_tot"), result.getTime("heure_tard"), new AgentDAO().find(result.getInt("id_agent")));
          } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e+" DAO Horaire");
        }
        return horaire_agent;
    }

    @Override
    public Horaire_agent create(Horaire_agent obj) {
    try {
            ResultSet result = this.connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE).executeQuery(
            "SELECT MAX(id_horaire_agent) FROM t_horaire_agent as id");
            if(result.first()){
            long id =result.getLong("Max");
            PreparedStatement prepare = this.connect.prepareStatement(
            "INSERT INTO t_horaire_agent (date_de_service, heure_tot, heure_tard, id_agent)"+"VALUES(?, ?, ?, ?)");
            prepare.setDate(1, obj.getDate_de_service());
            prepare.setTime(2, obj.getHeure_tot());
            prepare.setTime(3, obj.getHeure_tard());
            prepare.setInt(4, obj.getAgent().getId_agent());
            prepare.executeUpdate();
            obj = this.find(id); }
            JOptionPane.showMessageDialog(null, "Enregistrement de l'horaire effectué avec reussi","Brt Appli", JOptionPane.INFORMATION_MESSAGE);
   
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e+ " DAO Horaire_Agent");
        }
        return obj;    
    }

    @Override
    public Horaire_agent update(Horaire_agent obj) {
      try {int p = JOptionPane.showConfirmDialog(null, "Voulez vous modifier?", "Modifier", JOptionPane.YES_NO_OPTION);
            if (p==0){
            this.connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE).executeUpdate(
            "UPDATE t_horaire_agent SET date_de_service = '" +obj.getDate_de_service()+ "',"+" heure_tot = '" +obj.getHeure_tot() + "', heure_tard='"+obj.getHeure_tard()+"',"+" id_agent = '" +obj.getAgent().getId_agent()+ "'"+
            " WHERE id_horaire_agent = " +obj.getId_horaire_agent());
            obj = this.find(obj.getId_horaire_agent()); 
            JOptionPane.showMessageDialog(null, "Modification de l'horaire effectué avec reussi","Brt Appli", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e+ " DAO Horaire Agent");
        }
        return obj; 
    }

    @Override
    public void delete(Horaire_agent obj) {
        try {
             int p = JOptionPane.showConfirmDialog(null, "Voulez vous supprimer?", "Supprimer", JOptionPane.YES_NO_OPTION);
            if (p==0){
            this.connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE).executeUpdate(
            "DELETE FROM t_horaire_agent WHERE id_horaire_agent = " +obj.getId_horaire_agent());
            JOptionPane.showMessageDialog(null, "Suppression de l'horaire effectué avec reussi","Brt Appli", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e+ " DAO Horaire Agent");
        }
    }
    
}
