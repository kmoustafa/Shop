/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package daos;

import java.sql.Connection;
import java.util.List;
import model.Types;
import utils.DBHandler;

/**
 *
 * @author Kareem.Moustafa
 */
public class TypeDAO {
    
    Connection connection;
    
    public TypeDAO(){
        connection = DBHandler.connect();
    }
    
    public int insertType(){
        int status = 0;
        
        
        return status;
    }
    
    public int updateType(){
        int status = 0;
        
        return status;
    }
    
    public int deleteType(){
        int status = 0;
        
        return status;
    }
    
    public Types getTypeById(){
        Types b = null;
        
        return b;
    }
    
    public List<Types> getAllTypes(){
        List types = null;
        
        return types;
    }
}
