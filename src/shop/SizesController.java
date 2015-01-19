/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package shop;

import daos.SizeDAO;
import daos.SizeDAO;
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
import model.Sizes;


/**
 * FXML Controller class
 *
 * @author Kareem.Moustafa
 */
public class SizesController implements Initializable {

    /**
     * Initializes the controller class.
     */
   private static SizesController instanse;

    @FXML
    TextField code;

    @FXML
    TextField name;

    @FXML
    TextField search;

    @FXML
    TableView<Sizes> sizeTable;

    @FXML
    TableColumn codeColumn;

    @FXML
    TableColumn nameColumn;
    private ObservableList<Sizes> filteredData = FXCollections.observableArrayList();

    ObservableList<Sizes> list;
    public ArrayList<Sizes> editList;
    int selectedColumn;  
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
            list = FXCollections.observableArrayList();
        editList = new ArrayList<>();
        codeColumn.setCellValueFactory(new PropertyValueFactory("sizeId"));
        nameColumn.setCellValueFactory(new PropertyValueFactory("sizeName"));
        // costCenerTable = new TableView<>();
        Callback<TableColumn, TableCell> cellFactory
                = new Callback<TableColumn, TableCell>() {

                    public TableCell call(TableColumn p) {
                        return new EditingCell();
                    }
                };
        codeColumn.setCellFactory(cellFactory);
        nameColumn.setCellFactory(cellFactory);

        list.addListener(new ListChangeListener<Sizes>() {
            @Override
            public void onChanged(ListChangeListener.Change<? extends Sizes> change) {
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
        sizeTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observableValue, Object oldValue, Object newValue) {
                //Check whether item is selected and set value of selected item to Label
                if (sizeTable.getSelectionModel().getSelectedItem() != null) {
                    TableView.TableViewSelectionModel selectionModel = sizeTable.getSelectionModel();
                    ObservableList selectedCells = selectionModel.getSelectedCells();
                    Sizes z = (Sizes) selectionModel.getSelectedItem();
                    code.setText(z.getSizeId().toString());
                    name.setText(z.getSizeName());
                    TablePosition tablePosition = (TablePosition) selectedCells.get(0);

                    String st = tablePosition.getTableView().getSelectionModel().getTableView().getId();
                    //gives you selected cell value..
                    selectedColumn = tablePosition.getColumn();
                    Object GetSinglevalue = tablePosition.getTableColumn().getCellData(newValue);

                    BigDecimal getbothvalue = sizeTable.getSelectionModel().getSelectedItem().getSizeId();
                    //gives you first column value..
                    String Finalvaluetablerow = getbothvalue.toString().split(",")[0].substring(1);
                    System.out.println("The First column value of row.." + Finalvaluetablerow);
                }
            }
        });

    }    


    public SizesController() {
        instanse = this;
    }

    public static SizesController getInstance() {
        return instanse;
    }

    public void handleNew() {
        clearFields();
        code.setEditable(false);
        name.setEditable(true);
        SizeDAO sizeDAO = new SizeDAO();
        this.code.setText(String.valueOf(sizeDAO.getLastIndex() + 1));

    }

    public void save(int operation) {
        SizeDAO sizeDAO = new SizeDAO();
        if (operation == 1) {
            int id = sizeDAO.insertSize(Integer.valueOf(code.getText()), name.getText());
            Sizes size = sizeDAO.getSizeById(id);
            list.add(size);
           // sizeTable.setItems(list);
     //   fillTable();
            //handleNew();
            updateFilteredData();
            clearFields();

        } else if (operation == 2) {
            sizeDAO.updateSize(Integer.valueOf(code.getText()), name.getText());

            sizeTable.getSelectionModel().getSelectedItem().setSizeName(name.getText());
            updateFilteredData();
        }
    }

    public void update() {
        name.setEditable(true);
//        SizeDAO sizeDAO = new SizeDAO();
//        if (!editList.isEmpty()) {
//            for (Size size : editList) {
//                sizeDAO.updateSize(size.getSizeId().intValue(), size.getSizeName());
//            }
//        }
        //System.out.println(costCenterTable.getItems());
    }

    public void delete() {

        String id = sizeTable.getSelectionModel().getSelectedItem().getSizeId().toString();
        SizeDAO sizeDAO = new SizeDAO();
        sizeDAO.deleteSize(Integer.parseInt(id));
        list.remove(sizeTable.getSelectionModel().getSelectedItem());
        // sizeTable.setItems(list);
        updateFilteredData();

        // fillTable();
    }

    public void fillTable() {
        SizeDAO sizeDAO = new SizeDAO();
        ArrayList<Sizes> sizes = (ArrayList) sizeDAO.getAllSizes();
        list = FXCollections.observableArrayList();
        for (Sizes z : sizes) {
            list.add(z);
        }
        filteredData.addAll(list);

        sizeTable.setItems(filteredData);

    }

    // EditingCell - for editing capability in a TableCell
    public class EditingCell extends TableCell<Sizes, Object> {

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
            Sizes z = getTableView().getSelectionModel().getSelectedItem();
            if (st.equals("الكود")) {
                z.setSizeId(new BigDecimal(Integer.parseInt(String.valueOf(newValue))));
            } else if (st.equals("الإسم")) {
                z.setSizeName(String.valueOf(newValue));
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

        for (Sizes p : list) {
            if (matchesFilter(p)) {
                filteredData.add(p);
            }
        }

        // Must re-sort table after items changed
        reapplyTableSortOrder();
    }

    private boolean matchesFilter(Sizes z) {
        String filterString = search.getText();
        if (filterString == null || filterString.isEmpty()) {
            // No filter --> Add all.
            return true;
        }

        String lowerCaseFilterString = filterString.toLowerCase();

        if (z.getSizeName().toLowerCase().indexOf(lowerCaseFilterString) != -1) {
            return true;
        }

        return false; // Does not match
    }

    private void reapplyTableSortOrder() {
        ArrayList<TableColumn<Sizes, ?>> sortOrder = new ArrayList<>(sizeTable.getSortOrder());
        sizeTable.getSortOrder().clear();
        sizeTable.getSortOrder().addAll(sortOrder);
    }

    private void clearFields() {
        this.code.setText("");
        this.name.setText("");
    }    
}
