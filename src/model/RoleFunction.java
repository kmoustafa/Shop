package model;
// Generated Nov 14, 2014 12:02:34 AM by Hibernate Tools 3.6.0


import java.math.BigDecimal;

/**
 * RoleFunction generated by hbm2java
 */
public class RoleFunction  implements java.io.Serializable {


     private BigDecimal id;
     private Function function;
     private Role role;

    public RoleFunction() {
    }

	
    public RoleFunction(BigDecimal id) {
        this.id = id;
    }
    public RoleFunction(BigDecimal id, Function function, Role role) {
       this.id = id;
       this.function = function;
       this.role = role;
    }
   
    public BigDecimal getId() {
        return this.id;
    }
    
    public void setId(BigDecimal id) {
        this.id = id;
    }
    public Function getFunction() {
        return this.function;
    }
    
    public void setFunction(Function function) {
        this.function = function;
    }
    public Role getRole() {
        return this.role;
    }
    
    public void setRole(Role role) {
        this.role = role;
    }




}


