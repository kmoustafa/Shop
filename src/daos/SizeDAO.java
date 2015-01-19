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
import model.Sizes;
import oracle.jdbc.OracleTypes;
import utils.DBHandler;

/**
 *
 * @author Kareem.Moustafa
 */
public class SizeDAO {
    
    Connection connection;
    
    public SizeDAO(){
        connection = DBHandler.connect();
    }
    
    public int insertSize(int sizeId, String sizeName){
        int status = 0;

         try {
            if(connection == null || connection.isClosed())
                connection = DBHandler.connect();
            CallableStatement proc = connection.prepareCall("{ call \"insert_sizes\" ( ?,?,? ) } ");
            proc.setInt(1, sizeId);
            proc.setString(2, sizeName);          
            proc.registerOutParameter(3, java.sql.Types.NUMERIC);
            
            proc.execute();
            
            status = proc.getInt(3);
            
            proc.close();
            connection.close();
            return status;
        } catch (SQLException ex) {
            Logger.getLogger(SectionDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return status;
    }
    
    public int updateSize(int sizeId, String sizeName){
        int status = 0;
                 try {
            if(connection == null || connection.isClosed())
                connection = DBHandler.connect();
            CallableStatement proc = connection.prepareCall("{ call \"update_sizes\" ( ?,?,? ) } ");
            proc.setInt(1, sizeId);
            proc.setString(2, sizeName);
          
            proc.registerOutParameter(3, java.sql.Types.NUMERIC);
            
            proc.execute();
            
            status = proc.getInt(3);
            
            proc.close();
            connection.close();
            return status;
        } catch (SQLException ex) {
            Logger.getLogger(SectionDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return status;
    }
    
    public int deleteSize(int sizeId){
        int status = 0;
                        try {
            if(connection == null || connection.isClosed())
                connection = DBHandler.connect();
            CallableStatement proc = connection.prepareCall("{ call \"delete_sizes\" ( ?,? ) } ");
            proc.setInt(1, sizeId);

          
            proc.registerOutParameter(2, java.sql.Types.NUMERIC);
            
            proc.execute();
            
            status = proc.getInt(2);
            
            proc.close();
            connection.close();
            return status;
        } catch (SQLException ex) {
            Logger.getLogger(SectionDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return status;
    }
    
    public Sizes getSizeById(int sizeId){
        Sizes b = null;
          try {
            if(connection == null || connection.isClosed())
                connection = DBHandler.connect();
            CallableStatement proc = connection.prepareCall("{ call \"get_sizes_ById\" ( ?,? ) } ");
            proc.setInt(1, sizeId);    
            proc.registerOutParameter(2, OracleTypes.CURSOR);
            
            proc.execute();
              ResultSet r =(ResultSet) proc.getObject(2);
              
              if(r.next()){
                  b = new Sizes();
                  b.setSizeId(new BigDecimal(sizeId));
                  b.setSizeName(r.getString("SIZE_NAME"));
                  
              }
            proc.close();
            connection.close();
            return b;
        } catch (SQLException ex) {
            Logger.getLogger(SizeDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return b;
    }
    
    public List<Sizes> getAllSizes(){
        List sizes = null;
               Sizes b = null;
          try {
            if(connection == null || connection.isClosed())
                connection = DBHandler.connect();
            CallableStatement proc = connection.prepareCall("{ call \"get_all_from_sizes\" ( ? ) } ");
            proc.registerOutParameter(1, OracleTypes.CURSOR);
            
            proc.execute();
              ResultSet r =(ResultSet) proc.getObject(1);
              sizes = new ArrayList<Sizes>();
              while(r.next()){
                  b = new Sizes();
                  b.setSizeId(new BigDecimal(r.getInt("SIZE_ID")));
                  b.setSizeName(r.getString("SIZE_NAME"));
                  sizes.add(b);
              }
            proc.close();
            connection.close();
            return sizes;
        } catch (SQLException ex) {
            Logger.getLogger(SizeDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return sizes;
    }
     public int getLastIndex(){
        int lastIndex = 0;
            try {
            if(connection == null || connection.isClosed())
                connection = DBHandler.connect();
                Statement proc = connection.createStatement();
                
            
                ResultSet r =      proc.executeQuery("SELECT MAX(SIZE_ID) AS NEXTVAL FROM SIZES");
                  
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
