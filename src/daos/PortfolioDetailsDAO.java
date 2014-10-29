/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package daos;

import java.sql.Connection;
import java.util.List;
import model.PortfolioDetails;
import utils.DBHandler;

/**
 *
 * @author Kareem.Moustafa
 */
public class PortfolioDetailsDAO {
    
    Connection connection;
    
    public PortfolioDetailsDAO(){
        connection = DBHandler.connect();
    }
    
    public int insertPortfolioDetails(){
        int status = 0;
        
        
        return status;
    }
    
    public int updatePortfolioDetails(){
        int status = 0;
        
        return status;
    }
    
    public int deletePortfolioDetails(){
        int status = 0;
        
        return status;
    }
    
    public PortfolioDetails getPortfolioDetailsById(){
        PortfolioDetails b = null;
        
        return b;
    }
    
    public List<PortfolioDetails> getAllPortfolioDetailss(){
        List portDetails = null;
        
        return portDetails;
    }
}
