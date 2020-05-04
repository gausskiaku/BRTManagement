/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Dao;

import Entites.Animateur;
import Entites.Materiel;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;

/**
 *
 * @author Gauss
 */
public class AnimateurDAO extends Dao<Animateur>{

   
    public Animateur findOk(long id1, long id2) {
       Animateur animateur = new Animateur();
       try {
            ResultSet result = this.connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY).executeQuery(
            "SELECT * FROM t_animateur WHERE id_agent = " +id1+ " and id_prog = "+id2);
            
            if(result.first())
            animateur =  new Animateur(new AgentDAO().find(result.getInt("id_agent")), new ProgrammeDAO().find(result.getInt("id_prog")));
            
       } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e+" DAO Animateur : Find");
        }
        return animateur;
    }

    @Override
    public Animateur create(Animateur obj) {
        try {
            ResultSet result = this.connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE).executeQuery(
            "SELECT MAX(id_agent) as id1, MAX(id_prog) as id2 FROM t_animateur ");
            if(result.first()){
            long id1 =result.getLong("id1");
            long id2 = result.getLong("id2");
            PreparedStatement prepare = this.connect.prepareStatement(
            "INSERT INTO t_animateur (id_agent, id_prog)"+"VALUES(?, ?)");
            prepare.setInt(1, obj.getAgent().getId_agent());
            prepare.setInt(2, obj.getProgramme().getId_prog());
            prepare.executeUpdate();
            obj = this.findOk(id1,id2); 
            }
//           JOptionPane.showMessageDialog(null, "Enregistrement de l'animateur effectué avec reussi","Brt Appli", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e+ " DAO Animateur : Create");
        }
        return obj;
    }

    @Override
    public Animateur update(Animateur obj) {
        try {
            this.connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE).executeUpdate(
            "UPDATE t_animateur SET id_agent = '" +obj.getAgent().getId_agent()+ "',"+" id_prog = '" +obj.getProgramme().getId_prog() + "'"+
            " WHERE id_agent = " +obj.getAgent().getId_agent()+ " AND id_prog= '"+obj.getProgramme().getId_prog()+"'");
            obj = this.findOk(obj.getAgent().getId_agent(), obj.getProgramme().getId_prog()); 
                   JOptionPane.showMessageDialog(null, "Modification dde l'animateur effectué avec reussi","Brt Appli", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e+ " DAO Animateur : Update");
        }
        return obj;
    }

    @Override
    public void delete(Animateur obj) {
       try {
            this.connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE).executeUpdate(
            "DELETE FROM t_animateur WHERE id_agent = " +obj.getAgent().getId_agent()+ " AND id_prog = '"+obj.getProgramme().getId_prog()+"'");
            JOptionPane.showMessageDialog(null, "Suppression de l'animateur effectué avec reussi","Brt Appli", JOptionPane.INFORMATION_MESSAGE);
       } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e+ " DAO Animateur : Delete");
        }
    }

    @Override
    public Animateur find(long id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
