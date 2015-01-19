/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package shop;

import daos.CostCenterDAO;
import daos.PersonDAO;
import java.math.BigDecimal;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.NodeOrientation;
import javafx.scene.Node;
import javafx.scene.control.Accordion;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.TilePane;
import model.CostCenter;
import model.Persons;
import model.PersonsId;
import model.Zone;

/**
 * FXML Controller class
 *
 * @author Kareem.Moustafa
 */
public class VendorsController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
   private static VendorsController instanse;

    private TreeItem<String> treeRoot = new TreeItem<String>("الموردون");
    HashMap<Integer, Persons> venMap;
    @FXML
    TreeView vendorTree;
    @FXML
    Accordion mainInfoAcc;
    @FXML
    Accordion generalInfoAcc;
    @FXML
    TitledPane mainInfo;
    @FXML
    TitledPane generalInfo;
    @FXML
    TextField venCode;
    @FXML
    TextField venName;
    @FXML
    TextField venPhone1;
    @FXML
    TextField venPhone2;
    @FXML
    TextField venEmail;
    @FXML
    TextField venAdd;
    @FXML
    TextField contactName;
    @FXML
    TextField contactPhone;
    @FXML
    TextField openDebit;
    @FXML
    TextField openCredit;
    //ComapnyName = نسبة الخصم
    @FXML
    TextField companyName;
    @FXML
    TextField maxBalance;
    @FXML
    ComboBox<String> treatmentType;
    @FXML
    TextField zoneCode;
    @FXML
    ComboBox<Zone>  zoneCombo;
    
    ObservableList<String> treatmentTypeList = FXCollections.observableArrayList();
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        mainInfo.setExpanded(true);
        mainInfoAcc.setExpandedPane(mainInfo);
        venCode.setEditable(false);
        venName.setEditable(false);
        venPhone1.setEditable(false);
        venPhone2.setEditable(false);
        venEmail.setEditable(false);
        venAdd.setEditable(false);        
        contactName.setEditable(false);
        contactPhone.setEditable(false);
        openDebit.setEditable(false);        
        openCredit.setEditable(false);
//        treeRoot.getChildren().addAll(Arrays.asList(
//                new TreeItem<String>("Child Node 1"),
//                new TreeItem<String>("Child Node 2"),
//                new TreeItem<String>("Child Node 3")));
          vendorTree.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observableValue, Object oldValue, Object newValue) {
                //Check whether item is selected and set value of selected item to Label
                if (vendorTree.getSelectionModel().getSelectedItem() != null) {
                    TreeItem treeItem = (TreeItem)vendorTree.getSelectionModel().getSelectedItem();
                    int id = Integer.parseInt(((Label)treeItem.getGraphic()).getText());
                    Persons persons = venMap.get(id);
                    if(persons.getId().getPersonId() != BigDecimal.ZERO )
                        venCode.setText(persons.getId().getPersonId().toString());
                    if(persons.getPersonName() != null || !persons.getPersonName().isEmpty())
                        venName.setText(persons.getPersonName());
                }
            }
        });

        vendorTree.setRoot(treeRoot);
        treeRoot.setExpanded(true);
        treatmentTypeList.add("آجل");
        treatmentTypeList.add("نقدي");
        
    }   
    public VendorsController(){
        instanse = this;
       venMap = new HashMap<>();
    }
    
        public static VendorsController getInstance() {
        return instanse;
    }
        public void handleNew(){
        
        venCode.setEditable(true);
        venName.setEditable(true);
        venPhone1.setEditable(true);
        venPhone2.setEditable(true);
        venEmail.setEditable(true);
        venAdd.setEditable(true);        
        contactName.setEditable(true);
        contactPhone.setEditable(true);
        openDebit.setEditable(true);        
        openCredit.setEditable(true);
    }
    public void save() {
        PersonDAO personDAO = new PersonDAO();
        
        int id = personDAO.insertPerson(venEmail.getText(), 1, 0, 0, 0, 0, contactPhone.getText(), contactName.getText(), 0, Double.parseDouble(openCredit.getText()),
                Double.parseDouble(openDebit.getText()), null, venAdd.getText(), venPhone2.getText(), venPhone1.getText(), null, venName.getText(), 1,Integer.parseInt(venCode.getText()));
        treeRoot.getChildren().add(
                new TreeItem<String>(venName.getText() +" "+id));
        Persons p = new Persons(new PersonsId(new BigDecimal(id), new BigDecimal(1)), null, null, null, null, venName.getText(), null, venPhone1.getText(), venPhone2.getText(),
                venAdd.getText(), null, Double.parseDouble(openDebit.getText()), Double.parseDouble(openCredit.getText()), Double.NaN, contactName.getText(), contactPhone.getText(),
                BigDecimal.ZERO, venEmail.getText(), null, null, null);
        venMap.put(id, p);
        //handleNew();
    }        
    public void fillTree(){
          PersonDAO personDAO = new PersonDAO();
          List<Persons> persons = personDAO.getAllPersons();
          TreeItem<String> item;
          TreeCell<String> cell;
          Node node;
          for (Persons persons1 : persons) {
              node = new Label(persons1.getId().getPersonId().toString());
              
              item = new TreeItem<String>(persons1.getPersonName(),node);
              
           treeRoot.getChildren().add(item);  
           venMap.put(persons1.getId().getPersonId().intValue(), persons1);
        }
    }
    
    public void enableFields(boolean status){
         venCode.setEditable(status);
        venName.setEditable(status);
        venPhone1.setEditable(status);
        venPhone2.setEditable(status);
        venEmail.setEditable(status);
        venAdd.setEditable(status);        
        contactName.setEditable(status);
        contactPhone.setEditable(status);
        openDebit.setEditable(status);        
        openCredit.setEditable(status);
    }
    public void clearFields(boolean status){
        
    }
}
