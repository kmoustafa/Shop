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
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Banks;
import model.Charts;
import model.CostCenter;
import model.Users;
import oracle.jdbc.OracleTypes;
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
    
    public int insertCostCenter(int userId, String costCenterName){
        int status = 0;
        
         try {
            if(connection == null || connection.isClosed())
                connection = DBHandler.connect();
            CallableStatement proc = connection.prepareCall("{ call \"insert_cost_center\" ( ?,?,? ) } ");
            proc.setInt(1, userId);
            proc.setString(2, costCenterName);
                     
            proc.registerOutParameter(3, java.sql.Types.NUMERIC);
            
            proc.execute();
            
            status = proc.getInt(3);
            
            proc.close();
            connection.close();
            return status;
        } catch (SQLException ex) {
            ex.printStackTrace();
            Logger.getLogger(CostCenterDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return status;
    }
    
    public int updateCostCenter(int costCenterId, int userId, String costCenterName){
        int status = 0;
                
         try {
            if(connection == null || connection.isClosed())
                connection = DBHandler.connect();
            CallableStatement proc = connection.prepareCall("{ call \"update_cost_center\" ( ?,?,?,? ) } ");
            proc.setInt(1, costCenterId);
            proc.setInt(2, userId);
            proc.setString(3, costCenterName);
                     
            proc.registerOutParameter(4, java.sql.Types.NUMERIC);
            
            proc.execute();
            
            status = proc.getInt(4);
            
            proc.close();
            connection.close();
            return status;
        } catch (SQLException ex) {
            Logger.getLogger(CostCenterDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return status;
    }
    
    public int deleteCostCenter(int costCenterId){
        int status = 0;
         try {
            if(connection == null || connection.isClosed())
                connection = DBHandler.connect();
            CallableStatement proc = connection.prepareCall("{ call \"delete_cost_center\" ( ?,? ) } ");
            proc.setInt(1, costCenterId);
                     
            proc.registerOutParameter(2, java.sql.Types.NUMERIC);
            
            proc.execute();
            
            status = proc.getInt(2);
            
            proc.close();
            connection.close();
            return status;
        } catch (SQLException ex) {
            Logger.getLogger(CostCenterDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return status;
    }
    
    public CostCenter getCostCenterById(int costCenterId){
        CostCenter b = null;
          try {
            if(connection == null || connection.isClosed())
                connection = DBHandler.connect();
            CallableStatement proc = connection.prepareCall("{ call \"get_cost_center_ById\" ( ?,? ) } ");
            proc.setInt(1, costCenterId);    
            proc.registerOutParameter(2, OracleTypes.CURSOR);
            
            proc.execute();
              ResultSet r =(ResultSet) proc.getObject(2);
              
              if(r.next()){
                  b = new CostCenter();
                  b.setCostCenterId(new BigDecimal(costCenterId));
                  b.setCostCenterName(r.getString("COST_CENTER_NAME"));
                  UserDAO userDAO = new UserDAO();
                  Users user = userDAO.getUserById(r.getInt("USER_ID"));
                  b.setUsers(user);
                  
              }
            proc.close();
            connection.close();
            return b;
        } catch (SQLException ex) {
            Logger.getLogger(CostCenterDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return b;
    }
    
    public List<CostCenter> getAllCostCenters(){
        List costCenters = null;
        CostCenter b = null;
                  try {
            if(connection == null || connection.isClosed())
                connection = DBHandler.connect();
            CallableStatement proc = connection.prepareCall("{ call \"get_all_from_cost_center\" ( ? ) } ");
   
            proc.registerOutParameter(1, OracleTypes.CURSOR);
            
            proc.execute();
              ResultSet r =(ResultSet) proc.getObject(1);
              costCenters = new ArrayList();
              while(r.next()){
                  b = new CostCenter();
                  b.setCostCenterId(new BigDecimal(r.getInt("COST_CENTER_ID")));
                  b.setCostCenterName(r.getString("COST_CENTER_NAME"));
                  UserDAO userDAO = new UserDAO();
                  Users user = userDAO.getUserById(r.getInt("USER_ID"));
                  b.setUsers(user);
                  costCenters.add(b);
              }
            proc.close();
            connection.close();
            return costCenters;
        } catch (SQLException ex) {
            Logger.getLogger(CostCenterDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return costCenters;
    }
    
    public int getLastIndex(){
        int lastIndex = 0;
            try {
            if(connection == null || connection.isClosed())
                connection = DBHandler.connect();
                Statement proc = connection.createStatement();
                
            
                ResultSet r =      proc.executeQuery("SELECT MAX(COST_CENTER_ID) AS NEXTVAL FROM COST_CENTER");
                  
              if(r.next()){
                  
                  lastIndex = r.getInt("NEXTVAL");
                  
              }
            proc.close();
            connection.close();
        } catch (SQLException ex) {
            Logger.getLogger(CostCenterDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lastIndex;
    }
}
