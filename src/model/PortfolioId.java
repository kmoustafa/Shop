package model;
// Generated Nov 14, 2014 12:02:34 AM by Hibernate Tools 3.6.0


import java.math.BigDecimal;

/**
 * PortfolioId generated by hbm2java
 */
public class PortfolioId  implements java.io.Serializable {


     private BigDecimal portfolioId;
     private BigDecimal portfolioType;

    public PortfolioId() {
    }

    public PortfolioId(BigDecimal portfolioId, BigDecimal portfolioType) {
       this.portfolioId = portfolioId;
       this.portfolioType = portfolioType;
    }
   
    public BigDecimal getPortfolioId() {
        return this.portfolioId;
    }
    
    public void setPortfolioId(BigDecimal portfolioId) {
        this.portfolioId = portfolioId;
    }
    public BigDecimal getPortfolioType() {
        return this.portfolioType;
    }
    
    public void setPortfolioType(BigDecimal portfolioType) {
        this.portfolioType = portfolioType;
    }


   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof PortfolioId) ) return false;
		 PortfolioId castOther = ( PortfolioId ) other; 
         
		 return ( (this.getPortfolioId()==castOther.getPortfolioId()) || ( this.getPortfolioId()!=null && castOther.getPortfolioId()!=null && this.getPortfolioId().equals(castOther.getPortfolioId()) ) )
 && ( (this.getPortfolioType()==castOther.getPortfolioType()) || ( this.getPortfolioType()!=null && castOther.getPortfolioType()!=null && this.getPortfolioType().equals(castOther.getPortfolioType()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getPortfolioId() == null ? 0 : this.getPortfolioId().hashCode() );
         result = 37 * result + ( getPortfolioType() == null ? 0 : this.getPortfolioType().hashCode() );
         return result;
   }   


}


