package model;
// Generated Oct 24, 2014 1:11:37 AM by Hibernate Tools 3.6.0


import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

/**
 * Banks generated by hbm2java
 */
public class Banks  implements java.io.Serializable {


     private BigDecimal bankId;
     private Users users;
     private Charts charts;
     private String bankName;
     private String bankBranch;
     private String bankAddress;
     private String bankPhone;
     private String bankFax;
     private String bankEmail;
     private Double openDebit;
     private Double openCredit;
     private String bankIdNumber;
     private Set transactionses = new HashSet(0);

    public Banks() {
    }

	
    public Banks(BigDecimal bankId) {
        this.bankId = bankId;
    }
    public Banks(BigDecimal bankId, Users users, Charts charts, String bankName, String bankBranch, String bankAddress, String bankPhone, String bankFax, String bankEmail, Double openDebit, Double openCredit, String bankIdNumber, Set transactionses) {
       this.bankId = bankId;
       this.users = users;
       this.charts = charts;
       this.bankName = bankName;
       this.bankBranch = bankBranch;
       this.bankAddress = bankAddress;
       this.bankPhone = bankPhone;
       this.bankFax = bankFax;
       this.bankEmail = bankEmail;
       this.openDebit = openDebit;
       this.openCredit = openCredit;
       this.bankIdNumber = bankIdNumber;
       this.transactionses = transactionses;
    }
   
    public BigDecimal getBankId() {
        return this.bankId;
    }
    
    public void setBankId(BigDecimal bankId) {
        this.bankId = bankId;
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
    public String getBankName() {
        return this.bankName;
    }
    
    public void setBankName(String bankName) {
        this.bankName = bankName;
    }
    public String getBankBranch() {
        return this.bankBranch;
    }
    
    public void setBankBranch(String bankBranch) {
        this.bankBranch = bankBranch;
    }
    public String getBankAddress() {
        return this.bankAddress;
    }
    
    public void setBankAddress(String bankAddress) {
        this.bankAddress = bankAddress;
    }
    public String getBankPhone() {
        return this.bankPhone;
    }
    
    public void setBankPhone(String bankPhone) {
        this.bankPhone = bankPhone;
    }
    public String getBankFax() {
        return this.bankFax;
    }
    
    public void setBankFax(String bankFax) {
        this.bankFax = bankFax;
    }
    public String getBankEmail() {
        return this.bankEmail;
    }
    
    public void setBankEmail(String bankEmail) {
        this.bankEmail = bankEmail;
    }
    public Double getOpenDebit() {
        return this.openDebit;
    }
    
    public void setOpenDebit(Double openDebit) {
        this.openDebit = openDebit;
    }
    public Double getOpenCredit() {
        return this.openCredit;
    }
    
    public void setOpenCredit(Double openCredit) {
        this.openCredit = openCredit;
    }
    public String getBankIdNumber() {
        return this.bankIdNumber;
    }
    
    public void setBankIdNumber(String bankIdNumber) {
        this.bankIdNumber = bankIdNumber;
    }
    public Set getTransactionses() {
        return this.transactionses;
    }
    
    public void setTransactionses(Set transactionses) {
        this.transactionses = transactionses;
    }




}

