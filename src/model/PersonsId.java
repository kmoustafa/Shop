package model;
// Generated Nov 14, 2014 12:02:34 AM by Hibernate Tools 3.6.0


import java.math.BigDecimal;

/**
 * PersonsId generated by hbm2java
 */
public class PersonsId  implements java.io.Serializable {


     private BigDecimal personId;
     private BigDecimal personType;

    public PersonsId() {
    }

    public PersonsId(BigDecimal personId, BigDecimal personType) {
       this.personId = personId;
       this.personType = personType;
    }
   
    public BigDecimal getPersonId() {
        return this.personId;
    }
    
    public void setPersonId(BigDecimal personId) {
        this.personId = personId;
    }
    public BigDecimal getPersonType() {
        return this.personType;
    }
    
    public void setPersonType(BigDecimal personType) {
        this.personType = personType;
    }


   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof PersonsId) ) return false;
		 PersonsId castOther = ( PersonsId ) other; 
         
		 return ( (this.getPersonId()==castOther.getPersonId()) || ( this.getPersonId()!=null && castOther.getPersonId()!=null && this.getPersonId().equals(castOther.getPersonId()) ) )
 && ( (this.getPersonType()==castOther.getPersonType()) || ( this.getPersonType()!=null && castOther.getPersonType()!=null && this.getPersonType().equals(castOther.getPersonType()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getPersonId() == null ? 0 : this.getPersonId().hashCode() );
         result = 37 * result + ( getPersonType() == null ? 0 : this.getPersonType().hashCode() );
         return result;
   }   


}


