/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package daos;

import java.sql.Connection;
import java.util.List;
import model.ItemsSerials;
import utils.DBHandler;

/**
 *
 * @author Kareem.Moustafa
 */
public class ItemSerialDAO {
    
    Connection connection;
    
    public ItemSerialDAO(){
        connection = DBHandler.connect();
    }
    
    public int insertItemSerial(){
        int status = 0;
        
        
        return status;
    }
    
    public int updateItemSerial(){
        int status = 0;
        
        return status;
    }
    
    public int deleteItemSerial(){
        int status = 0;
        
        return status;
    }
    
    public ItemsSerials getItemSerialById(){
        ItemsSerials b = null;
        
        return b;
    }
    
    public List<ItemsSerials> getAllItemSerials(){
        List itemS = null;
        
        return itemS;
    }
}
