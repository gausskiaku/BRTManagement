/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package tfc;

import Dao.Dao;
import Dao.UserDAO;
import Entites.User;

/**
 *
 * @author Gauss
 */
public class Essaie {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Dao<User> userDao = new UserDAO();
        for (int i = 1; i < 10 ; i++)
        System.out.println(userDao.find(i));
    }
    
}
