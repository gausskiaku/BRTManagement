/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Dao;

import Entites.Agent;
import Entites.Materiel;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;
import javax.swing.JOptionPane;

/**
 *
 * @author Gauss
 */
public class AgentDAO extends Dao<Agent>{

    public ResultSet Recherche(String nom){
    ResultSet result = null;
        try {
            result = this.connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE).executeQuery(
                    "SELECT matricule_agent as \"Matricule\",nom_agent as \"Nom\",post_nom_agent as \"PostNom\",prenom_agent as \"Prenom\", sexe as \"Sexe\", etat_civil_agent as \"Etat civil\", nationalite_agent as \"Nationalité\", num_tel_agent as \"Telephone\", nom_parrain as \"Nom Parrain\", prenom_parrain as \"Prenom Parrain\" FROM t_agent INNER JOIN t_parrain ON t_agent.id_parrain = t_parrain.id_parrain WHERE t_agent.nom_agent LIKE '"+nom+"%'");
        } catch (Exception e) {
        }
        return result;
    }
    public ResultSet PourTable(){
        ResultSet result = null;
        try {
            result = this.connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE).executeQuery(
            "SELECT matricule_agent as \"Matricule\",nom_agent as \"Nom\",post_nom_agent as \"PostNom\",prenom_agent as \"Prenom\", sexe as \"Sexe\", etat_civil_agent as \"Etat civil\", nationalite_agent as \"Nationalité\", num_tel_agent as \"Telephone\", nom_parrain as \"Nom Parrain\", prenom_parrain as \"Prenom Parrain\" FROM t_agent INNER JOIN t_parrain ON t_agent.id_parrain = t_parrain.id_parrain");
            
        } catch (Exception e) {
        }
        return result;
    }
    public String Matricule(){
    String matricule= null;
    int id = 0;
        try {
            ResultSet result = this.connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE).executeQuery(
            "SELECT MAX(id_agent) FROM t_agent");
            if(result.next()){
            matricule = result.getString("max");
            if (matricule == null){
            id = 1;
            }
            else{
            System.out.println(matricule);
            id = Integer.valueOf(matricule).intValue()+1;
            }
            matricule = "BrtAfrica00"+id;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e + "Matricule : Dao Agent" );
        }
        return matricule;
    }
    public ResultSet AutreInfo (String nom){
        ResultSet result = null;
        try {
            result = this.connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE).executeQuery(
            "SELECT * FROM t_agent WHERE nom_agent ='"+nom+"' ");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    return result;
    }
    public ResultSet AutreInfo2 (String nom, String postnom){
        ResultSet result = null;
        try {
            result = this.connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE).executeQuery(
            "SELECT * FROM t_agent WHERE nom_agent ='"+nom+"' and post_nom_agent = '"+postnom+"' ");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    return result;
    }
    public ResultSet recup (String matricule){
        ResultSet result = null;
        try {
            result = this.connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE).executeQuery(
            "SELECT * FROM t_agent WHERE matricule_agent ='"+matricule+"' ");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e + "DAO AGENT RECUP");
        }
        return result;
    }
    public int dernier_id(){
        int id = 0;
        try {
           ResultSet result = this.connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE).executeQuery(
            "SELECT MAX(id_agent) FROM t_agent");
           if(result.next()){
            id = result.getInt("max");
           }
        } catch (Exception e) {
           JOptionPane.showMessageDialog(null, e + "Dernier Id : Dao Agent" );
        }
        System.out.println(id);
        return id;
}
    public int VerifierId (String nom, String postnom){
int id = 0;
    try {
        ResultSet result = this.connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE).executeQuery(
                "SELECT id_agent FROM t_agent WHERE nom_agent = '"+nom+"' and post_nom_agent = '"+postnom+"'");
        if(result.next())
        id = result.getInt("id_agent");
    } catch (Exception e) {
        JOptionPane.showMessageDialog(null, e + " Voir VerifierId : Agent");
    }
return id;
}
    
    @Override
    public Agent find(long id) {
        Agent agent = new Agent();
        try {
            ResultSet result = this.connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE).executeQuery(
            "SELECT * FROM t_agent WHERE id_agent = " +id);
            if(result.first())
            agent = new Agent((int) id, result.getString("matricule_agent"), result.getString("nom_agent"), result.getString("post_nom_agent"),
                    result.getString("lieu_de_naissance_agent"), result.getString("date_de_naissance_agent"), result.getString("nom_pere"), result.getString("nom_mere"),
                    result.getString("etat_civil_agent"), result.getString("nom_conj"), result.getInt("nombre_enfant"), result.getString("nationalite_agent"),
                    result.getString("province_agent"), result.getString("district_agent"), result.getString("territoire_agent"), result.getString("secteur_agent"),
                    result.getString("num_tel_agent"), result.getString("email_agent"), result.getString("commune_agent"), result.getString("quartier_agent"),
                    result.getString("avenue_agent"),(char) result.getObject("sexe"), result.getString("num"), result.getString("tel_urgent"), result.getString("degre_parental"),
                    result.getString("commune_urgent"), result.getString("quartier_urgent"), result.getString("avenue_urgent"), result.getString("num_urgent"),
                    result.getString("prenom_agent"), new ParrainDAO().find(result.getInt("id_parrain")), result.getBytes("photo_agent"));
            } catch (Exception e) {
//            JOptionPane.showMessageDialog(null, e+" DAO Agent : Recherche");
        }
        return agent;
    }

    @Override
    public Agent create(Agent obj) {
     try {
            ResultSet result = this.connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE).executeQuery(
            "SELECT MAX(id_agent) FROM t_agent as id");
            if(result.first()){
            long id =result.getLong("max");
            PreparedStatement prepare = this.connect.prepareStatement(
            "INSERT INTO t_agent (matricule_agent, nom_agent,post_nom_agent,prenom_agent,lieu_de_naissance_agent,date_de_naissance_agent,"
                    + "nom_pere,nom_mere, etat_civil_agent, nom_conj, nombre_enfant, nationalite_agent, province_agent, district_agent, "
                    + "territoire_agent, secteur_agent, num_tel_agent,email_agent,commune_agent,quartier_agent,avenue_agent, sexe, num, "
                    + "tel_urgent, degre_parental, commune_urgent, quartier_urgent, avenue_urgent, num_urgent, id_parrain, photo_agent) "
                    + "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
            
            prepare.setString(1, obj.getMatricule_agent());
            prepare.setString(2, obj.getNom_agent());
            prepare.setString(3, obj.getPost_nom_agent());
            prepare.setString(4, obj.getPrenom_agent());
            prepare.setString(5, obj.getLieu_de_naissance_agent());
            prepare.setString(6, obj.getDate_de_naissance_agent());
            prepare.setString(7, obj.getNom_pere());
            prepare.setString(8, obj.getNom_mere());
            prepare.setString(9, obj.getEtat_civil_agent());
            prepare.setString(10, obj.getNom_conj());
            prepare.setInt(11, obj.getNombre_enfant());
            prepare.setString(12, obj.getNationalite_agent());
            prepare.setString(13, obj.getProvince_agent());
            prepare.setString(14, obj.getDistrict_agent());
            prepare.setString(15, obj.getTerritoire_agent());
            prepare.setString(16, obj.getSecteur_agent());
            prepare.setString(17, obj.getNum_tel_agent());
            prepare.setString(18, obj.getEmail_agent());
            prepare.setString(19, obj.getCommune_agent());
            prepare.setString(20, obj.getQuartier_agent());
            prepare.setString(21, obj.getAvenue_agent());
            prepare.setObject(22, obj.getSexe());
            prepare.setString(23, obj.getNum());
            prepare.setString(24, obj.getTel_urgent());
            prepare.setString(25, obj.getDegre_parental());
            prepare.setString(26, obj.getCommune_urgent());
            prepare.setString(27, obj.getQuartier_urgent());
            prepare.setString(28, obj.getAvenue_urgent());
            prepare.setString(29, obj.getNum_urgent());
            prepare.setInt(30, obj.getParrain().getId_parrain());
            prepare.setBytes(31, obj.getPhoto_agent());
//            
//            
            prepare.executeUpdate();
            obj = this.find(id);
            }
//            JOptionPane.showMessageDialog(null, "Enregistrement de l'agent effectué avec reussi","Brt Appli", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e+ " DAO Agent : Create");
        }
        return obj;  
    }

    @Override
    public Agent update(Agent obj) {
        try {
            int p = JOptionPane.showConfirmDialog(null, "Voulez vous modifier?", "Modifier", JOptionPane.YES_NO_OPTION);
            if (p==0){
            this.connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE).executeUpdate(
            "UPDATE t_agent SET matricule_agent = '" +obj.getMatricule_agent()+ "',"+" nom_agent = '" +obj.getNom_agent() + "', "
                    + "post_nom_agent = '" +obj.getPost_nom_agent()+ "', lieu_de_naissance_agent ='"+obj.getLieu_de_naissance_agent()+"', "
                    + "date_de_naissance_agent = '"+obj.getDate_de_naissance_agent()+"', nom_pere = '"+obj.getNom_pere()+"', "
                    + "nom_mere = '"+obj.getNom_mere()+"', etat_civil_agent= '"+obj.getEtat_civil_agent()+"', nom_conj = '"+obj.getNom_conj()+"', "
                    + "nombre_enfant = '"+obj.getNombre_enfant()+"', nationalite_agent= '"+obj.getNationalite_agent()+"', province_agent = '"+obj.getProvince_agent()+"', "
                    + "district_agent ='"+obj.getDistrict_agent()+"', territoire_agent='"+obj.getTerritoire_agent()+"', secteur_agent = '"+obj.getSecteur_agent()+"', "
                    + "num_tel_agent ='"+obj.getNum_tel_agent()+"', email_agent='"+obj.getEmail_agent()+"', commune_agent='"+obj.getCommune_agent()+"', "
                    + "quartier_agent='"+obj.getQuartier_agent()+"', avenue_agent='"+obj.getAvenue_agent()+"', sexe='"+obj.getSexe()+"', num = '"+obj.getNum()+"', "
                    + "tel_urgent = '"+obj.getTel_urgent()+"', degre_parental= '"+obj.getDegre_parental()+"', commune_urgent='"+obj.getCommune_urgent()+"', "
                    + "quartier_urgent = '"+obj.getQuartier_urgent()+"', avenue_urgent= '"+obj.getAvenue_urgent()+"', num_urgent='"+obj.getNum_urgent()+"', "
                    + "photo_agent ='"+obj.getPhoto_agent()+"', prenom_agent='"+obj.getPrenom_agent()+"', id_parrain = '"+obj.getParrain().getId_parrain()+"' "
                    + "WHERE matricule_agent = " +obj.getMatricule_agent());
            
            obj = this.find(obj.getId_agent()); 
            JOptionPane.showMessageDialog(null, "Modification de l'agent effectué avec succes","Brt Appli", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e+ " DAO Agent");
        }
        return obj;
    }

    @Override
    public void delete(Agent obj) {
    try { int p = JOptionPane.showConfirmDialog(null, "Voulez vous supprimer?", "Supprimer", JOptionPane.YES_NO_OPTION);
            if (p==0){
            this.connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE).executeUpdate(
            "DELETE FROM t_agent WHERE id_agent = " +obj.getId_agent());
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e+ " DAO Atelier");
        }    
    }
    
    
}
