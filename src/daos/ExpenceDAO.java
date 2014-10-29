/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package daos;

import java.sql.Connection;
import java.util.List;
import model.Expences;
import utils.DBHandler;

/**
 *
 * @author Kareem.Moustafa
 */
public class ExpenceDAO {
    
    Connection connection;
    
    public ExpenceDAO(){
        connection = DBHandler.connect();
    }
    
    public int insertExpence(){
        int status = 0;
        
        
        return status;
    }
    
    public int updateExpence(){
        int status = 0;
        
        return status;
    }
    
    public int deleteExpence(){
        int status = 0;
        
        return status;
    }
    
    public Expences getExpenceById(){
        Expences b = null;
        
        return b;
    }
    
    public List<Expences> getAllExpences(){
        List exs = null;
        
        return exs;
    }
}
