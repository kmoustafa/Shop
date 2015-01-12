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
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Banks;
import model.Charts;
import model.CostCenter;
import model.Users;
import oracle.jdbc.OracleTypes;
import utils.DBHandler;

/**
 *
 * @author Kareem.Moustafa
 */
public class ChartDAO {
    
    Connection connection;
    
    public ChartDAO(){
        connection = DBHandler.connect();
    }
    
    public int insertChart(int accId, String accName, int userId, int costCenterId, int veiwMode, double openCredit, double openDebit, int rankAccount, int reportType, int debitOrCredit, String target, int isTarget){
        int status = 0;
         try {
            if(connection == null || connection.isClosed())
                connection = DBHandler.connect();
            CallableStatement proc = connection.prepareCall("{ call \"insert_charts\" ( ?,?,?,?,?,?,?,?,?,?,?,?,? ) } ");
            proc.setString(1, accName);
            proc.setInt(2, userId);
            if(costCenterId != 0)
                proc.setInt(3, costCenterId);
            else
                proc.setNull(3, OracleTypes.NULL);
            proc.setInt(4, veiwMode);
            proc.setDouble(5, openCredit);
            proc.setDouble(6, openDebit);
            proc.setInt(7, rankAccount);
            proc.setInt(8, reportType);            
            proc.setInt(9, debitOrCredit);            
            proc.setString(10, target);            
            proc.setInt(11, isTarget);     
            proc.setInt(12, accId);
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
    
    public int updateChart(int accId,String accName, int userId, int costCenterId, int veiwMode, double openCredit, double openDebit, int rankAccount, int reportType, int debitOrCredit, String target, int isTarget){
        int status = 0;
                 try {
            if(connection == null || connection.isClosed())
                connection = DBHandler.connect();
            CallableStatement proc = connection.prepareCall("{ call \"update_charts\" ( ?,?,?,?,?,?,?,?,?,?,?,?,? ) } ");
            proc.setInt(1, accId);
            proc.setString(2, accName);
            proc.setInt(3, userId);
            if(costCenterId != 0)
                proc.setInt(4, costCenterId);
            else
                proc.setNull(4, OracleTypes.NULL);
            proc.setInt(5, veiwMode);
            proc.setDouble(6, openCredit);
            proc.setDouble(7, openDebit);
            proc.setInt(8, rankAccount);
            proc.setInt(9, reportType);            
            proc.setInt(10, debitOrCredit);            
            proc.setString(11, target);            
            proc.setInt(12, isTarget);            
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
    
    public int deleteChart(int chartId){
        int status = 0;
         try {
            if(connection == null || connection.isClosed())
                connection = DBHandler.connect();
            CallableStatement proc = connection.prepareCall("{ call \"delete_charts\" ( ?,? ) } ");
            proc.setInt(1, chartId);    
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
    
    public Charts getChartById(int chartId){
        Charts b = null;
           try {
            if(connection == null || connection.isClosed())
                connection = DBHandler.connect();
            CallableStatement proc = connection.prepareCall("{ call \"get_charts_ById\" ( ?,? ) } ");
            proc.setInt(1, chartId);    
            proc.registerOutParameter(2, OracleTypes.CURSOR);
            
            proc.execute();
              ResultSet r =(ResultSet) proc.getObject(2);
              
              if(r.next()){
                  b = new Charts();
                  b.setAccId(new BigDecimal(chartId));
                  b.setAccName(r.getString("ACC_NAME"));
                  CostCenterDAO costCenterDAO = new CostCenterDAO();
                  CostCenter costCenter = costCenterDAO.getCostCenterById(r.getInt("COST_CENTER_ID"));
                  b.setCostCenter(costCenter);
                  b.setDebitOCredit(new BigDecimal(r.getInt("DEBIT_O_CREDIT")));
                  b.setIsTarget(new BigDecimal(r.getInt("IS_TARGET")));
                  b.setOpenCredt(r.getDouble("OPEN_CREDT"));
                  b.setOpenDebit(r.getDouble("OPEN_DEBIT"));
                  b.setRankAccount(new BigDecimal(r.getInt("RANK_ACCOUNT")));
                  b.setReportType(new BigDecimal(r.getInt("REPORT_TYPE")));
                  b.setTarget(r.getString("TARGET"));
                  UserDAO userDAO = new UserDAO();
                  Users user = userDAO.getUserById(r.getInt("USER_ID"));
                  b.setUsers(user);
                  b.setViewMode(new BigDecimal(r.getInt("VIEW_MODE")));
              }
            proc.close();
            connection.close();
            return b;
        } catch (SQLException ex) {
            Logger.getLogger(BankDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return b;
    }
    
    public List<Charts> getAllCharts(){
        List charts = null;
        Charts b = null;
         try {
            if(connection == null || connection.isClosed())
                connection = DBHandler.connect();
            CallableStatement proc = connection.prepareCall("{ call \"get_all_from_charts\" ( ? ) } ");
            proc.registerOutParameter(1, OracleTypes.CURSOR);
            
            proc.execute();
              ResultSet r =(ResultSet) proc.getObject(1);
              charts = new ArrayList();
              while(r.next()){
                  b = new Charts();
                  b.setAccId(new BigDecimal(r.getInt("ACC_ID")));
                  b.setAccName(r.getString("ACC_NAME"));
                  CostCenterDAO costCenterDAO = new CostCenterDAO();
                  CostCenter costCenter = costCenterDAO.getCostCenterById(r.getInt("COST_CENTER_ID"));
                  b.setCostCenter(costCenter);
                  b.setDebitOCredit(new BigDecimal(r.getInt("DEBIT_O_CREDIT")));
                  b.setIsTarget(new BigDecimal(r.getInt("IS_TARGET")));
                  b.setOpenCredt(r.getDouble("OPEN_CREDT"));
                  b.setOpenDebit(r.getDouble("OPEN_DEBIT"));
                  b.setRankAccount(new BigDecimal(r.getInt("RANK_ACCOUNT")));
                  b.setReportType(new BigDecimal(r.getInt("REPORT_TYPE")));
                  b.setTarget(r.getString("TARGET"));
                  UserDAO userDAO = new UserDAO();
                  Users user = userDAO.getUserById(r.getInt("USER_ID"));
                  b.setUsers(user);
                  b.setViewMode(new BigDecimal(r.getInt("VIEW_MODE")));
                  charts.add(b);
              }
            proc.close();
            connection.close();
            return charts;
        } catch (SQLException ex) {
            Logger.getLogger(BankDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return charts;
    }
    
    public int getNewId(int id){
        int status = 0;
        try {
            if(connection == null || connection.isClosed())
                connection = DBHandler.connect();
                Statement proc = connection.createStatement();
                ResultSet r = null;
                if(id > 0 && id <10){
                       r = proc.executeQuery("SELECT MAX(ACC_ID) AS NEXTVAL FROM CHARTS WHERE ACC_ID > 0 AND ACC_ID <10");

                }else if(id > 100 && id < 1000)
                       r = proc.executeQuery("SELECT MAX(ACC_ID) AS NEXTVAL FROM CHARTS WHERE ACC_ID > 100 AND ACC_ID < 1000 AND ACC_ID LIKE '"+(id/100)+"%'");
                else if (id > 10000 && id < 100000)
                       r = proc.executeQuery("SELECT MAX(ACC_ID) AS NEXTVAL FROM CHARTS WHERE ACC_ID > 10000 AND ACC_ID < 100000AND ACC_ID LIKE '"+(id/100)+"%'");
                else if ( id > 100000)
                       r = proc.executeQuery("SELECT MAX(ACC_ID) AS NEXTVAL FROM CHARTS WHERE ACC_ID > 100000 AND ACC_ID LIKE '"+(id/1000)+"%'");
              if(r.next()){
                  
                  status = r.getInt("NEXTVAL");
                  
              }
            proc.close();
            connection.close();
        } catch (SQLException ex) {
            Logger.getLogger(ZoneDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return status;
    }
}
