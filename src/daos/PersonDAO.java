/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package daos;

import java.sql.Connection;
import java.util.List;
import model.Persons;
import utils.DBHandler;

/**
 *
 * @author Kareem.Moustafa
 */
public class PersonDAO {
    
    Connection connection;
    
    public PersonDAO(){
        connection = DBHandler.connect();
    }
    
    public int insertPerson(){
        int status = 0;
        
        
        return status;
    }
    
    public int updatePerson(){
        int status = 0;
        
        return status;
    }
    
    public int deletePerson(){
        int status = 0;
        
        return status;
    }
    
    public Persons getPersonById(){
        Persons b = null;
        
        return b;
    }
    
    public List<Persons> getAllPersons(){
        List persons = null;
        
        return persons;
    }
}
