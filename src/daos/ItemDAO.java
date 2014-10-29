/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package daos;

import java.sql.Connection;
import java.util.List;
import model.Items;
import utils.DBHandler;

/**
 *
 * @author Kareem.Moustafa
 */
public class ItemDAO {
    
    Connection connection;
    
    public ItemDAO(){
        connection = DBHandler.connect();
    }
    
    public int insertItem(){
        int status = 0;
        
        
        return status;
    }
    
    public int updateItem(){
        int status = 0;
        
        return status;
    }
    
    public int deleteItem(){
        int status = 0;
        
        return status;
    }
    
    public Items getItemById(){
        Items b = null;
        
        return b;
    }
    
    public List<Items> getAllItems(){
        List item = null;
        
        return item;
    }
}
