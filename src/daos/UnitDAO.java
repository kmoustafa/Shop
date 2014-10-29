/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package daos;

import java.sql.Connection;
import java.util.List;
import model.Units;
import utils.DBHandler;

/**
 *
 * @author Kareem.Moustafa
 */
public class UnitDAO {
    
    Connection connection;
    
    public UnitDAO(){
        connection = DBHandler.connect();
    }
    
    public int insertUnit(){
        int status = 0;
        
        
        return status;
    }
    
    public int updateUnit(){
        int status = 0;
        
        return status;
    }
    
    public int deleteUnit(){
        int status = 0;
        
        return status;
    }
    
    public Units getUnitById(){
        Units b = null;
        
        return b;
    }
    
    public List<Units> getAllUnits(){
        List units = null;
        
        return units;
    }
}
