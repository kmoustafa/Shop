package model;
// Generated Nov 14, 2014 12:02:34 AM by Hibernate Tools 3.6.0


import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

/**
 * Types generated by hbm2java
 */
public class Types  implements java.io.Serializable {


     private BigDecimal typeId;
     private String typeName;
     private Set itemses = new HashSet(0);

    public Types() {
    }

	
    public Types(BigDecimal typeId) {
        this.typeId = typeId;
    }
    public Types(BigDecimal typeId, String typeName, Set itemses) {
       this.typeId = typeId;
       this.typeName = typeName;
       this.itemses = itemses;
    }
   
    public BigDecimal getTypeId() {
        return this.typeId;
    }
    
    public void setTypeId(BigDecimal typeId) {
        this.typeId = typeId;
    }
    public String getTypeName() {
        return this.typeName;
    }
    
    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }
    public Set getItemses() {
        return this.itemses;
    }
    
    public void setItemses(Set itemses) {
        this.itemses = itemses;
    }

    @Override
    public String toString() {
        return this.typeName; //To change body of generated methods, choose Tools | Templates.
    }




}


