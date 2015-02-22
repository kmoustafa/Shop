/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shop;

import daos.GroupDAO;
import daos.*;
import java.io.File;
import java.math.BigDecimal;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.util.Callback;

import model.Charts;
import model.Employees;
import model.Groups;
import model.Persons;
import model.*;
import model.Types;
import model.Units;
import org.hibernate.persister.entity.OuterJoinLoadable;

/**
 * FXML Controller class
 *
 * @author kareem.moustafa
 */
public class ItemsController implements Initializable {

    /**
     * Initializes the controller class.
     */
    private static ItemsController instanse;
    
    @FXML
    AnchorPane mainPane;
    @FXML
    Accordion accordion;
    @FXML
    TextField itemCode;
    @FXML
    TextField itemName;
    @FXML
    TextField barCode;
    @FXML
    TitledPane mainInfo;
    @FXML
    TitledPane itemsMatrix;
    @FXML
    TitledPane itemPrices;
    @FXML
    TitledPane image;
    @FXML
    ComboBox venCombo;
    @FXML
    TextField venCode;
    ObservableList<Persons> vendors = FXCollections.observableArrayList();
    @FXML
    ComboBox secCombo;
    @FXML
    TextField secCode;
    ObservableList<Sections> sections = FXCollections.observableArrayList();
    @FXML
    ComboBox groupCombo;
    @FXML
    TextField groupCode;
    ObservableList<Groups> groups = FXCollections.observableArrayList();
    @FXML
    ComboBox typeCombo;
    @FXML
    TextField typeCode;
    ObservableList<Types> types = FXCollections.observableArrayList();
    @FXML
    ComboBox unitCombo;
    @FXML
    TextField unitCode;
    ObservableList<Units> units = FXCollections.observableArrayList();
    @FXML
    TextField cost;
    @FXML
    TextField percentage;
    @FXML
    TextField reqQuant;
    @FXML
    TextField sales1;
    @FXML
    TextField sales2;
    @FXML
    TextField sales3;
    @FXML
    RadioButton stored;
    @FXML
    RadioButton service;
    @FXML
    RadioButton matrix;
    ToggleGroup toggleGroup;
    
    @FXML
    TableView<ItemColorsTable> colorsTable;
    @FXML
    TableColumn codeColumn;
    @FXML
    TableColumn nameColumn;
    @FXML
    TableColumn checked;
    ObservableList<ItemColorsTable> colorsList = FXCollections.observableArrayList();
    ArrayList<ItemColorsTable> selectedColors;
    @FXML
    TableView<ItemSizesTable> sizesTable;
    @FXML
    TableColumn sizeCodeColumn;
    @FXML
    TableColumn sizeNameColumn;
    @FXML
    TableColumn sizeChecked;
    ObservableList<ItemSizesTable> sizesList = FXCollections.observableArrayList();
    ArrayList<ItemSizesTable> selectedSizes;
    @FXML
    TableView<ItemMatrixTable> matrixTable;
    @FXML
    TableColumn matrixMColumn;
    @FXML
    TableColumn matrixCodeColumn;
    @FXML
    TableColumn matrixItemNameColumn;
    @FXML
    TableColumn matrixColorNameColumn;
    @FXML
    TableColumn matrixSizeNameColumn;
    ObservableList<ItemMatrixTable> matrixList = FXCollections.observableArrayList();
    @FXML
    TableView<ItemPricesTable> pricesTable;
    @FXML
    TableColumn pricesBuyPriceColumn;
    @FXML
    TableColumn pricesCodeColumn;
    @FXML
    TableColumn pricesSale1Column;
    @FXML
    TableColumn pricesSale2Column;
    @FXML
    TableColumn pricesSale3Column;
    @FXML
    TableColumn pricesReqQntColumn;
    @FXML
    TableColumn pricesColorNameColumn;
    @FXML
    TableColumn pricesSizeNameColumn;
    ObservableList<ItemPricesTable> pricesList = FXCollections.observableArrayList();
    @FXML
    Button chooseImage;
    @FXML
    ImageView itemImage;
    boolean isEnter = false;
//    @FXML
//    ComboBox mainAccountCombo;
//    @FXML
//    TextField mainAccount;
//    ObservableList<Charts> charts = FXCollections.observableArrayList();    

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
        toggleGroup = new ToggleGroup();
        initComboboxes();
        enableFields(false);
        stored.setToggleGroup(toggleGroup);
        service.setToggleGroup(toggleGroup);
        matrix.setToggleGroup(toggleGroup);
        
        initColorTable();
        initSizeTable();
        initMatrixTable();
        initPricesTable();
        toggleGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> ov, Toggle t, Toggle t1) {
                RadioButton selectedRadio = new RadioButton();
                if (t1 != null) {
                    selectedRadio = (RadioButton) t1; // Cast object to radio button
                } else if (t != null) {
                    selectedRadio = (RadioButton) t;
                } else if (ov != null) {
                    selectedRadio = (RadioButton) ov.getValue();
                }
                if (selectedRadio.getId().equals("stored")) {
                    itemPrices.setCollapsible(false);
                    itemPrices.setExpanded(false);
                    itemsMatrix.setCollapsible(false);
                    itemsMatrix.setExpanded(false);
                } else if (selectedRadio.getId().equals("service")) {
                    itemPrices.setCollapsible(false);
                    itemPrices.setExpanded(false);
                    itemsMatrix.setCollapsible(false);
                    itemsMatrix.setExpanded(false);
                } else if (selectedRadio.getId().equals("matrix")) {
                    itemPrices.setCollapsible(true);
                    // itemPrices.setExpanded(true);
                    itemsMatrix.setCollapsible(true);
                    itemsMatrix.setExpanded(true);
                }
            }
        });
        
        itemCode.setOnKeyPressed(new EventHandler<KeyEvent>() {
            
            @Override
            public void handle(KeyEvent event) {
                TextField code = (TextField) event.getSource();
                if (event.getCode() == KeyCode.TAB && !code.getText().isEmpty()) {
                    ItemDAO itemDAO = new ItemDAO();
                    Items item = itemDAO.getItemById(Integer.valueOf(code.getText()));
                    if (item != null) {
                        itemName.setText(item.getItemName());
                        enableFields(false);
                    }
                } else if (event.getCode() == KeyCode.ENTER && !code.getText().isEmpty()) {
                    isEnter = true;
                    fillData();
                }
            }
        });
        chooseImage.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event) {
                FileChooser filechooser = new FileChooser();
                File iconimage = filechooser.showOpenDialog(mainPane.getScene().getWindow());
                System.out.println(iconimage.getName());
                if (iconimage != null) {
                    String iconimagepath = iconimage.toURI().toString();
                    System.out.println(iconimagepath);
                    itemImage.setImage(new Image(iconimagepath));
                    File jarPath = new File(ItemsController.class.getProtectionDomain().getCodeSource().getLocation().getPath());
                    
                    System.out.println("Parent Path : " + jarPath.getParentFile().getParentFile().getAbsolutePath());
                }
            }
        });
    }
    
    public ItemsController() {
        instanse = this;
        selectedColors = new ArrayList<>();
        selectedSizes = new ArrayList<>();
    }
    
    public static ItemsController getInstance() {
        return instanse;
    }
    
    private void initComboboxes() {
        initGroupCombo();
        initSectionCombo();
        initTypeCombo();
        initUnitCombo();
        initVendorCombo();
    }
    
    public void handleNew() {
        accordion.setExpandedPane(null);
        accordion.setExpandedPane(mainInfo);
//        itemPrices.setCollapsible(false);
//        itemPrices.setExpanded(false);
//        itemsMatrix.setCollapsible(false);
//        itemsMatrix.setExpanded(false);
        isEnter = false;
        clearFields();
        enableFields(true);
        ItemDAO itemDAO = new ItemDAO();
        itemCode.setText(String.valueOf(itemDAO.getLastIndex() + 1));
        toggleGroup.selectToggle(stored);
        pricesTable.setEditable(true);
        
    }
    
    public void update (){
        fillData();
        enableFields(true);
        isEnter= true;

    }
    public void delete(){
                ItemDAO itemDAO = new ItemDAO();
        ItemSerialDAO itemSerialDAO = new ItemSerialDAO();
        itemDAO.deleteItem(Integer.valueOf(itemCode.getText()));
        itemSerialDAO.deleteItemSerial(Integer.valueOf(itemCode.getText()));
}
    public void save(int operation) {
        ItemDAO itemDAO = new ItemDAO();
        ItemSerialDAO itemSerialDAO = new ItemSerialDAO();
        if (operation == 1) {
            RadioButton rb = (RadioButton) toggleGroup.getSelectedToggle();
            if (rb.getId().equals("stored") || rb.getId().equals("service")) {
                int pType = 0;
                if (rb.getId().equals("service")) {
                    pType = 1;
                }
                int id = itemDAO.insertItem(pType, null, 1, Integer.valueOf(unitCode.getText()), Integer.valueOf(secCode.getText()), Integer.valueOf(groupCode.getText()),
                        Integer.valueOf(typeCode.getText()), 1, Integer.valueOf(venCode.getText()), null, itemName.getText(), Integer.valueOf(itemCode.getText()));
                int serialId = itemSerialDAO.insertItemSerial(barCode.getText(), id, 0, 0, Double.valueOf(percentage.getText()), Integer.valueOf(reqQuant.getText()),
                        Double.valueOf(sales1.getText()), Double.valueOf(sales2.getText()), Double.valueOf(sales3.getText()), Double.valueOf(cost.getText()), Double.valueOf(cost.getText()));
            } else if (rb.getId().equals("matrix")) {
                int id = itemDAO.insertItem(2, null, 1, Integer.valueOf(unitCode.getText()), Integer.valueOf(secCode.getText()), Integer.valueOf(groupCode.getText()),
                        Integer.valueOf(typeCode.getText()), 1, Integer.valueOf(venCode.getText()), null, itemName.getText(), Integer.valueOf(itemCode.getText()));
                for (ItemPricesTable ipt : pricesList) {
                    int serialId = itemSerialDAO.insertItemSerial(barCode.getText(), id, ipt.sizeIdProperty().get(), ipt.colorIdProperty().get(), Double.valueOf(percentage.getText()), Integer.valueOf(ipt.reqQunProperty().get()),
                            Double.valueOf(ipt.sale1Property().get()), Double.valueOf(ipt.sale2Property().get()), Double.valueOf(ipt.sale3Property().get()),
                            Double.valueOf(ipt.buyPriceProperty().get()), Double.valueOf(ipt.buyPriceProperty().get()));
                }
            }
            clearFields();
            enableFields(false);
        } else if (operation == 2) {
             RadioButton rb = (RadioButton) toggleGroup.getSelectedToggle();
            if (rb.getId().equals("stored") || rb.getId().equals("service")) {
                int pType = 0;
                if (rb.getId().equals("service")) {
                    pType = 1;
                }
                int id = itemDAO.updateItem( Integer.valueOf(itemCode.getText()),pType, null, 1, Integer.valueOf(unitCode.getText()), Integer.valueOf(secCode.getText()), Integer.valueOf(groupCode.getText()),
                        Integer.valueOf(typeCode.getText()), 1, Integer.valueOf(venCode.getText()), null, itemName.getText());
                int serialId = itemSerialDAO.updateItemSerial(0, barCode.getText(),Integer.valueOf(itemCode.getText()) , 0, 0, Double.valueOf(percentage.getText()), Integer.valueOf(reqQuant.getText()),
                        Double.valueOf(sales1.getText()), Double.valueOf(sales2.getText()), Double.valueOf(sales3.getText()), Double.valueOf(cost.getText()), Double.valueOf(cost.getText()));
            } else if (rb.getId().equals("matrix")) {
                int id = itemDAO.updateItem( Integer.valueOf(itemCode.getText()),2, null, 1, Integer.valueOf(unitCode.getText()), Integer.valueOf(secCode.getText()), Integer.valueOf(groupCode.getText()),
                        Integer.valueOf(typeCode.getText()), 1, Integer.valueOf(venCode.getText()), null, itemName.getText());
  
                for (ItemPricesTable ipt : pricesList) {
                    int serialId = itemSerialDAO.updateItemSerial(0,barCode.getText(), id, ipt.sizeIdProperty().get(), ipt.colorIdProperty().get(), Double.valueOf(percentage.getText()), Integer.valueOf(ipt.reqQunProperty().get()),
                            Double.valueOf(ipt.sale1Property().get()), Double.valueOf(ipt.sale2Property().get()), Double.valueOf(ipt.sale3Property().get()),
                            Double.valueOf(ipt.buyPriceProperty().get()), Double.valueOf(ipt.buyPriceProperty().get()));
                }
            }
            clearFields();
            enableFields(false);
        }
    }
    
    public void enableFields(boolean status) {
        itemCode.setEditable(status);
        itemName.setEditable(status);
        barCode.setEditable(status);
        cost.setEditable(status);
        percentage.setEditable(status);
        reqQuant.setEditable(status);
        sales1.setEditable(status);
        sales2.setEditable(status);
        sales3.setEditable(status);
        
        typeCombo.setDisable(!status);
        groupCombo.setDisable(!status);
        secCombo.setDisable(!status);
        unitCombo.setDisable(!status);
        venCombo.setDisable(!status);
        matrix.setDisable(!status);
        service.setDisable(!status);
        stored.setDisable(!status);
        
    }
    
    public void clearFields() {
        itemCode.setText("");
        itemName.setText("");
        barCode.setText("");
        cost.setText("");
        percentage.setText("");
        reqQuant.setText("");
        sales1.setText("");
        sales2.setText("");
        sales3.setText("");
        typeCode.setText("");
        groupCode.setText("");
        secCode.setText("");
        unitCode.setText("");
        venCode.setText("");
        toggleGroup.selectToggle(stored);
        typeCombo.setValue(null);
        typeCombo.hide();
        groupCombo.setValue(null);
        groupCombo.hide();
        secCombo.setValue(null);
        secCombo.hide();
        unitCombo.setValue(null);
        unitCombo.hide();
        venCombo.setValue(null);
        venCombo.hide();
        matrix.setSelected(false);
        service.setSelected(false);
        stored.setSelected(false);
        
        colorsTable.getItems().removeAll(colorsTable.getItems());
        sizesTable.getItems().removeAll(sizesTable.getItems());
        matrixTable.getItems().removeAll(matrixTable.getItems());
        pricesTable.getItems().removeAll(pricesTable.getItems());
        
    }
    
    private void initGroupCombo() {
        //Group 
        GroupDAO groupDAO = new GroupDAO();
        final List<Groups> groupses = groupDAO.getAllGroups();
        groups.addAll(groupses);
        
        groupCombo.setItems(groups);
        groupCombo.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observableValue, Object oldValue, Object newValue) {
                //Check whether item is selected and set value of selected item to Label
                if (groupCombo.getSelectionModel().getSelectedItem() != null) {
                    Groups c = (Groups) newValue;
                    groupCombo.setValue(c);
                    groupCode.setText(String.valueOf(c.getGroupId()));
                }
            }
        });
        
        groupCombo.setEditable(true);
        groupCombo.getEditor().textProperty()
                .addListener(new ChangeListener<String>() {
                    @Override
                    public void changed(ObservableValue<? extends String> observable,
                            String oldValue, String newValue) {
                        final TextField editor = groupCombo.getEditor();
                        final Groups selected = (Groups) groupCombo.getSelectionModel()
                        .getSelectedItem();
                        if (selected == null || !selected.getGroupName().equals(editor.getText())) {
                            filterGroupItems(newValue, groupCombo, groupses);
                            groupCombo.show();
                            if (groupCombo.getItems().size() == 1) {
                                final Groups c = (Groups) groupCombo.getItems().get(0);
                                String onlyOption = c.getGroupName();
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
    
    private <T> void filterGroupItems(String filter, ComboBox<Groups> comboBox,
            List<Groups> items) {
        List<Groups> filteredItems = new ArrayList<>();
        for (Groups item : items) {
            if (item.getGroupName().toString().toLowerCase().indexOf(filter.toLowerCase()) != -1) {
                filteredItems.add(item);
            }
        }
        comboBox.setItems(FXCollections.observableArrayList(filteredItems));
    }
    
    private void initTypeCombo() {
        //Group 
        TypeDAO typeDAO = new TypeDAO();
        final List<Types> typeses = typeDAO.getAllTypes();
        types.addAll(typeses);
        
        typeCombo.setItems(types);
        typeCombo.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observableValue, Object oldValue, Object newValue) {
                //Check whether item is selected and set value of selected item to Label
                if (typeCombo.getSelectionModel().getSelectedItem() != null) {
                    Types c = (Types) newValue;
                    typeCombo.setValue(c);
                    typeCode.setText(String.valueOf(c.getTypeId()));
                }
            }
        });
        
        typeCombo.setEditable(true);
        typeCombo.getEditor().textProperty()
                .addListener(new ChangeListener<String>() {
                    @Override
                    public void changed(ObservableValue<? extends String> observable,
                            String oldValue, String newValue) {
                        final TextField editor = typeCombo.getEditor();
                        final Types selected = (Types) typeCombo.getSelectionModel()
                        .getSelectedItem();
                        if (selected == null || !selected.getTypeName().equals(editor.getText())) {
                            filterTypeItems(newValue, typeCombo, typeses);
                            typeCombo.show();
                            if (typeCombo.getItems().size() == 1) {
                                final Types c = (Types) typeCombo.getItems().get(0);
                                String onlyOption = c.getTypeName();
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
    
    private <T> void filterTypeItems(String filter, ComboBox<Types> comboBox,
            List<Types> items) {
        List<Types> filteredItems = new ArrayList<>();
        for (Types item : items) {
            if (item.getTypeName().toString().toLowerCase().indexOf(filter.toLowerCase()) != -1) {
                filteredItems.add(item);
            }
        }
        comboBox.setItems(FXCollections.observableArrayList(filteredItems));
    }
    
    private void initSectionCombo() {
        //Group 
        SectionDAO sectionDAO = new SectionDAO();
        final List<Sections> sectionses = sectionDAO.getAllSections();
        sections.addAll(sectionses);
        
        secCombo.setItems(sections);
        secCombo.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observableValue, Object oldValue, Object newValue) {
                //Check whether item is selected and set value of selected item to Label
                if (secCombo.getSelectionModel().getSelectedItem() != null) {
                    Sections c = (Sections) newValue;
                    secCombo.setValue(c);
                    secCode.setText(String.valueOf(c.getSectionId()));
                }
            }
        });
        
        secCombo.setEditable(true);
        secCombo.getEditor().textProperty()
                .addListener(new ChangeListener<String>() {
                    @Override
                    public void changed(ObservableValue<? extends String> observable,
                            String oldValue, String newValue) {
                        final TextField editor = secCombo.getEditor();
                        final Sections selected = (Sections) secCombo.getSelectionModel()
                        .getSelectedItem();
                        if (selected == null || !selected.getSectionName().equals(editor.getText())) {
                            filterSectionItems(newValue, secCombo, sectionses);
                            secCombo.show();
                            if (secCombo.getItems().size() == 1) {
                                final Sections c = (Sections) secCombo.getItems().get(0);
                                String onlyOption = c.getSectionName();
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
    
    private <T> void filterSectionItems(String filter, ComboBox<Sections> comboBox,
            List<Sections> items) {
        List<Sections> filteredItems = new ArrayList<>();
        for (Sections item : items) {
            if (item.getSectionName().toString().toLowerCase().indexOf(filter.toLowerCase()) != -1) {
                filteredItems.add(item);
            }
        }
        comboBox.setItems(FXCollections.observableArrayList(filteredItems));
    }
    
    private void initUnitCombo() {
        //Group 
        UnitDAO unitDAO = new UnitDAO();
        final List<Units> unitses = unitDAO.getAllUnits();
        units.addAll(unitses);
        
        unitCombo.setItems(units);
        unitCombo.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observableValue, Object oldValue, Object newValue) {
                //Check whether item is selected and set value of selected item to Label
                if (unitCombo.getSelectionModel().getSelectedItem() != null) {
                    Units c = (Units) newValue;
                    unitCombo.setValue(c);
                    unitCode.setText(String.valueOf(c.getUnitId()));
                }
            }
        });
        
        unitCombo.setEditable(true);
        unitCombo.getEditor().textProperty()
                .addListener(new ChangeListener<String>() {
                    @Override
                    public void changed(ObservableValue<? extends String> observable,
                            String oldValue, String newValue) {
                        final TextField editor = unitCombo.getEditor();
                        final Units selected = (Units) unitCombo.getSelectionModel()
                        .getSelectedItem();
                        if (selected == null || !selected.getUnitName().equals(editor.getText())) {
                            filterUnitItems(newValue, unitCombo, unitses);
                            unitCombo.show();
                            if (unitCombo.getItems().size() == 1) {
                                final Units c = (Units) unitCombo.getItems().get(0);
                                String onlyOption = c.getUnitName();
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
    
    private <T> void filterUnitItems(String filter, ComboBox<Units> comboBox,
            List<Units> items) {
        List<Units> filteredItems = new ArrayList<>();
        for (Units item : items) {
            if (item.getUnitName().toString().toLowerCase().indexOf(filter.toLowerCase()) != -1) {
                filteredItems.add(item);
            }
        }
        comboBox.setItems(FXCollections.observableArrayList(filteredItems));
    }
    
    private void initVendorCombo() {
        //Group 
        PersonDAO unitDAO = new PersonDAO();
        final List<Persons> vendorses = unitDAO.getAllPersons(1);
        vendors.addAll(vendorses);
        
        venCombo.setItems(vendors);
        venCombo.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observableValue, Object oldValue, Object newValue) {
                //Check whether item is selected and set value of selected item to Label
                if (venCombo.getSelectionModel().getSelectedItem() != null) {
                    Persons c = (Persons) newValue;
                    venCombo.setValue(c);
                    venCode.setText(String.valueOf(c.getId().getPersonId()));
                }
            }
        });
        
        venCombo.setEditable(true);
        venCombo.getEditor().textProperty()
                .addListener(new ChangeListener<String>() {
                    @Override
                    public void changed(ObservableValue<? extends String> observable,
                            String oldValue, String newValue) {
                        final TextField editor = venCombo.getEditor();
                        final Persons selected = (Persons) venCombo.getSelectionModel()
                        .getSelectedItem();
                        if (selected == null || !selected.getPersonName().equals(editor.getText())) {
                            filterPersonItems(newValue, venCombo, vendorses);
                            venCombo.show();
                            if (venCombo.getItems().size() == 1) {
                                final Persons c = (Persons) venCombo.getItems().get(0);
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
    
    private void initColorTable() {

        // Init Color Table
        codeColumn.setCellValueFactory(new PropertyValueFactory("code"));
        nameColumn.setCellValueFactory(new PropertyValueFactory("name"));
        checked.setCellValueFactory(new PropertyValueFactory("checked"));
        checked.setCellFactory(new Callback<TableColumn<ItemColorsTable, Boolean>, TableCell<ItemColorsTable, Boolean>>() {
            
            public TableCell<ItemColorsTable, Boolean> call(TableColumn<ItemColorsTable, Boolean> p) {
                return new CheckBoxTableCell<ItemColorsTable, Boolean>();
            }
        });
        
        List<Colors> colors = new ColorDAO().getAllColors();
        for (Colors color : colors) {
            colorsList.add(new ItemColorsTable(color.getColorId().intValue(), color.getColorName(), false));
        }
        colorsTable.setItems(colorsList);
        colorsTable.setEditable(true);
    }
    
    private void initSizeTable() {
        // Init Size Table
        sizeCodeColumn.setCellValueFactory(new PropertyValueFactory("code"));
        sizeNameColumn.setCellValueFactory(new PropertyValueFactory("name"));
        sizeChecked.setCellValueFactory(new PropertyValueFactory("checked"));
        sizeChecked.setCellFactory(new Callback<TableColumn<ItemSizesTable, Boolean>, TableCell<ItemSizesTable, Boolean>>() {
            
            public TableCell<ItemSizesTable, Boolean> call(TableColumn<ItemSizesTable, Boolean> p) {
                return new CheckBoxTableCell<ItemSizesTable, Boolean>();
            }
        });
        
        List<Sizes> sizes = new SizeDAO().getAllSizes();
        for (Sizes size : sizes) {
            sizesList.add(new ItemSizesTable(size.getSizeId().intValue(), size.getSizeName(), false));
        }
        sizesTable.setItems(sizesList);
        sizesTable.setEditable(true);
        
    }
    
    private void initMatrixTable() {
        // Init Size Table
        matrixMColumn.setCellValueFactory(new PropertyValueFactory("m"));
        matrixCodeColumn.setCellValueFactory(new PropertyValueFactory("code"));
        matrixItemNameColumn.setCellValueFactory(new PropertyValueFactory("itemName"));
        matrixColorNameColumn.setCellValueFactory(new PropertyValueFactory("colorName"));
        matrixSizeNameColumn.setCellValueFactory(new PropertyValueFactory("sizeName"));
        matrixTable.setItems(matrixList);
        
    }
    
    private void initPricesTable() {
        // Init Size Table
        pricesBuyPriceColumn.setCellValueFactory(new PropertyValueFactory("buyPrice"));
        pricesCodeColumn.setCellValueFactory(new PropertyValueFactory("code"));
        pricesSale1Column.setCellValueFactory(new PropertyValueFactory("sale1"));
        pricesSale2Column.setCellValueFactory(new PropertyValueFactory("sale2"));
        pricesSale3Column.setCellValueFactory(new PropertyValueFactory("sale3"));
        
        pricesReqQntColumn.setCellValueFactory(new PropertyValueFactory("reqQun"));
        pricesColorNameColumn.setCellValueFactory(new PropertyValueFactory("colorName"));
        pricesSizeNameColumn.setCellValueFactory(new PropertyValueFactory("sizeName"));
        pricesTable.setItems(pricesList);
        itemPrices.setOnMouseClicked(new EventHandler<MouseEvent>() {
            
            @Override
            public void handle(MouseEvent event) {
                if (itemPrices.isExpanded() && matrixList.size() >= 1) {
                    if (!isEnter) {
                        updatePrices();
                    }
                }
            }
        });
        Callback<TableColumn, TableCell> cellFactory
                = new Callback<TableColumn, TableCell>() {
                    public TableCell call(TableColumn p) {
                        return new EditingCell();
                    }
                };
        pricesBuyPriceColumn.setCellFactory(cellFactory);
        pricesSale1Column.setCellFactory(cellFactory);
        pricesSale2Column.setCellFactory(cellFactory);
        pricesSale3Column.setCellFactory(cellFactory);
        pricesReqQntColumn.setCellFactory(cellFactory);
        pricesTable.setEditable(true);
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
    
    private void updateMatrix(int which, Object obj, boolean status) {
        ArrayList<ItemMatrixTable> list = new ArrayList<>();
        
        if (which == 0) {
            //color  
            ItemColorsTable itemColorsTable = (ItemColorsTable) obj;
            if (status) {
                //add
                if (selectedSizes.size() >= 1) {
                    for (ItemSizesTable size : selectedSizes) {
                        matrixList.add(new ItemMatrixTable(matrixList.size() + 1, itemCode.getText() + size.codeProperty().get() + itemColorsTable.codeProperty().get(),
                                itemName.getText(), itemColorsTable.nameProperty().get(), size.nameProperty().get(), size.codeProperty().get(), itemColorsTable.codeProperty().get()));
                    }
                    
                }
            } else {
                //remove
                for (int i = 0; i < matrixList.size(); i++) {
                    ItemMatrixTable imt = matrixList.get(i);
                    if (imt.codeProperty().get().indexOf(String.valueOf(itemColorsTable.codeProperty().get())) != -1 && imt.colorNameProperty().get().equals(itemColorsTable.nameProperty().get())) {
                        list.add(imt);
                    }
                }
                matrixList.removeAll(list);
                for (int i = 0; i < matrixList.size(); i++) {
                    matrixList.get(i).setM(i + 1);
                }
            }
        } else {
            ItemSizesTable itemSizesTable = (ItemSizesTable) obj;
            //size
            if (status) {
                //add
                if (selectedColors.size() >= 1) {
                    for (ItemColorsTable color : selectedColors) {
                        matrixList.add(new ItemMatrixTable(matrixTable.getItems().size() + 1, itemCode.getText() + itemSizesTable.codeProperty().get() + color.codeProperty().get(),
                                itemName.getText(), color.nameProperty().get(), itemSizesTable.nameProperty().get(), itemSizesTable.codeProperty().get(), color.codeProperty().get()));
                    }
                }
            } else {
                //remove
                for (int i = 0; i < matrixList.size(); i++) {
                    ItemMatrixTable imt = matrixList.get(i);
                    
                    if (imt.codeProperty().get().indexOf(String.valueOf(itemSizesTable.codeProperty().get())) != -1 && imt.sizeNameProperty().get().equals(itemSizesTable.nameProperty().get())) {
                        list.add(imt);
                    }
                    
                }
                matrixList.removeAll(list);
                for (int i = 0; i < matrixList.size(); i++) {
                    matrixList.get(i).setM(i + 1);
                }
            }
        }
        
    }
    
    private void updatePrices() {
        pricesList.removeAll(pricesList);
        pricesTable.getItems().removeAll(pricesTable.getItems());
        for (ItemMatrixTable matrix : matrixList) {
            pricesList.add(new ItemPricesTable(matrix.codeProperty().get(), cost.getText(), sales1.getText(), sales2.getText(), sales3.getText(), reqQuant.getText(),
                    matrix.colorNameProperty().get(), matrix.colorIdProperty().get(), matrix.sizeIdProperty().get(), matrix.sizeNameProperty().get()));
        }
    }
    
    public class ItemSizesTable {
        
        private IntegerProperty code;
        private StringProperty name;
        private BooleanProperty checked;
        
        public ItemSizesTable() {
            this.checked.addListener(new ChangeListener<Boolean>() {
                public void changed(ObservableValue<? extends Boolean> ov, Boolean t, Boolean t1) {
                    System.out.println(nameProperty().get());
                    System.out.println(t1.booleanValue());
                    
                }
            });
        }
        
        public ItemSizesTable(int code, String name, boolean checked) {
            this.code = new SimpleIntegerProperty(code);
            this.name = new SimpleStringProperty(name);
            this.checked = new SimpleBooleanProperty(checked);
            this.checked.addListener(new ChangeListener<Boolean>() {
                public void changed(ObservableValue<? extends Boolean> ov, Boolean t, Boolean t1) {
                    System.out.println(nameProperty().get());
                    System.out.println(t1.booleanValue());
                    if (t1.booleanValue()) {
                        ItemsController.this.selectedSizes.add(ItemSizesTable.this);
                        ItemsController.this.updateMatrix(1, ItemSizesTable.this, checkedProperty().get());
                    } else {
                        ItemsController.this.selectedSizes.remove(ItemSizesTable.this);
                        ItemsController.this.updateMatrix(1, ItemSizesTable.this, checkedProperty().get());
                    }
                }
            });
        }
        
        public IntegerProperty codeProperty() {
            return code;
        }
        
        public void setCode(int code) {
            this.code.set(code);
        }
        
        public StringProperty nameProperty() {
            return name;
        }
        
        public void setName(String name) {
            this.name.set(name);
        }
        
        public BooleanProperty checkedProperty() {
            return checked;
        }
        
        public void setChecked(boolean checked) {
            this.checked.set(checked);
        }
        
    }
    
    public class ItemColorsTable {
        
        private IntegerProperty code;
        private StringProperty name;
        private BooleanProperty checked;
        
        public ItemColorsTable() {
            this.checked.addListener(new ChangeListener<Boolean>() {
                public void changed(ObservableValue<? extends Boolean> ov, Boolean t, Boolean t1) {
                    System.out.println(nameProperty().get());
                    System.out.println(t1.booleanValue());
                }
            });
        }
        
        public ItemColorsTable(int code, String name, boolean checked) {
            this.code = new SimpleIntegerProperty(code);
            this.name = new SimpleStringProperty(name);
            this.checked = new SimpleBooleanProperty(checked);
            this.checked.addListener(new ChangeListener<Boolean>() {
                public void changed(ObservableValue<? extends Boolean> ov, Boolean t, Boolean t1) {
                    System.out.println(nameProperty().get());
                    System.out.println(t1.booleanValue());
                    if (t1.booleanValue()) {
                        ItemsController.this.selectedColors.add(ItemColorsTable.this);
                        ItemsController.this.updateMatrix(0, ItemColorsTable.this, checkedProperty().get());
                    } else {
                        ItemsController.this.selectedColors.remove(ItemColorsTable.this);
                        ItemsController.this.updateMatrix(0, ItemColorsTable.this, checkedProperty().get());
                    }
                }
            });
        }
        
        public IntegerProperty codeProperty() {
            return code;
        }
        
        public void setCode(int code) {
            this.code.set(code);
        }
        
        public StringProperty nameProperty() {
            return name;
        }
        
        public void setName(String name) {
            this.name.set(name);
        }
        
        public BooleanProperty checkedProperty() {
            return checked;
        }
        
        public void setChecked(boolean checked) {
            this.checked.set(checked);
        }
        
    }
    
    public class ItemMatrixTable {
        
        private IntegerProperty m;
        private StringProperty code;
        private StringProperty itemName;
        private StringProperty colorName;
        private StringProperty sizeName;
        private IntegerProperty sizeId;
        private IntegerProperty colorId;
        
        public ItemMatrixTable() {
            
        }
        
        public ItemMatrixTable(int m, String code, String itemName, String colorName, String sizeName, int sizeId, int colorId) {
            this.m = new SimpleIntegerProperty(m);
            this.code = new SimpleStringProperty(code);
            this.itemName = new SimpleStringProperty(itemName);
            this.colorName = new SimpleStringProperty(colorName);
            this.sizeName = new SimpleStringProperty(sizeName);
            this.sizeId = new SimpleIntegerProperty(sizeId);
            this.colorId = new SimpleIntegerProperty(colorId);
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
        
        public StringProperty itemNameProperty() {
            return itemName;
        }
        
        public void setItemName(String itemName) {
            this.itemName.set(itemName);
        }
        
        public StringProperty colorNameProperty() {
            return colorName;
        }
        
        public void setColorName(String colorName) {
            this.colorName.set(colorName);
        }
        
        public StringProperty sizeNameProperty() {
            return sizeName;
        }
        
        public void setSizeName(String sizeName) {
            this.sizeName.set(sizeName);
        }
        
        public IntegerProperty colorIdProperty() {
            return colorId;
        }
        
        public void setColorId(int colorId) {
            this.colorId.set(colorId);
        }
        
        public IntegerProperty sizeIdProperty() {
            return sizeId;
        }
        
        public void setSizeId(int sizeId) {
            this.sizeId.set(sizeId);
        }
    }
    
    public class ItemPricesTable {
        
        private StringProperty code;
        private StringProperty buyPrice;
        private StringProperty sale1;
        private StringProperty sale2;
        private StringProperty sale3;
        private StringProperty reqQun;
        private StringProperty colorName;
        private StringProperty sizeName;
        private IntegerProperty sizeId;
        private IntegerProperty colorId;
        
        public ItemPricesTable() {
            
        }
        
        public ItemPricesTable(String code, String buyPrice, String sale1, String sale2, String sale3, String reqQun, String colorName, int colorId, int sizeId, String sizeName) {
            this.code = new SimpleStringProperty(code);
            this.buyPrice = new SimpleStringProperty(buyPrice);
            this.sale1 = new SimpleStringProperty(sale1);
            this.sale2 = new SimpleStringProperty(sale2);
            this.sale3 = new SimpleStringProperty(sale3);
            this.reqQun = new SimpleStringProperty(reqQun);
            this.colorName = new SimpleStringProperty(colorName);
            this.sizeName = new SimpleStringProperty(sizeName);
            this.sizeId = new SimpleIntegerProperty(sizeId);
            this.colorId = new SimpleIntegerProperty(colorId);
        }
        
        public StringProperty codeProperty() {
            return code;
        }
        
        public void setCode(String code) {
            this.code.set(code);
        }
        
        public StringProperty buyPriceProperty() {
            return buyPrice;
        }
        
        public void setBuyPrice(String buyPrice) {
            this.buyPrice.set(buyPrice);
        }
        
        public StringProperty sale1Property() {
            return sale1;
        }
        
        public void setSale1Price(String sale1) {
            this.sale1.set(sale1);
        }
        
        public StringProperty sale2Property() {
            return sale2;
        }
        
        public void setSale2Price(String sale2) {
            this.sale2.set(sale2);
        }
        
        public StringProperty sale3Property() {
            return sale3;
        }
        
        public void setSale3Price(String sale3) {
            this.sale3.set(sale3);
        }
        
        public StringProperty reqQunProperty() {
            return reqQun;
        }
        
        public void setReqQun(String reqQun) {
            this.reqQun.set(reqQun);
        }
        
        public StringProperty colorNameProperty() {
            return colorName;
        }
        
        public void setColorName(String colorName) {
            this.colorName.set(colorName);
        }
        
        public StringProperty sizeNameProperty() {
            return sizeName;
        }
        
        public void setSizeName(String sizeName) {
            this.sizeName.set(sizeName);
        }
        
        public IntegerProperty colorIdProperty() {
            return colorId;
        }
        
        public void setColorId(int colorId) {
            this.colorId.set(colorId);
        }
        
        public IntegerProperty sizeIdProperty() {
            return sizeId;
        }
        
        public void setSizeId(int sizeId) {
            this.sizeId.set(sizeId);
        }
    }

    // EditingCell - for editing capability in a TableCell
    public class EditingCell extends TableCell<ItemPricesTable, Object> {
        
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
            String st = getTableColumn().getId();
            ItemPricesTable z = getTableView().getSelectionModel().getSelectedItem();
            if (st.equals("pricesBuyPriceColumn")) {
                z.setBuyPrice(String.valueOf(newValue));
            } else if (st.equals("pricesSale1Column")) {
                z.setSale1Price(String.valueOf(newValue));
            } else if (st.equals("pricesSale2Column")) {
                z.setSale2Price(String.valueOf(newValue));
            } else if (st.equals("pricesSale3Column")) {
                z.setSale3Price(String.valueOf(newValue));
            } else if (st.equals("pricesReqQntColumn")) {
                z.setReqQun(String.valueOf(newValue));
            }
            
            String s = getTableRow().getText();
            String l = getTableView().getId();
            //editList.add(z);
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
    
    public void fillData(){
                        

                            ItemDAO itemDAO = new ItemDAO();
                    Items item = itemDAO.getItemById(Integer.valueOf(itemCode.getText()));
                    List<ItemsSerials> itemsSerials = new ItemSerialDAO().getAllItemSerialsByItemId(item.getItemId().intValue());
                    if (item != null) {
                        if (item.getProductType().intValue() == 0 || item.getProductType().intValue() == 1) {
                            itemName.setText(item.getItemName());
                            barCode.setText(itemsSerials.get(0).getBarCode());
                            for (Persons ven : vendors) {
                                if (ven.getId().equals(item.getPersons().getId().getPersonId())) {
                                    venCombo.getSelectionModel().select(ven);
                                    break;
                                }
                            }
                            for (Groups group : groups) {
                                if (group.getGroupId().equals(item.getGroups().getGroupId())) {
                                    groupCombo.getSelectionModel().select(group);
                                    break;
                                }
                            }
                            for (Units unit : units) {
                                if (unit.getUnitId().equals(item.getUnits().getUnitId())) {
                                    unitCombo.getSelectionModel().select(unit);
                                    break;
                                }
                            }
                            for (Sections sec : sections) {
                                if (sec.getSectionId().equals(item.getSections().getSectionId())) {
                                    secCombo.getSelectionModel().select(sec);
                                    break;
                                }
                            }
                            for (Types type : types) {
                                if (type.getTypeId().equals(item.getTypes().getTypeId())) {
                                    typeCombo.getSelectionModel().select(type);
                                    break;
                                }
                            }
                            cost.setText(itemsSerials.get(0).getCost().toString());
                            percentage.setText(itemsSerials.get(0).getPercentage().toString());
                            reqQuant.setText(itemsSerials.get(0).getRequirQty().toString());
                            sales1.setText(itemsSerials.get(0).getSales1().toString());
                            sales2.setText(itemsSerials.get(0).getSales2().toString());
                            sales3.setText(itemsSerials.get(0).getSales3().toString());
                            if (item.getProductType().intValue() == 0) {
                                toggleGroup.selectToggle(stored);
                            } else if (item.getProductType().intValue() == 1) {
                                toggleGroup.selectToggle(service);
                            }
                            
                            enableFields(false);
                        } else {
                            itemName.setText(item.getItemName());
                            barCode.setText(itemsSerials.get(0).getBarCode());
                            toggleGroup.selectToggle(matrix);
                            for (Persons ven : vendors) {
                                if (ven.getId().equals(item.getPersons().getId().getPersonId())) {
                                    venCombo.getSelectionModel().select(ven);
                                    break;
                                }
                            }
                            for (Groups group : groups) {
                                if (group.getGroupId().equals(item.getGroups().getGroupId())) {
                                    groupCombo.getSelectionModel().select(group);
                                    break;
                                }
                            }
                            for (Units unit : units) {
                                if (unit.getUnitId().equals(item.getUnits().getUnitId())) {
                                    unitCombo.getSelectionModel().select(unit);
                                    break;
                                }
                            }
                            for (Sections sec : sections) {
                                if (sec.getSectionId().equals(item.getSections().getSectionId())) {
                                    secCombo.getSelectionModel().select(sec);
                                    break;
                                }
                            }
                            for (Types type : types) {
                                if (type.getTypeId().equals(item.getTypes().getTypeId())) {
                                    typeCombo.getSelectionModel().select(type);
                                    break;
                                }
                            }
                            itemsMatrix.setExpanded(true);
                            itemsMatrix.setCollapsible(true);
                            itemPrices.setCollapsible(true);
                            initColorTable();
                            initSizeTable();
                            String cName;
                            String sName;
                            
                            for (ItemsSerials imt : itemsSerials) {
                                
                                for (ItemColorsTable colors : colorsList) {
                                    if (colors.codeProperty().get() == imt.getColors().getColorId().intValue()) {
                                        colors.checkedProperty().set(true);
                                    }
                                }
                                for (ItemSizesTable sizes : sizesList) {
                                    if (sizes.codeProperty().get() == imt.getSizes().getSizeId().intValue()) {
                                        sizes.checkedProperty().set(true);
                                    }                                    
                                }
                                
                                pricesList.add(new ItemPricesTable(item.getItemId().toString() + imt.getSizes().getSizeId() + imt.getColors().getColorId() + "", imt.getCost().toString(), imt.getSales1().toString(), imt.getSales2().toString(), imt.getSales3().toString(),
                                        imt.getRequirQty().toString(),
                                        imt.getColors().getColorName(), imt.getColors().getColorId().intValue(), imt.getSizes().getSizeId().intValue(), imt.getSizes().getSizeName()));                                
                            }
                            pricesTable.setEditable(false);
                            enableFields(false);                            
                            
                        }
                    }
    }
}
