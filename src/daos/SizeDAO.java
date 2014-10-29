/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package daos;

import java.sql.Connection;
import java.util.List;
import model.Sizes;
import utils.DBHandler;

/**
 *
 * @author Kareem.Moustafa
 */
public class SizeDAO {
    
    Connection connection;
    
    public SizeDAO(){
        connection = DBHandler.connect();
    }
    
    public int insertSize(){
        int status = 0;
        
        
        return status;
    }
    
    public int updateSize(){
        int status = 0;
        
        return status;
    }
    
    public int deleteSize(){
        int status = 0;
        
        return status;
    }
    
    public Sizes getSizeById(){
        Sizes b = null;
        
        return b;
    }
    
    public List<Sizes> getAllSizes(){
        List sizes = null;
        
        return sizes;
    }
}
