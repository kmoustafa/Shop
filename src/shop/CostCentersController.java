/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package shop;

import daos.CostCenterDAO;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.util.Callback;
import model.CostCenter;
import utils.DBHandler;

/**
 * FXML Controller class
 *
 * @author Kareem.Moustafa
 */
public class CostCentersController implements Initializable {

    /**
     * Initializes the controller class.
     */
    private static CostCentersController instanse;
    @FXML
    TextField code;
    
    @FXML
    TextField name;
    
    @FXML
    TableView<CostCenter> costCenterTable;
    
    @FXML
    TableColumn codeColumn;
    
    @FXML
    TableColumn nameColumn;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
       
        codeColumn.setCellValueFactory(new PropertyValueFactory("costCenterId"));
        nameColumn.setCellValueFactory(new PropertyValueFactory("costCenterName"));
       // costCenerTable = new TableView<>();
           Callback<TableColumn, TableCell> cellFactory =
                new Callback<TableColumn, TableCell>() {
 
                    public TableCell call(TableColumn p) {
                        return new EditingCell();
                    }
                };
           codeColumn.setCellFactory(cellFactory);
           nameColumn.setCellFactory(cellFactory);
    }    
    
    public CostCentersController(){
        instanse = this;
    }
    public static CostCentersController getInstance(){
        return instanse;
    }
    public void save(){
        CostCenterDAO centerDAO = new CostCenterDAO();
        centerDAO.insertCostCenter(1, name.getText());
    }
    
    public void fillTable(){
         CostCenterDAO centerDAO = new CostCenterDAO();
        ArrayList<CostCenter> centers = (ArrayList)centerDAO.getAllCostCenters();
        ObservableList<CostCenter> list = FXCollections.observableArrayList();
        for (CostCenter costCenter : centers) {
            list.add(costCenter);
        }
            costCenterTable.setItems(list);
    }
    // EditingCell - for editing capability in a TableCell
    public static class EditingCell extends TableCell<CostCenter, Object> {
        private TextField textField;

        public EditingCell() {
        }
       
        @Override public void startEdit() {
            super.startEdit();

            if (textField == null) {
                createTextField();
            }
            setText(null);
            setGraphic(textField);
            textField.selectAll();
        }
       
        @Override public void cancelEdit() {
            super.cancelEdit();
            setText((String) getItem());
            setGraphic(null);
        }
       
        @Override public void updateItem(Object item, boolean empty) {
            super.updateItem(item, empty);
            if (empty) {
                setText(null);
                setGraphic(null);
            } else {
                if (isEditing()) {
                    if (textField != null) {
                        textField.setText(getString());
                    }
                    setText(null);
                    setGraphic(textField);
                } else {
                    setText(getString());
                    setGraphic(null);
                }
            }
        }

        private void createTextField() {
            textField = new TextField(getString());
            textField.setMinWidth(this.getWidth() - this.getGraphicTextGap() * 2);
            textField.setOnKeyReleased(new EventHandler<KeyEvent>() {                
                @Override public void handle(KeyEvent t) {
                    if (t.getCode() == KeyCode.ENTER) {
                        commitEdit(textField.getText());
                    } else if (t.getCode() == KeyCode.ESCAPE) {
                        cancelEdit();
                    }
                }
            });
        }

        private String getString() {
            return getItem() == null ? "" : getItem().toString();
        }
    }
}
