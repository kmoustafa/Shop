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
import model.Types;
import model.Units;
import oracle.jdbc.OracleTypes;
import utils.DBHandler;

/**
 *
 * @author Kareem.Moustafa
 */
public class UnitDAO {
    
    Connection connection;
    
    public UnitDAO(){
        connection = DBHandler.connect();
    }
    
    public int insertUnit(int unitId, String unitName){
        int status = 0;
        
                         try {
            if(connection == null || connection.isClosed())
                connection = DBHandler.connect();
            CallableStatement proc = connection.prepareCall("{ call \"insert_units\" ( ?,?,? ) } ");
            proc.setInt(1, unitId);
                      proc.setString(2, unitName);

            proc.registerOutParameter(3, java.sql.Types.NUMERIC);
            
            proc.execute();
            
            status = proc.getInt(3);
            
            proc.close();
            connection.close();
            return status;
        } catch (SQLException ex) {
            Logger.getLogger(UnitDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        return status;
    }
    
    public int updateUnit(int unitId, String unitName){
        int status = 0;
                           try {
            if(connection == null || connection.isClosed())
                connection = DBHandler.connect();
            CallableStatement proc = connection.prepareCall("{ call \"update_units\" ( ?,?,? ) } ");
           proc.setInt(1, unitId);
            proc.setString(2, unitName);
          
            proc.registerOutParameter(3, java.sql.Types.NUMERIC);
            
            proc.execute();
            
            status = proc.getInt(3);
            
            proc.close();
            connection.close();
            return status;
        } catch (SQLException ex) {
            Logger.getLogger(UnitDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return status;
    }
    
    public int deleteUnit(int unitId){
        int status = 0;
                                        try {
            if(connection == null || connection.isClosed())
                connection = DBHandler.connect();
            CallableStatement proc = connection.prepareCall("{ call \"delete_units\" ( ?,? ) } ");
           proc.setInt(1, unitId);

          
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
    
    public Units getUnitById(int unitId){
        Units b = null;
                 try {
            if(connection == null || connection.isClosed())
                connection = DBHandler.connect();
            CallableStatement proc = connection.prepareCall("{ call \"get_units_ById\" ( ?,? ) } ");
            proc.setInt(1, unitId);    
            proc.registerOutParameter(2, OracleTypes.CURSOR);
            
            proc.execute();
              ResultSet r =(ResultSet) proc.getObject(2);
              
              if(r.next()){
                  b = new Units();
                  b.setUnitId(new BigDecimal(unitId));
                  b.setUnitName(r.getString("UNIT_NAME"));
                  
              }
            proc.close();
            connection.close();
            return b;
        } catch (SQLException ex) {
            Logger.getLogger(UnitDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return b;
    }
    
    public List<Units> getAllUnits(){
        List units = null;
        Units b = null;
                   try {
            if(connection == null || connection.isClosed())
                connection = DBHandler.connect();
            CallableStatement proc = connection.prepareCall("{ call \"get_all_from_units\" ( ? ) } ");
            proc.registerOutParameter(1, OracleTypes.CURSOR);
            
            proc.execute();
              ResultSet r =(ResultSet) proc.getObject(1);
              units = new ArrayList();
              while(r.next()){
                  b = new Units();
                  b.setUnitId(new BigDecimal(r.getInt("UNIT_ID")));
                  b.setUnitName(r.getString("UNIT_NAME"));
                  units.add(b);
              }
            proc.close();
            connection.close();
            return units;
        } catch (SQLException ex) {
            Logger.getLogger(UnitDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return units;
    }
     public int getLastIndex(){
        int lastIndex = 0;
            try {
            if(connection == null || connection.isClosed())
                connection = DBHandler.connect();
                Statement proc = connection.createStatement();
                
            
                ResultSet r =      proc.executeQuery("SELECT MAX(UNIT_ID) AS NEXTVAL FROM UNITS");
                  
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
