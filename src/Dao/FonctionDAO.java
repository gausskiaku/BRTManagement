/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Dao;

import Entites.Fonction;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author Gauss
 */
public class FonctionDAO extends Dao<Fonction>{

    
    public ResultSet PourTable(){
        ResultSet result = null;
        try {
            result = this.connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE).executeQuery(
            "select id_fonction as \"Numero\", fonction as \"Fonction\" from t_fonction");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
        return result;
    }
    public int VerifierId (String nom_fonction){
    int id = 0;
    try {
        ResultSet result = this.connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE).executeQuery(
                "SELECT id_fonction FROM t_fonction WHERE fonction = '"+nom_fonction+"'");
        if(result.next())
        id = result.getInt("id_fonction");
    } catch (Exception e) {
        JOptionPane.showMessageDialog(null, e + " Voir VerifierId : Fonction");
    }
return id;
}
    @Override
    public Fonction find(long id) {
     Fonction fonction = new Fonction();
        try {
            ResultSet result = this.connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE).executeQuery(
            "SELECT * FROM t_fonction WHERE id_fonction = " +id);
            if(result.first())
            fonction = new Fonction((int) id, result.getString("fonction"));
       } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e + " Dao Fonction : Find");
        }
        return fonction;
    }

    @Override
    public Fonction create(Fonction obj) {
        try {
            ResultSet result = this.connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE).executeQuery(
                    "SELECT MAX(id_fonction) FROM t_fonction");
            if(result.first()){
            long id =result.getLong("max");
            PreparedStatement prepare = this.connect.prepareStatement("INSERT INTO t_fonction (fonction) VALUES(?)");
            prepare.setString(1, obj.getFonction());
            prepare.executeUpdate();
            obj = this.find(id); }
            JOptionPane.showMessageDialog(null, "Enregistrement de la fonction effectué avec reussi","Brt Appli", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e + " Dao Fonction : Create");
        }
        return obj;
    }

    @Override
    public Fonction update(Fonction obj) {
        try {
            int p = JOptionPane.showConfirmDialog(null, "Voulez vous modifier?", "Modifier", JOptionPane.YES_NO_OPTION);
            if (p==0){
            this.connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE).executeUpdate(
            "UPDATE t_fonction SET fonction = '" +obj.getFonction() + "'"+" WHERE id_fonction = " +obj.getId_fonction());
            obj = this.find(obj.getId_fonction());
            JOptionPane.showMessageDialog(null, "Modification de la fonction effectué avec reussi","Brt Appli", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e + " Dao Fonction : Update");
        }
        return obj;
    }

    @Override
    public void delete(Fonction obj) {
        try {
            int p = JOptionPane.showConfirmDialog(null, "Voulez vous supprimer?", "Supprimer", JOptionPane.YES_NO_OPTION);
            if (p==0){
            this.connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE).executeUpdate(
            "DELETE FROM t_fonction WHERE id_fonction = " +obj.getId_fonction());
            JOptionPane.showMessageDialog(null, "Suppression de la fonction effectué avec reussi","Brt Appli", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e+" Dao Fnction : Delete");
        }
    }
    
}
