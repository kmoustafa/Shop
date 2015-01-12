/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shop;

import daos.CostCenterDAO;
import daos.ExpenceDAO;
import java.math.BigDecimal;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
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
import model.CostCenter;
import model.Expences;

/**
 * FXML Controller class
 *
 * @author Kareem.Moustafa
 */
public class ExpensesController implements Initializable {

    /**
     * Initializes the controller class.
     */
    private static ExpensesController instanse;

    ObservableList<Expences> list;
    public ArrayList<Expences> editList;
    @FXML
    TextField expCode;
    @FXML
    TextField expName;
    @FXML
    TextField personalAccount;
    @FXML
    TableView<Expences> expTable;
    @FXML
    TableColumn codeColumn;
    @FXML
    TableColumn nameColumn;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        list = FXCollections.observableArrayList();
        editList = new ArrayList<>();
        codeColumn.setCellValueFactory(new PropertyValueFactory("expenceId"));
        nameColumn.setCellValueFactory(new PropertyValueFactory("expenceName"));
        // costCenerTable = new TableView<>();
        Callback<TableColumn, TableCell> cellFactory
                = new Callback<TableColumn, TableCell>() {

                    public TableCell call(TableColumn p) {
                        return new EditingCell();
                    }
                };
        codeColumn.setCellFactory(cellFactory);
        nameColumn.setCellFactory(cellFactory);

        expCode.setEditable(false);
        expName.setEditable(false);
        expTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observableValue, Object oldValue, Object newValue) {
                //Check whether item is selected and set value of selected item to Label
                if (expTable.getSelectionModel().getSelectedItem() != null) {
                    TableView.TableViewSelectionModel selectionModel = expTable.getSelectionModel();
                    ObservableList selectedCells = selectionModel.getSelectedCells();
                    Expences c = (Expences) selectionModel.getSelectedItem();
                    expCode.setText(c.getExpenceId().toString());
                    expName.setText(c.getExpenceName());
                    TablePosition tablePosition = (TablePosition) selectedCells.get(0);

                    String st = tablePosition.getTableView().getSelectionModel().getTableView().getId();
                    //gives you selected cell value..
                    //selectedColumn = tablePosition.getColumn();
                    Object GetSinglevalue = tablePosition.getTableColumn().getCellData(newValue);

                    BigDecimal getbothvalue = expTable.getSelectionModel().getSelectedItem().getExpenceId();
                    //gives you first column value..
                    String Finalvaluetablerow = getbothvalue.toString().split(",")[0].substring(1);
                    System.out.println("The First column value of row.." + Finalvaluetablerow);
                }
            }
        });
    }

    public ExpensesController() {
        instanse = this;
    }

    public static ExpensesController getInstance() {
        return instanse;
    }

    public ObservableList<Expences> getList() {
        return list;
    }

    public void setList(ObservableList<Expences> list) {
        this.list = list;
    }

    public ArrayList<Expences> getEditList() {
        return editList;
    }

    public void setEditList(ArrayList<Expences> editList) {
        this.editList = editList;
    }

    public TableColumn getCodeColumn() {
        return codeColumn;
    }

    public void setCodeColumn(TableColumn codeColumn) {
        this.codeColumn = codeColumn;
    }

    public void handleNew() {
        expCode.setEditable(true);
        expName.setEditable(true);
        ExpenceDAO expenceDAO = new ExpenceDAO();
        this.expCode.setText(String.valueOf(expenceDAO.getLastIndex()));
    }

    public void save() {
        ExpenceDAO expenceDAO = new ExpenceDAO();
        int id = expenceDAO.insertExpence(1, 0, expName.getText());
        Expences expences = expenceDAO.getExpenceById(id);
        list.add(expences);
        expTable.setItems(list);
        //   fillTable();
        handleNew();
    }
    public void update() {
        ExpenceDAO expenceDAO = new ExpenceDAO();
        if (!editList.isEmpty()) {
            for (Expences expences : editList) {
                expenceDAO.updateExpence(expences.getExpenceId().intValue(),1,0, expences.getExpenceName());
            }
        }
        //System.out.println(costCenterTable.getItems());
    }
        public void delete(){

        String id = expTable.getSelectionModel().getSelectedItem().getExpenceId().toString();
        ExpenceDAO expenceDAO = new ExpenceDAO();
        expenceDAO.deleteExpence(Integer.parseInt(id));
                list.remove(expTable.getSelectionModel().getSelectedItem());
        expTable.setItems(list);
               // fillTable();
    }
    public void fillTable() {
        ExpenceDAO expenceDAO = new ExpenceDAO();
        ArrayList<Expences> expenceses = (ArrayList) expenceDAO.getAllExpences();
        list = FXCollections.observableArrayList();
        for (Expences expences : expenceses) {
            list.add(expences);
        }
        expTable.setItems(list);

    }

    public class EditingCell extends TableCell<Expences, Object> {

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
            Expences c = getTableView().getSelectionModel().getSelectedItem();
            if (st.equals("الكود")) {
                c.setExpenceId(new BigDecimal(Integer.parseInt(String.valueOf(newValue))));
            } else if (st.equals("إسم المصروف")) {
                c.setExpenceName(String.valueOf(newValue));
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
}
