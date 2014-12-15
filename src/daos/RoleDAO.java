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
import model.Role;
import oracle.jdbc.OracleTypes;
import utils.DBHandler;

/**
 *
 * @author Kareem.Moustafa
 */
public class RoleDAO {
    
    Connection connection;
    
    public RoleDAO(){
        connection = DBHandler.connect();
    }
    
    public int insertRole(String description, String roleName){
        int status = 0;
        
          try {
            if(connection == null || connection.isClosed())
                connection = DBHandler.connect();
            CallableStatement proc = connection.prepareCall("{ call \"insert_role\" ( ?,?,? ) } ");
            proc.setString(1, description);
            proc.setString(2, roleName);      
            proc.registerOutParameter(3, java.sql.Types.NUMERIC);
            
            proc.execute();
            
            status = proc.getInt(3);
            
            proc.close();
            connection.close();
            return status;
        } catch (SQLException ex) {
            Logger.getLogger(RoleDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return status;
    }
    
    public int updateRole(int roleId, String description, String roleName){
        int status = 0;
                  try {
            if(connection == null || connection.isClosed())
                connection = DBHandler.connect();
            CallableStatement proc = connection.prepareCall("{ call \"update_role\" ( ?,?,?,? ) } ");
            proc.setInt(1, roleId);
            proc.setString(2, description);
            proc.setString(3, roleName);      
            proc.registerOutParameter(4, java.sql.Types.NUMERIC);
            
            proc.execute();
            
            status = proc.getInt(4);
            
            proc.close();
            connection.close();
            return status;
        } catch (SQLException ex) {
            Logger.getLogger(RoleDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return status;
    }
    
    public int deleteRole(int roleId){
        int status = 0;
                      try {
            if(connection == null || connection.isClosed())
                connection = DBHandler.connect();
            CallableStatement proc = connection.prepareCall("{ call \"delete_role\" ( ?,? ) } ");
            proc.setInt(1, roleId);
   
            proc.registerOutParameter(2, java.sql.Types.NUMERIC);
            
            proc.execute();
            
            status = proc.getInt(2);
            
            proc.close();
            connection.close();
            return status;
        } catch (SQLException ex) {
            Logger.getLogger(RoleDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return status;
    }
    
    public Role getRoleById(int roleId){
        Role b = null;
         try {
            if(connection == null || connection.isClosed())
                connection = DBHandler.connect();
            CallableStatement proc = connection.prepareCall("{ call \"get_role_ById\" ( ?,? ) } ");
            proc.setInt(1, roleId);
   
            proc.registerOutParameter(2,OracleTypes.CURSOR);
            
                proc.execute();
              ResultSet r =(ResultSet) proc.getObject(2);
            if(r.next()){
                b = new Role();
                b.setDescription(r.getString("DESCRIPTION"));
                b.setName(r.getString("NAME"));
                b.setRoleId(new BigDecimal(roleId));
            }
            proc.close();
            connection.close();
            return b;
        } catch (SQLException ex) {
            Logger.getLogger(RoleDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return b;
    }
    
    public List<Role> getAllRoles(){
        List roles = null;
                Role b = null;
         try {
            if(connection == null || connection.isClosed())
                connection = DBHandler.connect();
            CallableStatement proc = connection.prepareCall("{ call \"get_all_from_role\" ( ? ) } ");

   
            proc.registerOutParameter(1,OracleTypes.CURSOR);
            
                proc.execute();
              ResultSet r =(ResultSet) proc.getObject(1);
              roles = new ArrayList();
            while(r.next()){
                b = new Role();
                b.setDescription(r.getString("DESCRIPTION"));
                b.setName(r.getString("NAME"));
                b.setRoleId(new BigDecimal(r.getInt("ROLE_ID")));
                roles.add(b);
            }
            proc.close();
            connection.close();
            return roles;
        } catch (SQLException ex) {
            Logger.getLogger(RoleDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return roles;
    }
}
