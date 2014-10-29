/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package daos;

import java.sql.Connection;
import java.util.List;
import model.Sections;
import utils.DBHandler;

/**
 *
 * @author Kareem.Moustafa
 */
public class SectionDAO {
    
    Connection connection;
    
    public SectionDAO(){
        connection = DBHandler.connect();
    }
    
    public int insertSection(){
        int status = 0;
        
        
        return status;
    }
    
    public int updateSection(){
        int status = 0;
        
        return status;
    }
    
    public int deleteSection(){
        int status = 0;
        
        return status;
    }
    
    public Sections getSectionById(){
        Sections b = null;
        
        return b;
    }
    
    public List<Sections> getAllSections(){
        List sections = null;
        
        return sections;
    }
}
