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
import model.Companyinfo;
import model.Users;
import oracle.jdbc.OracleTypes;
import utils.DBHandler;

/**
 *
 * @author kareem.moustafa
 */
public class CompanyInfoDAO {
        Connection connection;
    
    public CompanyInfoDAO(){
        connection = DBHandler.connect();
    }
    
    public int insertCompanyInfo(String revenue, String expenses, String goodsLA, String goodsFR, String purchasesCost, String discPurchases, String returnedPurchases, String purchases, String vendors, String charge, String diffTreasury, String taxD
    , String salesTax, String salesCose, String discSales, String returnedSales, String sales, String clients, String bank, String treasury, int startMonth, String fYear){
            int status = 0;
        try {
            if(connection == null || connection.isClosed())
                connection = DBHandler.connect();
            CallableStatement proc = connection.prepareCall("{ call \"insert_companyinfo\" ( ?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,? ) } ");
            proc.setString(1, revenue);
            proc.setString(2, expenses);
            proc.setString(3, goodsLA);
            proc.setString(4, goodsFR);
            proc.setString(5, purchasesCost);
            proc.setString(6, discPurchases);
            proc.setString(7, returnedPurchases);
            proc.setString(8, purchases);
            proc.setString(9, vendors);
            proc.setString(10, charge);
            proc.setString(11, diffTreasury);
            proc.setString(12, taxD);
            proc.setString(13, salesTax);
            proc.setString(14, salesCose);
            proc.setString(15, discSales);
            proc.setString(16, returnedSales);
            proc.setString(17, sales);
            proc.setString(18, clients);
            proc.setString(19, bank);
            proc.setString(20, treasury);
            proc.setInt(21, startMonth);
            proc.setString(22, fYear);
            proc.registerOutParameter(23, java.sql.Types.NUMERIC);
            
            proc.execute();
            
            status = proc.getInt(23);
            
            proc.close();
            connection.close();
            return status;
        } catch (SQLException ex) {
            Logger.getLogger(CompanyInfoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return status;
    }
    
    public int updateCompanyInfo(int companyInfoId, String revenue, String expenses, String goodsLA, String goodsFR, String purchasesCost, String discPurchases, String returnedPurchases, String purchases, String vendors, String charge, String diffTreasury, String taxD
    , String salesTax, String salesCose, String discSales, String returnedSales, String sales, String clients, String bank, String treasury, int startMonth, String fYear){
        int status = 0;
        try {
            if(connection == null || connection.isClosed())
                connection = DBHandler.connect();
            CallableStatement proc = connection.prepareCall("{ call \"update_companyinfo\" ( ?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,? ) } ");
           proc.setInt(1, companyInfoId);
            proc.setString(2, revenue);
            proc.setString(3, expenses);
            proc.setString(4, goodsLA);
            proc.setString(5, goodsFR);
            proc.setString(6, purchasesCost);
            proc.setString(7, discPurchases);
            proc.setString(8, returnedPurchases);
            proc.setString(9, purchases);
            proc.setString(10, vendors);
            proc.setString(11, charge);
            proc.setString(12, diffTreasury);
            proc.setString(13, taxD);
            proc.setString(14, salesTax);
            proc.setString(15, salesCose);
            proc.setString(16, discSales);
            proc.setString(17, returnedSales);
            proc.setString(18, sales);
            proc.setString(19, clients);
            proc.setString(29, bank);
            proc.setString(21, treasury);
            proc.setInt(22, startMonth);
            proc.setString(23, fYear);
            proc.registerOutParameter(24, java.sql.Types.NUMERIC);
            
            proc.execute();
            
            status = proc.getInt(24);
            
            proc.close();
            connection.close();
            return status;
        } catch (SQLException ex) {
            Logger.getLogger(CompanyInfoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return status;
    }
    
    public int deleteCompanyInfo(int companyInfoId){
  int status = 0;
        try {
            if(connection == null || connection.isClosed())
                connection = DBHandler.connect();
            CallableStatement proc = connection.prepareCall("{ call \"delete_companyinfo\" ( ?,? ) } ");
            proc.setInt(1, companyInfoId);    
            proc.registerOutParameter(2, java.sql.Types.NUMERIC);
            
            proc.execute();
            
            status = proc.getInt(2);
            
            proc.close();
            connection.close();
            return status;
        } catch (SQLException ex) {
            Logger.getLogger(CompanyInfoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return status;
    }
    
    public Companyinfo getCompanyInfoById(int companyInfoId){
        
        Companyinfo b = null;
          try {
            if(connection == null || connection.isClosed())
                connection = DBHandler.connect();
            CallableStatement proc = connection.prepareCall("{ call \"get_companyinfo_ById\" ( ?,? ) } ");
            proc.setInt(1, companyInfoId);    
            proc.registerOutParameter(2, OracleTypes.CURSOR);
            
            proc.execute();
              ResultSet r =(ResultSet) proc.getObject(2);
              
              if(r.next()){
                  b = new Companyinfo();
                  b.setBank(r.getString("BANK"));
                  b.setCharge(r.getString("CHARGE"));
                  b.setClients(r.getString("CLIENTS"));
                  b.setDiffTreasury(r.getString("DIFF_TREASURY"));
                  b.setDiscPurchases(r.getString("DISC_PURCHASES"));
                  b.setDiscSales(r.getString("DISC_SALES"));
                  b.setExpenses(r.getString("EXPENSES"));
                  b.setFYear(r.getString("F_YEAR"));
                  b.setGoodsFrDuration(r.getString("GOODS_FR_DURATION"));
                  b.setGoodsLaDuration(r.getString("GOODS_LA_DURATION"));
                  b.setId(new BigDecimal(companyInfoId));
                  b.setPurchases(r.getString("PURCHASES"));
                  b.setPurchasesCost(r.getString("PURCHASES_COST"));
                  b.setReturnedPurchases(r.getString("RETURNED_PURCHASES"));
                  b.setReturnedSales(r.getString("RETURNED_SALES"));
                  b.setRevenue(r.getString("REVENUE"));
                  b.setSales(r.getString("SALES"));
                  b.setSalesTax(r.getString("SALES_TAX"));
                  b.setSalesCost(r.getString("SALES_COST"));
                  b.setStartMonth(new BigDecimal(r.getInt("START_MONTH")));
                  b.setTaxD(r.getString("TAX_D"));
                  b.setTreasury(r.getString("TREASURY"));
                  b.setVendors(r.getString("VENDORS"));
                 
              }
            proc.close();
            connection.close();
            return b;
        } catch (SQLException ex) {
            Logger.getLogger(CompanyInfoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return b;
    }
    
    public List<Companyinfo> getAllBanks(){
        List companyInfos = null;
        Companyinfo b = null;
          try {
            if(connection == null || connection.isClosed())
                connection = DBHandler.connect();
            CallableStatement proc = connection.prepareCall("{ call \"get_all_from_companyinfo\" ( ? ) } ");
            proc.registerOutParameter(1, OracleTypes.CURSOR);
            
            proc.execute();
              ResultSet r =(ResultSet) proc.getObject(1);
              companyInfos = new ArrayList<Companyinfo>();
              while(r.next()){
                  b = new Companyinfo();
                  b.setBank(r.getString("BANK"));
                  b.setCharge(r.getString("CHARGE"));
                  b.setClients(r.getString("CLIENTS"));
                  b.setDiffTreasury(r.getString("DIFF_TREASURY"));
                  b.setDiscPurchases(r.getString("DISC_PURCHASES"));
                  b.setDiscSales(r.getString("DISC_SALES"));
                  b.setExpenses(r.getString("EXPENSES"));
                  b.setFYear(r.getString("F_YEAR"));
                  b.setGoodsFrDuration(r.getString("GOODS_FR_DURATION"));
                  b.setGoodsLaDuration(r.getString("GOODS_LA_DURATION"));
                  b.setId(new BigDecimal(r.getInt("ID")));
                  b.setPurchases(r.getString("PURCHASES"));
                  b.setPurchasesCost(r.getString("PURCHASES_COST"));
                  b.setReturnedPurchases(r.getString("RETURNED_PURCHASES"));
                  b.setReturnedSales(r.getString("RETURNED_SALES"));
                  b.setRevenue(r.getString("REVENUE"));
                  b.setSales(r.getString("SALES"));
                  b.setSalesTax(r.getString("SALES_TAX"));
                  b.setSalesCost(r.getString("SALES_COST"));
                  b.setStartMonth(new BigDecimal(r.getInt("START_MONTH")));
                  b.setTaxD(r.getString("TAX_D"));
                  b.setTreasury(r.getString("TREASURY"));
                  b.setVendors(r.getString("VENDORS"));
                 companyInfos.add(b);
              }
            proc.close();
            connection.close();
            return companyInfos;
        } catch (SQLException ex) {
            Logger.getLogger(CompanyInfoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return companyInfos;
    }
}
