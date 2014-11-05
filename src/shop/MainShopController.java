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
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;

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
            tabPane.getSelectionModel().select(tab);
        } catch (IOException ex) {
            Logger.getLogger(MainShopController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
