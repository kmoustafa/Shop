/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package daos;

import java.sql.Connection;
import java.util.List;
import model.Banks;
import utils.DBHandler;

/**
 *
 * @author Kareem.Moustafa
 */
public class BankDAO {
    
    Connection connection;
    
    public BankDAO(){
        connection = DBHandler.connect();
    }
    
    public int insertBank(){
        int status = 0;
        
        
        return status;
    }
    
    public int updateBank(){
        int status = 0;
        
        return status;
    }
    
    public int deleteBank(){
        int status = 0;
        
        return status;
    }
    
    public Banks getBankById(){
        Banks b = null;
        
        return b;
    }
    
    public List<Banks> getAllBanks(){
        List banks = null;
        
        return banks;
    }
}
