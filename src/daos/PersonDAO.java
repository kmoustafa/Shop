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
import model.Department;
import model.Persons;
import model.PersonsId;
import model.Users;
import model.Zone;
import oracle.jdbc.OracleTypes;
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
    
    public int insertPerson(String personEmail, int userId, int zoneId, int deptId, int accId, int transType, String contactPhone, String contactName, double maxBalance, double opencredit, double opendebit, String notes,
            String personAdd, String personPhone2, String personPhone, String companyName, String personName, int personType, int personId){
        int status = 0;
        
         try {
            if(connection == null || connection.isClosed())
                connection = DBHandler.connect();
            CallableStatement proc = connection.prepareCall("{ call \"insert_persons\" ( ?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,? ) } ");
            proc.setString(1, personEmail);
            if(userId != 0)
                proc.setInt(2, userId );
            else
                proc.setNull(2, OracleTypes.NULL);
            if( zoneId != 0)
                proc.setInt(3, zoneId);
            else
                proc.setNull(3, OracleTypes.NULL);            
            if(deptId != 0)
                proc.setInt(4, deptId);
            else
                proc.setNull(4, OracleTypes.NULL);            
            if(accId != 0)
                proc.setInt(5, accId);
            else
                proc.setNull(5, OracleTypes.NULL);            
            proc.setInt(6, transType);
            proc.setString(7, contactPhone);
            proc.setString(8, contactName);
            proc.setDouble(9, maxBalance);            
            proc.setDouble(10, opencredit);            
            proc.setDouble(11, opendebit);            
            proc.setString(12, notes);            
            proc.setString(13, personAdd);
            proc.setString(14, personPhone2);
            proc.setString(15, personPhone);
            proc.setString(16, companyName);
            proc.setString(17, personName);
            proc.setInt(18, personType);
            proc.setInt(19, personId);
            proc.registerOutParameter(20, java.sql.Types.NUMERIC);
            
            proc.execute();
            
            status = proc.getInt(20);
            
            proc.close();
            connection.close();
            return status;
        } catch (SQLException ex) {
            Logger.getLogger(PersonDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return status;
    }
    
    public int updatePerson(int personId, String personEmail, int userId, int zoneId, int deptId, int accId, int transType, String contactPhone, String contactName, double maxBalance, double opencredit, double opendebit, String notes,
            String personAdd, String personPhone2, String personPhone, String companyName, String personName, int personType){
        int status = 0;
        
         try {
            if(connection == null || connection.isClosed())
                connection = DBHandler.connect();
            CallableStatement proc = connection.prepareCall("{ call \"update_persons\" ( ?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,? ) } ");
            proc.setInt(1, personId);
            proc.setString(2, personEmail);
            proc.setInt(3, userId );
            proc.setInt(4, zoneId);
            proc.setInt(5, deptId);
            proc.setInt(6, accId);
            proc.setInt(7, transType);
            proc.setString(8, contactPhone);
            proc.setString(9, contactName);
            proc.setDouble(10, maxBalance);            
            proc.setDouble(11, opencredit);            
            proc.setDouble(12, opendebit);            
            proc.setString(13, notes);            
            proc.setString(14, personAdd);
            proc.setString(15, personPhone2);
            proc.setString(16, personPhone);
            proc.setString(17, companyName);
            proc.setString(18, personName);
            proc.setInt(19, personType);
            proc.registerOutParameter(20, java.sql.Types.NUMERIC);
            
            proc.execute();
            
            status = proc.getInt(20);
            
            proc.close();
            connection.close();
            return status;
        } catch (SQLException ex) {
            Logger.getLogger(PersonDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return status;
    }
    
    public int deletePerson(int personId){
        int status = 0;
                 try {
            if(connection == null || connection.isClosed())
                connection = DBHandler.connect();
            CallableStatement proc = connection.prepareCall("{ call \"delete_persons\" ( ?,? ) } ");
            proc.setInt(1, personId);
            proc.registerOutParameter(2, java.sql.Types.NUMERIC);
            
            proc.execute();
            
            status = proc.getInt(2);
            
            proc.close();
            connection.close();
            return status;
        } catch (SQLException ex) {
            Logger.getLogger(PersonDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return status;
    }
    
    public Persons getPersonById(int personId){
        Persons b = null;
              try {
            if(connection == null || connection.isClosed())
                connection = DBHandler.connect();
            CallableStatement proc = connection.prepareCall("{ call \"get_persons_ById\" ( ?,? ) } ");
            proc.setInt(1, personId);    
            proc.registerOutParameter(2, OracleTypes.CURSOR);            
            proc.execute();
              ResultSet r =(ResultSet) proc.getObject(2);
              
              if(r.next()){
                  b = new Persons();
                  b.setCompanyName(r.getString("COMPANY_NAME"));
                  b.setContactName(r.getString("CONTACT_NAME"));
                  b.setContactPhone(r.getString("CONTACT_PHONE"));
                  DepartmentDAO departmentDAO = new DepartmentDAO();
                  Department d = departmentDAO.getDepartmentById(r.getInt("DEPT_ID"));
                  b.setDepartment(d);
                  PersonsId personsId = new PersonsId(new BigDecimal(personId),
                  new BigDecimal(r.getInt("PERSON_TYPE")));
                  b.setId(personsId);
                  b.setMaxBalance(r.getDouble("MAX_BALANCE"));
                  b.setNotes(r.getString("NOTES"));
                  b.setOpenCredit(r.getDouble("OPEN_CREDIT"));
                  b.setOpenDebit(r.getDouble("OPEN_DEBIT"));                  
                  b.setPersonAddress(r.getString("PERSON_ADDRESS"));
                  b.setPersonName(r.getString("PERSON_NAME"));
                  b.setPersonPhone(r.getString("PERSON_PHONE"));
                  b.setPersonPhone2(r.getString("PERSON_PHONE2"));
                  b.setPesronEmail(r.getString("PESRON_EMAIL"));
                  b.setTransType(new BigDecimal(r.getInt("TRANS_TYPE")));
 
                  ZoneDAO zoneDAO = new ZoneDAO();
                  Zone z = zoneDAO.getZoneById(r.getInt("ZONE_ID"));
                  b.setZone(z);
                  UserDAO userDAO = new UserDAO();
                  Users user = userDAO.getUserById(r.getInt("USER_ID"));
                  b.setUsers(user);
              }
            proc.close();
            connection.close();
            return b;
        } catch (SQLException ex) {
            Logger.getLogger(PersonDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return b;
    }
    
    public List<Persons> getAllPersons(){
        List persons = null;
                Persons b = null;
              try {
            if(connection == null || connection.isClosed())
                connection = DBHandler.connect();
            CallableStatement proc = connection.prepareCall("{ call \"get_all_from_persons\" ( ? ) } ");  
            proc.registerOutParameter(1, OracleTypes.CURSOR);            
            proc.execute();
              ResultSet r =(ResultSet) proc.getObject(1);
              persons = new ArrayList();
              while(r.next()){
                  b = new Persons();
                  b.setCompanyName(r.getString("COMPANY_NAME"));
                  b.setContactName(r.getString("CONTACT_NAME"));
                  b.setContactPhone(r.getString("CONTACT_PHONE"));
                  DepartmentDAO departmentDAO = new DepartmentDAO();
                  Department d = departmentDAO.getDepartmentById(r.getInt("DEPT_ID"));
                  b.setDepartment(d);
                  PersonsId personsId = new PersonsId(new BigDecimal(r.getInt("PERSON_ID")),
                  new BigDecimal(r.getInt("PERSON_TYPE")));
                  b.setId(personsId);
                  b.setMaxBalance(r.getDouble("MAX_BALANCE"));
                  b.setNotes(r.getString("NOTES"));
                  b.setOpenCredit(r.getDouble("OPEN_CREDIT"));
                  b.setOpenDebit(r.getDouble("OPEN_DEBIT"));                  
                  b.setPersonAddress(r.getString("PERSON_ADDRESS"));
                  b.setPersonName(r.getString("PERSON_NAME"));
                  b.setPersonPhone(r.getString("PERSON_PHONE"));
                  b.setPersonPhone2(r.getString("PERSON_PHONE2"));
                  b.setPesronEmail(r.getString("PESRON_EMAIL"));
                  b.setTransType(new BigDecimal(r.getInt("TRANS_TYPE")));
 
                  ZoneDAO zoneDAO = new ZoneDAO();
                  Zone z = zoneDAO.getZoneById(r.getInt("ZONE_ID"));
                  b.setZone(z);
                  UserDAO userDAO = new UserDAO();
                  Users user = userDAO.getUserById(r.getInt("USER_ID"));
                  b.setUsers(user);
                  persons.add(b);
              }
            proc.close();
            connection.close();
            return persons;
        } catch (SQLException ex) {
            Logger.getLogger(PersonDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return persons;
    }
}
