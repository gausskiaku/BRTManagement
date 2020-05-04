/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Dao;

import Entites.Agent;
import Entites.Animateur;
import Entites.Assumer;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;

/**
 *
 * @author Gauss
 */
public class AssumerDAO extends Dao<Assumer>{

    
    public Assumer findOk(long id1, long id2) {
       Assumer assumer = new Assumer();
       try {
            ResultSet result = this.connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY).executeQuery(
            "SELECT * FROM t_assumer WHERE id_agent = " +id1+ " and id_fonction = "+id2);
            if(result.first())
             assumer = new Assumer(new AgentDAO().find(result.getInt("id_agent")), new FonctionDAO().find(result.getInt("id_fonction")), result.getString("date_debut_assumer"));
            
       } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e+" DAO Assumer : Find");
        }
        return assumer;
    }
    @Override
    public Assumer find(long id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Assumer create(Assumer obj) {
      try {
            ResultSet result = this.connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE).executeQuery(
            "SELECT MAX(id_agent) as id1, MAX(id_fonction) as id2 FROM t_assumer ");
            if(result.first()){
            long id1 =result.getLong("id1");
            long id2 = result.getLong("id2");
            PreparedStatement prepare = this.connect.prepareStatement(
            "INSERT INTO t_assumer (id_agent, id_fonction, date_debut_assumer)"+"VALUES(?, ?, ?)");
            prepare.setInt(1, obj.getAgent().getId_agent());
            prepare.setInt(2, obj.getFonction().getId_fonction());
            prepare.setString(3, obj.getDate_debut());
            prepare.executeUpdate();
            obj = this.findOk(id1,id2); 
            }
//            JOptionPane.showMessageDialog(null, "Enregistrement assumer effectué avec reussi","Brt Appli", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e+ " DAO Assumer : Create");
        }
        return obj;  
    }

    @Override
    public Assumer update(Assumer obj) {
      try {
            this.connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE).executeUpdate(
            "UPDATE t_assumer SET id_agent = '" +obj.getAgent().getId_agent()+ "',"+" id_fonction = '" +obj.getFonction().getId_fonction() + "',"+" date_debut_assumer = '" +obj.getDate_debut()+ "'"+
            " WHERE id_agent = " +obj.getAgent().getId_agent()+ " AND id_fonction = '"+obj.getFonction().getId_fonction()+"'");
            obj = this.findOk(obj.getAgent().getId_agent(), obj.getFonction().getId_fonction());  
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e+ " DAO Assumer : Update");
        }
        return obj;  
    }

    @Override
    public void delete(Assumer obj) {
        try {
            this.connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE).executeUpdate(
            "DELETE FROM t_assumer WHERE id_agent = " +obj.getAgent().getId_agent()+ " AND id_fonction = '"+obj.getFonction().getId_fonction()+"'");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e+ " DAO Assumer : Delete");
        }
    }
    
}
