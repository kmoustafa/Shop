/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package shop;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

/**
 *
 * @author kareem.moustafa
 */
public class MainShopController implements Initializable {
    
    @FXML
    private Label label;
    
    @FXML
    private TabPane tabPane;
    
    @FXML
    private void handleButtonAction(ActionEvent event) {
        
        try {

            Tab tab = new Tab("بيانات الشركة");
            tab.setContent(FXMLLoader.load(this.getClass().getResource("CompanyInfo.fxml")));
            this.tabPane.getTabs().add(tab);
            tab = new Tab("شجرة الحسابات");
            tab.setContent(FXMLLoader.load(this.getClass().getResource("Charts.fxml")));
            this.tabPane.getTabs().add(tab);
            tab = new Tab("العملاء");
            tab.setContent(FXMLLoader.load(this.getClass().getResource("Customer.fxml")));
            this.tabPane.getTabs().add(tab);            
            tab = new Tab("الموردون");
            tab.setContent(FXMLLoader.load(this.getClass().getResource("Vendors.fxml")));
            this.tabPane.getTabs().add(tab);            
            tab = new Tab("المخازن");
            tab.setContent(FXMLLoader.load(this.getClass().getResource("Stores.fxml")));
            this.tabPane.getTabs().add(tab);            
            tab = new Tab("الألوان");
            tab.setContent(FXMLLoader.load(this.getClass().getResource("Colors.fxml")));
            this.tabPane.getTabs().add(tab);  
            tab = new Tab("مراكز التكلفة");
            tab.setContent(FXMLLoader.load(this.getClass().getResource("CostCenters.fxml")));
            this.tabPane.getTabs().add(tab);  
            tab = new Tab("أقسام وظيفية");
            tab.setContent(FXMLLoader.load(this.getClass().getResource("Departments.fxml")));
            this.tabPane.getTabs().add(tab);  
            tab = new Tab("المجموعات");
            tab.setContent(FXMLLoader.load(this.getClass().getResource("Groups.fxml")));
            this.tabPane.getTabs().add(tab);  
            tab = new Tab("أقسام");
            tab.setContent(FXMLLoader.load(this.getClass().getResource("Sections.fxml")));
            this.tabPane.getTabs().add(tab);  
            tab = new Tab("المقاسات");
            tab.setContent(FXMLLoader.load(this.getClass().getResource("Sizes.fxml")));
            this.tabPane.getTabs().add(tab);  
            tab = new Tab("الأنواع");
            tab.setContent(FXMLLoader.load(this.getClass().getResource("Types.fxml")));
            this.tabPane.getTabs().add(tab);  
            tab = new Tab("الوحدات");
            tab.setContent(FXMLLoader.load(this.getClass().getResource("Units.fxml")));
            this.tabPane.getTabs().add(tab);  
            tab = new Tab("المناطق");
            tab.setContent(FXMLLoader.load(this.getClass().getResource("Zones.fxml")));
            this.tabPane.getTabs().add(tab);  
            tab = new Tab("المصروفات");
            tab.setContent(FXMLLoader.load(this.getClass().getResource("Expenses.fxml")));
            this.tabPane.getTabs().add(tab); 
            tab = new Tab("البنوك");
            tab.setContent(FXMLLoader.load(this.getClass().getResource("Banks.fxml")));
            this.tabPane.getTabs().add(tab); 
            tab = new Tab("الكاشير");
            tab.setContent(FXMLLoader.load(this.getClass().getResource("Cashier.fxml")));
            this.tabPane.getTabs().add(tab); 
            //tabPane.getSelectionModel().select(tab);
        } catch (IOException ex) {
            Logger.getLogger(MainShopController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
