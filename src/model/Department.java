package model;
// Generated Nov 14, 2014 12:02:34 AM by Hibernate Tools 3.6.0


import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

/**
 * Department generated by hbm2java
 */
public class Department  implements java.io.Serializable {


     private BigDecimal deptId;
     private String deptName;
     private Set personses = new HashSet(0);

    public Department() {
    }

	
    public Department(BigDecimal deptId) {
        this.deptId = deptId;
    }
    public Department(BigDecimal deptId, String deptName, Set personses) {
       this.deptId = deptId;
       this.deptName = deptName;
       this.personses = personses;
    }
   
    public BigDecimal getDeptId() {
        return this.deptId;
    }
    
    public void setDeptId(BigDecimal deptId) {
        this.deptId = deptId;
    }
    public String getDeptName() {
        return this.deptName;
    }
    
    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }
    public Set getPersonses() {
        return this.personses;
    }
    
    public void setPersonses(Set personses) {
        this.personses = personses;
    }




}


