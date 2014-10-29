/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package daos;

import java.sql.Connection;
import java.util.List;
import model.Colors;
import utils.DBHandler;

/**
 *
 * @author Kareem.Moustafa
 */
public class ColorDAO {
    
    Connection connection;
    
    public ColorDAO(){
        connection = DBHandler.connect();
    }
    
    public int insertColor(){
        int status = 0;
        
        
        return status;
    }
    
    public int updateColor(){
        int status = 0;
        
        return status;
    }
    
    public int deleteColor(){
        int status = 0;
        
        return status;
    }
    
    public Colors getColorById(){
        Colors b = null;
        
        return b;
    }
    
    public List<Colors> getAllColors(){
        List colors = null;
        
        return colors;
    }
}
