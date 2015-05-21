/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package daos;

import java.math.BigDecimal;
import java.sql.Blob;
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
import model.Groups;
import model.Items;
import model.Persons;
import model.Sections;
import model.Types;
import model.Units;
import model.Users;
import oracle.jdbc.OracleTypes;
import utils.DBHandler;

/**
 *
 * @author Kareem.Moustafa
 */
public class ItemDAO {
    
    Connection connection;
    
    public ItemDAO(){
        connection = DBHandler.connect();
    }
    
    public int insertItem(int productType, String itemPic, int userId, int unitId, int sectionId, int groupId, int typeId, int personType, int personId, String itemNotes, String itemName, int itemId ){
        int status = 0;
        
           try {
            if(connection == null || connection.isClosed())
                connection = DBHandler.connect();
            CallableStatement proc = connection.prepareCall("{ call \"insert_items\" ( ?,?,?,?,?,?,?,?,?,?,?,?,? ) } ");
            proc.setInt(1, productType);
            proc.setString(2,itemPic );
            proc.setInt(3, userId);
            proc.setInt(4, unitId);
            proc.setInt(5, sectionId);
            proc.setInt(6, groupId);
            proc.setInt(7, typeId);
            proc.setInt(8, personType);            
            proc.setInt(9, personId);            
            proc.setString(10, itemNotes);            
            proc.setString(11, itemName);        
            proc.setInt(12, itemId);
            proc.registerOutParameter(13, java.sql.Types.NUMERIC);
            
            proc.execute();
            
            status = proc.getInt(13);
            
            proc.close();
            connection.close();
            return status;
        } catch (SQLException ex) {
            Logger.getLogger(ItemDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return status;
    }
    
    public int updateItem(int itemId,int productType, String itemPic, int userId, int unitId, int sectionId, int groupId, int typeId, int personType, int personId, String itemNotes, String itemName){
        int status = 0;
                   try {
            if(connection == null || connection.isClosed())
                connection = DBHandler.connect();
            CallableStatement proc = connection.prepareCall("{ call \"update_items\" ( ?,?,?,?,?,?,?,?,?,?,?,?,? ) } ");
            proc.setInt(1, itemId);
            proc.setInt(2, productType);
            proc.setString(3,itemPic );
            proc.setInt(4, userId);
            proc.setInt(5, unitId);
            proc.setInt(6, sectionId);
            proc.setInt(7, groupId);
            proc.setInt(8, typeId);
            proc.setInt(9, personType);            
            proc.setInt(10, personId);            
            proc.setString(11, itemNotes);            
            proc.setString(12, itemName);                        
            proc.registerOutParameter(13, java.sql.Types.NUMERIC);
            
            proc.execute();
            
            status = proc.getInt(13);
            
            proc.close();
            connection.close();
            return status;
        } catch (SQLException ex) {
            Logger.getLogger(ItemDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return status;
    }
    
    public int deleteItem(int itemId){
        int status = 0;
                          try {
            if(connection == null || connection.isClosed())
                connection = DBHandler.connect();
            CallableStatement proc = connection.prepareCall("{ call \"delete_items\" ( ?,? ) } ");
            proc.setInt(1, itemId);
                                
            proc.registerOutParameter(2, java.sql.Types.NUMERIC);
            
            proc.execute();
            
            status = proc.getInt(2);
            
            proc.close();
            connection.close();
            return status;
        } catch (SQLException ex) {
            Logger.getLogger(ItemDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return status;
    }
    
    public Items getItemById(int itemId){
        Items b = null;
               try {
            if(connection == null || connection.isClosed())
                connection = DBHandler.connect();
            CallableStatement proc = connection.prepareCall("{ call \"get_items_ById\" ( ?,? ) } ");
            proc.setInt(1, itemId);    
            proc.registerOutParameter(2, OracleTypes.CURSOR);
            
            proc.execute();
              ResultSet r =(ResultSet) proc.getObject(2);
              
              if(r.next()){
                  b = new Items();
                  GroupDAO groupDAO = new GroupDAO();
                  Groups g = groupDAO.getGroupById(r.getInt("GROUP_ID"));
                  b.setGroups(g);
                  b.setItemId(new BigDecimal(itemId));
                  b.setItemName(r.getString("ITEM_NAME"));
                  b.setItemNotes(r.getString("ITEM_NOTES"));
                  b.setItemPic(r.getString("ITEM_PIC"));
                  PersonDAO personDAO = new PersonDAO();
                  Persons p = personDAO.getPersonById(r.getInt("PERSON_ID"));
                  b.setPersons(p);
                  b.setProductType(new BigDecimal(r.getInt("PRODUCT_TYPE")));
                 
                  SectionDAO sectionDAO = new SectionDAO();
                  Sections sections = sectionDAO.getSectionById(r.getInt("SECTION_ID"));
                  b.setSections(sections);
                  TypeDAO typeDAO = new TypeDAO();
                  Types t = typeDAO.getTypeById(r.getInt("TYPE_ID"));
                  b.setTypes(t);
                  UnitDAO unitDAO = new UnitDAO();
                  Units u = unitDAO.getUnitById(r.getInt("UNIT_ID"));
                  b.setUnits(u);
                 
                  UserDAO userDAO = new UserDAO();
                  Users user = userDAO.getUserById(r.getInt("USER_ID"));
                  b.setUsers(user);
                  
              }
            proc.close();
            connection.close();
            return b;
        } catch (SQLException ex) {
            Logger.getLogger(ItemDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
  
        return b;
    }
    
    public List<Items> getAllItems(){
        List item = null;
                Items b = null;
               try {
            if(connection == null || connection.isClosed())
                connection = DBHandler.connect();
            CallableStatement proc = connection.prepareCall("{ call \"get_all_from_items\" ( ? ) } ");
    
            proc.registerOutParameter(1, OracleTypes.CURSOR);
            
            proc.execute();
              ResultSet r =(ResultSet) proc.getObject(1);
              item = new ArrayList();
              while(r.next()){
                  b = new Items();
                  GroupDAO groupDAO = new GroupDAO();
                  Groups g = groupDAO.getGroupById(r.getInt("GROUP_ID"));
                  b.setGroups(g);
                  b.setItemId(new BigDecimal(r.getInt("ITEM_ID")));
                  b.setItemName(r.getString("ITEM_NAME"));
                  b.setItemNotes(r.getString("ITEM_NOTES"));
                  b.setItemPic(r.getString("ITEM_PIC"));
                  PersonDAO personDAO = new PersonDAO();
                  Persons p = personDAO.getPersonById(r.getInt("PERSON_ID"));
                  b.setPersons(p);
                  b.setProductType(new BigDecimal(r.getInt("PERSON_TYPE")));
                  SectionDAO sectionDAO = new SectionDAO();
                  Sections sections = sectionDAO.getSectionById(r.getInt("SECTION_ID"));
                  b.setSections(sections);
                  TypeDAO typeDAO = new TypeDAO();
                  Types t = typeDAO.getTypeById(r.getInt("TYPE_ID"));
                  b.setTypes(t);
                  UnitDAO unitDAO = new UnitDAO();
                  Units u = unitDAO.getUnitById(r.getInt("UNIT_ID"));
                  b.setUnits(u);
                 
                  UserDAO userDAO = new UserDAO();
                  Users user = userDAO.getUserById(r.getInt("USER_ID"));
                  b.setUsers(user);
                  item.add(b);
              }
            proc.close();
            connection.close();
            return item;
        } catch (SQLException ex) {
            Logger.getLogger(ItemDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
  
        return item;
    }
        public int getLastIndex() {
        int lastIndex = 0;
        try {
            if (connection == null || connection.isClosed()) {
                connection = DBHandler.connect();
            }
            Statement proc = connection.createStatement();

            ResultSet r = proc.executeQuery("SELECT MAX(ITEM_ID) AS NEXTVAL FROM ITEMS");

            if (r.next()) {

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
