/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package model;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

/**
 *
 * @author Kareem Moustafa
 */
public class ItemMatrixTable_ {
    
    private IntegerProperty m;
    private StringProperty code;
    private StringProperty itemName;
    private StringProperty colorName;
    private StringProperty sizeName;

    public ItemMatrixTable_() {
         
    }

    public ItemMatrixTable_(int m, String code, String itemName, String colorName, String sizeName) {
        this.m = new SimpleIntegerProperty(m);
        this.code = new SimpleStringProperty(code);
        this.itemName = new SimpleStringProperty(itemName);
        this.colorName = new SimpleStringProperty(colorName);
        this.sizeName = new SimpleStringProperty(sizeName);
    }

    


    
    public StringProperty codeProperty() {
        return code;
    }

    public void setCode(String code) {
        this.code.set(code);
    }
    public StringProperty itemNameProperty() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName.set(itemName);
    }

    public StringProperty colorNameProperty() {
        return colorName;
    }

    public void setColorName(String colorName) {
        this.colorName.set(colorName);
    }
    public StringProperty sizeNameProperty() {
        return sizeName;
    }

    public void setSizeName(String sizeName) {
        this.sizeName.set(sizeName);
    }


    
    
}
