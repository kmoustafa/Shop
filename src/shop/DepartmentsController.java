/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package shop;

import daos.DepartmentDAO;
import daos.ExpenceDAO;
import daos.ZoneDAO;
import java.math.BigDecimal;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.util.Callback;
import model.Department;
import model.Expences;
import model.Zone;

/**
 * FXML Controller class
 *
 * @author Kareem.Moustafa
 */
public class DepartmentsController implements Initializable {

    /**
     * Initializes the controller class.
     */
 private static DepartmentsController instanse;

    ObservableList<Department> list;
    public ArrayList<Department> editList;
    @FXML
    TextField depCode;
    @FXML
    TextField depName;
        @FXML
    TextField search;
    @FXML
    TableView<Department> depTable;
    @FXML
    TableColumn codeColumn;
    @FXML
    TableColumn nameColumn;    
        private ObservableList<Department> filteredData = FXCollections.observableArrayList();

    @Override
    
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
        list = FXCollections.observableArrayList();
        editList = new ArrayList<>();
        codeColumn.setCellValueFactory(new PropertyValueFactory("deptId"));
        nameColumn.setCellValueFactory(new PropertyValueFactory("deptName"));
        // costCenerTable = new TableView<>();
        Callback<TableColumn, TableCell> cellFactory
                = new Callback<TableColumn, TableCell>() {

                    public TableCell call(TableColumn p) {
                        return new EditingCell();
                    }
                };
        codeColumn.setCellFactory(cellFactory);
        nameColumn.setCellFactory(cellFactory);

        
        list.addListener(new ListChangeListener<Department>() {
            @Override
            public void onChanged(ListChangeListener.Change<? extends Department> change) {
                updateFilteredData();
            }
        });
                // Listen for text changes in the filter text field
        search.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable,
                    String oldValue, String newValue) {

                updateFilteredData();
            }
        });

        depCode.setEditable(false);
        depName.setEditable(false);
        depTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observableValue, Object oldValue, Object newValue) {
                //Check whether item is selected and set value of selected item to Label
                if (depTable.getSelectionModel().getSelectedItem() != null) {
                    TableView.TableViewSelectionModel selectionModel = depTable.getSelectionModel();
                    ObservableList selectedCells = selectionModel.getSelectedCells();
                    Department c = (Department) selectionModel.getSelectedItem();
                    depCode.setText(c.getDeptId().toString());
                    depName.setText(c.getDeptName());
                    TablePosition tablePosition = (TablePosition) selectedCells.get(0);

                    String st = tablePosition.getTableView().getSelectionModel().getTableView().getId();
                    //gives you selected cell value..
                    //selectedColumn = tablePosition.getColumn();
                    Object GetSinglevalue = tablePosition.getTableColumn().getCellData(newValue);

                    BigDecimal getbothvalue = depTable.getSelectionModel().getSelectedItem().getDeptId();
                    //gives you first column value..
                    String Finalvaluetablerow = getbothvalue.toString().split(",")[0].substring(1);
                    System.out.println("The First column value of row.." + Finalvaluetablerow);
                }
            }
        });
    } 
      public DepartmentsController() {
        instanse = this;
    }

    public static DepartmentsController getInstance() {
        return instanse;
    }
        public void handleNew() {
        clearFields();
        depCode.setEditable(false);
        depName.setEditable(true);
               DepartmentDAO depDAO = new DepartmentDAO();
        this.depCode.setText(String.valueOf(depDAO.getLastIndex() + 1));
    }

        public void save(int operation) {
               DepartmentDAO depDAO = new DepartmentDAO();
               if(operation == 1){
        int id = depDAO.insertDepartment(Integer.valueOf(depCode.getText()), depName.getText());
        Department dep = depDAO.getDepartmentById(id);
        list.add(dep);
       // depTable.setItems(list);
        //   fillTable();
        //depTable.getSelectionModel().focus(depTable.getItems().size());
        
       // handleNew();
        updateFilteredData();
        clearFields();
               }else if(operation ==2){
               depDAO.updateDepartment(Integer.valueOf( depCode.getText()), depName.getText());
            depTable.getSelectionModel().getSelectedItem().setDeptName(depName.getText());
            updateFilteredData();
               }
    }
        public void update() {
                    depName.setEditable(true);

//               DepartmentDAO depDAO = new DepartmentDAO();
//        if (!editList.isEmpty()) {
//            for (Department dep : editList) {
//                depDAO.updateDepartment(dep.getDeptId().intValue(), dep.getDeptName());
//            }
//        }
        //System.out.println(costCenterTable.getItems());
    }
public void delete(){

        String id = depTable.getSelectionModel().getSelectedItem().getDeptId().toString();
               DepartmentDAO depDAO = new DepartmentDAO();
        depDAO.deleteDepartment(Integer.parseInt(id));
                list.remove(depTable.getSelectionModel().getSelectedItem());
        //depTable.setItems(list);
               // fillTable();
                updateFilteredData();
    }    
    public void fillTable(){
        DepartmentDAO depDAO = new DepartmentDAO();
        ArrayList<Department> deps = (ArrayList) depDAO.getAllDepartments();
        list = FXCollections.observableArrayList();
        for (Department dep : deps) {
            list.add(dep);
        }
              filteredData.addAll(list);

        depTable.setItems(filteredData);

    }
    
    
     public class EditingCell extends TableCell<Department, Object> {

        private TextField textField;
        boolean isText;
        public EditingCell() {
            isText = false;
        }

        @Override
        public void startEdit() {
            super.startEdit();
            String st = getTableColumn().getText();
            if (textField == null) {
                createTextField();
            }
            setText(null);
            setGraphic(textField);
            textField.selectAll();
        }

        @Override
        public void cancelEdit() {
            super.cancelEdit();
            setText(String.valueOf(getItem()));
            setGraphic(null);
            isText = false;
        }

        @Override
        public void updateItem(Object item, boolean empty) {
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
                    TableRow t = getTableRow();
                    setText(getString());
                    setGraphic(null);
                }
            }
        }

        @Override
        public void commitEdit(Object newValue) {
            super.commitEdit(newValue);
            String st = getTableColumn().getText();
           Department c = getTableView().getSelectionModel().getSelectedItem();
            if (st.equals("الكود")) {
                c.setDeptId(new BigDecimal(Integer.parseInt(String.valueOf(newValue))));
            } else if (st.equals("الإسم")) {
                c.setDeptName(String.valueOf(newValue));
            }
            String s = getTableRow().getText();
            String l = getTableView().getId();
            editList.add(c);
            System.out.println(editList.size());
            isText = false;
        }

        private void createTextField() {
            if(!isText){
            textField = new TextField(getString());
            textField.setMinWidth(this.getWidth() - this.getGraphicTextGap() * 2);
            textField.setOnKeyReleased(new EventHandler<KeyEvent>() {
                @Override
                public void handle(KeyEvent t) {
                    if (t.getCode() == KeyCode.ENTER) {
                        commitEdit(textField.getText());
                    } else if (t.getCode() == KeyCode.ESCAPE) {
                        cancelEdit();
                    }
                }
            });
            isText = true;
        }
        }

        private String getString() {
            Object o = getItem();
            return getItem() == null ? "" : getItem().toString();
        }
    }
     
         private void updateFilteredData() {
        filteredData.clear();

        for (Department p : list) {
            if (matchesFilter(p)) {
                filteredData.add(p);
            }
        }

        // Must re-sort table after items changed
        reapplyTableSortOrder();
    }

    private boolean matchesFilter(Department z) {
        String filterString = search.getText();
        if (filterString == null || filterString.isEmpty()) {
            // No filter --> Add all.
            return true;
        }

        String lowerCaseFilterString = filterString.toLowerCase();

        if (z.getDeptName().toLowerCase().indexOf(lowerCaseFilterString) != -1) {
            return true;
        }

        return false; // Does not match
    }

    private void reapplyTableSortOrder() {
        ArrayList<TableColumn<Department, ?>> sortOrder = new ArrayList<>(depTable.getSortOrder());
        depTable.getSortOrder().clear();
        depTable.getSortOrder().addAll(sortOrder);
    }

    private void clearFields() {
        this.depCode.setText("");
        this.depName.setText("");
    }
}
