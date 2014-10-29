/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package daos;

import java.sql.Connection;
import java.util.List;
import model.Stores;
import utils.DBHandler;

/**
 *
 * @author Kareem.Moustafa
 */
public class StoreDAO {
    
    Connection connection;
    
    public StoreDAO(){
        connection = DBHandler.connect();
    }
    
    public int insertStore(){
        int status = 0;
        
        
        return status;
    }
    
    public int updateStore(){
        int status = 0;
        
        return status;
    }
    
    public int deleteStore(){
        int status = 0;
        
        return status;
    }
    
    public Stores getStoreById(){
        Stores b = null;
        
        return b;
    }
    
    public List<Stores> getAllStores(){
        List stores = null;
        
        return stores;
    }
}
