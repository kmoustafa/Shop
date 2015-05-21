/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package daos;

import com.sun.jmx.remote.internal.ArrayQueue;
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
import model.Colors;
import model.Users;
import oracle.jdbc.OracleTypes;
import utils.DBHandler;

/**
 *
 * @author Kareem.Moustafa
 */
public class ColorDAO {
    
    Connection connection;
    
    public ColorDAO(){
        connection = DBHandler.connect();
    }
    
    public int insertColor(int colorId, String colorName){
        int status = 0;
         try {
            if(connection == null || connection.isClosed())
                connection = DBHandler.connect();
            CallableStatement proc = connection.prepareCall("{ call \"insert_colors\" ( ?,?,? ) } ");
            proc.setInt(1, colorId);
            proc.setString(2, colorName);
          
            proc.registerOutParameter(3, java.sql.Types.NUMERIC);
            
            proc.execute();
            
            status = proc.getInt(3);
            
            proc.close();
            connection.close();
            return status;
        } catch (SQLException ex) {
            Logger.getLogger(BankDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return status;
    }
    
    public int updateColor(int colorId, String colorName){
        int status = 0;
                 try {
            if(connection == null || connection.isClosed())
                connection = DBHandler.connect();
            CallableStatement proc = connection.prepareCall("{ call \"update_colors\" ( ?,?,? ) } ");
            proc.setInt(1, colorId);
            proc.setString(2, colorName);
          
            proc.registerOutParameter(3, java.sql.Types.NUMERIC);
            
            proc.execute();
            
            status = proc.getInt(3);
            
            proc.close();
            connection.close();
            return status;
        } catch (SQLException ex) {
            Logger.getLogger(BankDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return status;
    }
    
    public int deleteColor(int colorId){
        int status = 0;
          try {
            if(connection == null || connection.isClosed())
                connection = DBHandler.connect();
            CallableStatement proc = connection.prepareCall("{ call \"delete_colors\" ( ?,? ) } ");
            proc.setInt(1, colorId);    
            proc.registerOutParameter(2, java.sql.Types.NUMERIC);
            
            proc.execute();
            
            status = proc.getInt(2);
            
            proc.close();
            connection.close();
            return status;
        } catch (SQLException ex) {
            Logger.getLogger(BankDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return status;
    }
    
    public Colors getColorById(int colorId){
        Colors b = null;
         try {
            if(connection == null || connection.isClosed())
                connection = DBHandler.connect();
            CallableStatement proc = connection.prepareCall("{ call \"get_colors_ById\" ( ?,? ) } ");
            proc.setInt(1, colorId);    
            proc.registerOutParameter(2, OracleTypes.CURSOR);
            
            proc.execute();
              ResultSet r =(ResultSet) proc.getObject(2);
              
              if(r.next()){
                  b = new Colors();
                  b.setColorId(new BigDecimal(colorId));
                  b.setColorName(r.getString("COLOR_NAME"));
                  
              }
            proc.close();
            connection.close();
            return b;
        } catch (SQLException ex) {
            Logger.getLogger(BankDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return b;
    }
    
    public List<Colors> getAllColors(){
        List colors = null;
        Colors b = null;
                 try {
            if(connection == null || connection.isClosed())
                connection = DBHandler.connect();
            CallableStatement proc = connection.prepareCall("{ call \"get_all_from_colors\" ( ? ) } ");

            proc.registerOutParameter(1, OracleTypes.CURSOR);
            
            proc.execute();
              ResultSet r =(ResultSet) proc.getObject(1);
              colors = new ArrayList();
              while(r.next()){
                  if(r.getInt("COLOR_ID") != 0 ){
                  b = new Colors();
                  b.setColorId(new BigDecimal(r.getInt("COLOR_ID")));
                  b.setColorName(r.getString("COLOR_NAME"));
                  colors.add(b);
                  }
              }
            proc.close();
            connection.close();
            return colors;
        } catch (SQLException ex) {
            Logger.getLogger(BankDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return colors;
    }
     public int getLastIndex(){
        int lastIndex = 0;
            try {
            if(connection == null || connection.isClosed())
                connection = DBHandler.connect();
                Statement proc = connection.createStatement();
                
            
                ResultSet r =      proc.executeQuery("SELECT MAX(COLOR_ID) AS NEXTVAL FROM COLORS");
                  
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
