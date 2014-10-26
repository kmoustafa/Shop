package model;
// Generated Oct 24, 2014 1:11:37 AM by Hibernate Tools 3.6.0


import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

/**
 * Charts generated by hbm2java
 */
public class Charts  implements java.io.Serializable {


     private BigDecimal accId;
     private Users users;
     private CostCenter costCenter;
     private BigDecimal isTarget;
     private String target;
     private BigDecimal debitOCredit;
     private BigDecimal reportType;
     private BigDecimal rankAccount;
     private Double openDebit;
     private Double openCredt;
     private BigDecimal viewMode;
     private Set employeeses = new HashSet(0);
     private Set bankses = new HashSet(0);
     private Set transactionDetailses = new HashSet(0);
     private Set personses = new HashSet(0);
     private Set expenceses = new HashSet(0);

    public Charts() {
    }

	
    public Charts(BigDecimal accId) {
        this.accId = accId;
    }
    public Charts(BigDecimal accId, Users users, CostCenter costCenter, BigDecimal isTarget, String target, BigDecimal debitOCredit, BigDecimal reportType, BigDecimal rankAccount, Double openDebit, Double openCredt, BigDecimal viewMode, Set employeeses, Set bankses, Set transactionDetailses, Set personses, Set expenceses) {
       this.accId = accId;
       this.users = users;
       this.costCenter = costCenter;
       this.isTarget = isTarget;
       this.target = target;
       this.debitOCredit = debitOCredit;
       this.reportType = reportType;
       this.rankAccount = rankAccount;
       this.openDebit = openDebit;
       this.openCredt = openCredt;
       this.viewMode = viewMode;
       this.employeeses = employeeses;
       this.bankses = bankses;
       this.transactionDetailses = transactionDetailses;
       this.personses = personses;
       this.expenceses = expenceses;
    }
   
    public BigDecimal getAccId() {
        return this.accId;
    }
    
    public void setAccId(BigDecimal accId) {
        this.accId = accId;
    }
    public Users getUsers() {
        return this.users;
    }
    
    public void setUsers(Users users) {
        this.users = users;
    }
    public CostCenter getCostCenter() {
        return this.costCenter;
    }
    
    public void setCostCenter(CostCenter costCenter) {
        this.costCenter = costCenter;
    }
    public BigDecimal getIsTarget() {
        return this.isTarget;
    }
    
    public void setIsTarget(BigDecimal isTarget) {
        this.isTarget = isTarget;
    }
    public String getTarget() {
        return this.target;
    }
    
    public void setTarget(String target) {
        this.target = target;
    }
    public BigDecimal getDebitOCredit() {
        return this.debitOCredit;
    }
    
    public void setDebitOCredit(BigDecimal debitOCredit) {
        this.debitOCredit = debitOCredit;
    }
    public BigDecimal getReportType() {
        return this.reportType;
    }
    
    public void setReportType(BigDecimal reportType) {
        this.reportType = reportType;
    }
    public BigDecimal getRankAccount() {
        return this.rankAccount;
    }
    
    public void setRankAccount(BigDecimal rankAccount) {
        this.rankAccount = rankAccount;
    }
    public Double getOpenDebit() {
        return this.openDebit;
    }
    
    public void setOpenDebit(Double openDebit) {
        this.openDebit = openDebit;
    }
    public Double getOpenCredt() {
        return this.openCredt;
    }
    
    public void setOpenCredt(Double openCredt) {
        this.openCredt = openCredt;
    }
    public BigDecimal getViewMode() {
        return this.viewMode;
    }
    
    public void setViewMode(BigDecimal viewMode) {
        this.viewMode = viewMode;
    }
    public Set getEmployeeses() {
        return this.employeeses;
    }
    
    public void setEmployeeses(Set employeeses) {
        this.employeeses = employeeses;
    }
    public Set getBankses() {
        return this.bankses;
    }
    
    public void setBankses(Set bankses) {
        this.bankses = bankses;
    }
    public Set getTransactionDetailses() {
        return this.transactionDetailses;
    }
    
    public void setTransactionDetailses(Set transactionDetailses) {
        this.transactionDetailses = transactionDetailses;
    }
    public Set getPersonses() {
        return this.personses;
    }
    
    public void setPersonses(Set personses) {
        this.personses = personses;
    }
    public Set getExpenceses() {
        return this.expenceses;
    }
    
    public void setExpenceses(Set expenceses) {
        this.expenceses = expenceses;
    }




}


