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
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Banks;
import model.Charts;
import model.Employees;
import model.Users;
import oracle.jdbc.OracleTypes;
import utils.DBHandler;

/**
 *
 * @author Kareem.Moustafa
 */
public class EmployeeDAO {
    
    Connection connection;
    
    public EmployeeDAO(){
        connection = DBHandler.connect();
    }
    
    public int insertEmployee(Date hireDate, String info, int userId, int accId, double commision, double salary, String empPhone2, String empPhone1, String name, int empId){
        int status = 0;
        
         try {
            if(connection == null || connection.isClosed())
                connection = DBHandler.connect();
            CallableStatement proc = connection.prepareCall("{ call \"insert_employees\" ( ?,?,?,?,?,?,?,?,?,?,? ) } ");
            proc.setDate(1, new java.sql.Date(hireDate.getTime()));
            proc.setString(2, info);
            proc.setInt(3, userId);
            if(accId != 0)
                proc.setInt(4, accId);
            else
                proc.setNull(4, OracleTypes.NULL);
            proc.setDouble(5, commision);
            proc.setDouble(6, salary);
            proc.setString(7, empPhone2);
            proc.setString(8, empPhone2);            
            proc.setString(9, name);            
           proc.setInt(10, empId);
            proc.registerOutParameter(11, java.sql.Types.NUMERIC);
            
            proc.execute();
            
            status = proc.getInt(11);
            
            proc.close();
            connection.close();
            return status;
        } catch (SQLException ex) {
            Logger.getLogger(EmployeeDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return status;
    }
    
    public int updateEmployee(int empId,Date hireDate, String info, int userId, int accId, double commision, double salary, String empPhone2, String empPhone1, String name){
        int status = 0;
                 try {
            if(connection == null || connection.isClosed())
                connection = DBHandler.connect();
            CallableStatement proc = connection.prepareCall("{ call \"update_employees\" ( ?,?,?,?,?,?,?,?,?,?,? ) } ");
           proc.setInt(1, empId);
            proc.setDate(2, new java.sql.Date(hireDate.getTime()));
            proc.setString(3, info);
            proc.setInt(4, userId);
            if(accId != 0)
                proc.setInt(5, accId);
            else
                proc.setNull(5, OracleTypes.NULL);
            proc.setDouble(6, commision);
            proc.setDouble(7, salary);
            proc.setString(8, empPhone2);
            proc.setString(9, empPhone2);            
            proc.setString(10, name);            
           
            proc.registerOutParameter(11, java.sql.Types.NUMERIC);
            
            proc.execute();
            
            status = proc.getInt(11);
            
            proc.close();
            connection.close();
            return status;
        } catch (SQLException ex) {
            Logger.getLogger(EmployeeDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return status;
    }
    
    public int deleteEmployee(int empId){
        int status = 0;
                         try {
            if(connection == null || connection.isClosed())
                connection = DBHandler.connect();
            CallableStatement proc = connection.prepareCall("{ call \"delete_employees\" ( ?,? ) } ");
           proc.setInt(1, empId);
           
           
            proc.registerOutParameter(2, java.sql.Types.NUMERIC);
            
            proc.execute();
            
            status = proc.getInt(2);
            
            proc.close();
            connection.close();
            return status;
        } catch (SQLException ex) {
            Logger.getLogger(EmployeeDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return status;
    }
    
    public Employees getEmployeeById(int empId){
        Employees b = null;
          try {
            if(connection == null || connection.isClosed())
                connection = DBHandler.connect();
            CallableStatement proc = connection.prepareCall("{ call \"get_employees_ById\" ( ?,? ) } ");
            proc.setInt(1, empId);    
            proc.registerOutParameter(2, OracleTypes.CURSOR);
            
            proc.execute();
              ResultSet r =(ResultSet) proc.getObject(2);
              
              if(r.next()){
                  b = new Employees();
                  b.setCommision(r.getDouble("COMMISION"));
                  b.setEmpId(new BigDecimal(empId));
                  b.setEmpName(r.getString("EMP_NAME"));
                  b.setEmpPhone(r.getString("EMP_PHONE"));
                  b.setEmpPhone2(r.getString("EMP_PHONE2"));
                  b.setHireDate(r.getDate("HIRE_DATE"));
                  b.setInfo(r.getString("INFO"));
                  b.setSalary(r.getDouble("SALARY"));                  
                  UserDAO userDAO = new UserDAO();
                  Users user = userDAO.getUserById(r.getInt("USER_ID"));
                  b.setUsers(user);
                  
              }
            proc.close();
            connection.close();
            return b;
        } catch (SQLException ex) {
            Logger.getLogger(EmployeeDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return b;
    }
    
    public List<Employees> getAllEmployees(){
        List emps = null;
        Employees b = null;
                try {
            if(connection == null || connection.isClosed())
                connection = DBHandler.connect();
            CallableStatement proc = connection.prepareCall("{ call \"get_all_from_employees\" ( ? ) } ");
 
            proc.registerOutParameter(1, OracleTypes.CURSOR);
            
            proc.execute();
              ResultSet r =(ResultSet) proc.getObject(1);
              emps = new ArrayList();
              while(r.next()){
                  b = new Employees();
                  b.setCommision(r.getDouble("COMMISION"));
                  b.setEmpId(new BigDecimal(r.getInt("EMP_ID")));
                  b.setEmpName(r.getString("EMP_NAME"));
                  b.setEmpPhone(r.getString("EMP_PHONE"));
                  b.setEmpPhone2(r.getString("EMP_PHONE2"));
                  b.setHireDate(r.getDate("HIRE_DATE"));
                  b.setInfo(r.getString("INFO"));
                   ChartDAO chartDAO = new ChartDAO();
                  Charts chart = chartDAO.getChartById(r.getInt("ACC_ID"));
                  b.setCharts(chart);
                  b.setSalary(r.getDouble("SALARY"));                  
                  UserDAO userDAO = new UserDAO();
                  Users user = userDAO.getUserById(r.getInt("USER_ID"));
                  b.setUsers(user);
                  emps.add(b);
              }
            proc.close();
            connection.close();
            return emps;
        } catch (SQLException ex) {
            Logger.getLogger(EmployeeDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return emps;
    }
         public int getLastIndex(){
        int lastIndex = 0;
            try {
            if(connection == null || connection.isClosed())
                connection = DBHandler.connect();
                Statement proc = connection.createStatement();
                
            
                ResultSet r =      proc.executeQuery("SELECT MAX(EMP_ID) AS NEXTVAL FROM EMPLOYEES");
                  
              if(r.next()){
                  
                  lastIndex = r.getInt("NEXTVAL");
                  
              }
            proc.close();
            connection.close();
        } catch (SQLException ex) {
            Logger.getLogger(EmployeeDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lastIndex;
    }
    
}
