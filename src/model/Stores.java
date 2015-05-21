package model;
// Generated Nov 14, 2014 12:02:34 AM by Hibernate Tools 3.6.0


import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

/**
 * Stores generated by hbm2java
 */
public class Stores  implements java.io.Serializable {


     private BigDecimal storeId;
     private Users users;
     private Employees employees;
     private String storeName;
     private String storeAddress;
     private String storePhone;
     private BigDecimal isMain;
     private Set transactionses = new HashSet(0);

    public Stores() {
    }

	
    public Stores(BigDecimal storeId) {
        this.storeId = storeId;
    }
    public Stores(BigDecimal storeId, Users users, Employees employees, String storeName, String storeAddress, String storePhone, BigDecimal isMain, Set transactionses) {
       this.storeId = storeId;
       this.users = users;
       this.employees = employees;
       this.storeName = storeName;
       this.storeAddress = storeAddress;
       this.storePhone = storePhone;
       this.isMain = isMain;
       this.transactionses = transactionses;
    }
   
    public BigDecimal getStoreId() {
        return this.storeId;
    }
    
    public void setStoreId(BigDecimal storeId) {
        this.storeId = storeId;
    }
    public Users getUsers() {
        return this.users;
    }
    
    public void setUsers(Users users) {
        this.users = users;
    }
    public Employees getEmployees() {
        return this.employees;
    }
    
    public void setEmployees(Employees employees) {
        this.employees = employees;
    }
    public String getStoreName() {
        return this.storeName;
    }
    
    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }
    public String getStoreAddress() {
        return this.storeAddress;
    }
    
    public void setStoreAddress(String storeAddress) {
        this.storeAddress = storeAddress;
    }
    public String getStorePhone() {
        return this.storePhone;
    }
    
    public void setStorePhone(String storePhone) {
        this.storePhone = storePhone;
    }
    public BigDecimal getIsMain() {
        return this.isMain;
    }
    
    public void setIsMain(BigDecimal isMain) {
        this.isMain = isMain;
    }
    public Set getTransactionses() {
        return this.transactionses;
    }
    
    public void setTransactionses(Set transactionses) {
        this.transactionses = transactionses;
    }

    @Override
    public String toString() {
        return this.storeName; //To change body of generated methods, choose Tools | Templates.
    }




}


