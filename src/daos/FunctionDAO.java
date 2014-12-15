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
import model.Charts;
import model.Expences;
import model.Function;
import model.Users;
import oracle.jdbc.OracleTypes;
import utils.DBHandler;

/**
 *
 * @author Kareem.Moustafa
 */
public class FunctionDAO {
    
    Connection connection;
    
    public FunctionDAO(){
        connection = DBHandler.connect();
    }
    
    public int insertFunction(String description, String name, String functionId){
        int status = 0;
        
         try {
            if(connection == null || connection.isClosed())
                connection = DBHandler.connect();
            CallableStatement proc = connection.prepareCall("{ call \"insert_function\" ( ?,?,?,? ) } ");
            proc.setString(1, description);
            proc.setString(2, name);
            proc.setString(3, functionId);
                     
            proc.registerOutParameter(4, java.sql.Types.NUMERIC);
            
            proc.execute();
            
            status = proc.getInt(4);
            
            proc.close();
            connection.close();
            return status;
        } catch (SQLException ex) {
            Logger.getLogger(FunctionDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return status;
    }
    
    public int updateFunction(int id, String description, String name, String functionId){
        int status = 0;
        
                 try {
            if(connection == null || connection.isClosed())
                connection = DBHandler.connect();
            CallableStatement proc = connection.prepareCall("{ call \"update_function\" ( ?,?,?,?,? ) } ");
            proc.setInt(1, id);
            proc.setString(2, description);
            proc.setString(3, name);
            proc.setString(4, functionId);
                     
            proc.registerOutParameter(5, java.sql.Types.NUMERIC);
            
            proc.execute();
            
            status = proc.getInt(5);
            
            proc.close();
            connection.close();
            return status;
        } catch (SQLException ex) {
            Logger.getLogger(FunctionDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return status;
    }
    
    public int deleteFunction(int functionId){
        int status = 0;
        
                         try {
            if(connection == null || connection.isClosed())
                connection = DBHandler.connect();
            CallableStatement proc = connection.prepareCall("{ call \"delete_function\" ( ?,? ) } ");
            proc.setInt(1, functionId);

                     
            proc.registerOutParameter(2, java.sql.Types.NUMERIC);
            
            proc.execute();
            
            status = proc.getInt(2);
            
            proc.close();
            connection.close();
            return status;
        } catch (SQLException ex) {
            Logger.getLogger(FunctionDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return status;
    }
    
    public Function getFunctionById(int functionId){
        Function b = null;
         try {
            if(connection == null || connection.isClosed())
                connection = DBHandler.connect();
            CallableStatement proc = connection.prepareCall("{ call \"get_function_ById\" ( ?,? ) } ");
            proc.setInt(1, functionId);    
            proc.registerOutParameter(2, OracleTypes.CURSOR);
            
            proc.execute();
              ResultSet r =(ResultSet) proc.getObject(2);
              
              if(r.next()){
                  b = new Function();
                  b.setId(new BigDecimal(functionId));
                  b.setDescription(r.getString("DESCRIPTION"));
                  b.setFunctionId(r.getString("FUNCTION_ID"));
                  b.setName(r.getString("NAME"));
                  
                 
              }
            proc.close();
            connection.close();
            return b;
        } catch (SQLException ex) {
            Logger.getLogger(FunctionDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
  
        return b;
    }
    
    public List<Function> getAllFunctions(){
        List functions = null;
                Function b = null;
         try {
            if(connection == null || connection.isClosed())
                connection = DBHandler.connect();
            CallableStatement proc = connection.prepareCall("{ call \"get_all_from_function\" ( ? ) } ");
    
            proc.registerOutParameter(1, OracleTypes.CURSOR);
            
            proc.execute();
              ResultSet r =(ResultSet) proc.getObject(1);
              functions = new ArrayList();
              while(r.next()){
                  b = new Function();
                  b.setId(new BigDecimal(r.getInt("ID")));
                  b.setDescription(r.getString("DESCRIPTION"));
                  b.setFunctionId(r.getString("FUNCTION_ID"));
                  b.setName(r.getString("NAME"));
                 functions.add(b);
                 
              }
            proc.close();
            connection.close();
            return functions;
        } catch (SQLException ex) {
            Logger.getLogger(FunctionDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return functions;
    }
}
