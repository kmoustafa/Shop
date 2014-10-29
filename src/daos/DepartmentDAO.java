/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package daos;

import java.sql.Connection;
import java.util.List;
import model.Department;
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
    
    public int insertDepartment(){
        int status = 0;
        
        
        return status;
    }
    
    public int updateDepartment(){
        int status = 0;
        
        return status;
    }
    
    public int deleteDepartment(){
        int status = 0;
        
        return status;
    }
    
    public Department getDepartmentById(){
        Department b = null;
        
        return b;
    }
    
    public List<Department> getAllDepartments(){
        List depts = null;
        
        return depts;
    }
}
