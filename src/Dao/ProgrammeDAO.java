/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Dao;

import Entites.Activite;
import Entites.Presence;
import Entites.Programme;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author Gauss
 */
public class ProgrammeDAO extends Dao<Programme>{

    
    
    public int dernier_id(){
        int id = 0;
        try {
           ResultSet result = this.connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE).executeQuery(
            "SELECT MAX(id_prog) FROM t_programme");
           if(result.next()){
            id = result.getInt("max");
           }
        } catch (Exception e) {
           JOptionPane.showMessageDialog(null, e + "Dernier Id : Dao ProgrammeDao" );
        }
        System.out.println(id);
        return id;
}
    public ResultSet Gestion(String departement){
    ResultSet result = null;
        try {
            result = this.connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE).executeQuery(
            "SELECT id_prog as \"Numero\", date_prog as \"Date programmé\", rediffusion as \"Rediffusion\", date_redif as \"Date de rediffusion\", heure_debut as \"Heure du debut\", heure_fin as \"Heure de la fin\", id_act as \"Num activité\" FROM t_programme WHERE t_programme.departement = '"+departement+"' ORDER BY date_prog, heure_debut, heure_fin;");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e+"Programme DAO : Gestion");
        }
    
    return result;
    }
    public ResultSet Recherche(String date, String departement){
    ResultSet result = null;
        try {
            result = this.connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE).executeQuery(
            "SELECT id_prog as \"Numero\", date_prog as \"Date programmé\", rediffusion as \"Rediffusion\", date_redif as \"Date de rediffusion\", heure_debut as \"Heure du debut\", heure_fin as \"Heure de la fin\", nom_act as \"Nom de l'activité\", type_act as \"Type activité\" FROM t_programme INNER JOIN t_activite ON t_programme.id_act = t_activite.id_act WHERE t_programme.departement LIKE '"+departement+"%' and date_prog Like '"+date+"%' ORDER BY date_prog, heure_debut, heure_fin");
        } catch (Exception e) {
        }
        return result;
    }
    public ResultSet PourTable(String departement){
        ResultSet result = null;
        try {
            result = this.connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE).executeQuery(
            "SELECT id_prog as \"Numero\", date_prog as \"Date programmé\", rediffusion as \"Rediffusion\", date_redif as \"Date de rediffusion\", heure_debut as \"Heure du debut\", heure_fin as \"Heure de la fin\", nom_act as \"Nom de l'activité\", type_act as \"Type activité\" FROM t_programme INNER JOIN t_activite ON t_programme.id_act = t_activite.id_act WHERE t_programme.departement = '"+departement+"' ORDER BY date_prog, heure_debut, heure_fin");
            
        } catch (Exception e) {
        }
        return result;
    }
     public ResultSet PourTable_Horaire(String departement){
        ResultSet result = null;
        try {
            result = this.connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE).executeQuery(
            "SELECT t_agent.nom_agent as \"Nom agent\", t_agent.post_nom_agent as \"PostNom\", t_agent.prenom_agent as \"Prenom\", t_activite.nom_act as \"Activité\", t_activite.type_act as \"Type\", t_programme.date_prog as \"Date difussion\", t_programme.heure_debut as \"Heure debut\", t_programme.heure_fin as \"Heure fin\", t_presence.date_presence as \"Date presence\", t_presence.heure_arrive as \"Heure d'arrivé\", t_presence.heure_depart as \"Heure du depart\" "
            + "FROM t_activite INNER JOIN t_programme ON t_activite.id_act = t_programme.id_act INNER JOIN t_animateur ON t_programme.id_prog = t_animateur.id_prog INNER JOIN t_agent ON t_animateur.id_agent = t_agent.id_agent INNER JOIN t_presence ON t_agent.id_agent = t_presence.id_agent WHERE t_programme.departement LIKE '"+departement+"%'");
            
        } catch (Exception e) {
        }
        return result;
    }
     public ResultSet PourTable_HoraireRechercheDate(String departement, String date){
        ResultSet result = null;
        try {
            result = this.connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE).executeQuery(
            "SELECT t_agent.nom_agent as \"Nom agent\", t_agent.post_nom_agent as \"PostNom\", t_agent.prenom_agent as \"Prenom\", t_activite.nom_act as \"Activité\", t_activite.type_act as \"Type\", t_programme.date_prog as \"Date difussion\", t_programme.heure_debut as \"Heure debut\", t_programme.heure_fin as \"Heure fin\", t_presence.date_presence as \"Date presence\", t_presence.heure_arrive as \"Heure d'arrivé\", t_presence.heure_depart as \"Heure du depart\" "
            + "FROM t_activite INNER JOIN t_programme ON t_activite.id_act = t_programme.id_act INNER JOIN t_animateur ON t_programme.id_prog = t_animateur.id_prog INNER JOIN t_agent ON t_animateur.id_agent = t_agent.id_agent INNER JOIN t_presence ON t_agent.id_agent = t_presence.id_agent WHERE t_programme.departement LIKE '"+departement+"%' and t_programme.date_prog = '"+date+"'");
            
        } catch (Exception e) {
        }
        return result;
    }
     public ResultSet PourTable_HoraireRechercheNom(String departement, String nom){
        ResultSet result = null;
        try {
            result = this.connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE).executeQuery(
            "SELECT t_agent.nom_agent as \"Nom agent\", t_agent.post_nom_agent as \"PostNom\", t_agent.prenom_agent as \"Prenom\", t_activite.nom_act as \"Activité\", t_activite.type_act as \"Type\", t_programme.date_prog as \"Date difussion\", t_programme.heure_debut as \"Heure debut\", t_programme.heure_fin as \"Heure fin\", t_presence.date_presence as \"Date presence\", t_presence.heure_arrive as \"Heure d'arrivé\", t_presence.heure_depart as \"Heure du depart\" "
            + "FROM t_activite INNER JOIN t_programme ON t_activite.id_act = t_programme.id_act INNER JOIN t_animateur ON t_programme.id_prog = t_animateur.id_prog INNER JOIN t_agent ON t_animateur.id_agent = t_agent.id_agent INNER JOIN t_presence ON t_agent.id_agent = t_presence.id_agent WHERE t_programme.departement LIKE '"+departement+"%' and t_agent.nom_agent LIKE '"+nom+"%'");
            
        } catch (Exception e) {
        }
        return result;
    }
    @Override
    public Programme find(long id) {
            Programme programme = new Programme();
    try {
            ResultSet result = this.connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE).executeQuery(
            "SELECT * FROM t_programme WHERE id_prog = " +id);
            if(result.first())
            programme = new Programme((int) id, result.getString("date_prog"), result.getString("heure_debut"), result.getString("heure_fin"), result.getString("rediffusion"), result.getString("date_redif"), result.getString("departement"), new ActiviteDAO().find(result.getInt("id_act")));
     } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e+ " Dao Programme : find");
       }
            return programme;
    }

    @Override
    public Programme create(Programme obj) {
    try {
            ResultSet result = this.connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE).executeQuery(
            "SELECT MAX(id_prog) FROM t_programme as id");
            if(result.first()){
            long id =result.getLong("max");
            PreparedStatement prepare = this.connect.prepareStatement("INSERT INTO t_programme (heure_debut, heure_fin, rediffusion,date_redif,date_prog, departement, id_act) VALUES(?,?,?,?,?,?,?)");
            prepare.setString(1, obj.getHeure_debut());
            prepare.setString(2, obj.getHeure_fin());
            prepare.setString(3, obj.getRediffusion());
            prepare.setString(4, obj.getDate_redif());
            prepare.setString(5, obj.getDate_prog());
            prepare.setString(6, obj.getDepartement());
            prepare.setInt(7, obj.getActivite().getId_act());
            prepare.executeUpdate();
            obj = this.find(id); }
            JOptionPane.showMessageDialog(null, "Enregistrement du programme effectué avec reussi","Brt Appli", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e+ " Dao Programme : Create");
        }
            return obj;
    }

    @Override
    public Programme update(Programme obj) {
    try {
            this.connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE).executeUpdate(
            "UPDATE t_programme SET heure_debut = '" +obj.getHeure_debut() + "' ,heure_fin = '"+obj.getHeure_fin()+"', rediffusion='"+obj.getRediffusion()+"', date_redif='"+obj.getDate_redif()+"', "
                    + "date_prog = '"+obj.getDate_prog()+"', departement = '"+obj.getDepartement()+"', id_act='"+obj.getActivite().getId_act()+"'"+" WHERE id_prog = " +obj.getId_prog());
            obj = this.find(obj.getId_prog());
            JOptionPane.showMessageDialog(null, "Modification du programme effectué avec reussi","Brt Appli", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e + " Dao Programme : Update");
        }
            return obj;
    }

    @Override
    public void delete(Programme obj) {
    try {
         int p = JOptionPane.showConfirmDialog(null, "Voulez vous supprimer?", "Supprimer", JOptionPane.YES_NO_OPTION);
            if (p==0){
            this.connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE).executeUpdate(
            "DELETE FROM t_programme WHERE id_prog = " +obj.getId_prog());
            JOptionPane.showMessageDialog(null, "Suppression du programme effectué avec reussi","Brt Appli", JOptionPane.INFORMATION_MESSAGE);
            }
            } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e+ " Dao Programme : Delete");
        }
    }
    
}
