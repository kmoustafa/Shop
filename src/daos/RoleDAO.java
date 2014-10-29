/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package daos;

import java.sql.Connection;
import java.util.List;
import model.Role;
import utils.DBHandler;

/**
 *
 * @author Kareem.Moustafa
 */
public class RoleDAO {
    
    Connection connection;
    
    public RoleDAO(){
        connection = DBHandler.connect();
    }
    
    public int insertRole(){
        int status = 0;
        
        
        return status;
    }
    
    public int updateRole(){
        int status = 0;
        
        return status;
    }
    
    public int deleteRole(){
        int status = 0;
        
        return status;
    }
    
    public Role getRoleById(){
        Role b = null;
        
        return b;
    }
    
    public List<Role> getAllRoles(){
        List roles = null;
        
        return roles;
    }
}
