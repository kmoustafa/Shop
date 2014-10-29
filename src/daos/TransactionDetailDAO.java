/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package daos;

import java.sql.Connection;
import java.util.List;
import model.TransactionDetails;
import utils.DBHandler;

/**
 *
 * @author Kareem.Moustafa
 */
public class TransactionDetailDAO {
    
    Connection connection;
    
    public TransactionDetailDAO(){
        connection = DBHandler.connect();
    }
    
    public int insertTransactionDetail(){
        int status = 0;
        
        
        return status;
    }
    
    public int updateTransactionDetail(){
        int status = 0;
        
        return status;
    }
    
    public int deleteTransactionDetail(){
        int status = 0;
        
        return status;
    }
    
    public TransactionDetails getTransactionDetailById(){
        TransactionDetails b = null;
        
        return b;
    }
    
    public List<TransactionDetails> getAllTransactionDetails(){
        List transDetails = null;
        
        return transDetails;
    }
}
