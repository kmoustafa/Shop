package model;
// Generated Nov 14, 2014 12:02:34 AM by Hibernate Tools 3.6.0


import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

/**
 * Sections generated by hbm2java
 */
public class Sections  implements java.io.Serializable {


     private BigDecimal sectionId;
     private String sectionName;
     private Set itemses = new HashSet(0);

    public Sections() {
    }

	
    public Sections(BigDecimal sectionId) {
        this.sectionId = sectionId;
    }
    public Sections(BigDecimal sectionId, String sectionName, Set itemses) {
       this.sectionId = sectionId;
       this.sectionName = sectionName;
       this.itemses = itemses;
    }
   
    public BigDecimal getSectionId() {
        return this.sectionId;
    }
    
    public void setSectionId(BigDecimal sectionId) {
        this.sectionId = sectionId;
    }
    public String getSectionName() {
        return this.sectionName;
    }
    
    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
    }
    public Set getItemses() {
        return this.itemses;
    }
    
    public void setItemses(Set itemses) {
        this.itemses = itemses;
    }

    @Override
    public String toString() {
        return this.sectionName; //To change body of generated methods, choose Tools | Templates.
    }




}


