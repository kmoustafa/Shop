/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package daos;

import java.sql.Connection;
import java.util.List;
import model.Charts;
import utils.DBHandler;

/**
 *
 * @author Kareem.Moustafa
 */
public class ChartDAO {
    
    Connection connection;
    
    public ChartDAO(){
        connection = DBHandler.connect();
    }
    
    public int insertChart(){
        int status = 0;
        
        
        return status;
    }
    
    public int updateChart(){
        int status = 0;
        
        return status;
    }
    
    public int deleteChart(){
        int status = 0;
        
        return status;
    }
    
    public Charts getChartById(){
        Charts b = null;
        
        return b;
    }
    
    public List<Charts> getAllCharts(){
        List charts = null;
        
        return charts;
    }
}
