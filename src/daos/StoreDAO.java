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
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Banks;
import model.Charts;
import model.Employees;
import model.Stores;
import model.Users;
import oracle.jdbc.OracleTypes;
import utils.DBHandler;

/**
 *
 * @author Kareem.Moustafa
 */
public class StoreDAO {
    
    Connection connection;
    
    public StoreDAO(){
        connection = DBHandler.connect();
    }
    
    public int insertStore(int userId, int empId, int isMain, String storePhone, String storeAdd, String storeName, int storeId){
        int status = 0;
        
                 try {
            if(connection == null || connection.isClosed())
                connection = DBHandler.connect();
            CallableStatement proc = connection.prepareCall("{ call \"insert_stores\" ( ?,?,?,?,?,?,?,? ) } ");
            proc.setInt(1, userId);
            proc.setInt(2,empId);
            proc.setInt(3, isMain);
            proc.setString(4, storePhone);
            proc.setString(5, storeAdd);
            proc.setString(6, storeName); 
            proc.setInt(7, storeId);
            proc.registerOutParameter(8, java.sql.Types.NUMERIC);
            
            proc.execute();
            
            status = proc.getInt(8);
            
            proc.close();
            connection.close();
            return status;
        } catch (SQLException ex) {
            Logger.getLogger(StoreDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return status;
    }
    
    public int updateStore(int storeId, int userId, int empId, int isMain, String storePhone, String storeAdd, String storeName){
        int status = 0;
         try {
            if(connection == null || connection.isClosed())
                connection = DBHandler.connect();
            CallableStatement proc = connection.prepareCall("{ call \"update_stores\" ( ?,?,?,?,?,?,?,? ) } ");
            proc.setInt(1, storeId);
            proc.setInt(2, userId);
            proc.setInt(3,empId);
            proc.setInt(4, isMain);
            proc.setString(5, storePhone);
            proc.setString(6, storeAdd);
            proc.setString(7, storeName);          
            proc.registerOutParameter(8, java.sql.Types.NUMERIC);
            
            proc.execute();
            
            status = proc.getInt(8);
            
            proc.close();
            connection.close();
            return status;
        } catch (SQLException ex) {
            Logger.getLogger(StoreDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return status;
    }
    
    public int deleteStore(int storeId){
        int status = 0;
                 try {
            if(connection == null || connection.isClosed())
                connection = DBHandler.connect();
            CallableStatement proc = connection.prepareCall("{ call \"delete_stores\" ( ?,? ) } ");
            proc.setInt(1, storeId);          
            proc.registerOutParameter(2, java.sql.Types.NUMERIC);
            
            proc.execute();
            
            status = proc.getInt(2);
            
            proc.close();
            connection.close();
            return status;
        } catch (SQLException ex) {
            Logger.getLogger(StoreDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return status;
    }
    
    public Stores getStoreById(int storeId){
        Stores b = null;
           try {
            if(connection == null || connection.isClosed())
                connection = DBHandler.connect();
            CallableStatement proc = connection.prepareCall("{ call \"get_stores_ById\" ( ?,? ) } ");
            proc.setInt(1, storeId);    
            proc.registerOutParameter(2, OracleTypes.CURSOR);
            
            proc.execute();
              ResultSet r =(ResultSet) proc.getObject(2);
              
              if(r.next()){
                  b = new Stores();
                  EmployeeDAO employeeDAO = new EmployeeDAO();
                  Employees e = employeeDAO.getEmployeeById(r.getInt("EMP_ID"));
                  b.setEmployees(e);
                  b.setIsMain(new BigDecimal(r.getInt("IS_MAIN")));
                  b.setStoreAddress(r.getString("STORE_ADDRESS"));
                  b.setStoreName(r.getString("STORE_NAME"));
                  b.setStorePhone(r.getString("STORE_PHONE"));
                  b.setStoreId(new BigDecimal(storeId));
                  UserDAO userDAO = new UserDAO();
                  Users user = userDAO.getUserById(r.getInt("USER_ID"));
                  b.setUsers(user);
                  
              }
            proc.close();
            connection.close();
            return b;
        } catch (SQLException ex) {
            Logger.getLogger(StoreDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return b;
    }
    
    public List<Stores> getAllStores(){
        List stores = null;
                Stores b = null;
           try {
            if(connection == null || connection.isClosed())
                connection = DBHandler.connect();
            CallableStatement proc = connection.prepareCall("{ call \"get_all_from_stores\" ( ? ) } ");
  
            proc.registerOutParameter(1, OracleTypes.CURSOR);
            
            proc.execute();
              ResultSet r =(ResultSet) proc.getObject(1);
              stores = new ArrayList();
              while(r.next()){
                  b = new Stores();
                  EmployeeDAO employeeDAO = new EmployeeDAO();
                  Employees e = employeeDAO.getEmployeeById(r.getInt("EMP_ID"));
                  b.setEmployees(e);
                  b.setIsMain(new BigDecimal(r.getInt("IS_MAIN")));
                  b.setStoreAddress(r.getString("STORE_ADDRESS"));
                  b.setStoreName(r.getString("STORE_NAME"));
                  b.setStorePhone(r.getString("STORE_PHONE"));
                  b.setStoreId(new BigDecimal(r.getInt("STORE_ID")));
                  UserDAO userDAO = new UserDAO();
                  Users user = userDAO.getUserById(r.getInt("USER_ID"));
                  b.setUsers(user);
                  stores.add(b);
              }
            proc.close();
            connection.close();
            return stores;
        } catch (SQLException ex) {
            Logger.getLogger(StoreDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return stores;
    }
     public int getLastIndex(){
        int lastIndex = 0;
            try {
            if(connection == null || connection.isClosed())
                connection = DBHandler.connect();
                Statement proc = connection.createStatement();
                
            
                ResultSet r =      proc.executeQuery("SELECT MAX(STORE_ID) AS NEXTVAL FROM STORES");
                  
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
