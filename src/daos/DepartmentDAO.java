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
import model.Department;
import oracle.jdbc.OracleTypes;
import utils.DBHandler;

/**
 *
 * @author Kareem.Moustafa
 */
public class DepartmentDAO {
    
    Connection connection;
    
    public DepartmentDAO(){
        connection = DBHandler.connect();
    }
    
    public int insertDepartment(int deptId, String deptName){
        int status = 0;
         try {
            if(connection == null || connection.isClosed())
                connection = DBHandler.connect();
            CallableStatement proc = connection.prepareCall("{ call \"insert_department\" ( ?,?,? ) } ");
            proc.setInt(1, deptId);
                      proc.setString(2, deptName);
            proc.registerOutParameter(3, java.sql.Types.NUMERIC);
            
            proc.execute();
            
            status = proc.getInt(3);
            
            proc.close();
            connection.close();
            return status;
        } catch (SQLException ex) {
            Logger.getLogger(DepartmentDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return status;
    }
    
    public int updateDepartment(int depdId, String deptName){
        int status = 0;
               try {
            if(connection == null || connection.isClosed())
                connection = DBHandler.connect();
            CallableStatement proc = connection.prepareCall("{ call \"update_department\" ( ?,?,? ) } ");
            proc.setInt(1, depdId);
            proc.setString(2, deptName);
          
            proc.registerOutParameter(3, java.sql.Types.NUMERIC);
            
            proc.execute();
            
            status = proc.getInt(3);
            
            proc.close();
            connection.close();
            return status;
        } catch (SQLException ex) {
            Logger.getLogger(DepartmentDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return status;
    }
    
    public int deleteDepartment(int deptId){
        int status = 0;
           try {
            if(connection == null || connection.isClosed())
                connection = DBHandler.connect();
            CallableStatement proc = connection.prepareCall("{ call \"delete_department\" ( ?,? ) } ");
            proc.setInt(1, deptId);    
            proc.registerOutParameter(2, java.sql.Types.NUMERIC);
            
            proc.execute();
            
            status = proc.getInt(2);
            
            proc.close();
            connection.close();
            return status;
        } catch (SQLException ex) {
            Logger.getLogger(DepartmentDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return status;
    }
    
    public Department getDepartmentById(int deptId){
        Department b = null;
         try {
            if(connection == null || connection.isClosed())
                connection = DBHandler.connect();
            CallableStatement proc = connection.prepareCall("{ call \"get_department_ById\" ( ?,? ) } ");
            proc.setInt(1, deptId);    
            proc.registerOutParameter(2, OracleTypes.CURSOR);
            
            proc.execute();
              ResultSet r =(ResultSet) proc.getObject(2);
              
              if(r.next()){
                  b = new Department();
                  b.setDeptId(new BigDecimal(deptId));
                  b.setDeptName(r.getString("DEPT_NAME"));
                  
              }
            proc.close();
            connection.close();
            return b;
        } catch (SQLException ex) {
            Logger.getLogger(DepartmentDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return b;
    }
    
    public List<Department> getAllDepartments(){
        List depts = null;
        Department b = null;
              try {
            if(connection == null || connection.isClosed())
                connection = DBHandler.connect();
            CallableStatement proc = connection.prepareCall("{ call \"get_all_from_department\" ( ? ) } ");

            proc.registerOutParameter(1, OracleTypes.CURSOR);
            
            proc.execute();
              ResultSet r =(ResultSet) proc.getObject(1);
              depts = new ArrayList();
              while(r.next()){
                  b = new Department();
                  b.setDeptId(new BigDecimal(r.getInt("DEPT_ID")));
                  b.setDeptName(r.getString("DEPT_NAME"));
                  depts.add(b);
              }
            proc.close();
            connection.close();
            return depts;
        } catch (SQLException ex) {
            Logger.getLogger(BankDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return depts;
    }
         public int getLastIndex(){
        int lastIndex = 0;
            try {
            if(connection == null || connection.isClosed())
                connection = DBHandler.connect();
                Statement proc = connection.createStatement();
                
            
                ResultSet r =      proc.executeQuery("SELECT MAX(DEPT_ID) AS NEXTVAL FROM DEPARTMENT");
                  
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
