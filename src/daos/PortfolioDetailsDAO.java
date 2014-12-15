/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package daos;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Persons;
import model.Portfolio;
import model.PortfolioDetails;
import model.PortfolioId;
import model.Users;
import oracle.jdbc.OracleTypes;
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
    
    public int insertPortfolioDetails(int portType, int portId, int noteAmount, String nameNote, java.sql.Date noteDate,
            int noteKind, int noteNumber){
        int status = 0;
        try {
            if(connection == null || connection.isClosed())
                connection = DBHandler.connect();
            CallableStatement proc = connection.prepareCall("{ call \"insert_portfolio_details\" ( ?,?,?,?,?,?,?,? ) } ");
            proc.setInt(1, portType);
            proc.setInt(2, portId);
            proc.setInt(3, noteAmount);
            proc.setString(4, nameNote);
            proc.setDate(5, noteDate);
            proc.setInt(6, noteKind);
            proc.setInt(7, noteNumber);           
            proc.registerOutParameter(8, java.sql.Types.NUMERIC);
            
            proc.execute();
            
            status = proc.getInt(8);
            
            proc.close();
            connection.close();
            return status;
        } catch (SQLException ex) {
            Logger.getLogger(PortfolioDetailsDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        return status;
    }
    
    public int updatePortfolioDetails(int portDetails, int portType, int portId, int noteAmount, String nameNote, java.sql.Date noteDate,
            int noteKind, int noteNumber){
        int status = 0;
              try {
            if(connection == null || connection.isClosed())
                connection = DBHandler.connect();
            CallableStatement proc = connection.prepareCall("{ call \"update_portfolio_details\" ( ?,?,?,?,?,?,?,?,? ) } ");
            proc.setInt(1, portDetails);
            proc.setInt(2, portType);
            proc.setInt(3, portId);
            proc.setInt(4, noteAmount);
            proc.setString(5, nameNote);
            proc.setDate(6, noteDate);
            proc.setInt(7, noteKind);
            proc.setInt(8, noteNumber);           
            proc.registerOutParameter(9, java.sql.Types.NUMERIC);
            
            proc.execute();
            
            status = proc.getInt(9);
            
            proc.close();
            connection.close();
            return status;
        } catch (SQLException ex) {
            Logger.getLogger(PortfolioDetailsDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return status;
    }
    
    public int deletePortfolioDetails(int portDetails){
        int status = 0;
                     try {
            if(connection == null || connection.isClosed())
                connection = DBHandler.connect();
            CallableStatement proc = connection.prepareCall("{ call \"delete_portfolio_details\" ( ?,? ) } ");
            proc.setInt(1, portDetails);
                    
            proc.registerOutParameter(2, java.sql.Types.NUMERIC);
            
            proc.execute();
            
            status = proc.getInt(2);
            
            proc.close();
            connection.close();
            return status;
        } catch (SQLException ex) {
            Logger.getLogger(PortfolioDetailsDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return status;
    }
    
    public PortfolioDetails getPortfolioDetailsById(int portDetailsId){
        PortfolioDetails b = null;
         try {
            if(connection == null || connection.isClosed())
                connection = DBHandler.connect();
            CallableStatement proc = connection.prepareCall("{ call \"get_portfolio_details_ById\" ( ?,? ) } ");
            proc.setInt(1, portDetailsId);    
            proc.registerOutParameter(2, OracleTypes.CURSOR);
            
            proc.execute();
              ResultSet r =(ResultSet) proc.getObject(2);
              
              if(r.next()){
                  b = new PortfolioDetails();
                  b.setNameNote(r.getString("NAME_NOTE"));
                  b.setNoteAmount(new BigDecimal(r.getInt("NOTE_AMOUNT")));
                  b.setNoteDate(r.getDate("NOTE_DATE"));
                  b.setNoteKind(new BigDecimal(r.getInt("NOTE_KIND")));
                  b.setNoteNumber(new BigDecimal(r.getInt("NOTE_NUMBER")));
                  b.setPortDId(new BigDecimal(portDetailsId));
                  PortfolioDAO portfolioDAO = new PortfolioDAO();
                  Portfolio p = portfolioDAO.getPortfolioById(r.getInt("PORTFOLIO_ID"));
                  b.setPortfolio(p);
                     
              }
            proc.close();
            connection.close();
            return b;
        } catch (SQLException ex) {
            Logger.getLogger(PortfolioDetailsDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return b;
    }
    
    public List<PortfolioDetails> getAllPortfolioDetailss(){
        List portDetails = null;
              PortfolioDetails b = null;
         try {
            if(connection == null || connection.isClosed())
                connection = DBHandler.connect();
            CallableStatement proc = connection.prepareCall("{ call \"get_all_from_portfolio_details\" ( ? ) } ");
  
            proc.registerOutParameter(1, OracleTypes.CURSOR);
            
            proc.execute();
              ResultSet r =(ResultSet) proc.getObject(1);
              portDetails = new ArrayList();
              if(r.next()){
                  b = new PortfolioDetails();
                  b.setNameNote(r.getString("NAME_NOTE"));
                  b.setNoteAmount(new BigDecimal(r.getInt("NOTE_AMOUNT")));
                  b.setNoteDate(r.getDate("NOTE_DATE"));
                  b.setNoteKind(new BigDecimal(r.getInt("NOTE_KIND")));
                  b.setNoteNumber(new BigDecimal(r.getInt("NOTE_NUMBER")));
                  b.setPortDId(new BigDecimal(r.getInt("PORT_D_ID")));
                  PortfolioDAO portfolioDAO = new PortfolioDAO();
                  Portfolio p = portfolioDAO.getPortfolioById(r.getInt("PORTFOLIO_ID"));
                  b.setPortfolio(p);
                  portDetails.add(b);
              }
            proc.close();
            connection.close();
            return portDetails;
        } catch (SQLException ex) {
            Logger.getLogger(PortfolioDetailsDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return portDetails;
    }
}
