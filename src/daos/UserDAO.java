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
import model.Role;
import model.Units;
import model.Users;
import oracle.jdbc.OracleTypes;
import utils.DBHandler;

/**
 *
 * @author Kareem.Moustafa
 */
public class UserDAO {
    
    Connection connection;
    
    public UserDAO(){
        connection = DBHandler.connect();
    }
    
    public int insertUser(int roleId, int userType, String password, String userName, String name){
        int status = 0;
                       try {
            if(connection == null || connection.isClosed())
                connection = DBHandler.connect();
            CallableStatement proc = connection.prepareCall("{ call \"insert_users\" ( ?,?,?,?,?,? ) } ");
            proc.setInt(1, roleId);
            proc.setInt(2, userType);
            proc.setString(3, password);
            proc.setString(4, userName);
            proc.setString(5,name);
            proc.registerOutParameter(6, java.sql.Types.NUMERIC);
            
            proc.execute();
            
            status = proc.getInt(6);
            
            proc.close();
            connection.close();
            return status;
        } catch (SQLException ex) {
            Logger.getLogger(UnitDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return status;
    }
    
    public int updateUser(int userId, int roleId, int userType, String password, String userName, String name){
        int status = 0;
                            try {
            if(connection == null || connection.isClosed())
                connection = DBHandler.connect();
            CallableStatement proc = connection.prepareCall("{ call \"update_users\" ( ?,?,?,?,?,?,? ) } ");
            proc.setInt(1, userId);
            proc.setInt(2, roleId);
            proc.setInt(3, userType);
            proc.setString(4, password);
            proc.setString(5, userName);
            proc.setString(6,name);
            proc.registerOutParameter(7, java.sql.Types.NUMERIC);
            
            proc.execute();
            
            status = proc.getInt(7);
            
            proc.close();
            connection.close();
            return status;
        } catch (SQLException ex) {
            Logger.getLogger(UnitDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return status;
    }
    
    public int deleteUser(int userId){
        int status = 0;
                                    try {
            if(connection == null || connection.isClosed())
                connection = DBHandler.connect();
            CallableStatement proc = connection.prepareCall("{ call \"update_users\" ( ?,? ) } ");
            proc.setInt(1, userId);

            proc.registerOutParameter(2, java.sql.Types.NUMERIC);
            
            proc.execute();
            
            status = proc.getInt(2);
            
            proc.close();
            connection.close();
            return status;
        } catch (SQLException ex) {
            Logger.getLogger(UnitDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return status;
    }
    
    public Users getUserById(int userId){
        Users b = null;
           try {
            if(connection == null || connection.isClosed())
                connection = DBHandler.connect();
            CallableStatement proc = connection.prepareCall("{ call \"get_users_ById\" ( ?,? ) } ");
            proc.setInt(1, userId);    
            proc.registerOutParameter(2, OracleTypes.CURSOR);
            
            proc.execute();
              ResultSet r =(ResultSet) proc.getObject(2);
              
              if(r.next()){
                  b = new Users();
                  b.setId(new BigDecimal(userId));
                  b.setPassword(r.getString("PASSWORD"));
                  b.setName(r.getString("NAME"));
                  RoleDAO roleDAO = new RoleDAO();
                  Role r1 = roleDAO.getRoleById(r.getInt("ROLE_ID"));
                  b.setRole(r1);
                  b.setUserName(r.getString("USER_NAME"));
                  b.setUserType(new BigDecimal(r.getInt("USER_TYPE")));
                  
              }
            proc.close();
            connection.close();
            return b;
        } catch (SQLException ex) {
            Logger.getLogger(UnitDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return b;
    }
    
    public List<Users> getAllUsers(){
        List users = null;
                Users b = null;
           try {
            if(connection == null || connection.isClosed())
                connection = DBHandler.connect();
            CallableStatement proc = connection.prepareCall("{ call \"get_all_from_users\" ( ? ) } ");
    
            proc.registerOutParameter(1, OracleTypes.CURSOR);
            
            proc.execute();
              ResultSet r =(ResultSet) proc.getObject(1);
              users = new ArrayList();
              while(r.next()){
                  b = new Users();
                  b.setId(new BigDecimal(r.getInt("USER_ID")));
                  b.setPassword(r.getString("PASSWORD"));
                  b.setName(r.getString("NAME"));
                  RoleDAO roleDAO = new RoleDAO();
                  Role r1 = roleDAO.getRoleById(r.getInt("ROLE_ID"));
                  b.setRole(r1);
                  b.setUserName(r.getString("USER_NAME"));
                  b.setUserType(new BigDecimal(r.getInt("USER_TYPE")));
                  users.add(b);
              }
            proc.close();
            connection.close();
            return users;
        } catch (SQLException ex) {
            Logger.getLogger(UnitDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return users;
    }
}
