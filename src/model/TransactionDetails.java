package model;
// Generated Oct 24, 2014 1:11:37 AM by Hibernate Tools 3.6.0


import java.math.BigDecimal;
import java.util.Date;

/**
 * TransactionDetails generated by hbm2java
 */
public class TransactionDetails  implements java.io.Serializable {


     private BigDecimal transDId;
     private ItemsSerials itemsSerials;
     private Sizes sizes;
     private Colors colors;
     private Transactions transactions;
     private PortfolioDetails portfolioDetails;
     private Charts charts;
     private BigDecimal qty;
     private Double transDUnitPrice;
     private Double transDDiscPercent;
     private Double transDDiscValue;
     private String transDetBarcode;
     private String transDetWsid;
     private String transDetNotes;
     private Date transDetDate;

    public TransactionDetails() {
    }

	
    public TransactionDetails(BigDecimal transDId) {
        this.transDId = transDId;
    }
    public TransactionDetails(BigDecimal transDId, ItemsSerials itemsSerials, Sizes sizes, Colors colors, Transactions transactions, PortfolioDetails portfolioDetails, Charts charts, BigDecimal qty, Double transDUnitPrice, Double transDDiscPercent, Double transDDiscValue, String transDetBarcode, String transDetWsid, String transDetNotes, Date transDetDate) {
       this.transDId = transDId;
       this.itemsSerials = itemsSerials;
       this.sizes = sizes;
       this.colors = colors;
       this.transactions = transactions;
       this.portfolioDetails = portfolioDetails;
       this.charts = charts;
       this.qty = qty;
       this.transDUnitPrice = transDUnitPrice;
       this.transDDiscPercent = transDDiscPercent;
       this.transDDiscValue = transDDiscValue;
       this.transDetBarcode = transDetBarcode;
       this.transDetWsid = transDetWsid;
       this.transDetNotes = transDetNotes;
       this.transDetDate = transDetDate;
    }
   
    public BigDecimal getTransDId() {
        return this.transDId;
    }
    
    public void setTransDId(BigDecimal transDId) {
        this.transDId = transDId;
    }
    public ItemsSerials getItemsSerials() {
        return this.itemsSerials;
    }
    
    public void setItemsSerials(ItemsSerials itemsSerials) {
        this.itemsSerials = itemsSerials;
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
    public Transactions getTransactions() {
        return this.transactions;
    }
    
    public void setTransactions(Transactions transactions) {
        this.transactions = transactions;
    }
    public PortfolioDetails getPortfolioDetails() {
        return this.portfolioDetails;
    }
    
    public void setPortfolioDetails(PortfolioDetails portfolioDetails) {
        this.portfolioDetails = portfolioDetails;
    }
    public Charts getCharts() {
        return this.charts;
    }
    
    public void setCharts(Charts charts) {
        this.charts = charts;
    }
    public BigDecimal getQty() {
        return this.qty;
    }
    
    public void setQty(BigDecimal qty) {
        this.qty = qty;
    }
    public Double getTransDUnitPrice() {
        return this.transDUnitPrice;
    }
    
    public void setTransDUnitPrice(Double transDUnitPrice) {
        this.transDUnitPrice = transDUnitPrice;
    }
    public Double getTransDDiscPercent() {
        return this.transDDiscPercent;
    }
    
    public void setTransDDiscPercent(Double transDDiscPercent) {
        this.transDDiscPercent = transDDiscPercent;
    }
    public Double getTransDDiscValue() {
        return this.transDDiscValue;
    }
    
    public void setTransDDiscValue(Double transDDiscValue) {
        this.transDDiscValue = transDDiscValue;
    }
    public String getTransDetBarcode() {
        return this.transDetBarcode;
    }
    
    public void setTransDetBarcode(String transDetBarcode) {
        this.transDetBarcode = transDetBarcode;
    }
    public String getTransDetWsid() {
        return this.transDetWsid;
    }
    
    public void setTransDetWsid(String transDetWsid) {
        this.transDetWsid = transDetWsid;
    }
    public String getTransDetNotes() {
        return this.transDetNotes;
    }
    
    public void setTransDetNotes(String transDetNotes) {
        this.transDetNotes = transDetNotes;
    }
    public Date getTransDetDate() {
        return this.transDetDate;
    }
    
    public void setTransDetDate(Date transDetDate) {
        this.transDetDate = transDetDate;
    }




}


