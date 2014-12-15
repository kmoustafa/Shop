/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package daos;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Banks;
import model.Charts;
import model.Persons;
import model.Portfolio;
import model.PortfolioId;
import model.Users;
import oracle.jdbc.OracleTypes;
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
    
    public int insertPortfolio(int userId, int persontype, int personId, String portNotes, int portAmount,
            java.sql.Date portDate, int protType)
    {
        int status = 0;
         try {
            if(connection == null || connection.isClosed())
                connection = DBHandler.connect();
            CallableStatement proc = connection.prepareCall("{ call \"insert_portfolio\" ( ?,?,?,?,?,?,?,? ) } ");
            proc.setInt(1, userId);
            proc.setInt(2, persontype);
            proc.setInt(3, personId);
            proc.setString(4, portNotes);
            proc.setInt(5, portAmount);
            proc.setDate(6, portDate);
            proc.setInt(7, protType);           
            proc.registerOutParameter(8, java.sql.Types.NUMERIC);
            
            proc.execute();
            
            status = proc.getInt(8);
            
            proc.close();
            connection.close();
            return status;
        } catch (SQLException ex) {
            Logger.getLogger(PortfolioDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return status;
    }
    
    public int updatePortfolio(int portId, int userId, int persontype, int personId, String portNotes, int portAmount,
            java.sql.Date portDate, int protType){
        int status = 0;
                 try {
            if(connection == null || connection.isClosed())
                connection = DBHandler.connect();
            CallableStatement proc = connection.prepareCall("{ call \"update_portfolio\" ( ?,?,?,?,?,?,?,?,? ) } ");
            proc.setInt(1, portId);
            proc.setInt(2, userId);
            proc.setInt(3, persontype);
            proc.setInt(4, personId);
            proc.setString(5, portNotes);
            proc.setInt(6, portAmount);
            proc.setDate(7, portDate);
            proc.setInt(8, protType);           
            proc.registerOutParameter(9, java.sql.Types.NUMERIC);
            
            proc.execute();
            
            status = proc.getInt(9);
            
            proc.close();
            connection.close();
            return status;
        } catch (SQLException ex) {
            Logger.getLogger(PortfolioDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return status;
    }
    
    public int deletePortfolio(int portId){
        int status = 0;
                         try {
            if(connection == null || connection.isClosed())
                connection = DBHandler.connect();
            CallableStatement proc = connection.prepareCall("{ call \"delete_portfolio\" ( ?,? ) } ");
            proc.setInt(1, portId);
          
            proc.registerOutParameter(2, java.sql.Types.NUMERIC);
            
            proc.execute();
            
            status = proc.getInt(2);
            
            proc.close();
            connection.close();
            return status;
        } catch (SQLException ex) {
            Logger.getLogger(PortfolioDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return status;
    }
    
    public Portfolio getPortfolioById(int portId){
        Portfolio b = null;
         try {
            if(connection == null || connection.isClosed())
                connection = DBHandler.connect();
            CallableStatement proc = connection.prepareCall("{ call \"get_portfolio_ById\" ( ?,? ) } ");
            proc.setInt(1, portId);    
            proc.registerOutParameter(2, OracleTypes.CURSOR);
            
            proc.execute();
              ResultSet r =(ResultSet) proc.getObject(2);
              
              if(r.next()){
                  b = new Portfolio();
                  b.setId(new PortfolioId(new BigDecimal(portId),
                          new BigDecimal(r.getInt("PORTFOLIO_TYPE"))));
                  PersonDAO personDAO = new PersonDAO();
                  Persons p = personDAO.getPersonById(r.getInt("PERSON_ID"));
                  b.setPersons(p);
                  b.setPortDate(r.getDate("PORT_DATE"));
                  b.setPortNotes(r.getString("PORT_NOTES"));
                  b.setProtfolioAmount(new BigDecimal(r.getString("BANK_FAX")));
                  UserDAO userDAO = new UserDAO();
                  Users user = userDAO.getUserById(r.getInt("USER_ID"));
                  b.setUsers(user);
                  
              }
            proc.close();
            connection.close();
            return b;
        } catch (SQLException ex) {
            Logger.getLogger(BankDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return b;
    }
    
    public List<Portfolio> getAllPortfolios(){
        List portfolios = null;
                Portfolio b = null;
         try {
            if(connection == null || connection.isClosed())
                connection = DBHandler.connect();
            CallableStatement proc = connection.prepareCall("{ call \"get_all_from_portfolio\" ( ? ) } ");
   
            proc.registerOutParameter(1, OracleTypes.CURSOR);
            
            proc.execute();
              ResultSet r =(ResultSet) proc.getObject(1);
              portfolios = new ArrayList();
              while(r.next()){
                  b = new Portfolio();
                  b.setId(new PortfolioId(new BigDecimal(r.getInt("PORTFOLIO_ID")),
                          new BigDecimal(r.getInt("PORTFOLIO_TYPE"))));
                  PersonDAO personDAO = new PersonDAO();
                  Persons p = personDAO.getPersonById(r.getInt("PERSON_ID"));
                  b.setPersons(p);
                  b.setPortDate(r.getDate("PORT_DATE"));
                  b.setPortNotes(r.getString("PORT_NOTES"));
                  b.setProtfolioAmount(new BigDecimal(r.getString("BANK_FAX")));
                  UserDAO userDAO = new UserDAO();
                  Users user = userDAO.getUserById(r.getInt("USER_ID"));
                  b.setUsers(user);
                  portfolios.add(b);
              }
            proc.close();
            connection.close();
            return portfolios;
        } catch (SQLException ex) {
            Logger.getLogger(BankDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return portfolios;
    }
}
