/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Dao;

import Entites.Presence;
import Entites.User;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author Gauss
 */
public class UserDAO extends Dao<User>{
    
    
     public ResultSet PourTable(){
        ResultSet result = null;
        try {
            result = this.connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE).executeQuery(
            "SELECT id_user as \"Numero\", nom_user as \"Nom du departement\", passwd_user as \"Mot de passe\", nom_agent as \"Nom de l'agent\", post_nom_agent as \"Postnom\", prenom_agent as \"Prenom\" FROM t_user INNER JOIN t_agent ON t_user.id_agent = t_agent.id_agent");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
        return result;
    }
     public ResultSet Recherche(String nom){
    ResultSet result = null;
        try {
            result = this.connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE).executeQuery(
                    "SELECT t_user.nom_user, t_user.passwd_user,prenom_agent, t_agent.matricule_agent, t_agent.nom_agent FROM t_user INNER JOIN t_agent ON t_user.id_agent = t_agent.id_agent WHERE t_user.passwd_user = '"+nom+"'");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
        return result;
    }
    @Override
    public User find(long id) {
        User user = new User();
        try {
            ResultSet result = this.connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE).executeQuery(
            "SELECT * FROM t_user WHERE id_user = " +id);
            if(result.first())
            user = new User((int) id, result.getString("nom_user"), result.getString("passwd_user"), new AgentDAO().find(result.getInt("id_agent")));
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e+" Dao User : find");
        }
        return user;
        
    }

    @Override
    public User create(User obj) {
        try {
            ResultSet result = this.connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE).executeQuery(
"SELECT MAX(id_user) FROM t_user as id");
            if(result.first()){
            long id =result.getLong("max");
            PreparedStatement prepare = this.connect.prepareStatement("INSERT INTO t_user (nom_user, passwd_user, id_agent) VALUES(?,?,?)");
            prepare.setString(1, obj.getNom_user());
            prepare.setString(2, obj.getPasswd_user());
            prepare.setInt(3, obj.getAgent().getId_agent()); 
            prepare.executeUpdate();
             
obj = this.find(id); }
            JOptionPane.showMessageDialog(null, "Enregistrement de l'utilisateur effectué avec succes","Brt Appli", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e+ " Dao User : Create");
        }
        return obj;
    }

    @Override
    public User update(User obj) {
        try {
            int p = JOptionPane.showConfirmDialog(null, "Voulez vous modifier?", "Modifier", JOptionPane.YES_NO_OPTION);
            if (p==0){
            this.connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE).executeUpdate(
            "UPDATE t_user SET nom_user = '" +obj.getNom_user() + "' ,passwd_user = '"+obj.getPasswd_user()+"'"+" WHERE id_user = " +obj.getId_user());
            obj = this.find(obj.getId_user());
            }
            } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e+ " Dao User : Update");
        }
        return obj;
    }

    @Override
    public void delete(User obj) {
        try {
             int p = JOptionPane.showConfirmDialog(null, "Voulez vous supprimer?", "Supprimer", JOptionPane.YES_NO_OPTION);
            if (p==0){
             this.connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE).executeUpdate(
            "DELETE FROM t_user WHERE id_user = " +obj.getId_user());
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e+ " Dao User : Delete");
        }
    }
    
}
