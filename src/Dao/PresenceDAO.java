/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Dao;

import Entites.Horaire_agent;
import Entites.Materiel;
import Entites.Presence;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author Gauss
 */
public class PresenceDAO extends Dao<Presence>{
    
    
     public ResultSet InfoCombo(String date){
        ResultSet result = null;
        try {
            result = this.connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE).executeQuery(
            "SELECT t_agent.matricule_agent, t_agent.nom_agent, t_agent.post_nom_agent, t_agent.prenom_agent, t_presence.date_presence, t_presence.heure_arrive, t_presence.heure_depart FROM t_agent INNER JOIN t_presence ON t_agent.id_agent = t_presence.id_agent WHERE t_presence.disponibilite = 'Oui' and t_presence.date_presence LIKE '"+date+"%'");
            
        } catch (Exception e) {
        }
        return result;
    }
      public ResultSet Info(String date){
        ResultSet result = null;
        try {
            result = this.connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE).executeQuery(
            "SELECT * FROM t_presence RIGHT JOIN t_agent ON t_presence.id_agent = t_agent.id_agent WHERE t_presence.heure_arrive IS NULL and (t_presence.date_presence NOT LIKE '"+date+"%' OR t_presence.date_presence IS NULL)");
            
        } catch (Exception e) {
        }
        return result;
    }
     public ResultSet PourTable(String date){
        ResultSet result = null;
        try {
            result = this.connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE).executeQuery(
            "SELECT t_presence.id_presence as \"Numero\", t_agent.nom_agent as \"Nom\", t_agent.post_nom_agent as \"PostNom\", t_agent.prenom_agent as \"Prenom\", t_presence.heure_arrive as \"Heure d'arrivé\", t_presence.heure_depart as \"Heure de Sortie\" "
          + "FROM t_agent INNER JOIN t_presence ON t_agent.id_agent = t_presence.id_agent WHERE t_presence.date_presence LIKE '"+date+"%' ORDER BY t_presence.heure_arrive");
            
        } catch (Exception e) {
        }
        return result;
    }
     public int VerifierId (String nom, String postnom , String date){
    int id = 0;
    try {
        ResultSet result = this.connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE).executeQuery(
                "SELECT * FROM t_presence INNER JOIN t_agent ON t_presence.id_agent = t_agent.id_agent WHERE t_presence.date_presence LIKE '"+date+"%' and t_agent.nom_agent LIKE '"+nom+"%' and t_agent.post_nom_agent LIKE '"+postnom+"%'");
        if(result.last())
        id = result.getInt("id_presence");
    } catch (Exception e) {
        JOptionPane.showMessageDialog(null, e + " Voir VerifierId : Presence");
    }
return id;
}
     public Presence updateSortie (Presence obj){
        try {
            this.connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE).executeUpdate(
            "UPDATE t_presence SET heure_depart = '"+obj.getHeure_depart()+"', disponibilite = '"+obj.getDisponibilite()+"' WHERE id_presence = "+obj.getId_presence());
            obj = this.find(obj.getId_presence());
            JOptionPane.showMessageDialog(null, "Enregistrement du sortie de l'agent effectué avec succes","Brt Appli", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e+ " DAO Presence : Sortie");
        }
        return obj;
}
    @Override
    public Presence find(long id) {
        Presence presence = new Presence();
        try {
            ResultSet result = this.connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY).executeQuery(
            "SELECT * FROM t_presence WHERE id_presence = " +id);
            if(result.first())
            presence = new Presence((int) id, result.getString("date_presence"), result.getString("jour"), result.getString("heure_arrive"), result.getString("heure_depart"), result.getString("disponibilite"), new AgentDAO().find(result.getInt("id_agent")));
            } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e+" DAO Presence : Find");
        }
        return presence;   
    }

    @Override
    public Presence create(Presence obj) {
        try {
            ResultSet result = this.connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE).executeQuery(
"SELECT MAX(id_presence) FROM t_presence as id");
            if(result.first()){
            long id =result.getLong("max");
PreparedStatement prepare = this.connect.prepareStatement("INSERT INTO t_presence (date_presence, jour, heure_arrive,heure_depart, disponibilite, id_agent) VALUES(?,?,?,?,?,?)");
prepare.setString(1, obj.getDate_presence());
prepare.setString(2, obj.getJour());
prepare.setString(3, obj.getHeure_arrive());
prepare.setString(4, obj.getHeure_depart());
prepare.setString(5, obj.getDisponibilite());
prepare.setInt(6, obj.getAgent().getId_agent());
prepare.executeUpdate();
obj = this.find(id); }
            JOptionPane.showMessageDialog(null, "Enregistrement de la presence effectué avec Succes","Brt Appli", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e + " Dao presence : Create");
        }
        return obj;
    }

    @Override
    public Presence update(Presence obj) {
        try {
            this.connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE).executeUpdate(
"UPDATE t_presence SET date_presence = '" +obj.getDate_presence() + "' ,jour = '"+obj.getJour()+"' ,heure_arrive='"+obj.getHeure_arrive()+"' , heure_depart='"+obj.getHeure_depart()+"', id_agent = '"+obj.getAgent().getId_agent()+"'"+" WHERE id_presence = " +obj.getId_presence());
obj = this.find(obj.getId_presence());
JOptionPane.showMessageDialog(null, "Modification de la presence effectué avec reussi","Brt Appli", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e + " Dao presence : Update");
        }
        return obj;
         }
    public Presence updateGestion(Presence obj) {
        try {
            int p = JOptionPane.showConfirmDialog(null, "Voulez vous modifier?", "Modifier", JOptionPane.YES_NO_OPTION);
            if (p==0){
            this.connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE).executeUpdate(
"UPDATE t_presence SET heure_arrive='"+obj.getHeure_arrive()+"' , heure_depart='"+obj.getHeure_depart()+"'"+" WHERE id_presence = " +obj.getId_presence());
obj = this.find(obj.getId_presence());
JOptionPane.showMessageDialog(null, "Modification de la presence effectué avec reussi","Brt Appli", JOptionPane.INFORMATION_MESSAGE);
            }
            } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e + " Dao presence : Update");
        }
        return obj;
         }
    @Override
    public void delete(Presence obj) {
        try {
             int p = JOptionPane.showConfirmDialog(null, "Voulez vous supprimer?", "Supprimer", JOptionPane.YES_NO_OPTION);
            if (p==0){
            this.connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE).executeUpdate(
            "DELETE FROM t_presence WHERE id_presence = " +obj.getId_presence());
            JOptionPane.showMessageDialog(null, "Suppression de la presence effectué avec reussi","Brt Appli", JOptionPane.INFORMATION_MESSAGE);
            }
            } catch (Exception e) {
             JOptionPane.showMessageDialog(null, e + " Dao presence : Delete");
        }
    }
    
    
}
