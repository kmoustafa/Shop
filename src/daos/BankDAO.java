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
import javax.print.DocFlavor;
import model.Banks;
import model.Charts;
import model.Users;
import oracle.jdbc.OracleTypes;
import utils.DBHandler;

/**
 *
 * @author Kareem.Moustafa
 */
public class BankDAO {
    
    Connection connection;
    
    public BankDAO(){
        connection = DBHandler.connect();
    }
    
    public int insertBank(String bankName, String bankBranch, String bankAdd, String bankPhone, String bankFax, String bankEmail, double openDebit, double openCredit, String bankIdNo, int accId, int userId, String accType){
            int status = 0;
        try {
            if(connection == null || connection.isClosed())
                connection = DBHandler.connect();
            CallableStatement proc = connection.prepareCall("{ call \"insert_banks\" ( ?,?,?,?,?,?,?,?,?,?,?,?,? ) } ");
            proc.setString(1, accType);
            proc.setInt(2, userId);
            proc.setInt(3, accId);
            proc.setString(4, bankIdNo);
            proc.setDouble(5, openCredit);
            proc.setDouble(6, openDebit);
            proc.setString(7, bankEmail);
            proc.setString(8, bankFax);            
            proc.setString(9, bankPhone);            
            proc.setString(10, bankAdd);            
            proc.setString(11, bankBranch);            
            proc.setString(12, bankName);            
            proc.registerOutParameter(13, java.sql.Types.NUMERIC);
            
            proc.execute();
            
            status = proc.getInt(13);
            
            proc.close();
            connection.close();
            return status;
        } catch (SQLException ex) {
            Logger.getLogger(BankDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return status;
    }
    
    public int updateBank(int bankId, String bankName, String bankBranch, String bankAdd, String bankPhone, String bankFax, String bankEmail, double openDebit, double openCredit, String bankIdNo, int accId, int userId, String accType){
        int status = 0;
        try {
            if(connection == null || connection.isClosed())
                connection = DBHandler.connect();
            CallableStatement proc = connection.prepareCall("{ call \"update_banks\" ( ?,?,?,?,?,?,?,?,?,?,?,?,?,? ) } ");
            proc.setInt(1, bankId);
            proc.setString(2, accType);
            proc.setInt(3, userId);
            proc.setInt(4, accId);
            proc.setString(5, bankIdNo);
            proc.setDouble(6, openCredit);
            proc.setDouble(7, openDebit);
            proc.setString(8, bankEmail);
            proc.setString(9, bankFax);            
            proc.setString(10, bankPhone);            
            proc.setString(11, bankAdd);            
            proc.setString(12, bankBranch);            
            proc.setString(13, bankName);            
            proc.registerOutParameter(14, java.sql.Types.NUMERIC);
            
            proc.execute();
            
            status = proc.getInt(14);
            
            proc.close();
            connection.close();
            return status;
        } catch (SQLException ex) {
            Logger.getLogger(BankDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return status;
    }
    
    public int deleteBank(int bankId){
  int status = 0;
        try {
            if(connection == null || connection.isClosed())
                connection = DBHandler.connect();
            CallableStatement proc = connection.prepareCall("{ call \"delete_banks\" ( ?,? ) } ");
            proc.setInt(1, bankId);    
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
    
    public Banks getBankById(int bankId){
        Banks b = null;
          try {
            if(connection == null || connection.isClosed())
                connection = DBHandler.connect();
            CallableStatement proc = connection.prepareCall("{ call \"get_banks_ById\" ( ?,? ) } ");
            proc.setInt(1, bankId);    
            proc.registerOutParameter(2, OracleTypes.CURSOR);
            
            proc.execute();
              ResultSet r =(ResultSet) proc.getObject(2);
              
              if(r.next()){
                  b = new Banks();
                  b.setAccType(r.getString("ACC_TYPE"));
                  b.setBankAddress(r.getString("BANK_ADDRESS"));
                  b.setBankBranch(r.getString("BANK_BRANCH"));
                  b.setBankEmail(r.getString("BANK_EMAIL"));
                  b.setBankFax(r.getString("BANK_FAX"));
                  b.setBankId(new BigDecimal(bankId));
                  b.setBankIdNumber(r.getString("BANK_ID_NUMBER"));
                  b.setBankName(r.getString("BANK_NAME"));
                  b.setBankPhone(r.getString("BANK_PHONE"));
                  ChartDAO chartDAO = new ChartDAO();
                  Charts chart = chartDAO.getChartById(r.getInt("ACC_ID"));
                  b.setCharts(chart);
                  b.setOpenCredit(r.getDouble("OPEN_CREDIT"));
                  b.setOpenDebit(r.getDouble("OPEN_DEBIT"));
                  UserDAO userDAO = new UserDAO();
                  Users user = userDAO.getUserById(r.getInt("USER_ID"));
                  b.setUsers(user);
                  
              }
            proc.close();
            connection.close();
            return b;
        } catch (SQLException ex) {
            Logger.getLogger(BankDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return b;
    }
    
    public List<Banks> getAllBanks(){
        List banks = null;
        Banks b = null;
         try {
            if(connection == null || connection.isClosed())
                connection = DBHandler.connect();
            CallableStatement proc = connection.prepareCall("{ call \"get_all_from_banks\" ( ? ) } ");
            proc.registerOutParameter(1, OracleTypes.CURSOR);
            
            proc.execute();
              ResultSet r =(ResultSet) proc.getObject(1);
              banks = new ArrayList();
              while(r.next()){
                  b = new Banks();
                  b.setAccType(r.getString("ACC_TYPE"));
                  b.setBankAddress(r.getString("BANK_ADDRESS"));
                  b.setBankBranch(r.getString("BANK_BRANCH"));
                  b.setBankEmail(r.getString("BANK_EMAIL"));
                  b.setBankFax(r.getString("BANK_FAX"));
                  b.setBankId(new BigDecimal(r.getInt("BANK_ID")));
                  b.setBankIdNumber(r.getString("BANK_ID_NUMBER"));
                  b.setBankName(r.getString("BANK_NAME"));
                  b.setBankPhone(r.getString("BANK_PHONE"));
                  ChartDAO chartDAO = new ChartDAO();
                  Charts chart = chartDAO.getChartById(r.getInt("ACC_ID"));
                  b.setCharts(chart);
                  b.setOpenCredit(r.getDouble("OPEN_CREDIT"));
                  b.setOpenDebit(r.getDouble("OPEN_DEBIT"));
                  UserDAO userDAO = new UserDAO();
                  Users user = userDAO.getUserById(r.getInt("USER_ID"));
                  b.setUsers(user);
                  banks.add(b);
              }
            proc.close();
            connection.close();
            return banks;
        } catch (SQLException ex) {
            Logger.getLogger(BankDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return banks;
    }
}
