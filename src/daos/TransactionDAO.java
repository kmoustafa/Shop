/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package daos;

import java.sql.Connection;
import java.util.List;
import model.Transactions;
import utils.DBHandler;

/**
 *
 * @author Kareem.Moustafa
 */
public class TransactionDAO {
    
    Connection connection;
    
    public TransactionDAO(){
        connection = DBHandler.connect();
    }
    
    public int insertTransaction(){
        int status = 0;
        
        
        return status;
    }
    
    public int updateTransaction(){
        int status = 0;
        
        return status;
    }
    
    public int deleteTransaction(){
        int status = 0;
        
        return status;
    }
    
    public Transactions getTransactionById(){
        Transactions b = null;
        
        return b;
    }
    
    public List<Transactions> getAllTransactions(){
        List trans = null;
        
        return trans;
    }
}
