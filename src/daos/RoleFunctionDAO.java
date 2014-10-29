/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package daos;

import java.sql.Connection;
import java.util.List;
import model.RoleFunction;
import utils.DBHandler;

/**
 *
 * @author Kareem.Moustafa
 */
public class RoleFunctionDAO {
    
    Connection connection;
    
    public RoleFunctionDAO(){
        connection = DBHandler.connect();
    }
    
    public int insertRoleFunction(){
        int status = 0;
        
        
        return status;
    }
    
    public int updateRoleFunction(){
        int status = 0;
        
        return status;
    }
    
    public int deleteRoleFunction(){
        int status = 0;
        
        return status;
    }
    
    public RoleFunction getRoleFunctionById(){
        RoleFunction b = null;
        
        return b;
    }
    
    public List<RoleFunction> getAllRoleFunctions(){
        List rf = null;
        
        return rf;
    }
}
