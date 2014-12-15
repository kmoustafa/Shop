package model;
// Generated Nov 14, 2014 12:02:34 AM by Hibernate Tools 3.6.0


import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

/**
 * Sizes generated by hbm2java
 */
public class Sizes  implements java.io.Serializable {


     private BigDecimal sizeId;
     private String sizeName;
     private Set transactionDetailses = new HashSet(0);
     private Set itemsSerialses = new HashSet(0);

    public Sizes() {
    }

	
    public Sizes(BigDecimal sizeId) {
        this.sizeId = sizeId;
    }
    public Sizes(BigDecimal sizeId, String sizeName, Set transactionDetailses, Set itemsSerialses) {
       this.sizeId = sizeId;
       this.sizeName = sizeName;
       this.transactionDetailses = transactionDetailses;
       this.itemsSerialses = itemsSerialses;
    }
   
    public BigDecimal getSizeId() {
        return this.sizeId;
    }
    
    public void setSizeId(BigDecimal sizeId) {
        this.sizeId = sizeId;
    }
    public String getSizeName() {
        return this.sizeName;
    }
    
    public void setSizeName(String sizeName) {
        this.sizeName = sizeName;
    }
    public Set getTransactionDetailses() {
        return this.transactionDetailses;
    }
    
    public void setTransactionDetailses(Set transactionDetailses) {
        this.transactionDetailses = transactionDetailses;
    }
    public Set getItemsSerialses() {
        return this.itemsSerialses;
    }
    
    public void setItemsSerialses(Set itemsSerialses) {
        this.itemsSerialses = itemsSerialses;
    }




}


