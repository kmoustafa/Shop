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
import model.Banks;
import model.Charts;
import model.Colors;
import model.ItemsSerials;
import model.PortfolioDetails;
import model.Sizes;
import model.TransactionDetails;
import model.Transactions;
import model.Users;
import oracle.jdbc.OracleTypes;
import utils.DBHandler;

/**
 *
 * @author Kareem.Moustafa
 */
public class TransactionDetailDAO {
    
    Connection connection;
    
    public TransactionDetailDAO(){
        connection = DBHandler.connect();
    }
    
    public int insertTransactionDetail(double accDr, double accCr, int transType, int transNo, int portDId, int itemDId, int sizeId, int colorId, int accId,
            java.sql.Date transDDate, String transDNotes, String transDWSID, String transDBarcode, double transDDiscValue, double transeDDiscPercent, double transDUnitPrice, int quantity){
        int status = 0;
        
         try {
            if(connection == null || connection.isClosed())
                connection = DBHandler.connect();
            CallableStatement proc = connection.prepareCall("{ call \"insert_transaction_details\" ( ?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,? ) } ");
            proc.setDouble(1, accDr);
            proc.setDouble(2, accCr);
            proc.setInt(3, transType);
            proc.setInt(4, transNo);
            proc.setInt(5, portDId);
            proc.setInt(6, itemDId);
            proc.setInt(7, sizeId);
            proc.setInt(8, colorId);            
            proc.setInt(9, accId);            
            proc.setDate(10, transDDate);            
            proc.setString(11, transDNotes);            
            proc.setString(12, transDWSID);            
            proc.setString(13, transDBarcode);
            proc.setDouble(14, transDDiscValue);
            proc.setDouble(15, transDUnitPrice);
            proc.setDouble(16, transDUnitPrice);
            proc.setInt(17, quantity);
            proc.registerOutParameter(18, java.sql.Types.NUMERIC);
            
            proc.execute();
            
            status = proc.getInt(18);
            
            proc.close();
            connection.close();
            return status;
        } catch (SQLException ex) {
            Logger.getLogger(TransactionDetailDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return status;
    }
    
    public int updateTransactionDetail(int transDId, double accDr, double accCr, int transType, int transNo, int portDId, int itemDId, int sizeId, int colorId, int accId,
            java.sql.Date transDDate, String transDNotes, String transDWSID, String transDBarcode, double transDDiscValue, double transeDDiscPercent, double transDUnitPrice, int quantity){
        int status = 0;
         
         try {
            if(connection == null || connection.isClosed())
                connection = DBHandler.connect();
            CallableStatement proc = connection.prepareCall("{ call \"update_transaction_details\" ( ?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,? ) } ");
            proc.setInt(1, transDId);
            proc.setDouble(2, accDr);
            proc.setDouble(3, accCr);
            proc.setInt(4, transType);
            proc.setInt(5, transNo);
            proc.setInt(6, portDId);
            proc.setInt(7, itemDId);
            proc.setInt(8, sizeId);
            proc.setInt(9, colorId);            
            proc.setInt(10, accId);            
            proc.setDate(11, transDDate);            
            proc.setString(12, transDNotes);            
            proc.setString(13, transDWSID);            
            proc.setString(14, transDBarcode);
            proc.setDouble(15, transDDiscValue);
            proc.setDouble(16, transDUnitPrice);
            proc.setDouble(17, transDUnitPrice);
            proc.setInt(18, quantity);
            proc.registerOutParameter(19, java.sql.Types.NUMERIC);
            
            proc.execute();
            
            status = proc.getInt(19);
            
            proc.close();
            connection.close();
            return status;
        } catch (SQLException ex) {
            Logger.getLogger(TransactionDetailDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return status;
    }
    
    public int deleteTransactionDetail(int transDId){
        int status = 0;
                 try {
            if(connection == null || connection.isClosed())
                connection = DBHandler.connect();
            CallableStatement proc = connection.prepareCall("{ call \"delete_transaction_details\" ( ?,? ) } ");
            proc.setInt(1, transDId);
          
            proc.registerOutParameter(2, java.sql.Types.NUMERIC);
            
            proc.execute();
            
            status = proc.getInt(2);
            
            proc.close();
            connection.close();
            return status;
        } catch (SQLException ex) {
            Logger.getLogger(TransactionDetailDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return status;
    }
    
    public TransactionDetails getTransactionDetailById(int transDId){
        TransactionDetails b = null;
        try {
            if(connection == null || connection.isClosed())
                connection = DBHandler.connect();
            CallableStatement proc = connection.prepareCall("{ call \"get_transaction_details_ById\" ( ?,? ) } ");
            proc.setInt(1, transDId);    
            proc.registerOutParameter(2, OracleTypes.CURSOR);
            
            proc.execute();
              ResultSet r =(ResultSet) proc.getObject(2);
              
              if(r.next()){
                  b = new TransactionDetails();
                  b.setAccCr(r.getDouble("ACC_CR"));
                  b.setAccDr(r.getDouble("ACC_DR"));
                  ChartDAO chartDAO = new ChartDAO();
                  Charts chart = chartDAO.getChartById(r.getInt("ACC_ID"));
                  b.setCharts(chart);
                  ColorDAO colorDAO = new ColorDAO();
                  Colors c = colorDAO.getColorById(r.getInt("COLOR_ID"));
                  b.setColors(c);
                  ItemSerialDAO isdao = new ItemSerialDAO();
                  ItemsSerials is = isdao.getItemSerialById(r.getInt("ITEM_D_ID"));
                  b.setItemsSerials(is);
                  PortfolioDetailsDAO aO = new PortfolioDetailsDAO();
                  PortfolioDetails details = aO.getPortfolioDetailsById(r.getInt("PORT_D_ID"));
                  b.setPortfolioDetails(details);
                  b.setQty(new BigDecimal(r.getInt("QTY")));
                  SizeDAO sdao = new SizeDAO();
                  Sizes s = sdao.getSizeById(r.getInt("SIZE_ID"));
                  b.setSizes(s);
                  b.setTransDDiscPercent(r.getDouble("TRANS_D_DISC_PERCENT"));
                  b.setTransDDiscValue(r.getDouble("TRANS_D_DISC_VALUE"));
                  b.setTransDId(new BigDecimal(transDId));
                  b.setTransDUnitPrice(r.getDouble("TRANS_D_UNIT_PRICE"));
                  b.setTransDetBarcode(r.getString("TRANS_DET_BARCODE"));
                  b.setTransDetDate(r.getDate("TRANS_DET_DATE"));
                  b.setTransDetNotes(r.getString("TRANS_DET_NOTES"));
                  b.setTransDetWsid(r.getString("TRANS_DET_WSID"));
                  TransactionDAO transactionDAO = new TransactionDAO();
                  Transactions t = transactionDAO.getTransactionById(r.getInt("TRANS_NO"), r.getInt("TRANS_TYPE"));
                  b.setTransactions(t);
              }
            proc.close();
            connection.close();
            return b;
        } catch (SQLException ex) {
            Logger.getLogger(TransactionDetailDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return b;
    }
    
    public List<TransactionDetails> getAllTransactionDetails(){
        List transDetails = null;
        TransactionDetails b = null;
                try {
            if(connection == null || connection.isClosed())
                connection = DBHandler.connect();
            CallableStatement proc = connection.prepareCall("{ call \"get_all_from_trans_details\" ( ? ) } ");
 
            proc.registerOutParameter(1, OracleTypes.CURSOR);
            
            proc.execute();
              ResultSet r =(ResultSet) proc.getObject(1);
              transDetails = new ArrayList();
              while(r.next()){
                  b = new TransactionDetails();
                  b.setAccCr(r.getDouble("ACC_CR"));
                  b.setAccDr(r.getDouble("ACC_DR"));
                  ChartDAO chartDAO = new ChartDAO();
                  Charts chart = chartDAO.getChartById(r.getInt("ACC_ID"));
                  b.setCharts(chart);
                  ColorDAO colorDAO = new ColorDAO();
                  Colors c = colorDAO.getColorById(r.getInt("COLOR_ID"));
                  b.setColors(c);
                  ItemSerialDAO isdao = new ItemSerialDAO();
                  ItemsSerials is = isdao.getItemSerialById(r.getInt("ITEM_D_ID"));
                  b.setItemsSerials(is);
                  PortfolioDetailsDAO aO = new PortfolioDetailsDAO();
                  PortfolioDetails details = aO.getPortfolioDetailsById(r.getInt("PORT_D_ID"));
                  b.setPortfolioDetails(details);
                  b.setQty(new BigDecimal(r.getInt("QTY")));
                  SizeDAO sdao = new SizeDAO();
                  Sizes s = sdao.getSizeById(r.getInt("SIZE_ID"));
                  b.setSizes(s);
                  b.setTransDDiscPercent(r.getDouble("TRANS_D_DISC_PERCENT"));
                  b.setTransDDiscValue(r.getDouble("TRANS_D_DISC_VALUE"));
                  b.setTransDId(new BigDecimal(r.getInt("TRANS_D_ID")));
                  b.setTransDUnitPrice(r.getDouble("TRANS_D_UNIT_PRICE"));
                  b.setTransDetBarcode(r.getString("TRANS_DET_BARCODE"));
                  b.setTransDetDate(r.getDate("TRANS_DET_DATE"));
                  b.setTransDetNotes(r.getString("TRANS_DET_NOTES"));
                  b.setTransDetWsid(r.getString("TRANS_DET_WSID"));
                  TransactionDAO transactionDAO = new TransactionDAO();
                  Transactions t = transactionDAO.getTransactionById(r.getInt("TRANS_NO"), r.getInt("TRANS_TYPE"));
                  b.setTransactions(t);
                  transDetails.add(b);
              }
            proc.close();
            connection.close();
            return transDetails;
        } catch (SQLException ex) {
            Logger.getLogger(TransactionDetailDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return transDetails;
    }
}
