/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Entites;

/**
 *
 * @author Gauss
 */
public class Fonction {
    private int id_fonction;
    private String fonction;

    public Fonction() {
    }

    public Fonction(int id_fonction, String fonction) {
        this.id_fonction = id_fonction;
        this.fonction = fonction;
    }

    public int getId_fonction() {
        return id_fonction;
    }

    public void setId_fonction(int id_fonction) {
        this.id_fonction = id_fonction;
    }

    public String getFonction() {
        return fonction;
    }

    public void setFonction(String fonction) {
        this.fonction = fonction;
    }

   }
