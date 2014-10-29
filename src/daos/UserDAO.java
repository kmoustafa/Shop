/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package daos;

import java.sql.Connection;
import java.util.List;
import model.Users;
import utils.DBHandler;

/**
 *
 * @author Kareem.Moustafa
 */
public class UserDAO {
    
    Connection connection;
    
    public UserDAO(){
        connection = DBHandler.connect();
    }
    
    public int insertUser(){
        int status = 0;
        
        
        return status;
    }
    
    public int updateUser(){
        int status = 0;
        
        return status;
    }
    
    public int deleteUser(){
        int status = 0;
        
        return status;
    }
    
    public Users getUserById(){
        Users b = null;
        
        return b;
    }
    
    public List<Users> getAllUsers(){
        List users = null;
        
        return users;
    }
}
