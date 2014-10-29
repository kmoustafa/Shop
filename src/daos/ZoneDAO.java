/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package daos;

import java.sql.Connection;
import java.util.List;
import model.Zone;
import utils.DBHandler;

/**
 *
 * @author Kareem.Moustafa
 */
public class ZoneDAO {
    
    Connection connection;
    
    public ZoneDAO(){
        connection = DBHandler.connect();
    }
    
    public int insertZone(){
        int status = 0;
        
        
        return status;
    }
    
    public int updateZone(){
        int status = 0;
        
        return status;
    }
    
    public int deleteZone(){
        int status = 0;
        
        return status;
    }
    
    public Zone getZoneById(){
        Zone b = null;
        
        return b;
    }
    
    public List<Zone> getAllZones(){
        List zones = null;
        
        return zones;
    }
}
