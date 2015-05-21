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
import model.Items;
import model.ItemsSerials;
import model.Persons;
import model.Sections;
import model.Sizes;
import model.Types;
import model.Units;
import model.Users;
import oracle.jdbc.OracleTypes;
import utils.DBHandler;

/**
 *
 * @author Kareem.Moustafa
 */
public class ItemSerialDAO {
    
    Connection connection;
    
    public ItemSerialDAO(){
        connection = DBHandler.connect();
    }
    
    public int insertItemSerial(String barCode, int itemId, int sizeId, int colorId, double preceentage, int reqQuantity, double sales1, double sales2, double sales3, double stCost, double cost){
        int status = 0;
        
          try {
                          int id = getLastIndex()+1;
            if(connection == null || connection.isClosed())
                connection = DBHandler.connect();

            CallableStatement proc = connection.prepareCall("{ call \"insert_items_serials\" ( ?,?,?,?,?,?,?,?,?,?,?,?,? ) } ");
            proc.setString(1, barCode);
            proc.setInt(2,itemId );
            proc.setInt(3, sizeId);
            proc.setInt(4, colorId);
            proc.setDouble(5, preceentage);
            proc.setInt(6, reqQuantity);
            proc.setDouble(7, sales1);
            proc.setDouble(8, sales2);            
            proc.setDouble(9, sales3);            
            proc.setDouble(10, stCost);            
            proc.setDouble(11, cost);                        
            proc.setInt(12, id);
            proc.registerOutParameter(13, java.sql.Types.NUMERIC);
            
            proc.execute();
            
            status = proc.getInt(13);
            
            proc.close();
            connection.close();
            return status;
        } catch (SQLException ex) {
            Logger.getLogger(ItemSerialDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return status;
    }
    
    public int updateItemSerial(int itemserialId,String barCode, int itemId, int sizeId, int colorId, double preceentage, int reqQuantity, double sales1, double sales2, double sales3, double stCost, double cost){
        int status = 0;
                
          try {
            if(connection == null || connection.isClosed())
                connection = DBHandler.connect();
            CallableStatement proc = connection.prepareCall("{ call \"update_items_serials\" ( ?,?,?,?,?,?,?,?,?,?,?,?,? ) } ");
            proc.setInt(1, itemserialId);
            proc.setString(2, barCode);
            proc.setInt(3,itemId );
            proc.setInt(4, sizeId);
            proc.setInt(5, colorId);
            proc.setDouble(6, preceentage);
            proc.setInt(7, reqQuantity);
            proc.setDouble(8, sales1);
            proc.setDouble(9, sales2);            
            proc.setDouble(10, sales3);            
            proc.setDouble(11, stCost);            
            proc.setDouble(12, cost);                        
            proc.registerOutParameter(13, java.sql.Types.NUMERIC);
            
            proc.execute();
            
            status = proc.getInt(13);
            
            proc.close();
            connection.close();
            return status;
        } catch (SQLException ex) {
            Logger.getLogger(ItemSerialDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return status;
    }
    
    public int deleteItemSerial(int itemserialId){
        int status = 0;
                  try {
            if(connection == null || connection.isClosed())
                connection = DBHandler.connect();
            CallableStatement proc = connection.prepareCall("{ call \"delete_items_serials\" ( ?,? ) } ");
            proc.setInt(1, itemserialId);
                              
            proc.registerOutParameter(2, java.sql.Types.NUMERIC);
            
            proc.execute();
            
            status = proc.getInt(2);
            
            proc.close();
            connection.close();
            return status;
        } catch (SQLException ex) {
            Logger.getLogger(ItemSerialDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return status;
    }
    
    public ItemsSerials getItemSerialById(int itemserialId){
        ItemsSerials b = null;
            try {
            if(connection == null || connection.isClosed())
                connection = DBHandler.connect();
            CallableStatement proc = connection.prepareCall("{ call \"get_items_serials_ById\" ( ?,? ) } ");
            proc.setInt(1, itemserialId);    
            proc.registerOutParameter(2, OracleTypes.CURSOR);
            
            proc.execute();
              ResultSet r =(ResultSet) proc.getObject(2);
              
              if(r.next()){
                  b = new ItemsSerials();
                  b.setBarCode(r.getString("BAR_CODE"));
                  ColorDAO colorDAO = new ColorDAO();
                  Colors c = colorDAO.getColorById(r.getInt("COLOR_ID"));
                  b.setColors(c);
                  b.setCost(r.getDouble("COST"));
                  b.setItemDId(new BigDecimal(itemserialId));
                  ItemDAO itemDAO = new ItemDAO();
                  Items i = itemDAO.getItemById(r.getInt("ITEM_ID"));
                  b.setItems(i);
                  b.setPercentage(r.getDouble("PERCENTAGE"));
                  b.setRequirQty(new BigDecimal(r.getInt("REQUIR_QTY")));
                  b.setSales1(r.getDouble("SALES1"));
                  b.setSales2(r.getDouble("SALES2"));
                  b.setSales3(r.getDouble("SALES3"));
                  SizeDAO sizeDAO = new SizeDAO();
                  Sizes s = sizeDAO.getSizeById(r.getInt("SIZE_ID"));
                  b.setSizes(s);
                  b.setStCost(r.getDouble("ST_COST"));
                 
                  
              }
            proc.close();
            connection.close();
            return b;
        } catch (SQLException ex) {
            Logger.getLogger(ItemSerialDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return b;
    }
    
    public List<ItemsSerials> getAllItemSerials(){
        List itemS = null;
                ItemsSerials b = null;
            try {
            if(connection == null || connection.isClosed())
                connection = DBHandler.connect();
            CallableStatement proc = connection.prepareCall("{ call \"get_all_from_items_serials\" ( ? ) } ");  
            proc.registerOutParameter(1, OracleTypes.CURSOR);
            
            proc.execute();
              ResultSet r =(ResultSet) proc.getObject(1);
              itemS = new ArrayList();
              while(r.next()){
                  b = new ItemsSerials();
                  b.setBarCode(r.getString("BAR_CODE"));
                  ColorDAO colorDAO = new ColorDAO();
                  Colors c = colorDAO.getColorById(r.getInt("COLOR_ID"));
                  b.setColors(c);
                  b.setCost(r.getDouble("COST"));
                  b.setItemDId(new BigDecimal(r.getDouble("ITEM_D_ID")));
                  ItemDAO itemDAO = new ItemDAO();
                  Items i = itemDAO.getItemById(r.getInt("ITEM_ID"));
                  b.setItems(i);
                  b.setPercentage(r.getDouble("PERCENTAGE"));
                  b.setRequirQty(new BigDecimal(r.getInt("REQUIR_QTY")));
                  b.setSales1(r.getDouble("SALES1"));
                  b.setSales2(r.getDouble("SALES2"));
                  b.setSales3(r.getDouble("SALES3"));
                  SizeDAO sizeDAO = new SizeDAO();
                  Sizes s = sizeDAO.getSizeById(r.getInt("SIZE_ID"));
                  b.setSizes(s);
                  b.setStCost(r.getDouble("ST_COST"));
                 itemS.add(b);
                  
              }
            proc.close();
            connection.close();
            return itemS;
        } catch (SQLException ex) {
            Logger.getLogger(ItemSerialDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return itemS;
    }
    public List<ItemsSerials> getAllItemSerialsByItemId(int itemId){
        List itemS = null;
                ItemsSerials b = null;
            try {
            if(connection == null || connection.isClosed())
                connection = DBHandler.connect();
            CallableStatement proc = connection.prepareCall("{ call \"get_all_items_s_by_itemID\" ( ?, ? ) } ");  
            proc.setInt(1, itemId);
            proc.registerOutParameter(2, OracleTypes.CURSOR);
            
            proc.execute();
              ResultSet r =(ResultSet) proc.getObject(2);
              itemS = new ArrayList();
              while(r.next()){
                  b = new ItemsSerials();
                  b.setBarCode(r.getString("BAR_CODE"));
                  ColorDAO colorDAO = new ColorDAO();
                  Colors c = colorDAO.getColorById(r.getInt("COLOR_ID"));
                  b.setColors(c);
                  b.setCost(r.getDouble("COST"));
                  b.setItemDId(new BigDecimal(r.getDouble("ITEM_D_ID")));
                  ItemDAO itemDAO = new ItemDAO();
                  Items i = itemDAO.getItemById(r.getInt("ITEM_ID"));
                  b.setItems(i);
                  b.setPercentage(r.getDouble("PERCENTAGE"));
                  b.setRequirQty(new BigDecimal(r.getInt("REQUIR_QTY")));
                  b.setSales1(r.getDouble("SALES1"));
                  b.setSales2(r.getDouble("SALES2"));
                  b.setSales3(r.getDouble("SALES3"));
                  SizeDAO sizeDAO = new SizeDAO();
                  Sizes s = sizeDAO.getSizeById(r.getInt("SIZE_ID"));
                  b.setSizes(s);
                  b.setStCost(r.getDouble("ST_COST"));
                 itemS.add(b);
                  
              }
            proc.close();
            connection.close();
            return itemS;
        } catch (SQLException ex) {
            Logger.getLogger(ItemSerialDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return itemS;
    }    
        public int getLastIndex() {
        int lastIndex = 0;
        try {
            if (connection == null || connection.isClosed()) {
                connection = DBHandler.connect();
            }
            Statement proc = connection.createStatement();

            ResultSet r = proc.executeQuery("SELECT MAX(ITEM_D_ID) AS NEXTVAL FROM ITEMS_SERIALS");

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
