/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package daos;

import java.sql.Connection;
import java.util.List;
import model.CostCenter;
import utils.DBHandler;

/**
 *
 * @author Kareem.Moustafa
 */
public class CostCenterDAO {
    
    Connection connection;
    
    public CostCenterDAO(){
        connection = DBHandler.connect();
    }
    
    public int insertCostCenter(){
        int status = 0;
        
        
        return status;
    }
    
    public int updateCostCenter(){
        int status = 0;
        
        return status;
    }
    
    public int deleteCostCenter(){
        int status = 0;
        
        return status;
    }
    
    public CostCenter getCostCenterById(){
        CostCenter b = null;
        
        return b;
    }
    
    public List<CostCenter> getAllCostCenters(){
        List costCenters = null;
        
        return costCenters;
    }
}
