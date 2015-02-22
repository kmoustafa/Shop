/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package shop;

import daos.ChartDAO;
import daos.PersonDAO;
import daos.ZoneDAO;
import java.math.BigDecimal;
import java.net.URL;
import java.util.ArrayList;
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
import javafx.scene.control.Accordion;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.util.Callback;
import model.Charts;
import model.Persons;
import model.PersonsId;
import model.Zone;

/**
 * FXML Controller class
 *
 * @author Kareem.Moustafa
 */
public class CustomerController implements Initializable {

   /**
     * Initializes the controller class.
     */
    private static CustomerController instanse;

    private TreeItem<String> treeRoot = new TreeItem<String>("العملاء");
    HashMap<Integer, Persons> cusMap;
    @FXML
    TreeView customerTree;
    @FXML
    Accordion mainInfoAcc;
    @FXML
    Accordion generalInfoAcc;
    @FXML
    TitledPane mainInfo;
    @FXML
    TitledPane generalInfo;
    @FXML
    TextField cusCode;
    @FXML
    TextField cusName;
    @FXML
    TextField cusPhone1;
    @FXML
    TextField cusPhone2;
    @FXML
    TextField cusEmail;
    @FXML
    TextArea cusAdd;
    @FXML
    TextField contactName;
    @FXML
    TextField contactPhone;
    @FXML
    TextField openDebit;
    @FXML
    TextField openCredit;
    //ComapnyName = نسبة الخصم
    @FXML
    TextField companyName;
    @FXML
    TextField maxBalance;
    @FXML
    ComboBox<String> treatmentType;
    @FXML
    TextField zoneCode;
    @FXML
    ComboBox<Zone> zoneCombo;
    ObservableList<Zone> zonesList = FXCollections.observableArrayList();
    @FXML
    TextField accCode;
    @FXML
    ComboBox<Charts> accCombo;
    ObservableList<Charts> accsList = FXCollections.observableArrayList();

    ObservableList<String> treatmentTypeList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        mainInfo.setExpanded(true);
        mainInfoAcc.setExpandedPane(mainInfo);
        enableFields(false);
//        treeRoot.getChildren().addAll(Arrays.asList(
//                new TreeItem<String>("Child Node 1"),
//                new TreeItem<String>("Child Node 2"),
//                new TreeItem<String>("Child Node 3")));
        customerTree.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observableValue, Object oldValue, Object newValue) {
                //Check whether item is selected and set value of selected item to Label
                if (customerTree.getSelectionModel().getSelectedItem() != null) {
                    TreeItem treeItem = (TreeItem) customerTree.getSelectionModel().getSelectedItem();
                    if (treeItem.getGraphic() != null) {
                        int id = Integer.parseInt(((Label) treeItem.getGraphic()).getText());
                        Persons persons = cusMap.get(id);
                        if (persons.getId().getPersonId() != BigDecimal.ZERO) {
                            cusCode.setText(persons.getId().getPersonId().toString());
                        }
                        if (persons.getPersonName() != null && !persons.getPersonName().isEmpty()) {
                            cusName.setText(persons.getPersonName());
                        }
                        if (persons.getPersonPhone() != null && !persons.getPersonPhone().isEmpty()) {
                            cusPhone1.setText(persons.getPersonPhone());
                        }
                        if (persons.getPersonPhone2() != null && !persons.getPersonPhone2().isEmpty()) {
                            cusPhone2.setText(persons.getPersonPhone2());
                        }
                        if (persons.getPesronEmail() != null && !persons.getPesronEmail().isEmpty()) {
                            cusEmail.setText(persons.getPesronEmail());
                        }
                        if (persons.getPersonAddress() != null && !persons.getPersonAddress().isEmpty()) {
                            cusAdd.setText(persons.getPersonAddress());
                        }
                        if (persons.getContactName() != null && !persons.getContactName().isEmpty()) {
                            contactName.setText(persons.getContactName());
                        }
                        if (persons.getContactPhone() != null && !persons.getContactPhone().isEmpty()) {
                            contactPhone.setText(persons.getContactPhone());
                        }
                        if (persons.getOpenCredit() != 0) {
                            openCredit.setText(String.valueOf(persons.getOpenCredit()));
                        }
                        if (persons.getOpenDebit() != 0) {
                            openDebit.setText(String.valueOf(persons.getOpenDebit()));
                        }
                        if (persons.getCompanyName() != null && !persons.getCompanyName().isEmpty()) {
                            companyName.setText(persons.getCompanyName());
                        }
                        if (persons.getMaxBalance() != 0) {
                            maxBalance.setText(persons.getMaxBalance().toString());
                        }
                        if (persons.getZone() != null && persons.getZone().getZoneId().intValue() != 0) {
                            zoneCode.setText(persons.getZone().getZoneId().toString());
                            for (Zone zone : zonesList) {
                                if (zone.getZoneId().intValue() == persons.getZone().getZoneId().intValue()) {
                                    zoneCombo.setValue(zone);
                                    break;
                                }
                            }
                        }
                        if (persons.getTransType() != null) {
                            if (persons.getTransType().intValue() == 0) {
                                treatmentType.setValue("آجل");
                            } else {
                                treatmentType.setValue("نقدي");
                            }
                        }
                        if (persons.getCharts() != null || persons.getCharts().getAccId().intValue() != 0) {
                            accCode.setText(persons.getCharts().getAccId().toString());
                            for (Charts chart : accsList) {
                                if (chart.getAccId().intValue() == persons.getCharts().getAccId().intValue()) {
                                    accCombo.setValue(chart);
                                    break;
                                }
                            }
                        }
                    }
                }
            }
        });

        customerTree.setRoot(treeRoot);
        treeRoot.setExpanded(true);
        treatmentTypeList.add("آجل");
        treatmentTypeList.add("نقدي");
        treatmentType.setItems(treatmentTypeList);

        ChartDAO chartDAO = new ChartDAO();
        final List<Charts> charts = chartDAO.getAllCharts();
        accsList.addAll(charts);
        accCombo.setCellFactory(new Callback<ListView<Charts>, ListCell<Charts>>() {

            @Override
            public ListCell<Charts> call(ListView<Charts> p) {

                final ListCell<Charts> cell = new ListCell<Charts>() {

                    @Override
                    protected void updateItem(Charts t, boolean bln) {
                        super.updateItem(t, bln);

                        if (t != null) {
                            setText(t.getAccName());
                        } else {
                            setText(null);
                        }
                    }

                };

                return cell;
            }
        });
        accCombo.setItems(accsList);
        accCombo.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observableValue, Object oldValue, Object newValue) {
                //Check whether item is selected and set value of selected item to Label
                if (accCombo.getSelectionModel().getSelectedItem() != null) {
                    Charts c = (Charts) newValue;
                    accCombo.setValue(c);
                    accCode.setText(String.valueOf(c.getAccId()));
                }
            }
        });
        accCombo.setEditable(true);
        accCombo.getEditor().textProperty()
                .addListener(new ChangeListener<String>() {
                    @Override
                    public void changed(ObservableValue<? extends String> observable,
                            String oldValue, String newValue) {
                        final TextField editor = accCombo.getEditor();
                        final Charts selected = (Charts) accCombo.getSelectionModel()
                        .getSelectedItem();
                        if (selected == null || !selected.getAccName().equals(editor.getText())) {
                            filterItemsCharts(newValue, accCombo, charts);
                            accCombo.show();
                            if (accCombo.getItems().size() == 1) {
                                final Charts c = (Charts) accCombo.getItems().get(0);
                                String onlyOption = c.getAccName();
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
        
        
        
        ZoneDAO zoneDAO = new ZoneDAO();
        final List<Zone> zones = zoneDAO.getAllZones();
        zonesList.addAll(zones);
        zoneCombo.setCellFactory(new Callback<ListView<Zone>, ListCell<Zone>>() {

            @Override
            public ListCell<Zone> call(ListView<Zone> p) {

                final ListCell<Zone> cell = new ListCell<Zone>() {

                    @Override
                    protected void updateItem(Zone t, boolean bln) {
                        super.updateItem(t, bln);

                        if (t != null) {
                            setText(t.getZoneName());
                        } else {
                            setText(null);
                        }
                    }

                };

                return cell;
            }
        });
        zoneCombo.setItems(zonesList);
        zoneCombo.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observableValue, Object oldValue, Object newValue) {
                //Check whether item is selected and set value of selected item to Label
                if (zoneCombo.getSelectionModel().getSelectedItem() != null ) {
                    Zone c = (Zone) newValue;
                    zoneCombo.setValue(c);
                    zoneCode.setText(String.valueOf(c.getZoneId()));
                }
                System.out.println(zoneCombo.getSelectionModel().getSelectedItem());
            }
        });
        zoneCombo.setEditable(true);
        zoneCombo.getEditor().textProperty()
                .addListener(new ChangeListener<String>() {
                    @Override
                    public void changed(ObservableValue<? extends String> observable,
                            String oldValue, String newValue) {
                        final TextField editor = zoneCombo.getEditor();
                        final Zone selected = (Zone) zoneCombo.getSelectionModel()
                        .getSelectedItem();
                        if (selected == null || !selected.getZoneName().equals(editor.getText())) {
                            filterItems(newValue, zoneCombo, zones);
                            zoneCombo.show();
                            if (zoneCombo.getItems().size() == 1) {
                                final Zone c = (Zone) zoneCombo.getItems().get(0);
                                String onlyOption = c.getZoneName();
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

    public CustomerController() {
        instanse = this;
        cusMap = new HashMap<>();
    }

    public static CustomerController getInstance() {
        return instanse;
    }

    public void handleNew() {
        clearFields();
        enableFields(true);
        PersonDAO personDAO = new PersonDAO();
        int id = personDAO.getCustomerLastIndex();
        cusCode.setText(String.valueOf(id + 1));

    }

    public void save(int operation) {
        PersonDAO personDAO = new PersonDAO();

        int treatmentTypeId = 0;
        if (treatmentType.getValue().equals("آجل")) {
            treatmentTypeId = 0;
        } else if (treatmentType.getValue().equals("نقدي")) {
            treatmentTypeId = 1;
        }

        Persons p = new Persons(new PersonsId(new BigDecimal(cusCode.getText()), new BigDecimal(0)), null, (new ZoneDAO()).getZoneById(Integer.valueOf(zoneCode.getText())),
                null, (new ChartDAO()).getChartById(Integer.valueOf(accCode.getText())), cusName.getText(), companyName.getText(), cusPhone1.getText(), cusPhone2.getText(),
                cusAdd.getText(), null, Double.parseDouble(openDebit.getText()), Double.parseDouble(openCredit.getText()), new Double(maxBalance.getText()),
                contactName.getText(), contactPhone.getText(),
                new BigDecimal(treatmentTypeId), cusEmail.getText(), null, null, null);
        if (operation == 1) {
            int id = personDAO.insertPerson(cusEmail.getText(), 1, Integer.valueOf(zoneCode.getText()), 0, Integer.valueOf(accCode.getText()), treatmentTypeId, contactPhone.getText(), contactName.getText(), Double.valueOf(maxBalance.getText()), Double.parseDouble(openCredit.getText()),
                    Double.parseDouble(openDebit.getText()), null, cusAdd.getText(), cusPhone2.getText(), cusPhone1.getText(), companyName.getText(), cusName.getText(), 0, Integer.parseInt(cusCode.getText()));
            treeRoot.getChildren().add(
                    new TreeItem<String>(cusName.getText() + " " + id));

            cusMap.put(id, p);
            enableFields(false);
        } else if (operation == 2) {
            TreeItem treeItem = (TreeItem) customerTree.getSelectionModel().getSelectedItem();
            ((TreeItem) customerTree.getSelectionModel().getSelectedItem()).setValue(cusName.getText());
            int id = personDAO.updatePerson(Integer.parseInt(cusCode.getText()), cusEmail.getText(), 1, Integer.valueOf(zoneCode.getText()), 0, Integer.valueOf(accCode.getText()), treatmentTypeId, contactPhone.getText(), contactName.getText(), Double.valueOf(maxBalance.getText()), Double.parseDouble(openCredit.getText()),
                    Double.parseDouble(openDebit.getText()), null, cusAdd.getText(), cusPhone2.getText(), cusPhone1.getText(), companyName.getText(), cusName.getText(), 0);

            cusMap.remove(Integer.parseInt(cusCode.getText()));
            cusMap.put(Integer.parseInt(cusCode.getText()), p);
        }
        //handleNew();
    }

    public void update() {
        enableFields(true);

        //System.out.println(costCenterTable.getItems());
    }

    public void delete() {
        PersonDAO personDAO = new PersonDAO();
        TreeItem parent = ((TreeItem) customerTree.getSelectionModel().getSelectedItem()).getParent();
        personDAO.deletePerson(Integer.parseInt(cusCode.getText()), 1);
        parent.getChildren().remove(((TreeItem) customerTree.getSelectionModel().getSelectedItem()));

    }

    public void fillTree() {
        PersonDAO personDAO = new PersonDAO();
        List<Persons> persons = personDAO.getAllPersons(0);
        TreeItem<String> item;
        TreeCell<String> cell;
        Node node;
        for (Persons persons1 : persons) {
            node = new Label(persons1.getId().getPersonId().toString());

            item = new TreeItem<String>(persons1.getPersonName(), node);

            treeRoot.getChildren().add(item);
            cusMap.put(persons1.getId().getPersonId().intValue(), persons1);
        }
    }

    public void enableFields(boolean status) {
        cusName.setEditable(status);
        cusPhone1.setEditable(status);
        cusPhone2.setEditable(status);
        cusEmail.setEditable(status);
        cusAdd.setEditable(status);
        contactName.setEditable(status);
        contactPhone.setEditable(status);
        openDebit.setEditable(status);
        openCredit.setEditable(status);
        companyName.setEditable(status);
        maxBalance.setEditable(status);

    }

    public void clearFields() {
        cusCode.setText("");
        cusName.setText("");
        cusPhone1.setText("");
        cusPhone2.setText("");
        cusEmail.setText("");
        cusAdd.setText("");
        contactName.setText("");
        contactPhone.setText("");
        openDebit.setText("");
        openCredit.setText("");
        companyName.setText("");
        maxBalance.setText("");

    }

    private <T> void filterItems(String filter, ComboBox<Zone> comboBox,
            List<Zone> items) {
        List<Zone> filteredItems = new ArrayList<>();
        for (Zone item : items) {
            if (item.getZoneName().toString().toLowerCase().indexOf(filter.toLowerCase()) != -1) {
                filteredItems.add(item);
            }
        }
        comboBox.setItems(FXCollections.observableArrayList(filteredItems));
    }

    private <T> void filterItemsCharts(String filter, ComboBox<Charts> comboBox,
            List<Charts> items) {
        List<Charts> filteredItems = new ArrayList<>();
        for (Charts item : items) {
            if (item.getAccName().toString().toLowerCase().indexOf(filter.toLowerCase()) != -1) {
                filteredItems.add(item);
            }
        }
        comboBox.setItems(FXCollections.observableArrayList(filteredItems));
    }
    
}
