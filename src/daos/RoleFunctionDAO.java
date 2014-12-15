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
import model.Colors;
import model.Function;
import model.Role;
import model.RoleFunction;
import oracle.jdbc.OracleTypes;
import utils.DBHandler;

/**
 *
 * @author Kareem.Moustafa
 */
public class RoleFunctionDAO {
    
    Connection connection;
    
    public RoleFunctionDAO(){
        connection = DBHandler.connect();
    }
    
    public int insertRoleFunction(int functionId, int roleId){
        int status = 0;
           try {
            if(connection == null || connection.isClosed())
                connection = DBHandler.connect();
            CallableStatement proc = connection.prepareCall("{ call \"insert_role_function\" ( ?,?,? ) } ");
            proc.setInt(1, functionId);
            proc.setInt(2, roleId);
            proc.registerOutParameter(3, java.sql.Types.NUMERIC);
            
            proc.execute();
            
            status = proc.getInt(3);
            
            proc.close();
            connection.close();
            return status;
        } catch (SQLException ex) {
            Logger.getLogger(RoleFunctionDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return status;
    }
    
    public int updateRoleFunction(int roleFunctionId, int functionId, int roleId){
        int status = 0;
                   try {
            if(connection == null || connection.isClosed())
                connection = DBHandler.connect();
            CallableStatement proc = connection.prepareCall("{ call \"update_role_function\" ( ?,?,?,? ) } ");
            proc.setInt(1, roleFunctionId);
            proc.setInt(2, functionId);
            proc.setInt(3, roleId);
            proc.registerOutParameter(4, java.sql.Types.NUMERIC);
            
            proc.execute();
            
            status = proc.getInt(4);
            
            proc.close();
            connection.close();
            return status;
        } catch (SQLException ex) {
            Logger.getLogger(RoleFunctionDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return status;
    }
    
    public int deleteRoleFunction(int roleFunctionId){
        int status = 0;
                           try {
            if(connection == null || connection.isClosed())
                connection = DBHandler.connect();
            CallableStatement proc = connection.prepareCall("{ call \"update_role_function\" ( ?,? ) } ");
            proc.setInt(1, roleFunctionId);
            proc.registerOutParameter(2, java.sql.Types.NUMERIC);
            
            proc.execute();
            
            status = proc.getInt(2);
            
            proc.close();
            connection.close();
            return status;
        } catch (SQLException ex) {
            Logger.getLogger(RoleFunctionDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return status;
    }
    
    public RoleFunction getRoleFunctionById(int roleFunctionId){
        RoleFunction b = null;
           try {
            if(connection == null || connection.isClosed())
                connection = DBHandler.connect();
            CallableStatement proc = connection.prepareCall("{ call \"get_role_function_ById\" ( ?,? ) } ");
            proc.setInt(1, roleFunctionId);    
            proc.registerOutParameter(2, OracleTypes.CURSOR);
            
            proc.execute();
              ResultSet r =(ResultSet) proc.getObject(2);
              
              if(r.next()){
                  b = new RoleFunction();
                  FunctionDAO functionDAO = new FunctionDAO();
                  Function f = functionDAO.getFunctionById(r.getInt("FUNCTION_ID"));
                  b.setFunction(f);
                  b.setId(new BigDecimal(r.CLOSE_CURSORS_AT_COMMIT));
                  RoleDAO rdao = new RoleDAO();
                  Role role = rdao.getRoleById(r.getInt("ROLE_ID"));
                  b.setRole(role);
              }
            proc.close();
            connection.close();
            return b;
        } catch (SQLException ex) {
            Logger.getLogger(BankDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return b;
    }
    
    public List<RoleFunction> getAllRoleFunctions(){
        List rf = null;
                RoleFunction b = null;
           try {
            if(connection == null || connection.isClosed())
                connection = DBHandler.connect();
            CallableStatement proc = connection.prepareCall("{ call \"get_all_from_role_function\" ( ? ) } ");
   
            proc.registerOutParameter(1, OracleTypes.CURSOR);
            
            proc.execute();
              ResultSet r =(ResultSet) proc.getObject(1);
              rf = new ArrayList();
              while(r.next()){
                  b = new RoleFunction();
                  FunctionDAO functionDAO = new FunctionDAO();
                  Function f = functionDAO.getFunctionById(r.getInt("FUNCTION_ID"));
                  b.setFunction(f);
                  b.setId(new BigDecimal(r.CLOSE_CURSORS_AT_COMMIT));
                  RoleDAO rdao = new RoleDAO();
                  Role role = rdao.getRoleById(r.getInt("ROLE_ID"));
                  b.setRole(role);
                  rf.add(b);
              }
            proc.close();
            connection.close();
            return rf;
        } catch (SQLException ex) {
            Logger.getLogger(BankDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return rf;
    }
}
