/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package daos;

import java.sql.Connection;
import java.util.List;
import model.Function;
import utils.DBHandler;

/**
 *
 * @author Kareem.Moustafa
 */
public class FunctionDAO {
    
    Connection connection;
    
    public FunctionDAO(){
        connection = DBHandler.connect();
    }
    
    public int insertFunction(){
        int status = 0;
        
        
        return status;
    }
    
    public int updateFunction(){
        int status = 0;
        
        return status;
    }
    
    public int deleteFunction(){
        int status = 0;
        
        return status;
    }
    
    public Function getFunctionById(){
        Function b = null;
        
        return b;
    }
    
    public List<Function> getAllFunctions(){
        List functions = null;
        
        return functions;
    }
}
