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
import model.Colors;
import model.Groups;
import oracle.jdbc.OracleTypes;
import utils.DBHandler;

/**
 *
 * @author Kareem.Moustafa
 */
public class GroupDAO {
    
    Connection connection;
    
    public GroupDAO(){
        connection = DBHandler.connect();
    }
    
    public int insertGroup(int groupId, String groupName){
        int status = 0;
        
         try {
            if(connection == null || connection.isClosed())
                connection = DBHandler.connect();
            CallableStatement proc = connection.prepareCall("{ call \"insert_groups\" ( ?,?,? ) } ");
            proc.setInt(1, groupId);
            proc.setString(2, groupName);          
            proc.registerOutParameter(3, java.sql.Types.NUMERIC);
            
            proc.execute();
            
            status = proc.getInt(3);
            
            proc.close();
            connection.close();
            return status;
        } catch (SQLException ex) {
            Logger.getLogger(GroupDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        return status;
    }
    
    public int updateGroup(int groupId, String groupName){
        int status = 0;
        try {
            if(connection == null || connection.isClosed())
                connection = DBHandler.connect();
            CallableStatement proc = connection.prepareCall("{ call \"update_groups\" ( ?,?,? ) } ");
            proc.setInt(1, groupId);
            proc.setString(2, groupName);
          
            proc.registerOutParameter(3, java.sql.Types.NUMERIC);
            
            proc.execute();
            
            status = proc.getInt(3);
            
            proc.close();
            connection.close();
            return status;
        } catch (SQLException ex) {
            Logger.getLogger(GroupDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return status;
    }
    
    public int deleteGroup(int groupId){
        int status = 0;
        
        try {
            if(connection == null || connection.isClosed())
                connection = DBHandler.connect();
            CallableStatement proc = connection.prepareCall("{ call \"delete_groups\" ( ?,? ) } ");
            proc.setInt(1, groupId);
       
          
            proc.registerOutParameter(2, java.sql.Types.NUMERIC);
            
            proc.execute();
            
            status = proc.getInt(2);
            
            proc.close();
            connection.close();
            return status;
        } catch (SQLException ex) {
            Logger.getLogger(GroupDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
                
        return status;
    }
    
    public Groups getGroupById(int groupId){
        Groups b = null;
         try {
            if(connection == null || connection.isClosed())
                connection = DBHandler.connect();
            CallableStatement proc = connection.prepareCall("{ call \"get_groups_ById\" ( ?,? ) } ");
            proc.setInt(1, groupId);    
            proc.registerOutParameter(2, OracleTypes.CURSOR);
            
            proc.execute();
              ResultSet r =(ResultSet) proc.getObject(2);
              
              if(r.next()){
                  b = new Groups();
                  b.setGroupId(new BigDecimal(groupId));
                  b.setGroupName(r.getString("GROUP_NAME"));
                  
              }
            proc.close();
            connection.close();
            return b;
        } catch (SQLException ex) {
            Logger.getLogger(GroupDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return b;
    }
    
    public List<Groups> getAllGroups(){
        List groups = null;
                Groups b = null;
         try {
            if(connection == null || connection.isClosed())
                connection = DBHandler.connect();
            CallableStatement proc = connection.prepareCall("{ call \"get_all_from_groups\" ( ? ) } ");
  
            proc.registerOutParameter(1, OracleTypes.CURSOR);
            
            proc.execute();
              ResultSet r =(ResultSet) proc.getObject(1);
              groups = new ArrayList();
              while(r.next()){
                  b = new Groups();
                  b.setGroupId(new BigDecimal(r.getInt("GROUP_ID")));
                  b.setGroupName(r.getString("GROUP_NAME"));
                  groups.add(b);
              }
            proc.close();
            connection.close();
            return groups;
        } catch (SQLException ex) {
            Logger.getLogger(GroupDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return groups;
    }
     public int getLastIndex(){
        int lastIndex = 0;
            try {
            if(connection == null || connection.isClosed())
                connection = DBHandler.connect();
                Statement proc = connection.createStatement();
                
            
                ResultSet r =      proc.executeQuery("SELECT MAX(GROUP_ID) AS NEXTVAL FROM GROUPS");
                  
              if(r.next()){
                  
                  lastIndex = r.getInt("NEXTVAL");
                  
              }
            proc.close();
            connection.close();
        } catch (SQLException ex) {
            Logger.getLogger(ZoneDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lastIndex;
    }    
}
