/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package shop;


import daos.EmployeeDAO;
import java.math.BigDecimal;
import java.net.URL;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import model.Employees;

/**
 * FXML Controller class
 *
 * @author kareem.moustafa
 */
public class EmployeesController implements Initializable {

    /**
     * Initializes the controller class.
     */
     private static EmployeesController instanse;

    private TreeItem<String> treeRoot = new TreeItem<String>("الموظفين");
    HashMap<Integer, Employees> venMap;
    @FXML
    TextField empCode;
    @FXML
    TextField empName;
    @FXML
    TextField empPhone1;
    @FXML
    TextField empPhone2;
    @FXML
    TextField empSalary;
    @FXML
    TextField empCommision;
    @FXML
    DatePicker hireDate;
    @FXML
    TextArea notes;
       @FXML
    TreeView empTree;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
          enableFields(false);
                  empTree.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observableValue, Object oldValue, Object newValue) {
                //Check whether item is selected and set value of selected item to Label
                if (empTree.getSelectionModel().getSelectedItem() != null) {
                    TreeItem treeItem = (TreeItem)empTree.getSelectionModel().getSelectedItem();
                    int id = Integer.parseInt(((Label)treeItem.getGraphic()).getText());
                    Employees emp = venMap.get(id);
                    if(emp.getEmpId() != BigDecimal.ZERO )
                        empCode.setText(emp.getEmpId().toString());
                    if(emp.getEmpName() != null || !emp.getEmpName().isEmpty())
                        empName.setText(emp.getEmpName());
                    if(emp.getEmpPhone() != null || !emp.getEmpPhone().isEmpty())
                        empPhone1.setText(emp.getEmpPhone());
                    if(emp.getEmpPhone2() != null || !emp.getEmpPhone2().isEmpty())
                        empPhone2.setText(emp.getEmpPhone2());                    
                    if(emp.getHireDate() != null ){
                        java.util.Date d = new Date(emp.getHireDate().getTime());
                         Instant instant = d.toInstant();
LocalDate localDate = instant.atZone(ZoneId.systemDefault()).toLocalDate();
                        hireDate.setValue(localDate);
                    }
                    if(emp.getInfo() != null || !emp.getInfo().isEmpty())
                        notes.setText(emp.getInfo());
                 
                    if(emp.getSalary() != null || emp.getSalary()!= 0)
                        empSalary.setText(emp.getSalary().toString());
                    if(emp.getCommision()!= null || emp.getCommision() != 0)
                        empCommision.setText(emp.getCommision().toString());                    
                }
            }
        });

        empTree.setRoot(treeRoot);

        treeRoot.setExpanded(true);
    }    
        public EmployeesController() {
        instanse = this;
           venMap = new HashMap<>();
    }
    public static EmployeesController getInstance() {
        return instanse;
    }   
    
        public void handleNew() {
        enableFields(true);
    }
    public void save() {
        EmployeeDAO employeeDAO = new EmployeeDAO();
        TreeItem<String> item =  new TreeItem<String>(empName.getText());
        LocalDate localDate = hireDate .getValue();
        Instant instant = Instant.from(localDate.atStartOfDay(ZoneId.systemDefault()));
        Date date = Date.from(instant);
        int id = employeeDAO.insertEmployee(date, notes.getText(), 1, 0, Double.parseDouble(empCommision.getText()) , Double.parseDouble(empSalary.getText()),
                empPhone2.getText(), empPhone1.getText(), empName.getText());
         Node node = new Label(String.valueOf(id));
         item.setGraphic(node);
        treeRoot.getChildren().add(
               item);
        Employees emp = new Employees(new BigDecimal(id), null, null, empName.getText(), empPhone1.getText(), empPhone2.getText(), Double.parseDouble(empSalary.getText()),
                Double.parseDouble(empCommision.getText()), null, null, notes.getText(), date);
        venMap.put(id, emp);
        //handleNew();
    } 
    public void update() {
        EmployeeDAO employeeDAO = new EmployeeDAO();
        TreeItem treeItem = (TreeItem)empTree.getSelectionModel().getSelectedItem();
 LocalDate localDate = hireDate .getValue();
        Instant instant = Instant.from(localDate.atStartOfDay(ZoneId.systemDefault()));
        Date date = Date.from(instant);
        int id = employeeDAO.updateEmployee(Integer.parseInt(empCode.getText()),date, notes.getText(), 1, 0, Double.parseDouble(empCommision.getText()),
                Double.parseDouble(empSalary.getText()), empPhone2.getText(), empPhone1.getText(), empName.getText());
        //System.out.println(costCenterTable.getItems());
    }
    public void delete(){
        EmployeeDAO employeeDAO = new EmployeeDAO();
         employeeDAO.deleteEmployee(Integer.parseInt(empCode.getText()));
         
         treeRoot.getChildren().remove((TreeItem)empTree.getSelectionModel().getSelectedItem());
    }    
    public void fillTree(){
        EmployeeDAO employeeDAO = new EmployeeDAO();
        List<Employees> employeeses = employeeDAO.getAllEmployees();
        TreeItem<String> item;
        TreeCell<String> cell;
        Node node;
        for (Employees emp : employeeses) {
            node = new Label(emp.getEmpId().toString());

            item = new TreeItem<String>(emp.getEmpName(), node);
            
            treeRoot.getChildren().add(item);
           
            venMap.put(emp.getEmpId().intValue(), emp);
        }
    }
    
    public void enableFields(boolean status) {
        empCode.setEditable(status);
        empName.setEditable(status);
        empPhone1.setEditable(status);
        empPhone2.setEditable(status);
        empSalary.setEditable(status);
        empCommision.setEditable(status);
        hireDate.setEditable(status);
        notes.setEditable(status);


    }
}
