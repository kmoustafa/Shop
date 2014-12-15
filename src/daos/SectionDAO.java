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
import model.Sections;
import oracle.jdbc.OracleTypes;
import utils.DBHandler;

/**
 *
 * @author Kareem.Moustafa
 */
public class SectionDAO {
    
    Connection connection;
    
    public SectionDAO(){
        connection = DBHandler.connect();
    }
    
    public int insertSection(String sectionName){
        int status = 0;
          try {
            if(connection == null || connection.isClosed())
                connection = DBHandler.connect();
            CallableStatement proc = connection.prepareCall("{ call \"insert_sections\" ( ?,? ) } ");
            proc.setString(1, sectionName);
          
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
    
    public int updateSection(int sectionId, String sectionName){
        int status = 0;
                  try {
            if(connection == null || connection.isClosed())
                connection = DBHandler.connect();
            CallableStatement proc = connection.prepareCall("{ call \"update_sections\" ( ?,?,? ) } ");
          
            proc.setInt(1, sectionId);
            proc.setString(2, sectionName);
          
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
    
    public int deleteSection(int sectionId){
        int status = 0;
                try {
            if(connection == null || connection.isClosed())
                connection = DBHandler.connect();
            CallableStatement proc = connection.prepareCall("{ call \"delete_sections\" ( ?,? ) } ");         
            proc.setInt(1, sectionId);  
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
    
    public Sections getSectionById(int sectionId){
        Sections b = null;
            try {
            if(connection == null || connection.isClosed())
                connection = DBHandler.connect();
            CallableStatement proc = connection.prepareCall("{ call \"get_sections_ById\" ( ?,? ) } ");
            proc.setInt(1, sectionId);    
            proc.registerOutParameter(2, OracleTypes.CURSOR);
            
            proc.execute();
              ResultSet r =(ResultSet) proc.getObject(2);
              
              if(r.next()){
                  b = new Sections();
                  b.setSectionId(new BigDecimal(sectionId));
                  b.setSectionName(r.getString("SECTION_NAME"));
                 
              }
            proc.close();
            connection.close();
            return b;
        } catch (SQLException ex) {
            Logger.getLogger(SectionDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return b;
    }
    
    public List<Sections> getAllSections(){
        List sections = null;
           Sections b = null;
            try {
            if(connection == null || connection.isClosed())
                connection = DBHandler.connect();
            CallableStatement proc = connection.prepareCall("{ call \"get_all_from_sections\" ( ? ) } ");
   
            proc.registerOutParameter(1, OracleTypes.CURSOR);
            
            proc.execute();
              ResultSet r =(ResultSet) proc.getObject(1);
              sections = new ArrayList();
              while(r.next()){
                  b = new Sections();
                  b.setSectionId(new BigDecimal(r.getInt("SECTION_ID")));
                  b.setSectionName(r.getString("SECTION_NAME"));
                 sections.add(b);
              }
            proc.close();
            connection.close();
            return sections;
        } catch (SQLException ex) {
            Logger.getLogger(SectionDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return sections;
    }
}
