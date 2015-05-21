/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package daos;

import java.math.BigDecimal;
import java.math.BigInteger;
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
import model.Employees;
import model.Expences;
import model.ItemsSerials;
import model.Persons;
import model.PortfolioDetails;
import model.Sizes;
import model.Stores;
import model.TransactionDetails;
import model.Transactions;
import model.TransactionsId;
import model.Users;
import oracle.jdbc.OracleTypes;
import utils.DBHandler;

/**
 *
 * @author Kareem.Moustafa
 */
public class TransactionDAO {
    
    Connection connection;
    
    public TransactionDAO(){
        connection = DBHandler.connect();
    }
    
    public int insertTransaction(int transNo, int transType, int userId, int expenseId, int empId, int storeId, int bankId, int personType, int personId, int taxValue, int taxPercent,
            String transNotes, String transVisa, int transDiscValue, int transDiscPercent, java.sql.Date transDate, int transWSID, String tarnsAttach){
        int status = 0;
          try {
            if(connection == null || connection.isClosed())
                connection = DBHandler.connect();
            CallableStatement proc = connection.prepareCall("{ call \"insert_transaction_details\" ( ?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,? ) } ");

            proc.setInt(1, userId);
            proc.setInt(2, expenseId);
            proc.setInt(3, empId);
            proc.setInt(4, storeId);
            proc.setInt(5, bankId);
            proc.setInt(6, personType);
            proc.setInt(7, personId);
            proc.setInt(8, taxValue);            
            proc.setInt(9, taxPercent);                    
            proc.setString(10, transNotes);
            proc.setString(11, transVisa);
            proc.setInt(12, transDiscValue);
            proc.setInt(13, transDiscPercent);
            proc.setDate(14, transDate);
            proc.setInt(15, transWSID);            
            proc.setInt(16, transType);
            proc.setInt(17, transNo);
            proc.setString(18, tarnsAttach);
            proc.registerOutParameter(19, java.sql.Types.NUMERIC);
            
            proc.execute();
            
            status = proc.getInt(19);
            
            proc.close();
            connection.close();
            return status;
        } catch (SQLException ex) {
            Logger.getLogger(TransactionDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return status;
    }
    
    public int updateTransaction(int transNo, int transType, int userId, int expenseId, int empId, int storeId, int bankId, int personType, int personId, int taxValue, int taxPercent,
            String transNotes, String transVisa, int transDiscValue, int transDiscPercent, java.sql.Date transDate, int transWSID, String transAttach){
        int status = 0;
                  try {
            if(connection == null || connection.isClosed())
                connection = DBHandler.connect();
            CallableStatement proc = connection.prepareCall("{ call \"update_transactions\" ( ?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,? ) } ");

            proc.setInt(1, userId);
            proc.setInt(2, expenseId);
            proc.setInt(3, empId);
            proc.setInt(4, storeId);
            proc.setInt(5, bankId);
            proc.setInt(6, personType);
            proc.setInt(7, personId);
            proc.setInt(8, taxValue);            
            proc.setInt(9, taxPercent);                    
            proc.setString(10, transNotes);
            proc.setString(11, transVisa);
            proc.setInt(12, transDiscValue);
            proc.setInt(13, transDiscPercent);
            proc.setDate(14, transDate);
            proc.setInt(15, transWSID);            
            proc.setInt(16, transType);
            proc.setInt(17, transNo);
            proc.setString(18,transAttach);
            proc.registerOutParameter(19, java.sql.Types.NUMERIC);
            
            proc.execute();
            
            status = proc.getInt(19);
            
            proc.close();
            connection.close();
            return status;
        } catch (SQLException ex) {
            Logger.getLogger(TransactionDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return status;
    }
    
    public int deleteTransaction(int transNo, int transType){
        int status = 0;
                          try {
            if(connection == null || connection.isClosed())
                connection = DBHandler.connect();
            CallableStatement proc = connection.prepareCall("{ call \"delete_transactions\" ( ?,?,? ) } ");
          
            proc.setInt(2, transType);
            proc.setInt(1, transNo);
            proc.registerOutParameter(3, java.sql.Types.NUMERIC);
            
            proc.execute();
            
            status = proc.getInt(3);
            
            proc.close();
            connection.close();
            return status;
        } catch (SQLException ex) {
            Logger.getLogger(TransactionDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return status;
    }
    
    public Transactions getTransactionById(int transId, int transType){
        Transactions b = null;
                try {
            if(connection == null || connection.isClosed())
                connection = DBHandler.connect();
            CallableStatement proc = connection.prepareCall("{ call \"get_transactions_ById\" ( ?,?,? ) } ");
            proc.setInt(1, transId);  
            proc.setInt(2, transType);
            proc.registerOutParameter(3, OracleTypes.CURSOR);
            
            proc.execute();
              ResultSet r =(ResultSet) proc.getObject(3);
              
              if(r.next()){
                  b = new Transactions();
                  BankDAO bankDAO = new BankDAO();
                  Banks bn = bankDAO.getBankById(r.getInt("BANK_ID"));
                  b.setBanks(bn);
                  EmployeeDAO employeeDAO = new EmployeeDAO();
                  Employees e = employeeDAO.getEmployeeById(r.getInt("EMP_ID"));
                  b.setEmployees(e);
                  ExpenceDAO expenceDAO = new ExpenceDAO();
                  Expences e1 = expenceDAO.getExpenceById(r.getInt("EXPENCE_ID"));
                  b.setExpences(e1);
                  model.TransactionsId transactionsId = new TransactionsId(new BigDecimal(transId),new BigDecimal(transType));
                  b.setId(transactionsId);
                  PersonDAO personDAO = new PersonDAO();
                  Persons p = personDAO.getPersonById(r.getInt("PERSON_ID"));
                  b.setPersons(p);
                  StoreDAO storeDAO = new StoreDAO();
                  Stores s = storeDAO.getStoreById(r.getInt("STORE_ID"));
                  b.setStores(s);
                  b.setTaxPercent(new BigDecimal(r.getInt("TAX_PERCENT")));
                  b.setTaxValue(new BigDecimal(r.getInt("TAX_VALUE")));
                  b.setTransDate(r.getDate("TRANS_DATE"));
                  b.setTransDiscPercent(new BigDecimal(r.getInt("TRANS_DISC_PERCENT")));
                  b.setTransDiscValue(new BigDecimal(r.getInt("TRANS_DISC_VALUE")));
                  b.setTransNote(r.getString("TRANS_NOTE"));
                  b.setTransVisa(r.getString("TRANS_VISA"));
                  b.setTransAttach(r.getString("TRANS_ATTACH"));
                  
                  b.setTransWsid(new BigDecimal(r.getInt("TRANS_WSID")));
                UserDAO userDAO = new UserDAO();
                  Users user = userDAO.getUserById(r.getInt("USER_ID"));
                  b.setUsers(user);
              }
            proc.close();
            connection.close();
            return b;
        } catch (SQLException ex) {
            Logger.getLogger(TransactionDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return b;
    }
    
    public List<Transactions> getAllTransactions(){
        List trans = null;
                Transactions b = null;
                try {
            if(connection == null || connection.isClosed())
                connection = DBHandler.connect();
            CallableStatement proc = connection.prepareCall("{ call \"get_all_from_transactions\" ( ? ) } ");
     
            proc.registerOutParameter(1, OracleTypes.CURSOR);
            
            proc.execute();
              ResultSet r =(ResultSet) proc.getObject(1);
              trans = new ArrayList();
              while(r.next()){
                  b = new Transactions();
                  BankDAO bankDAO = new BankDAO();
                  Banks bn = bankDAO.getBankById(r.getInt("BANK_ID"));
                  b.setBanks(bn);
                  EmployeeDAO employeeDAO = new EmployeeDAO();
                  Employees e = employeeDAO.getEmployeeById(r.getInt("EMP_ID"));
                  b.setEmployees(e);
                  ExpenceDAO expenceDAO = new ExpenceDAO();
                  Expences e1 = expenceDAO.getExpenceById(r.getInt("EXPENCE_ID"));
                  b.setExpences(e1);
                  model.TransactionsId transactionsId = new TransactionsId(new BigDecimal(r.getInt("TRANS_NO")),new BigDecimal(r.getInt("TRANS_TYPE")));
                  b.setId(transactionsId);
                  PersonDAO personDAO = new PersonDAO();
                  Persons p = personDAO.getPersonById(r.getInt("PERSON_ID"));
                  b.setPersons(p);
                  StoreDAO storeDAO = new StoreDAO();
                  Stores s = storeDAO.getStoreById(r.getInt("STORE_ID"));
                  b.setStores(s);
                  b.setTaxPercent(new BigDecimal(r.getInt("TAX_PERCENT")));
                  b.setTaxValue(new BigDecimal(r.getInt("TAX_VALUE")));
                  b.setTransDate(r.getDate("TRANS_DATE"));
                  b.setTransDiscPercent(new BigDecimal(r.getInt("TRANS_DISC_PERCENT")));
                  b.setTransDiscValue(new BigDecimal(r.getInt("TRANS_DISC_VALUE")));
                  b.setTransNote(r.getString("TRANS_NOTE"));
                  b.setTransVisa(r.getString("TRANS_VISA"));
                                    b.setTransAttach(r.getString("TRANS_ATTACH"));

                  b.setTransWsid(new BigDecimal(r.getInt("TRANS_WSID")));
                UserDAO userDAO = new UserDAO();
                  Users user = userDAO.getUserById(r.getInt("USER_ID"));
                  b.setUsers(user);
                  trans.add(b);
              }
            proc.close();
            connection.close();
            return trans;
        } catch (SQLException ex) {
            Logger.getLogger(TransactionDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return trans;
    }
}
