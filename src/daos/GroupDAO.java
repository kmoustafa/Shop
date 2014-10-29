/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package daos;

import java.sql.Connection;
import java.util.List;
import model.Groups;
import utils.DBHandler;

/**
 *
 * @author Kareem.Moustafa
 */
public class GroupDAO {
    
    Connection connection;
    
    public GroupDAO(){
        connection = DBHandler.connect();
    }
    
    public int insertGroup(){
        int status = 0;
        
        
        return status;
    }
    
    public int updateGroup(){
        int status = 0;
        
        return status;
    }
    
    public int deleteGroup(){
        int status = 0;
        
        return status;
    }
    
    public Groups getGroupById(){
        Groups b = null;
        
        return b;
    }
    
    public List<Groups> getAllGroups(){
        List groups = null;
        
        return groups;
    }
}
