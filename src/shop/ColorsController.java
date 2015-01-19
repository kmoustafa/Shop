/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package shop;

import daos.ColorDAO;
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
import model.Colors;
import model.Zone;


/**
 * FXML Controller class
 *
 * @author Kareem.Moustafa
 */
public class ColorsController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
     private static ColorsController instanse;

    @FXML
    TextField code;

    @FXML
    TextField name;

    @FXML
    TextField search;

    @FXML
    TableView<Colors> colorTable;

    @FXML
    TableColumn codeColumn;

    @FXML
    TableColumn nameColumn;
    private ObservableList<Colors> filteredData = FXCollections.observableArrayList();

    ObservableList<Colors> list;
    public ArrayList<Colors> editList;
    int selectedColumn;

    public ObservableList<Colors> getList() {
        return list;
    }

    public void setList(ObservableList<Colors> list) {
        this.list = list;
    }
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
          list = FXCollections.observableArrayList();
        editList = new ArrayList<>();
        codeColumn.setCellValueFactory(new PropertyValueFactory("colorId"));
        nameColumn.setCellValueFactory(new PropertyValueFactory("colorName"));
        // costCenerTable = new TableView<>();
        Callback<TableColumn, TableCell> cellFactory
                = new Callback<TableColumn, TableCell>() {

                    public TableCell call(TableColumn p) {
                        return new EditingCell();
                    }
                };
        codeColumn.setCellFactory(cellFactory);
        nameColumn.setCellFactory(cellFactory);

        list.addListener(new ListChangeListener<Colors>() {
            @Override
            public void onChanged(ListChangeListener.Change<? extends Colors> change) {
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
        colorTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observableValue, Object oldValue, Object newValue) {
                //Check whether item is selected and set value of selected item to Label
                if (colorTable.getSelectionModel().getSelectedItem() != null) {
                    TableView.TableViewSelectionModel selectionModel = colorTable.getSelectionModel();
                    ObservableList selectedCells = selectionModel.getSelectedCells();
                Colors colors = (Colors) selectionModel.getSelectedItem();
                    code.setText(colors.getColorId().toString());
                    name.setText(colors.getColorName());
                    TablePosition tablePosition = (TablePosition) selectedCells.get(0);

                    String st = tablePosition.getTableView().getSelectionModel().getTableView().getId();
                    //gives you selected cell value..
                    selectedColumn = tablePosition.getColumn();
                    Object GetSinglevalue = tablePosition.getTableColumn().getCellData(newValue);

                    BigDecimal getbothvalue = colorTable.getSelectionModel().getSelectedItem().getColorId();
                    //gives you first column value..
                    String Finalvaluetablerow = getbothvalue.toString().split(",")[0].substring(1);
                    System.out.println("The First column value of row.." + Finalvaluetablerow);
                }
            }
        });

    }    
    
    public ColorsController() {
        instanse = this;
    }

    public static ColorsController getInstance() {
        return instanse;
    }

    public void handleNew() {
        clearFields();
        code.setEditable(false);
        name.setEditable(true);
        ColorDAO colorDAO = new ColorDAO();
        this.code.setText(String.valueOf(colorDAO.getLastIndex() + 1));

    }

    public void save(int operation) {
        ColorDAO colorDAO = new ColorDAO();
        if (operation == 1) {
            int id = colorDAO.insertColor(Integer.valueOf(code.getText()), name.getText());
            Colors color = colorDAO.getColorById(id);
            list.add(color);
           // zoneTable.setItems(list);
     //   fillTable();
            //handleNew();
            updateFilteredData();
            clearFields();
        } else if (operation == 2) {
            colorDAO.updateColor(Integer.valueOf(code.getText()), name.getText());

            colorTable.getSelectionModel().getSelectedItem().setColorName(name.getText());
            updateFilteredData();
        }
    }

    public void update() {
        name.setEditable(true);
//        ZoneDAO zoneDAO = new ZoneDAO();
//        if (!editList.isEmpty()) {
//            for (Zone zone : editList) {
//                zoneDAO.updateZone(zone.getZoneId().intValue(), zone.getZoneName());
//            }
//        }
        //System.out.println(costCenterTable.getItems());
    }

    public void delete() {

        String id = colorTable.getSelectionModel().getSelectedItem().getColorId().toString();
        ColorDAO colorDAO = new ColorDAO();
        colorDAO.deleteColor(Integer.parseInt(id));
        list.remove(colorTable.getSelectionModel().getSelectedItem());
        // zoneTable.setItems(list);
        updateFilteredData();

        // fillTable();
    }

    public void fillTable() {
        ColorDAO colorDAO = new ColorDAO();
        ArrayList<Colors> colors = (ArrayList) colorDAO.getAllColors();
        list = FXCollections.observableArrayList();
        for (Colors z : colors) {
            list.add(z);
        }
        filteredData.addAll(list);

        colorTable.setItems(filteredData);

    }

    // EditingCell - for editing capability in a TableCell
    public class EditingCell extends TableCell<Colors, Object> {

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
            Colors z = getTableView().getSelectionModel().getSelectedItem();
            if (st.equals("الكود")) {
                z.setColorId(new BigDecimal(Integer.parseInt(String.valueOf(newValue))));
            } else if (st.equals("الإسم")) {
                z.setColorName(String.valueOf(newValue));
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

        for (Colors p : list) {
            if (matchesFilter(p)) {
                filteredData.add(p);
            }
        }

        // Must re-sort table after items changed
        reapplyTableSortOrder();
    }

    private boolean matchesFilter(Colors z) {
        String filterString = search.getText();
        if (filterString == null || filterString.isEmpty()) {
            // No filter --> Add all.
            return true;
        }

        String lowerCaseFilterString = filterString.toLowerCase();

        if (z.getColorName().toLowerCase().indexOf(lowerCaseFilterString) != -1) {
            return true;
        }

        return false; // Does not match
    }

    private void reapplyTableSortOrder() {
        ArrayList<TableColumn<Colors, ?>> sortOrder = new ArrayList<>(colorTable.getSortOrder());
        colorTable.getSortOrder().clear();
        colorTable.getSortOrder().addAll(sortOrder);
    }

    private void clearFields() {
        this.code.setText("");
        this.name.setText("");
    }
}
