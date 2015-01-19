/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package shop;

import daos.TypeDAO;
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
import model.Types;

/**
 * FXML Controller class
 *
 * @author Kareem.Moustafa
 */
public class TypesController implements Initializable {

    /**
     * Initializes the controller class.
     */
    private static TypesController instanse;

    @FXML
    TextField code;

    @FXML
    TextField name;

    @FXML
    TextField search;

    @FXML
    TableView<Types> typeTable;

    @FXML
    TableColumn codeColumn;

    @FXML
    TableColumn nameColumn;
    private ObservableList<Types> filteredData = FXCollections.observableArrayList();

    ObservableList<Types> list;
    public ArrayList<Types> editList;
    int selectedColumn;

    public ObservableList<Types> getList() {
        return list;
    }

    public void setList(ObservableList<Types> list) {
        this.list = list;
    }

    public ArrayList<Types> getEditList() {
        return editList;
    }

    public void setEditList(ArrayList<Types> editList) {
        this.editList = editList;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        list = FXCollections.observableArrayList();
        editList = new ArrayList<>();
        codeColumn.setCellValueFactory(new PropertyValueFactory("typeId"));
        nameColumn.setCellValueFactory(new PropertyValueFactory("typeName"));
        // costCenerTable = new TableView<>();
        Callback<TableColumn, TableCell> cellFactory
                = new Callback<TableColumn, TableCell>() {

                    public TableCell call(TableColumn p) {
                        return new EditingCell();
                    }
                };
        codeColumn.setCellFactory(cellFactory);
        nameColumn.setCellFactory(cellFactory);

        list.addListener(new ListChangeListener<Types>() {
            @Override
            public void onChanged(ListChangeListener.Change<? extends Types> change) {
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
        typeTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observableValue, Object oldValue, Object newValue) {
                //Check whether item is selected and set value of selected item to Label
                if (typeTable.getSelectionModel().getSelectedItem() != null) {
                    TableView.TableViewSelectionModel selectionModel = typeTable.getSelectionModel();
                    ObservableList selectedCells = selectionModel.getSelectedCells();
                    Types z = (Types) selectionModel.getSelectedItem();
                    code.setText(z.getTypeId().toString());
                    name.setText(z.getTypeName());
                    TablePosition tablePosition = (TablePosition) selectedCells.get(0);

                    String st = tablePosition.getTableView().getSelectionModel().getTableView().getId();
                    //gives you selected cell value..
                    selectedColumn = tablePosition.getColumn();
                    Object GetSinglevalue = tablePosition.getTableColumn().getCellData(newValue);

                    BigDecimal getbothvalue = typeTable.getSelectionModel().getSelectedItem().getTypeId();
                    //gives you first column value..
                    String Finalvaluetablerow = getbothvalue.toString().split(",")[0].substring(1);
                    System.out.println("The First column value of row.." + Finalvaluetablerow);
                }
            }
        });

    }

    public TypesController() {
        instanse = this;
    }

    public static TypesController getInstance() {
        return instanse;
    }

    public void handleNew() {
        clearFields();
        code.setEditable(false);
        name.setEditable(true);
        TypeDAO typeDAO = new TypeDAO();
        this.code.setText(String.valueOf(typeDAO.getLastIndex() + 1));

    }

    public void save(int operation) {
        TypeDAO typeDAO = new TypeDAO();
        if (operation == 1) {
            int id = typeDAO.insertType(Integer.valueOf(code.getText()), name.getText());
            Types type = typeDAO.getTypeById(id);
            list.add(type);
           // typeTable.setItems(list);
     //   fillTable();
            //handleNew();
            updateFilteredData();
                        clearFields();

        } else if (operation == 2) {
            typeDAO.updateType(Integer.valueOf(code.getText()), name.getText());

            typeTable.getSelectionModel().getSelectedItem().setTypeName(name.getText());
            updateFilteredData();
        }
    }

    public void update() {
        name.setEditable(true);
//        TypeDAO typeDAO = new TypeDAO();
//        if (!editList.isEmpty()) {
//            for (Type type : editList) {
//                typeDAO.updateType(type.getTypeId().intValue(), type.getTypeName());
//            }
//        }
        //System.out.println(costCenterTable.getItems());
    }

    public void delete() {

        String id = typeTable.getSelectionModel().getSelectedItem().getTypeId().toString();
        TypeDAO typeDAO = new TypeDAO();
        typeDAO.deleteType(Integer.parseInt(id));
        list.remove(typeTable.getSelectionModel().getSelectedItem());
        // typeTable.setItems(list);
        updateFilteredData();

        // fillTable();
    }

    public void fillTable() {
        TypeDAO typeDAO = new TypeDAO();
        ArrayList<Types> types = (ArrayList) typeDAO.getAllTypes();
        list = FXCollections.observableArrayList();
        for (Types z : types) {
            list.add(z);
        }
        filteredData.addAll(list);

        typeTable.setItems(filteredData);

    }

    // EditingCell - for editing capability in a TableCell
    public class EditingCell extends TableCell<Types, Object> {

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
            Types z = getTableView().getSelectionModel().getSelectedItem();
            if (st.equals("الكود")) {
                z.setTypeId(new BigDecimal(Integer.parseInt(String.valueOf(newValue))));
            } else if (st.equals("الإسم")) {
                z.setTypeName(String.valueOf(newValue));
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

        for (Types p : list) {
            if (matchesFilter(p)) {
                filteredData.add(p);
            }
        }

        // Must re-sort table after items changed
        reapplyTableSortOrder();
    }

    private boolean matchesFilter(Types z) {
        String filterString = search.getText();
        if (filterString == null || filterString.isEmpty()) {
            // No filter --> Add all.
            return true;
        }

        String lowerCaseFilterString = filterString.toLowerCase();

        if (z.getTypeName().toLowerCase().indexOf(lowerCaseFilterString) != -1) {
            return true;
        }

        return false; // Does not match
    }

    private void reapplyTableSortOrder() {
        ArrayList<TableColumn<Types, ?>> sortOrder = new ArrayList<>(typeTable.getSortOrder());
        typeTable.getSortOrder().clear();
        typeTable.getSortOrder().addAll(sortOrder);
    }

    private void clearFields() {
        this.code.setText("");
        this.name.setText("");
    }   
    
}
