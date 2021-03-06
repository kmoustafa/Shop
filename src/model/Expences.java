package model;
// Generated Nov 14, 2014 12:02:34 AM by Hibernate Tools 3.6.0


import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

/**
 * Expences generated by hbm2java
 */
public class Expences  implements java.io.Serializable {


     private BigDecimal expenceId;
     private Users users;
     private Charts charts;
     private String expenceName;
     private Set transactionses = new HashSet(0);

    public Expences() {
    }

	
    public Expences(BigDecimal expenceId) {
        this.expenceId = expenceId;
    }
    public Expences(BigDecimal expenceId, Users users, Charts charts, String expenceName, Set transactionses) {
       this.expenceId = expenceId;
       this.users = users;
       this.charts = charts;
       this.expenceName = expenceName;
       this.transactionses = transactionses;
    }
   
    public BigDecimal getExpenceId() {
        return this.expenceId;
    }
    
    public void setExpenceId(BigDecimal expenceId) {
        this.expenceId = expenceId;
    }
    public Users getUsers() {
        return this.users;
    }
    
    public void setUsers(Users users) {
        this.users = users;
    }
    public Charts getCharts() {
        return this.charts;
    }
    
    public void setCharts(Charts charts) {
        this.charts = charts;
    }
    public String getExpenceName() {
        return this.expenceName;
    }
    
    public void setExpenceName(String expenceName) {
        this.expenceName = expenceName;
    }
    public Set getTransactionses() {
        return this.transactionses;
    }
    
    public void setTransactionses(Set transactionses) {
        this.transactionses = transactionses;
    }




}


