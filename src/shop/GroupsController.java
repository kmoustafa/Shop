/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package shop;

import daos.GroupDAO;
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
import model.Groups;

/**
 * FXML Controller class
 *
 * @author Kareem.Moustafa
 */
public class GroupsController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
     private static GroupsController instanse;

    @FXML
    TextField code;

    @FXML
    TextField name;

    @FXML
    TextField search;

    @FXML
    TableView<Groups> groupTable;

    @FXML
    TableColumn codeColumn;

    @FXML
    TableColumn nameColumn;
    private ObservableList<Groups> filteredData = FXCollections.observableArrayList();

    ObservableList<Groups> list;
    public ArrayList<Groups> editList;
    int selectedColumn;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
          list = FXCollections.observableArrayList();
        editList = new ArrayList<>();
        codeColumn.setCellValueFactory(new PropertyValueFactory("groupId"));
        nameColumn.setCellValueFactory(new PropertyValueFactory("groupName"));
        // costCenerTable = new TableView<>();
        Callback<TableColumn, TableCell> cellFactory
                = new Callback<TableColumn, TableCell>() {

                    public TableCell call(TableColumn p) {
                        return new EditingCell();
                    }
                };
        codeColumn.setCellFactory(cellFactory);
        nameColumn.setCellFactory(cellFactory);

        list.addListener(new ListChangeListener<Groups>() {
            @Override
            public void onChanged(ListChangeListener.Change<? extends Groups> change) {
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
        groupTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observableValue, Object oldValue, Object newValue) {
                //Check whether item is selected and set value of selected item to Label
                if (groupTable.getSelectionModel().getSelectedItem() != null) {
                    TableView.TableViewSelectionModel selectionModel = groupTable.getSelectionModel();
                    ObservableList selectedCells = selectionModel.getSelectedCells();
                    Groups z = (Groups) selectionModel.getSelectedItem();
                    code.setText(z.getGroupId().toString());
                    name.setText(z.getGroupName());
                    TablePosition tablePosition = (TablePosition) selectedCells.get(0);

                    String st = tablePosition.getTableView().getSelectionModel().getTableView().getId();
                    //gives you selected cell value..
                    selectedColumn = tablePosition.getColumn();
                    Object GetSinglevalue = tablePosition.getTableColumn().getCellData(newValue);

                    BigDecimal getbothvalue = groupTable.getSelectionModel().getSelectedItem().getGroupId();
                    //gives you first column value..
                    String Finalvaluetablerow = getbothvalue.toString().split(",")[0].substring(1);
                    System.out.println("The First column value of row.." + Finalvaluetablerow);
                }
            }
        });

    }    
    
    public GroupsController() {
        instanse = this;
    }

    public static GroupsController getInstance() {
        return instanse;
    }

    public void handleNew() {
        clearFields();
        code.setEditable(false);
        name.setEditable(true);
        GroupDAO groupDAO = new GroupDAO();
        this.code.setText(String.valueOf(groupDAO.getLastIndex() + 1));

    }

    public void save(int operation) {
        GroupDAO groupDAO = new GroupDAO();
        if (operation == 1) {
            int id = groupDAO.insertGroup(Integer.valueOf(code.getText()), name.getText());
            Groups group = groupDAO.getGroupById(id);
            list.add(group);
           // groupTable.setItems(list);
     //   fillTable();
            //handleNew();
            updateFilteredData();
                        clearFields();

        } else if (operation == 2) {
            groupDAO.updateGroup(Integer.valueOf(code.getText()), name.getText());

            groupTable.getSelectionModel().getSelectedItem().setGroupName(name.getText());
            updateFilteredData();
        }
    }

    public void update() {
        name.setEditable(true);
//        GroupDAO groupDAO = new GroupDAO();
//        if (!editList.isEmpty()) {
//            for (Groups group : editList) {
//                groupDAO.updateGroups(group.getGroupsId().intValue(), group.getGroupsName());
//            }
//        }
        //System.out.println(costCenterTable.getItems());
    }

    public void delete() {

        String id = groupTable.getSelectionModel().getSelectedItem().getGroupId().toString();
        GroupDAO groupDAO = new GroupDAO();
        groupDAO.deleteGroup(Integer.parseInt(id));
        list.remove(groupTable.getSelectionModel().getSelectedItem());
        // groupTable.setItems(list);
        updateFilteredData();

        // fillTable();
    }

    public void fillTable() {
        GroupDAO groupDAO = new GroupDAO();
        ArrayList<Groups> groups = (ArrayList) groupDAO.getAllGroups();
        list = FXCollections.observableArrayList();
        for (Groups z : groups) {
            list.add(z);
        }
        filteredData.addAll(list);

        groupTable.setItems(filteredData);

    }

    // EditingCell - for editing capability in a TableCell
    public class EditingCell extends TableCell<Groups, Object> {

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
            Groups z = getTableView().getSelectionModel().getSelectedItem();
            if (st.equals("الكود")) {
                z.setGroupId(new BigDecimal(Integer.parseInt(String.valueOf(newValue))));
            } else if (st.equals("الإسم")) {
                z.setGroupName(String.valueOf(newValue));
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

        for (Groups p : list) {
            if (matchesFilter(p)) {
                filteredData.add(p);
            }
        }

        // Must re-sort table after items changed
        reapplyTableSortOrder();
    }

    private boolean matchesFilter(Groups z) {
        String filterString = search.getText();
        if (filterString == null || filterString.isEmpty()) {
            // No filter --> Add all.
            return true;
        }

        String lowerCaseFilterString = filterString.toLowerCase();

        if (z.getGroupName().toLowerCase().indexOf(lowerCaseFilterString) != -1) {
            return true;
        }

        return false; // Does not match
    }

    private void reapplyTableSortOrder() {
        ArrayList<TableColumn<Groups, ?>> sortOrder = new ArrayList<>(groupTable.getSortOrder());
        groupTable.getSortOrder().clear();
        groupTable.getSortOrder().addAll(sortOrder);
    }

    private void clearFields() {
        this.code.setText("");
        this.name.setText("");
    }
}
