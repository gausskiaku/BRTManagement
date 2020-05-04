/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Classe_trans;

/**
 *
 * @author Gauss
 */
public class pasvVde {
     public pasvVde()
    {}
         public boolean bst(String mot) {
        boolean val = false;
        try {
            if (mot.toString().equals("")) {
                val = true;
            }
        } catch (Exception e) {
        }
        return val;
    }
public boolean  bfl(String mot){
    boolean val=false;
    try {
        float n=Float.parseFloat(mot);
        val=true;
    } catch (Exception e) {
    }
    return val;
}
    public boolean bint(String mot) {
        boolean val = false;
        try {
            int n = Integer.parseInt(mot);
           val = true;
        } catch (Exception e) {
        }
        return val;
    }
    public static void main(String[] d) {
        new pasvVde();
    }
    
}
