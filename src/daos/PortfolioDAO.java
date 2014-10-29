/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package daos;

import java.sql.Connection;
import java.util.List;
import model.Portfolio;
import utils.DBHandler;

/**
 *
 * @author Kareem.Moustafa
 */
public class PortfolioDAO {
    
    Connection connection;
    
    public PortfolioDAO(){
        connection = DBHandler.connect();
    }
    
    public int insertPortfolio(){
        int status = 0;
        
        
        return status;
    }
    
    public int updatePortfolio(){
        int status = 0;
        
        return status;
    }
    
    public int deletePortfolio(){
        int status = 0;
        
        return status;
    }
    
    public Portfolio getPortfolioById(){
        Portfolio b = null;
        
        return b;
    }
    
    public List<Portfolio> getAllPortfolios(){
        List portfolios = null;
        
        return portfolios;
    }
}
