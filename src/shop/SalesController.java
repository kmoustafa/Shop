/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shop;

import daos.EmployeeDAO;
import daos.ItemDAO;
import daos.ItemSerialDAO;
import daos.PersonDAO;
import daos.StoreDAO;
import java.awt.AWTException;
import java.awt.Robot;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.util.Callback;
import model.Employees;
import model.Items;
import model.ItemsSerials;
import model.Persons;
import model.Stores;

/**
 * FXML Controller class
 *
 * @author kareem.moustafa
 */
public class SalesController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
        initStoreCombo();
        initCustomerCombo();
        initEmpCombo();
        initSalesTable();
    }

    public SalesController() {
        instanse = this;
    }

    public static SalesController getInstance() {
        return instanse;
    }

    public void initCustomerCombo() {
        //Group 
        PersonDAO unitDAO = new PersonDAO();
        final List<Persons> customeres = unitDAO.getAllPersons(0);
        customers.addAll(customeres);

        customerCombo.setItems(customers);
        customerCombo.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observableValue, Object oldValue, Object newValue) {
                //Check whether item is selected and set value of selected item to Label
                if (customerCombo.getSelectionModel().getSelectedItem() != null) {
                    Persons c = (Persons) newValue;
                    customerCombo.setValue(c);
                    customerCode.setText(String.valueOf(c.getId().getPersonId()));
                }
            }
        });

        customerCombo.setEditable(true);
        customerCombo.getEditor().textProperty()
                .addListener(new ChangeListener<String>() {
                    @Override
                    public void changed(ObservableValue<? extends String> observable,
                            String oldValue, String newValue) {
                        final TextField editor = customerCombo.getEditor();
                        final Persons selected = (Persons) customerCombo.getSelectionModel()
                        .getSelectedItem();
                        if (selected == null || !selected.getPersonName().equals(editor.getText())) {
                            filterPersonItems(newValue, customerCombo, customeres);
                            customerCombo.show();
                            if (customerCombo.getItems().size() == 1) {
                                final Persons c = (Persons) customerCombo.getItems().get(0);
                                String onlyOption = c.getPersonName();
                                final String current = editor.getText();
                                if (onlyOption.length() > current.length()) {
                                    editor.setText(onlyOption);
                                    // Not quite sure why this only works using
                                    // Platform.runLater(...)
                                    Platform.runLater(new Runnable() {
                                        @Override
                                        public void run() {
                                            editor.selectRange(current.length(), onlyOption.length());
                                        }
                                    });
                                }
                            }
                        }
                    }
                });

    }

    public void initEmpCombo() {
        EmployeeDAO employeeDAO = new EmployeeDAO();
        final List<Employees> employeeses = employeeDAO.getAllEmployees();
        employees.addAll(employeeses);
        empCombo.setCellFactory(new Callback<ListView<Employees>, ListCell<Employees>>() {

            @Override
            public ListCell<Employees> call(ListView<Employees> p) {

                final ListCell<Employees> cell = new ListCell<Employees>() {

                    @Override
                    protected void updateItem(Employees t, boolean bln) {
                        super.updateItem(t, bln);

                        if (t != null) {
                            setText(t.getEmpName());
                        } else {
                            setText(null);
                        }
                    }

                };

                return cell;
            }
        });
        empCombo.setItems(employees);
        empCombo.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observableValue, Object oldValue, Object newValue) {
                //Check whether item is selected and set value of selected item to Label
                if (empCombo.getSelectionModel().getSelectedItem() != null) {
                    Employees c = (Employees) newValue;
                    empCombo.setValue(c);
                    empCode.setText(String.valueOf(c.getEmpId()));
                }
                System.out.println(storeCombo.getSelectionModel().getSelectedItem());
            }
        });
        empCombo.setEditable(true);
        empCombo.getEditor().textProperty()
                .addListener(new ChangeListener<String>() {
                    @Override
                    public void changed(ObservableValue<? extends String> observable,
                            String oldValue, String newValue) {
                        final TextField editor = empCombo.getEditor();
                        final Employees selected = (Employees) empCombo.getSelectionModel()
                        .getSelectedItem();
                        if (selected == null || !selected.getEmpName().equals(editor.getText())) {
                            filterEmployeeItems(newValue, empCombo, employeeses);
                            empCombo.show();
                            if (empCombo.getItems().size() == 1) {
                                final Employees c = (Employees) empCombo.getItems().get(0);
                                String onlyOption = c.getEmpName();
                                final String current = editor.getText();
                                if (onlyOption.length() > current.length()) {
                                    editor.setText(onlyOption);
                                    // Not quite sure why this only works using
                                    // Platform.runLater(...)
                                    Platform.runLater(new Runnable() {
                                        @Override
                                        public void run() {
                                            editor.selectRange(current.length(), onlyOption.length());
                                        }
                                    });
                                }
                            }
                        }
                    }
                });
    }

    public void initStoreCombo() {
        StoreDAO storeDAO = new StoreDAO();
        final List<Stores> storeses = storeDAO.getAllStores();
        storesList.addAll(storeses);
        storeCombo.setCellFactory(new Callback<ListView<Stores>, ListCell<Stores>>() {

            @Override
            public ListCell<Stores> call(ListView<Stores> p) {

                final ListCell<Stores> cell = new ListCell<Stores>() {

                    @Override
                    protected void updateItem(Stores t, boolean bln) {
                        super.updateItem(t, bln);

                        if (t != null) {
                            setText(t.getStoreName());
                        } else {
                            setText(null);
                        }
                    }

                };

                return cell;
            }
        });
        storeCombo.setItems(storesList);
        storeCombo.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observableValue, Object oldValue, Object newValue) {
                //Check whether item is selected and set value of selected item to Label
                if (storeCombo.getSelectionModel().getSelectedItem() != null) {
                    Stores c = (Stores) newValue;
                    storeCombo.setValue(c);
                    storeCode.setText(String.valueOf(c.getStoreId()));
                }
                System.out.println(storeCombo.getSelectionModel().getSelectedItem());
            }
        });
        storeCombo.setEditable(true);
        storeCombo.getEditor().textProperty()
                .addListener(new ChangeListener<String>() {
                    @Override
                    public void changed(ObservableValue<? extends String> observable,
                            String oldValue, String newValue) {
                        final TextField editor = storeCombo.getEditor();
                        final Stores selected = (Stores) storeCombo.getSelectionModel()
                        .getSelectedItem();
                        if (selected == null || !selected.getStoreName().equals(editor.getText())) {
                            filterItems(newValue, storeCombo, storeses);
                            storeCombo.show();
                            if (storeCombo.getItems().size() == 1) {
                                final Stores c = (Stores) storeCombo.getItems().get(0);
                                String onlyOption = c.getStoreName();
                                final String current = editor.getText();
                                if (onlyOption.length() > current.length()) {
                                    editor.setText(onlyOption);
                                    // Not quite sure why this only works using
                                    // Platform.runLater(...)
                                    Platform.runLater(new Runnable() {
                                        @Override
                                        public void run() {
                                            editor.selectRange(current.length(), onlyOption.length());
                                        }
                                    });
                                }
                            }
                        }
                    }
                });
    }

    private <T> void filterItems(String filter, ComboBox<Stores> comboBox,
            List<Stores> items) {
        List<Stores> filteredItems = new ArrayList<>();
        for (Stores item : items) {
            if (item.getStoreName().toString().toLowerCase().indexOf(filter.toLowerCase()) != -1) {
                filteredItems.add(item);
            }
        }
        comboBox.setItems(FXCollections.observableArrayList(filteredItems));
    }

    private <T> void filterPersonItems(String filter, ComboBox<Persons> comboBox,
            List<Persons> items) {
        List<Persons> filteredItems = new ArrayList<>();
        for (Persons item : items) {
            if (item.getPersonName().toString().toLowerCase().indexOf(filter.toLowerCase()) != -1) {
                filteredItems.add(item);
            }
        }
        comboBox.setItems(FXCollections.observableArrayList(filteredItems));
    }

    private <T> void filterEmployeeItems(String filter, ComboBox<Employees> comboBox,
            List<Employees> items) {
        List<Employees> filteredItems = new ArrayList<>();
        for (Employees item : items) {
            if (item.getEmpName().toString().toLowerCase().indexOf(filter.toLowerCase()) != -1) {
                filteredItems.add(item);
            }
        }
        comboBox.setItems(FXCollections.observableArrayList(filteredItems));
    }

    private void initSalesTable() {
        // Init Size Table
        mColumn.setCellValueFactory(new PropertyValueFactory("m"));
        codeColumn.setCellValueFactory(new PropertyValueFactory("code"));
        barCodeColumn.setCellValueFactory(new PropertyValueFactory("barCode"));
        itemNameColumn.setCellValueFactory(new PropertyValueFactory("itemName"));
        itemColorColumn.setCellValueFactory(new PropertyValueFactory("itemColor"));

        itemSizeColumn.setCellValueFactory(new PropertyValueFactory("itemSize"));
        itemQuantColumn.setCellValueFactory(new PropertyValueFactory("itemQuant"));
        priceColumn.setCellValueFactory(new PropertyValueFactory("price"));
        nDiscColumn.setCellValueFactory(new PropertyValueFactory("nDisc"));
        qDiscColumn.setCellValueFactory(new PropertyValueFactory("qDisc"));
        totalColumn.setCellValueFactory(new PropertyValueFactory("total"));
        balanceColumn.setCellValueFactory(new PropertyValueFactory("balance"));
        for (int i = 0; i < 100; i++) {
            tableList.add(new SalesTable(i + 1, null, null, null, null, null, 0, 0.0, 0.0, 0.0, 0.0, 0.0));
        }
        salesTable.setItems(tableList);
//        tableList.setOnMouseClicked(new EventHandler<MouseEvent>() {
//            
//            @Override
//            public void handle(MouseEvent event) {
//                if (itemPrices.isExpanded() && matrixList.size() >= 1) {
//                    if (!isEnter) {
//                        updatePrices();
//                    }
//                }
//            }
//        });
        Callback<TableColumn, TableCell> cellFactory
                = new Callback<TableColumn, TableCell>() {
                    public TableCell call(TableColumn p) {
                        return new EditingCell();
                    }
                };
        codeColumn.setCellFactory(cellFactory);
        itemQuantColumn.setCellFactory(cellFactory);
        salesTable.setEditable(true);
    }

    public class SalesTable {

        private IntegerProperty m;
        private StringProperty code;
        private StringProperty barCode;
        private StringProperty itemName;
        private StringProperty itemSize;
        private StringProperty itemColor;
        private IntegerProperty itemQuant;
        private DoubleProperty price;
        private DoubleProperty nDisc;
        private DoubleProperty qDics;
        private DoubleProperty total;
        private DoubleProperty balance;

        public SalesTable() {

        }

        public SalesTable(int m, String code, String barCode, String itemName, String itemSize, String itemColor, int itemQuant, double price, double nDisc,
                double qDisc, double total, double balance) {
            this.m = new SimpleIntegerProperty(m);
            this.code = new SimpleStringProperty(code);
            this.barCode = new SimpleStringProperty(barCode);
            this.itemName = new SimpleStringProperty(itemName);
            this.itemColor = new SimpleStringProperty(itemColor);
            this.itemQuant = new SimpleIntegerProperty(itemQuant);
            this.price = new SimpleDoubleProperty(price);
            this.nDisc = new SimpleDoubleProperty(nDisc);
            this.qDics = new SimpleDoubleProperty(qDisc);
            this.total = new SimpleDoubleProperty(total);
            this.balance = new SimpleDoubleProperty(balance);
        }

        public IntegerProperty mProperty() {
            return m;
        }

        public void setM(int m) {
            this.m.set(m);
        }

        public StringProperty codeProperty() {
            return code;
        }

        public void setCode(String code) {
            this.code.set(code);
        }

        public StringProperty barCodeProperty() {
            return barCode;
        }

        public void setBarCode(String barCode) {
            this.barCode.set(barCode);
        }

        public StringProperty itemNameProperty() {
            return itemName;
        }

        public void setItemName(String itemName) {
            this.itemName.set(itemName);
        }

        public StringProperty itemColorProperty() {
            return itemColor;
        }

        public void setItemColor(String itemColor) {
            this.itemColor.set(itemColor);
        }

        public StringProperty itemSizeProperty() {
            return itemSize;
        }

        public void setItemSize(String itemSize) {
            this.itemSize.set(itemSize);
        }

        public IntegerProperty itemQuantProperty() {
            return itemQuant;
        }

        public void setItemQuant(int itemQuant) {
            this.itemQuant.set(itemQuant);
        }

        public DoubleProperty priceProperty() {
            return price;
        }

        public void setPrice(double price) {
            this.price.set(price);
        }

        public DoubleProperty nDiscProperty() {
            return nDisc;
        }

        public void setNDisc(double nDisc) {
            this.nDisc.set(nDisc);
        }

        public DoubleProperty qDiscProperty() {
            return qDics;
        }

        public void setQDisc(double qDisc) {
            this.qDics.set(qDisc);
        }

        public DoubleProperty totalProperty() {
            return total;
        }

        public void setTotal(double total) {
            this.total.set(total);
        }

        public DoubleProperty balanceProperty() {
            return balance;
        }

        public void setBalance(double balance) {
            this.balance.set(balance);
        }
    }

    public class EditingCell extends TableCell<SalesTable, Object> {

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
            try {
                super.commitEdit(newValue);
//            String st = getTableColumn().getId();
                String st = (String) newValue;
                System.out.println("WRITED VALUE : " + newValue);
                int itemId = 0;
                try {
                    itemId = Integer.parseInt(st);
                } catch (Exception e) {
                }
   
                SalesTable z = getTableView().getSelectionModel().getSelectedItem();
                if (st.equals("codeColumn")) {
                    z.setCode(String.valueOf(newValue));
                }
//            else if (st.equals("pricesSale1Column")) {
//                z.setSale1Price(String.valueOf(newValue));
//            } else if (st.equals("pricesSale2Column")) {
//                z.setSale2Price(String.valueOf(newValue));
//            } else if (st.equals("pricesSale3Column")) {
//                z.setSale3Price(String.valueOf(newValue));
//            } else if (st.equals("pricesReqQntColumn")) {
//                z.setReqQun(String.valueOf(newValue));
//            }
                System.out.println(SalesController.this.salesTable.getItems().size());
                // SalesController.this.salesTable.getFocusModel().focus(getTableView().getFocusModel().getFocusedCell().getRow()-1, itemQuantColumn);
                SalesController.this.salesTable.getSelectionModel().select(SalesController.this.salesTable.getSelectionModel().getSelectedCells().get(0).getRow(), SalesController.this.itemQuantColumn);
                SalesController.this.salesTable.edit(SalesController.this.salesTable.getSelectionModel().getSelectedCells().get(0).getRow(), SalesController.this.itemQuantColumn);
               // SalesController.this.salesTable.getSelectionModel().selectFirst();
                //  SalesController.this.salesTable.getSelectionModel().select(getTableView().getFocusModel().getFocusedCell().getRow()-1, itemQuantColumn);
                Robot r = new Robot();
                r.keyPress(java.awt.event.KeyEvent.VK_ENTER);
//                String s = getTableRow().getText();
//                String l = getTableView().getId();

                System.out.println(SalesController.this.salesTable.getSelectionModel().getSelectedCells().get(0).getColumn());
                System.out.println(SalesController.this.salesTable.getSelectionModel().getSelectedCells().get(0).getRow());
                             if(itemId != 0 ){
                    Items item = SalesController.this.selectedItem = new ItemDAO().getItemById(itemId);
                    ItemsSerials itemsSerials = SalesController.this.selectedItemsSerials = new ItemSerialDAO().getItemSerialById(itemId);
                    int index = SalesController.this.salesTable.getSelectionModel().getSelectedIndex();
                    if(item != null && itemsSerials != null){
                        SalesController.this.salesTable.getItems().get(index).setBarCode(itemsSerials.getBarCode());
                        SalesController.this.salesTable.getItems().get(index).setItemName(item.getItemName());
                    }
                }
                //editList.add(z);
            } catch (AWTException ex) {
                Logger.getLogger(SalesController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        private void createTextField() {
            textField = new TextField(getString());
            textField.setMinWidth(this.getWidth() - this.getGraphicTextGap() * 2);
            textField.setOnKeyReleased(new EventHandler<KeyEvent>() {
                @Override
                public void handle(KeyEvent t) {
                    if (t.getCode() == KeyCode.ENTER) {
                        commitEdit(textField.getText());
                        // getTableView().getFocusModel().focus(5);
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

    public void clearData(){
        recNo.setText("");
        recDate.setValue(null);
        storeCombo.setValue(null);
        storeCode.setText("");
        customerCombo.setValue(null);
        customerCode.setText("");
        empCode.setText("");
        empCombo.setValue(null);
        transAttach.setText("");
        afterBalance.setText("");
        creditMax.setText("");
        currentBalance.setText("");
        deduce.setText("");
        expenses.setText("");
        notes.setText("");
        percDeduce.setText("");
        total.setText("");
        totalPaid.setText("");
        totalReceipt.setText("");
        salesTable.getItems().removeAll(tableList);
        tableList.removeAll(tableList);
    }
    private static SalesController instanse;

    @FXML
    TextField recNo;
    @FXML
    DatePicker recDate;
    @FXML
    ComboBox storeCombo;
    ObservableList<Stores> storesList = FXCollections.observableArrayList();
    @FXML
    TextField storeCode;
    @FXML
    ComboBox customerCombo;
    ObservableList<Persons> customers = FXCollections.observableArrayList();
    @FXML
    TextField customerCode;
    @FXML
    ComboBox empCombo;
    ObservableList<Employees> employees = FXCollections.observableArrayList();

    @FXML
    TextField empCode;
    @FXML
    TextField transAttach;
    @FXML
    TableView<SalesTable> salesTable;
    @FXML
    TableColumn mColumn;
    @FXML
    TableColumn codeColumn;
    @FXML
    TableColumn barCodeColumn;
    @FXML
    TableColumn itemNameColumn;
    @FXML
    TableColumn itemColorColumn;
    @FXML
    TableColumn itemSizeColumn;

    @FXML
    TableColumn itemQuantColumn;
    @FXML
    TableColumn priceColumn;
    @FXML
    TableColumn nDiscColumn;
    @FXML
    TableColumn qDiscColumn;
    @FXML
    TableColumn totalColumn;
    @FXML
    TableColumn balanceColumn;
    ObservableList<SalesTable> tableList = FXCollections.observableArrayList();
    @FXML
    Label creditMax;
    @FXML
    Label currentBalance;
    @FXML
    Label afterBalance;
    @FXML
    TextArea notes;
    @FXML
    TextField totalPaid;
    @FXML
    TextField total;
    @FXML
    TextField percDeduce;
    @FXML
    TextField deduce;
    @FXML
    TextField expenses;
    @FXML
    TextField totalReceipt;
    public Items selectedItem;
    public ItemsSerials selectedItemsSerials;
    
}
