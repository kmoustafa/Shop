package model;
// Generated Nov 14, 2014 12:02:34 AM by Hibernate Tools 3.6.0


import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

/**
 * ItemsSerials generated by hbm2java
 */
public class ItemsSerials  implements java.io.Serializable {


     private BigDecimal itemDId;
     private Sizes sizes;
     private Colors colors;
     private Items items;
     private Double cost;
     private Double stCost;
     private Double sales1;
     private Double sales2;
     private Double sales3;
     private BigDecimal requirQty;
     private Double percentage;
     private Set transactionDetailses = new HashSet(0);
     private String barCode;

    public ItemsSerials() {
    }

	
    public ItemsSerials(BigDecimal itemDId) {
        this.itemDId = itemDId;
    }
    public ItemsSerials(BigDecimal itemDId, Sizes sizes, Colors colors, Items items, Double cost, Double stCost, Double sales1, Double sales2, Double sales3, BigDecimal requirQty, Double percentage, Set transactionDetailses, String barCode) {
       this.itemDId = itemDId;
       this.sizes = sizes;
       this.colors = colors;
       this.items = items;
       this.cost = cost;
       this.stCost = stCost;
       this.sales1 = sales1;
       this.sales2 = sales2;
       this.sales3 = sales3;
       this.requirQty = requirQty;
       this.percentage = percentage;
       this.transactionDetailses = transactionDetailses;
       this.barCode = barCode;
    }
   
    public BigDecimal getItemDId() {
        return this.itemDId;
    }
    
    public void setItemDId(BigDecimal itemDId) {
        this.itemDId = itemDId;
    }
    public Sizes getSizes() {
        return this.sizes;
    }
    
    public void setSizes(Sizes sizes) {
        this.sizes = sizes;
    }
    public Colors getColors() {
        return this.colors;
    }
    
    public void setColors(Colors colors) {
        this.colors = colors;
    }
    public Items getItems() {
        return this.items;
    }
    
    public void setItems(Items items) {
        this.items = items;
    }
    public Double getCost() {
        return this.cost;
    }
    
    public void setCost(Double cost) {
        this.cost = cost;
    }
    public Double getStCost() {
        return this.stCost;
    }
    
    public void setStCost(Double stCost) {
        this.stCost = stCost;
    }
    public Double getSales1() {
        return this.sales1;
    }
    
    public void setSales1(Double sales1) {
        this.sales1 = sales1;
    }
    public Double getSales2() {
        return this.sales2;
    }
    
    public void setSales2(Double sales2) {
        this.sales2 = sales2;
    }
    public Double getSales3() {
        return this.sales3;
    }
    
    public void setSales3(Double sales3) {
        this.sales3 = sales3;
    }
    public BigDecimal getRequirQty() {
        return this.requirQty;
    }
    
    public void setRequirQty(BigDecimal requirQty) {
        this.requirQty = requirQty;
    }
    public Double getPercentage() {
        return this.percentage;
    }
    
    public void setPercentage(Double percentage) {
        this.percentage = percentage;
    }
    public Set getTransactionDetailses() {
        return this.transactionDetailses;
    }
    
    public void setTransactionDetailses(Set transactionDetailses) {
        this.transactionDetailses = transactionDetailses;
    }
    public String getBarCode() {
        return this.barCode;
    }
    
    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }



}


