/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Entites;

import java.sql.Date;





/**
 *
 * @author Gauss
 */
public class Agent {
    private int id_agent;
    private String matricule_agent;
    private String nom_agent;
    private String post_nom_agent;
    private String lieu_de_naissance_agent;
    private String date_de_naissance_agent;
    private String nom_pere;
    private String nom_mere;
    private String etat_civil_agent;
    private String nom_conj;
    private int nombre_enfant;
    private String nationalite_agent;
    private String province_agent;
    private String district_agent;
    private String territoire_agent;
    private String secteur_agent;
    private String num_tel_agent;
    private String email_agent;
    private String commune_agent;
    private String quartier_agent;
    private String avenue_agent;
    private char sexe;
    private String num;
    private String tel_urgent;
    private String degre_parental;
    private String commune_urgent;
    private String quartier_urgent;
    private String avenue_urgent;
    private String num_urgent;
    private Parrain parrain = new Parrain();
    private byte[] photo_agent = new byte[1024];
    private String prenom_agent;
   
   
    public Agent() {
    }

    public Agent(int id_agent, String matricule_agent, String nom_agent, String post_nom_agent, String lieu_de_naissance_agent, String date_de_naissance_agent, String nom_pere, String nom_mere, String etat_civil_agent, String nom_conj, int nombre_enfant, String nationalite_agent, String province_agent, String district_agent, String territoire_agent, String secteur_agent, String num_tel_agent, String email_agent, String commune_agent, String quartier_agent, String avenue_agent, char sexe, String num, String tel_urgent, String degre_parental, String commune_urgent, String quartier_urgent, String avenue_urgent, String num_urgent, String prenom_agent, Parrain parrain, byte[] photo_agent) {
        this.id_agent = id_agent;
        this.matricule_agent = matricule_agent;
        this.nom_agent = nom_agent;
        this.post_nom_agent = post_nom_agent;
        this.lieu_de_naissance_agent = lieu_de_naissance_agent;
        this.date_de_naissance_agent = date_de_naissance_agent;
        this.nom_pere = nom_pere;
        this.nom_mere = nom_mere;
        this.etat_civil_agent = etat_civil_agent;
        this.nom_conj = nom_conj;
        this.nombre_enfant = nombre_enfant;
        this.nationalite_agent = nationalite_agent;
        this.province_agent = province_agent;
        this.district_agent = district_agent;
        this.territoire_agent = territoire_agent;
        this.secteur_agent = secteur_agent;
        this.num_tel_agent = num_tel_agent;
        this.email_agent = email_agent;
        this.commune_agent = commune_agent;
        this.quartier_agent = quartier_agent;
        this.avenue_agent = avenue_agent;
        this.sexe = sexe;
        this.num = num;
        this.tel_urgent = tel_urgent;
        this.degre_parental = degre_parental;
        this.commune_urgent = commune_urgent;
        this.quartier_urgent = quartier_urgent;
        this.avenue_urgent = avenue_urgent;
        this.num_urgent = num_urgent;
        this.prenom_agent = prenom_agent;
        this.parrain = parrain;
        this.photo_agent = photo_agent;
    }

    public int getId_agent() {
        return id_agent;
    }

    public void setId_agent(int id_agent) {
        this.id_agent = id_agent;
    }

    public String getMatricule_agent() {
        return matricule_agent;
    }

    public void setMatricule_agent(String matricule_agent) {
        this.matricule_agent = matricule_agent;
    }

    public String getNom_agent() {
        return nom_agent;
    }

    public void setNom_agent(String nom_agent) {
        this.nom_agent = nom_agent;
    }

    public String getPost_nom_agent() {
        return post_nom_agent;
    }

    public void setPost_nom_agent(String post_nom_agent) {
        this.post_nom_agent = post_nom_agent;
    }

    public String getLieu_de_naissance_agent() {
        return lieu_de_naissance_agent;
    }

    public void setLieu_de_naissance_agent(String lieu_de_naissance_agent) {
        this.lieu_de_naissance_agent = lieu_de_naissance_agent;
    }

    public String getDate_de_naissance_agent() {
        return date_de_naissance_agent;
    }

    public void setDate_de_naissance_agent(String date_de_naissance_agent) {
        this.date_de_naissance_agent = date_de_naissance_agent;
    }

    public String getNom_pere() {
        return nom_pere;
    }

    public void setNom_pere(String nom_pere) {
        this.nom_pere = nom_pere;
    }

    public String getNom_mere() {
        return nom_mere;
    }

    public void setNom_mere(String nom_mere) {
        this.nom_mere = nom_mere;
    }

    public String getEtat_civil_agent() {
        return etat_civil_agent;
    }

    public void setEtat_civil_agent(String etat_civil_agent) {
        this.etat_civil_agent = etat_civil_agent;
    }

    public String getNom_conj() {
        return nom_conj;
    }

    public void setNom_conj(String nom_conj) {
        this.nom_conj = nom_conj;
    }

    public int getNombre_enfant() {
        return nombre_enfant;
    }

    public void setNombre_enfant(int nombre_enfant) {
        this.nombre_enfant = nombre_enfant;
    }

    public String getNationalite_agent() {
        return nationalite_agent;
    }

    public void setNationalite_agent(String nationalite_agent) {
        this.nationalite_agent = nationalite_agent;
    }

    public String getProvince_agent() {
        return province_agent;
    }

    public void setProvince_agent(String province_agent) {
        this.province_agent = province_agent;
    }

    public String getDistrict_agent() {
        return district_agent;
    }

    public void setDistrict_agent(String district_agent) {
        this.district_agent = district_agent;
    }

    public String getTerritoire_agent() {
        return territoire_agent;
    }

    public void setTerritoire_agent(String territoire_agent) {
        this.territoire_agent = territoire_agent;
    }

    public String getSecteur_agent() {
        return secteur_agent;
    }

    public void setSecteur_agent(String secteur_agent) {
        this.secteur_agent = secteur_agent;
    }

    public String getNum_tel_agent() {
        return num_tel_agent;
    }

    public void setNum_tel_agent(String num_tel_agent) {
        this.num_tel_agent = num_tel_agent;
    }

    public String getEmail_agent() {
        return email_agent;
    }

    public void setEmail_agent(String email_agent) {
        this.email_agent = email_agent;
    }

    public String getCommune_agent() {
        return commune_agent;
    }

    public void setCommune_agent(String commune_agent) {
        this.commune_agent = commune_agent;
    }

    public String getQuartier_agent() {
        return quartier_agent;
    }

    public void setQuartier_agent(String quartier_agent) {
        this.quartier_agent = quartier_agent;
    }

    public String getAvenue_agent() {
        return avenue_agent;
    }

    public void setAvenue_agent(String avenue_agent) {
        this.avenue_agent = avenue_agent;
    }

    public char getSexe() {
        return sexe;
    }

    public void setSexe(char sexe) {
        this.sexe = sexe;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getTel_urgent() {
        return tel_urgent;
    }

    public void setTel_urgent(String tel_urgent) {
        this.tel_urgent = tel_urgent;
    }

    public String getDegre_parental() {
        return degre_parental;
    }

    public void setDegre_parental(String degre_parental) {
        this.degre_parental = degre_parental;
    }

    public String getCommune_urgent() {
        return commune_urgent;
    }

    public void setCommune_urgent(String commune_urgent) {
        this.commune_urgent = commune_urgent;
    }

    public String getQuartier_urgent() {
        return quartier_urgent;
    }

    public void setQuartier_urgent(String quartier_urgent) {
        this.quartier_urgent = quartier_urgent;
    }

    public String getAvenue_urgent() {
        return avenue_urgent;
    }

    public void setAvenue_urgent(String avenue_urgent) {
        this.avenue_urgent = avenue_urgent;
    }

    public String getNum_urgent() {
        return num_urgent;
    }

    public void setNum_urgent(String num_urgent) {
        this.num_urgent = num_urgent;
    }

    public Parrain getParrain() {
        return parrain;
    }

    public void setParrain(Parrain parrain) {
        this.parrain = parrain;
    }

    public byte[] getPhoto_agent() {
        return photo_agent;
    }

    public void setPhoto_agent(byte[] photo_agent) {
        this.photo_agent = photo_agent;
    }

    public String getPrenom_agent() {
        return prenom_agent;
    }

    public void setPrenom_agent(String prenom_agent) {
        this.prenom_agent = prenom_agent;
    }
    
}    