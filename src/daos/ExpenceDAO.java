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
import model.Charts;
import model.CostCenter;
import model.Expences;
import model.Users;
import oracle.jdbc.OracleTypes;
import utils.DBHandler;

/**
 *
 * @author Kareem.Moustafa
 */
public class ExpenceDAO {
    
    Connection connection;
    
    public ExpenceDAO(){
        connection = DBHandler.connect();
    }
    
    public int insertExpence(int userId, int accId, String expenseName){
        int status = 0;
       
          try {
            if(connection == null || connection.isClosed())
                connection = DBHandler.connect();
            CallableStatement proc = connection.prepareCall("{ call \"insert_expences\" ( ?,?,?,? ) } ");
            proc.setInt(1, userId);
            proc.setInt(2, accId);
            proc.setString(3, expenseName);
                     
            proc.registerOutParameter(4, java.sql.Types.NUMERIC);
            
            proc.execute();
            
            status = proc.getInt(4);
            
            proc.close();
            connection.close();
            return status;
        } catch (SQLException ex) {
            Logger.getLogger(ExpenceDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return status;
    }
    
    public int updateExpence(int expenseId, int userId, int accId, String expenseName){
        int status = 0;
        
         try {
            if(connection == null || connection.isClosed())
                connection = DBHandler.connect();
            CallableStatement proc = connection.prepareCall("{ call \"update_expences\" ( ?,?,?,?,? ) } ");
            proc.setInt(1, expenseId);
            proc.setInt(2, userId);
            proc.setInt(3, accId);
            proc.setString(4, expenseName);
                     
            proc.registerOutParameter(5, java.sql.Types.NUMERIC);
            
            proc.execute();
            
            status = proc.getInt(5);
            
            proc.close();
            connection.close();
            return status;
        } catch (SQLException ex) {
            Logger.getLogger(ExpenceDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        return status;
    }
    
    public int deleteExpence(int expenseId){
        int status = 0;
        
               try {
            if(connection == null || connection.isClosed())
                connection = DBHandler.connect();
            CallableStatement proc = connection.prepareCall("{ call \"delete_expences\" ( ?,?,?,?,? ) } ");
            proc.setInt(1, expenseId);
          
            proc.registerOutParameter(2, java.sql.Types.NUMERIC);
            
            proc.execute();
            
            status = proc.getInt(2);
            
            proc.close();
            connection.close();
            return status;
        } catch (SQLException ex) {
            Logger.getLogger(ExpenceDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return status;
    }
    
    public Expences getExpenceById(int expenseId){
        Expences b = null;
          try {
            if(connection == null || connection.isClosed())
                connection = DBHandler.connect();
            CallableStatement proc = connection.prepareCall("{ call \"get_expences_ById\" ( ?,? ) } ");
            proc.setInt(1, expenseId);    
            proc.registerOutParameter(2, OracleTypes.CURSOR);
            
            proc.execute();
              ResultSet r =(ResultSet) proc.getObject(2);
              
              if(r.next()){
                  b = new Expences();
                  b.setExpenceId(new BigDecimal(expenseId));
                  b.setExpenceName(r.getString("EXPENCE_NAME"));
                  UserDAO userDAO = new UserDAO();
                  Users user = userDAO.getUserById(r.getInt("USER_ID"));
                  b.setUsers(user);
                  ChartDAO chartDAO = new ChartDAO();
                  Charts chart = chartDAO.getChartById(r.getInt("ACC_ID"));
                  b.setCharts(chart);
                 
              }
            proc.close();
            connection.close();
            return b;
        } catch (SQLException ex) {
            Logger.getLogger(ExpenceDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return b;
    }
    
    public List<Expences> getAllExpences(){
        List exs = null;
        Expences b = null;
                  try {
            if(connection == null || connection.isClosed())
                connection = DBHandler.connect();
            CallableStatement proc = connection.prepareCall("{ call \"get_all_from_expences\" ( ? ) } ");
  
            proc.registerOutParameter(1, OracleTypes.CURSOR);
            
            proc.execute();
              ResultSet r =(ResultSet) proc.getObject(1);
              exs = new ArrayList<Expences>();
              while(r.next()){
                  b = new Expences();
                  b.setExpenceId(new BigDecimal(r.getInt("EXPENCE_ID")));
                  b.setExpenceName(r.getString("EXPENCE_NAME"));
                  UserDAO userDAO = new UserDAO();
                  Users user = userDAO.getUserById(r.getInt("USER_ID"));
                  b.setUsers(user);
                  ChartDAO chartDAO = new ChartDAO();
                  Charts chart = chartDAO.getChartById(r.getInt("ACC_ID"));
                  b.setCharts(chart);
                 exs.add(b);
              }
            proc.close();
            connection.close();
            return exs;
        } catch (SQLException ex) {
            Logger.getLogger(ExpenceDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return exs;
    }
}
