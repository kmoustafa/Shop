/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package daos;

import java.sql.Connection;
import java.util.List;
import model.Employees;
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
    
    public int insertEmployee(){
        int status = 0;
        
        
        return status;
    }
    
    public int updateEmployee(){
        int status = 0;
        
        return status;
    }
    
    public int deleteEmployee(){
        int status = 0;
        
        return status;
    }
    
    public Employees getEmployeeById(){
        Employees b = null;
        
        return b;
    }
    
    public List<Employees> getAllEmployees(){
        List emps = null;
        
        return emps;
    }
}
