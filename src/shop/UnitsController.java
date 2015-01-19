/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package shop;

import daos.UnitDAO;
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
import model.Units;

/**
 * FXML Controller class
 *
 * @author Kareem.Moustafa
 */
public class UnitsController implements Initializable {

    /**
     * Initializes the controller class.
     */
    private static UnitsController instanse;

    @FXML
    TextField code;

    @FXML
    TextField name;

    @FXML
    TextField search;

    @FXML
    TableView<Units> unitTable;

    @FXML
    TableColumn codeColumn;

    @FXML
    TableColumn nameColumn;
    private ObservableList<Units> filteredData = FXCollections.observableArrayList();

    ObservableList<Units> list;
    public ArrayList<Units> editList;
    int selectedColumn;

    public ObservableList<Units> getList() {
        return list;
    }

    public void setList(ObservableList<Units> list) {
        this.list = list;
    }

    public ArrayList<Units> getEditList() {
        return editList;
    }

    public void setEditList(ArrayList<Units> editList) {
        this.editList = editList;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        list = FXCollections.observableArrayList();
        editList = new ArrayList<>();
        codeColumn.setCellValueFactory(new PropertyValueFactory("unitId"));
        nameColumn.setCellValueFactory(new PropertyValueFactory("unitName"));
        // costCenerTable = new TableView<>();
        Callback<TableColumn, TableCell> cellFactory
                = new Callback<TableColumn, TableCell>() {

                    public TableCell call(TableColumn p) {
                        return new EditingCell();
                    }
                };
        codeColumn.setCellFactory(cellFactory);
        nameColumn.setCellFactory(cellFactory);

        list.addListener(new ListChangeListener<Units>() {
            @Override
            public void onChanged(ListChangeListener.Change<? extends Units> change) {
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
        unitTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observableValue, Object oldValue, Object newValue) {
                //Check whether item is selected and set value of selected item to Label
                if (unitTable.getSelectionModel().getSelectedItem() != null) {
                    TableView.TableViewSelectionModel selectionModel = unitTable.getSelectionModel();
                    ObservableList selectedCells = selectionModel.getSelectedCells();
                    Units z = (Units) selectionModel.getSelectedItem();
                    code.setText(z.getUnitId().toString());
                    name.setText(z.getUnitName());
                    TablePosition tablePosition = (TablePosition) selectedCells.get(0);

                    String st = tablePosition.getTableView().getSelectionModel().getTableView().getId();
                    //gives you selected cell value..
                    selectedColumn = tablePosition.getColumn();
                    Object GetSinglevalue = tablePosition.getTableColumn().getCellData(newValue);

                    BigDecimal getbothvalue = unitTable.getSelectionModel().getSelectedItem().getUnitId();
                    //gives you first column value..
                    String Finalvaluetablerow = getbothvalue.toString().split(",")[0].substring(1);
                    System.out.println("The First column value of row.." + Finalvaluetablerow);
                }
            }
        });

    }

    public UnitsController() {
        instanse = this;
    }

    public static UnitsController getInstance() {
        return instanse;
    }

    public void handleNew() {
        clearFields();
        code.setEditable(false);
        name.setEditable(true);
        UnitDAO unitDAO = new UnitDAO();
        this.code.setText(String.valueOf(unitDAO.getLastIndex() + 1));

    }

    public void save(int operation) {
        UnitDAO unitDAO = new UnitDAO();
        if (operation == 1) {
            int id = unitDAO.insertUnit(Integer.valueOf(code.getText()), name.getText());
            Units unit = unitDAO.getUnitById(id);
            list.add(unit);
           // unitTable.setItems(list);
     //   fillTable();
            //handleNew();
            updateFilteredData();
                        clearFields();

        } else if (operation == 2) {
            unitDAO.updateUnit(Integer.valueOf(code.getText()), name.getText());

            unitTable.getSelectionModel().getSelectedItem().setUnitName(name.getText());
            updateFilteredData();
        }
    }

    public void update() {
        name.setEditable(true);
//        UnitDAO unitDAO = new UnitDAO();
//        if (!editList.isEmpty()) {
//            for (Unit unit : editList) {
//                unitDAO.updateUnit(unit.getUnitId().intValue(), unit.getUnitName());
//            }
//        }
        //System.out.println(costCenterTable.getItems());
    }

    public void delete() {

        String id = unitTable.getSelectionModel().getSelectedItem().getUnitId().toString();
        UnitDAO unitDAO = new UnitDAO();
        unitDAO.deleteUnit(Integer.parseInt(id));
        list.remove(unitTable.getSelectionModel().getSelectedItem());
        // unitTable.setItems(list);
        updateFilteredData();

        // fillTable();
    }

    public void fillTable() {
        UnitDAO unitDAO = new UnitDAO();
        ArrayList<Units> units = (ArrayList) unitDAO.getAllUnits();
        list = FXCollections.observableArrayList();
        for (Units z : units) {
            list.add(z);
        }
        filteredData.addAll(list);

        unitTable.setItems(filteredData);

    }

    // EditingCell - for editing capability in a TableCell
    public class EditingCell extends TableCell<Units, Object> {

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
            String st = getTableColumn().getText();
            Units z = getTableView().getSelectionModel().getSelectedItem();
            if (st.equals("الكود")) {
                z.setUnitId(new BigDecimal(Integer.parseInt(String.valueOf(newValue))));
            } else if (st.equals("الإسم")) {
                z.setUnitName(String.valueOf(newValue));
            }
            String s = getTableRow().getText();
            String l = getTableView().getId();
            editList.add(z);
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

        for (Units p : list) {
            if (matchesFilter(p)) {
                filteredData.add(p);
            }
        }

        // Must re-sort table after items changed
        reapplyTableSortOrder();
    }

    private boolean matchesFilter(Units z) {
        String filterString = search.getText();
        if (filterString == null || filterString.isEmpty()) {
            // No filter --> Add all.
            return true;
        }

        String lowerCaseFilterString = filterString.toLowerCase();

        if (z.getUnitName().toLowerCase().indexOf(lowerCaseFilterString) != -1) {
            return true;
        }

        return false; // Does not match
    }

    private void reapplyTableSortOrder() {
        ArrayList<TableColumn<Units, ?>> sortOrder = new ArrayList<>(unitTable.getSortOrder());
        unitTable.getSortOrder().clear();
        unitTable.getSortOrder().addAll(sortOrder);
    }

    private void clearFields() {
        this.code.setText("");
        this.name.setText("");
    }
}
