package model;
// Generated Nov 14, 2014 12:02:34 AM by Hibernate Tools 3.6.0


import java.math.BigDecimal;

/**
 * Companyinfo generated by hbm2java
 */
public class Companyinfo  implements java.io.Serializable {


     private BigDecimal id;
     private String FYear;
     private BigDecimal startMonth;
     private String treasury;
     private String bank;
     private String clients;
     private String sales;
     private String returnedSales;
     private String discSales;
     private String salesCost;
     private String salesTax;
     private String taxD;
     private String diffTreasury;
     private String charge;
     private String vendors;
     private String purchases;
     private String returnedPurchases;
     private String discPurchases;
     private String purchasesCost;
     private String goodsFrDuration;
     private String goodsLaDuration;
     private String expenses;
     private String revenue;

    public Companyinfo() {
    }

	
    public Companyinfo(BigDecimal id) {
        this.id = id;
    }
    public Companyinfo(BigDecimal id, String FYear, BigDecimal startMonth, String treasury, String bank, String clients, String sales, String returnedSales, String discSales, String salesCost, String salesTax, String taxD, String diffTreasury, String charge, String vendors, String purchases, String returnedPurchases, String discPurchases, String purchasesCost, String goodsFrDuration, String goodsLaDuration, String expenses, String revenue) {
       this.id = id;
       this.FYear = FYear;
       this.startMonth = startMonth;
       this.treasury = treasury;
       this.bank = bank;
       this.clients = clients;
       this.sales = sales;
       this.returnedSales = returnedSales;
       this.discSales = discSales;
       this.salesCost = salesCost;
       this.salesTax = salesTax;
       this.taxD = taxD;
       this.diffTreasury = diffTreasury;
       this.charge = charge;
       this.vendors = vendors;
       this.purchases = purchases;
       this.returnedPurchases = returnedPurchases;
       this.discPurchases = discPurchases;
       this.purchasesCost = purchasesCost;
       this.goodsFrDuration = goodsFrDuration;
       this.goodsLaDuration = goodsLaDuration;
       this.expenses = expenses;
       this.revenue = revenue;
    }
   
    public BigDecimal getId() {
        return this.id;
    }
    
    public void setId(BigDecimal id) {
        this.id = id;
    }
    public String getFYear() {
        return this.FYear;
    }
    
    public void setFYear(String FYear) {
        this.FYear = FYear;
    }
    public BigDecimal getStartMonth() {
        return this.startMonth;
    }
    
    public void setStartMonth(BigDecimal startMonth) {
        this.startMonth = startMonth;
    }
    public String getTreasury() {
        return this.treasury;
    }
    
    public void setTreasury(String treasury) {
        this.treasury = treasury;
    }
    public String getBank() {
        return this.bank;
    }
    
    public void setBank(String bank) {
        this.bank = bank;
    }
    public String getClients() {
        return this.clients;
    }
    
    public void setClients(String clients) {
        this.clients = clients;
    }
    public String getSales() {
        return this.sales;
    }
    
    public void setSales(String sales) {
        this.sales = sales;
    }
    public String getReturnedSales() {
        return this.returnedSales;
    }
    
    public void setReturnedSales(String returnedSales) {
        this.returnedSales = returnedSales;
    }
    public String getDiscSales() {
        return this.discSales;
    }
    
    public void setDiscSales(String discSales) {
        this.discSales = discSales;
    }
    public String getSalesCost() {
        return this.salesCost;
    }
    
    public void setSalesCost(String salesCost) {
        this.salesCost = salesCost;
    }
    public String getSalesTax() {
        return this.salesTax;
    }
    
    public void setSalesTax(String salesTax) {
        this.salesTax = salesTax;
    }
    public String getTaxD() {
        return this.taxD;
    }
    
    public void setTaxD(String taxD) {
        this.taxD = taxD;
    }
    public String getDiffTreasury() {
        return this.diffTreasury;
    }
    
    public void setDiffTreasury(String diffTreasury) {
        this.diffTreasury = diffTreasury;
    }
    public String getCharge() {
        return this.charge;
    }
    
    public void setCharge(String charge) {
        this.charge = charge;
    }
    public String getVendors() {
        return this.vendors;
    }
    
    public void setVendors(String vendors) {
        this.vendors = vendors;
    }
    public String getPurchases() {
        return this.purchases;
    }
    
    public void setPurchases(String purchases) {
        this.purchases = purchases;
    }
    public String getReturnedPurchases() {
        return this.returnedPurchases;
    }
    
    public void setReturnedPurchases(String returnedPurchases) {
        this.returnedPurchases = returnedPurchases;
    }
    public String getDiscPurchases() {
        return this.discPurchases;
    }
    
    public void setDiscPurchases(String discPurchases) {
        this.discPurchases = discPurchases;
    }
    public String getPurchasesCost() {
        return this.purchasesCost;
    }
    
    public void setPurchasesCost(String purchasesCost) {
        this.purchasesCost = purchasesCost;
    }
    public String getGoodsFrDuration() {
        return this.goodsFrDuration;
    }
    
    public void setGoodsFrDuration(String goodsFrDuration) {
        this.goodsFrDuration = goodsFrDuration;
    }
    public String getGoodsLaDuration() {
        return this.goodsLaDuration;
    }
    
    public void setGoodsLaDuration(String goodsLaDuration) {
        this.goodsLaDuration = goodsLaDuration;
    }
    public String getExpenses() {
        return this.expenses;
    }
    
    public void setExpenses(String expenses) {
        this.expenses = expenses;
    }
    public String getRevenue() {
        return this.revenue;
    }
    
    public void setRevenue(String revenue) {
        this.revenue = revenue;
    }




}


