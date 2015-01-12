/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shop;

import daos.CostCenterDAO;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URL;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.Event;
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
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
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
    private @FXML
    TextField code;

    @FXML
    TextField name;
    
    @FXML
    TextField search;
    
    @FXML
    TableView<CostCenter> costCenterTable;

    @FXML
    TableColumn codeColumn;

    @FXML
    TableColumn nameColumn;

    ObservableList<CostCenter> list;
    private ObservableList<CostCenter> filteredData = FXCollections.observableArrayList();

    public ArrayList<CostCenter> editList;
    int selectedColumn;

    public ArrayList<CostCenter> getEditList() {
        return editList;
    }

    public void setEditList(ArrayList<CostCenter> editList) {
        this.editList = editList;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        list = FXCollections.observableArrayList();
        editList = new ArrayList<>();
        codeColumn.setCellValueFactory(new PropertyValueFactory("costCenterId"));
        nameColumn.setCellValueFactory(new PropertyValueFactory("costCenterName"));
        // costCenerTable = new TableView<>();
        Callback<TableColumn, TableCell> cellFactory
                = new Callback<TableColumn, TableCell>() {

                    public TableCell call(TableColumn p) {
                        return new EditingCell();
                    }
                };
        codeColumn.setCellFactory(cellFactory);
        nameColumn.setCellFactory(cellFactory);

//        FilteredList<CostCenter> filteredList = new FilteredList<CostCenter>(list, p -> true);
//         name.textProperty().addListener((observable, oldValue, newValue) -> {
//            filteredList.setPredicate(costCenter -> {
//                // If filter text is empty, display all persons.
//                if (newValue == null || newValue.isEmpty()) {
//                    return true;
//                }
//
//                // Compare first name and last name of every person with filter text.
//                String lowerCaseFilter = newValue.toLowerCase();
//
//                if (costCenter.getCostCenterName().toLowerCase().indexOf(lowerCaseFilter) != -1) {
//                    return true; // Filter matches first name.
//                }
//                return false; // Does not match.
//            });
//        });
//         
//  // 3. Wrap the FilteredList in a SortedList. 
//        SortedList<CostCenter> sortedData = new SortedList<>(filteredList);
//
//        // 4. Bind the SortedList comparator to the TableView comparator.
//        sortedData.comparatorProperty().bind(costCenterTable.comparatorProperty());
//
//        // 5. Add sorted (and filtered) data to the table.
//        costCenterTable.setItems(sortedData);     
        
          list.addListener(new ListChangeListener<CostCenter>() {
            @Override
            public void onChanged(ListChangeListener.Change<? extends CostCenter> change) {
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
        code.setEditable(false);
        name.setEditable(false);
        costCenterTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observableValue, Object oldValue, Object newValue) {
                //Check whether item is selected and set value of selected item to Label
                if (costCenterTable.getSelectionModel().getSelectedItem() != null) {
                    TableView.TableViewSelectionModel selectionModel = costCenterTable.getSelectionModel();
                    ObservableList selectedCells = selectionModel.getSelectedCells();
                    CostCenter c = (CostCenter) selectionModel.getSelectedItem();
                    code.setText(c.getCostCenterId().toString());
                    name.setText(c.getCostCenterName());
                    TablePosition tablePosition = (TablePosition) selectedCells.get(0);

                    String st = tablePosition.getTableView().getSelectionModel().getTableView().getId();
                    //gives you selected cell value..
                    selectedColumn = tablePosition.getColumn();
                    Object GetSinglevalue = tablePosition.getTableColumn().getCellData(newValue);

                    BigDecimal getbothvalue = costCenterTable.getSelectionModel().getSelectedItem().getCostCenterId();
                    //gives you first column value..
                    String Finalvaluetablerow = getbothvalue.toString().split(",")[0].substring(1);
                    System.out.println("The First column value of row.." + Finalvaluetablerow);
                }
            }
        });

    }
    public void handleNew(){
        
        code.setEditable(true);
        name.setEditable(true);
        CostCenterDAO costCenterDAO = new CostCenterDAO();
        this.code.setText(String.valueOf(costCenterDAO.getLastIndex()));

    }
    public CostCentersController() {
        instanse = this;
        
    }

    public static CostCentersController getInstance() {
        return instanse;
    }

    public void save() {
        CostCenterDAO centerDAO = new CostCenterDAO();
        int id = centerDAO.insertCostCenter(1, name.getText());
        CostCenter newCostCenter = centerDAO.getCostCenterById(id);
        list.add(newCostCenter);
        costCenterTable.setItems(list);
     //   fillTable();
        handleNew();
    }
    
    
    public void update() {
        CostCenterDAO centerDAO = new CostCenterDAO();
        if (!editList.isEmpty()) {
            for (CostCenter costCenter : editList) {
                centerDAO.updateCostCenter(costCenter.getCostCenterId().intValue(),1, costCenter.getCostCenterName());
            }
        }
        //System.out.println(costCenterTable.getItems());
    }

    public void delete(){
     
        String id = costCenterTable.getSelectionModel().getSelectedItem().getCostCenterId().toString();
                CostCenterDAO centerDAO = new CostCenterDAO();
                centerDAO.deleteCostCenter(Integer.parseInt(id));
                   list.remove(costCenterTable.getSelectionModel().getSelectedItem());
        costCenterTable.setItems(list);
               // fillTable();
    }
    public void fillTable() {
        CostCenterDAO centerDAO = new CostCenterDAO();
        ArrayList<CostCenter> centers = (ArrayList) centerDAO.getAllCostCenters();
        list = FXCollections.observableArrayList();
        for (CostCenter costCenter : centers) {
            list.add(costCenter);
        }
                        filteredData.addAll(list);

        costCenterTable.setItems(filteredData);

    }

    // EditingCell - for editing capability in a TableCell
    public class EditingCell extends TableCell<CostCenter, Object> {

        private TextField textField;

        public EditingCell() {
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
            System.out.println();
            String st = getTableColumn().getText();
            CostCenter c = getTableView().getSelectionModel().getSelectedItem();
            if (st.equals("الكود")) {
                c.setCostCenterId(new BigDecimal(Integer.parseInt(String.valueOf(newValue))));
            } else if (st.equals("الإسم")) {
                c.setCostCenterName(String.valueOf(newValue));
            }
            String s = getTableRow().getText();
            String l = getTableView().getId();
            editList.add(c);
            System.out.println(editList.size());
        }

        private void createTextField() {
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
        }

        private String getString() {
            Object o = getItem();
            return getItem() == null ? "" : getItem().toString();
        }
    }
    
        private void updateFilteredData() {
        filteredData.clear();

        for (CostCenter p : list) {
            if (matchesFilter(p)) {
                filteredData.add(p);
            }
        }

        // Must re-sort table after items changed
        reapplyTableSortOrder();
    }
    private boolean matchesFilter(CostCenter center) {
        String filterString = search.getText();
        if (filterString == null || filterString.isEmpty()) {
            // No filter --> Add all.
            return true;
        }

        String lowerCaseFilterString = filterString.toLowerCase();

        if (center.getCostCenterName().toLowerCase().indexOf(lowerCaseFilterString) != -1) {
            return true;
        } 

        return false; // Does not match
    }
       private void reapplyTableSortOrder() {
        ArrayList<TableColumn<CostCenter, ?>> sortOrder = new ArrayList<>(costCenterTable.getSortOrder());
        costCenterTable.getSortOrder().clear();
        costCenterTable.getSortOrder().addAll(sortOrder);
    }
}
