/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shop;

import daos.ChartDAO;
import daos.EmployeeDAO;
import daos.StoreDAO;
import java.math.BigDecimal;
import java.net.URL;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.util.Callback;
import model.Charts;
import model.Employees;
import model.Stores;

/**
 * FXML Controller class
 *
 * @author Kareem.Moustafa
 */
public class StoresController implements Initializable {

    /**
     * Initializes the controller class.
     */
    private static StoresController instanse;

    private TreeItem<String> treeRoot = new TreeItem<String>("المخازن");
    HashMap<Integer, Stores> storeMap;
    @FXML
    TextField storeCode;
    @FXML
    TextField storeName;
    @FXML
    CheckBox isMain;
    @FXML
    TextField storePhone;
    @FXML
    TextArea storeAdd;
    @FXML
    TreeView storeTree;
    @FXML
    ComboBox contactPersonCombo;
    @FXML
    TextField contactPerson;
    ObservableList<Employees> employeeses = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        enableFields(false);
        storeTree.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observableValue, Object oldValue, Object newValue) {
                //Check whether item is selected and set value of selected item to Label
                if (storeTree.getSelectionModel().getSelectedItem() != null) {
                    TreeItem treeItem = (TreeItem) storeTree.getSelectionModel().getSelectedItem();
                    if (treeItem.getGraphic() != null) {
                        int id = Integer.parseInt(((Label) treeItem.getGraphic()).getText());
                        Stores store = storeMap.get(id);
                        if (store.getStoreId() != BigDecimal.ZERO) {
                            storeCode.setText(store.getStoreId().toString());
                        }
                        if (store.getStoreName() != null || !store.getStoreName().isEmpty()) {
                            storeName.setText(store.getStoreName());
                        }
                        if (store.getIsMain() != null) {
                            if (store.getIsMain().intValue() == 0) {
                                isMain.setSelected(false);
                            } else if (store.getIsMain().intValue() == 1) {
                                isMain.setSelected(true);
                            }
                        }
                        if (store.getStorePhone() != null || !store.getStorePhone().isEmpty()) {
                            storePhone.setText(store.getStorePhone());
                        }

                        if (store.getStoreAddress() != null || !store.getStoreAddress().isEmpty()) {
                            storeAdd.setText(store.getStoreAddress());
                        }

                        if (store.getEmployees() != null) {
                            contactPerson.setText(store.getEmployees().getEmpId().toString());
                            for (Employees object : employeeses) {
                                if (object.getEmpId().intValue() == store.getEmployees().getEmpId().intValue()) {
                                    contactPersonCombo.setValue(object);
                                    break;
                                }
                            }
                        }
                    }
                }
            }
        });

        storeTree.setRoot(treeRoot);

        treeRoot.setExpanded(true);

        EmployeeDAO employeeDAO = new EmployeeDAO();
        final List<Employees> employees = employeeDAO.getAllEmployees();
        employeeses.addAll(employees);
//        contactPersonCombo.setCellFactory(new Callback<ListView<Employees>, ListCell<Employees>>() {
//
//            @Override
//            public ListCell<Employees> call(ListView<Employees> p) {
//
//                final ListCell<Employees> cell = new ListCell<Employees>() {
//
//                    @Override
//                    protected void updateItem(Employees t, boolean bln) {
//                        super.updateItem(t, bln);
//
//                        if (t != null) {
//                            setText(t.getEmpName());
//                        } else {
//                            setText(null);
//                        }
//                    }
//
//                };
//
//                return cell;
//            }
//        });
        contactPersonCombo.setItems(employeeses);
        contactPersonCombo.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observableValue, Object oldValue, Object newValue) {
                //Check whether item is selected and set value of selected item to Label
                if (contactPersonCombo.getSelectionModel().getSelectedItem() != null) {
                    Employees c = (Employees) newValue;
                    contactPersonCombo.setValue(c);
                    contactPerson.setText(String.valueOf(c.getEmpId()));
                }
            }
        });

        contactPersonCombo.setEditable(true);
        contactPersonCombo.getEditor().textProperty()
                .addListener(new ChangeListener<String>() {
                    @Override
                    public void changed(ObservableValue<? extends String> observable,
                            String oldValue, String newValue) {
                        final TextField editor = contactPersonCombo.getEditor();
                        final Employees selected = (Employees) contactPersonCombo.getSelectionModel()
                        .getSelectedItem();
                        if (selected == null || !selected.getEmpName().equals(editor.getText())) {
                            filterItems(newValue, contactPersonCombo, employees);
                            contactPersonCombo.show();
                            if (contactPersonCombo.getItems().size() == 1) {
                                final Employees c = (Employees) contactPersonCombo.getItems().get(0);
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

    public StoresController() {
        instanse = this;
        storeMap = new HashMap<>();
    }

    public static StoresController getInstance() {
        return instanse;
    }

    public void handleNew() {
        clearFields();
        enableFields(true);
        StoreDAO storeDAO = new StoreDAO();
        this.storeCode.setText(String.valueOf(storeDAO.getLastIndex() + 1));

    }

    public void save(int operation) {
        StoreDAO storeDAO = new StoreDAO();
        TreeItem<String> item = new TreeItem<String>(storeName.getText());

        Stores emp = new Stores(new BigDecimal(storeCode.getText()), null, (Employees) contactPersonCombo.getSelectionModel().getSelectedItem(),
                storeName.getText(), storeAdd.getText(), storePhone.getText(), isMain.isSelected() ? new BigDecimal(1) : new BigDecimal(0), null);

        if (operation == 1) {
            int id = storeDAO.insertStore(1, Integer.valueOf(contactPerson.getText()), isMain.isSelected() ? 1 : 0, storePhone.getText(), storeAdd.getText(),
                    storeName.getText(), Integer.valueOf(storeCode.getText()));
            Node node = new Label(String.valueOf(id));
            item.setGraphic(node);
            treeRoot.getChildren().add(
                    item);

            storeMap.put(id, emp);
            enableFields(false);
        } else if (operation == 2) {
            ((TreeItem) storeTree.getSelectionModel().getSelectedItem()).setValue(storeName.getText());

            int id = storeDAO.updateStore(Integer.valueOf(storeCode.getText()), 1, Integer.valueOf(contactPerson.getText()), isMain.isSelected() ? 1 : 0, storePhone.getText(), storeAdd.getText(),
                    storeName.getText());
        }
        //handleNew();
    }

    public void update() {
//        EmployeeDAO employeeDAO = new EmployeeDAO();
//        TreeItem treeItem = (TreeItem)empTree.getSelectionModel().getSelectedItem();
// LocalDate localDate = hireDate .getValue();
//        Instant instant = Instant.from(localDate.atStartOfDay(ZoneId.systemDefault()));
//        Date date = Date.from(instant);
//        int id = employeeDAO.updateEmployee(Integer.parseInt(empCode.getText()),date, notes.getText(), 1, 0, Double.parseDouble(empCommision.getText()),
//                Double.parseDouble(empSalary.getText()), empPhone2.getText(), empPhone1.getText(), empName.getText());
        //System.out.println(costCenterTable.getItems());
        enableFields(true);
    }

    public void delete() {
        StoreDAO storeDAO = new StoreDAO();
        storeDAO.deleteStore(Integer.parseInt(storeCode.getText()));

        treeRoot.getChildren().remove((TreeItem) storeTree.getSelectionModel().getSelectedItem());
    }

    public void fillTree() {
        StoreDAO storeDAO = new StoreDAO();
        List<Stores> storeses = storeDAO.getAllStores();
        TreeItem<String> item;
        TreeCell<String> cell;
        Node node;
        for (Stores store : storeses) {
            node = new Label(store.getStoreId().toString());

            item = new TreeItem<String>(store.getStoreName(), node);

            treeRoot.getChildren().add(item);

            storeMap.put(store.getStoreId().intValue(), store);
        }
    }

    public void enableFields(boolean status) {
        storeName.setEditable(status);
        storePhone.setEditable(status);
        storeAdd.setEditable(status);

    }

    public void clearFields() {
        storeCode.setText("");
        storeName.setText("");
        storePhone.setText("");
        storeAdd.setText("");
        contactPerson.setText("");
        isMain.setSelected(Boolean.FALSE);
        contactPersonCombo.setValue(null);
        contactPersonCombo.hide();

    }

    private <T> void filterItems(String filter, ComboBox<Employees> comboBox,
            List<Employees> items) {
        List<Employees> filteredItems = new ArrayList<>();
        for (Employees item : items) {
            if (item.getEmpName().toString().toLowerCase().indexOf(filter.toLowerCase()) != -1) {
                filteredItems.add(item);
            }
        }
        comboBox.setItems(FXCollections.observableArrayList(filteredItems));
    }

}
