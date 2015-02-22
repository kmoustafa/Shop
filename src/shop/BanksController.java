/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shop;

import daos.BankDAO;
import daos.ChartDAO;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.util.Callback;
import model.Banks;
import model.Charts;
import model.Expences;
import model.Persons;
import org.omg.CORBA.BAD_CONTEXT;

/**
 * FXML Controller class
 *
 * @author Kareem.Moustafa
 */
public class BanksController implements Initializable {

    /**
     * Initializes the controller class.
     */
    private static BanksController instanse;

    private TreeItem<String> treeRoot = new TreeItem<String>("البنوك");
    HashMap<Integer, Banks> venMap;
    @FXML
    TextField bankCode;
    @FXML
    TextField bankName;
    @FXML
    TextField bankBran;
    @FXML
    TextField bankPhone;
    @FXML
    TextField bankFax;
    @FXML
    TextField bankEmail;
    @FXML
    TextArea bankAdd;
    @FXML
    TextField debit;
    @FXML
    TextField credit;
    @FXML
    TextField bankIdNumber;
    @FXML
    TextField accType;
    @FXML
    TreeView bankTree;
    @FXML
    ComboBox mainAccountCombo;
    @FXML
    TextField mainAccount;

    ObservableList<Charts> chartsA = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        enableFields(false);
        bankTree.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observableValue, Object oldValue, Object newValue) {
                //Check whether item is selected and set value of selected item to Label
                if (bankTree.getSelectionModel().getSelectedItem() != null) {
                    TreeItem treeItem = (TreeItem) bankTree.getSelectionModel().getSelectedItem();
                    int id = Integer.parseInt(((Label) treeItem.getGraphic()).getText());
                    Banks banks = venMap.get(id);
                    if (banks.getBankId() != BigDecimal.ZERO) {
                        bankCode.setText(banks.getBankId().toString());
                    }
                    if (banks.getBankName() != null || !banks.getBankName().isEmpty()) {
                        bankName.setText(banks.getBankName());
                    }
                    if (banks.getAccType() != null || !banks.getAccType().isEmpty()) {
                        accType.setText(banks.getAccType());
                    }
                    if (banks.getBankAddress() != null || !banks.getBankAddress().isEmpty()) {
                        bankAdd.setText(banks.getBankAddress());
                    }
                    if (banks.getBankBranch() != null || !banks.getBankBranch().isEmpty()) {
                        bankBran.setText(banks.getBankBranch());
                    }
                    if (banks.getBankEmail() != null || !banks.getBankEmail().isEmpty()) {
                        bankEmail.setText(banks.getBankEmail());
                    }
                    if (banks.getBankFax() != null || !banks.getBankFax().isEmpty()) {
                        bankFax.setText(banks.getBankFax());
                    }
                    if (banks.getBankIdNumber() != null || !banks.getBankIdNumber().isEmpty()) {
                        bankIdNumber.setText(banks.getBankIdNumber());
                    }
                    if (banks.getBankPhone() != null || !banks.getBankPhone().isEmpty()) {
                        bankPhone.setText(banks.getBankPhone());
                    }
                    if (banks.getOpenCredit() != null || banks.getOpenCredit() != 0) {
                        credit.setText(banks.getOpenCredit().toString());
                    }
                    if (banks.getOpenDebit() != null || banks.getOpenDebit() != 0) {
                        debit.setText(banks.getOpenDebit().toString());
                    }
                    if (banks.getCharts() != null) {
                        mainAccount.setText(banks.getCharts().getAccId().toString());
                        for (Charts object : chartsA) {
                            if (object.getAccId().intValue() == banks.getCharts().getAccId().intValue()) {
                                mainAccountCombo.setValue(object);
                                break;
                            }
                        }
                    }
                }
            }
        });

        bankTree.setRoot(treeRoot);

        treeRoot.setExpanded(true);

        ChartDAO chartDAO = new ChartDAO();
        final List<Charts> charts = chartDAO.getAllCharts();
        chartsA.addAll(charts);
        mainAccountCombo.setCellFactory(new Callback<ListView<Charts>, ListCell<Charts>>() {

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
        mainAccountCombo.setItems(chartsA);
        mainAccountCombo.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observableValue, Object oldValue, Object newValue) {
                //Check whether item is selected and set value of selected item to Label
                if (mainAccountCombo.getSelectionModel().getSelectedItem() != null) {
                    Charts c = (Charts) newValue;
                    mainAccountCombo.setValue(c);
                    mainAccount.setText(String.valueOf(c.getAccId()));
                }
            }
        });

        mainAccountCombo.setEditable(true);
        mainAccountCombo.getEditor().textProperty()
                .addListener(new ChangeListener<String>() {
                    @Override
                    public void changed(ObservableValue<? extends String> observable,
                            String oldValue, String newValue) {
                        final TextField editor = mainAccountCombo.getEditor();
                        final Charts selected = (Charts) mainAccountCombo.getSelectionModel()
                        .getSelectedItem();
                        if (selected == null || !selected.getAccName().equals(editor.getText())) {
                            filterItems(newValue, mainAccountCombo, charts);
                            mainAccountCombo.show();
                            if (mainAccountCombo.getItems().size() == 1) {
                                final Charts c = (Charts) mainAccountCombo.getItems().get(0);
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
    }

    public BanksController() {
        instanse = this;
        venMap = new HashMap<>();
    }

    public static BanksController getInstance() {
        return instanse;
    }

    public void handleNew() {
        clearFields();
        enableFields(true);
        BankDAO bankDAO = new BankDAO();
        this.bankCode.setText(String.valueOf(bankDAO.getLastIndex() + 1));

    }

    public void save(int operation) {
        BankDAO bankDAO = new BankDAO();
        Banks b = new Banks(new BigDecimal(bankCode.getText()), null, new ChartDAO().getChartById(Integer.valueOf(mainAccount.getText())), bankName.getText(), bankBran.getText(), bankAdd.getText(), bankPhone.getText(), bankFax.getText(),
                bankEmail.getText(), Double.parseDouble(debit.getText()), Double.parseDouble(credit.getText()), bankIdNumber.getText(), accType.getText(), null);
        if (operation == 1) {
            TreeItem<String> item = new TreeItem<String>(bankName.getText());
            int id = bankDAO.insertBank(Integer.valueOf(bankCode.getText()), bankName.getText(), bankBran.getText(), bankAdd.getText(), bankPhone.getText(), bankFax.getText(), bankEmail.getText(),
                    Double.parseDouble(debit.getText()), Double.parseDouble(credit.getText()), bankIdNumber.getText(), Integer.valueOf(mainAccount.getText()), 1, accType.getText());
            Node node = new Label(String.valueOf(id));
            item.setGraphic(node);
            treeRoot.getChildren().add(
                    item);

            venMap.put(id, b);
            enableFields(false);
        } else if (operation == 2) {
            ((TreeItem) bankTree.getSelectionModel().getSelectedItem()).setValue(bankName.getText());
            int id = bankDAO.updateBank(Integer.parseInt(bankCode.getText()), bankName.getText(), bankBran.getText(), bankAdd.getText(), bankPhone.getText(), bankFax.getText(), bankEmail.getText(),
                    Double.parseDouble(debit.getText()), Double.parseDouble(credit.getText()), bankIdNumber.getText(), Integer.valueOf(mainAccount.getText()), 1, accType.getText());
        }
        //handleNew();
    }

    public void update() {
//        BankDAO bankDAO = new BankDAO();
//        TreeItem treeItem = (TreeItem)bankTree.getSelectionModel().getSelectedItem();
//
//        int id = bankDAO.updateBank(Integer.parseInt(bankCode.getText()),bankName.getText(), bankBran.getText(), bankAdd.getText(), bankPhone.getText(), bankFax.getText(), bankEmail.getText(),
//                Double.parseDouble(debit.getText()), Double.parseDouble(credit.getText()), bankIdNumber.getText(), 0, 1, accType.getText());
        //System.out.println(costCenterTable.getItems());
        enableFields(true);
    }

    public void delete() {
        BankDAO bankDAO = new BankDAO();
        bankDAO.deleteBank(Integer.parseInt(bankCode.getText()));

        treeRoot.getChildren().remove((TreeItem) bankTree.getSelectionModel().getSelectedItem());
    }

    public void fillTree() {
        BankDAO bankDAO = new BankDAO();
        List<Banks> banks = bankDAO.getAllBanks();
        TreeItem<String> item;
        TreeCell<String> cell;
        Node node;
        for (Banks bank1 : banks) {
            node = new Label(bank1.getBankId().toString());

            item = new TreeItem<String>(bank1.getBankName(), node);

            treeRoot.getChildren().add(item);
            venMap.put(bank1.getBankId().intValue(), bank1);
        }
    }

    public void enableFields(boolean status) {
        //    bankCode.setEditable(status);
        bankAdd.setEditable(status);
        bankBran.setEditable(status);
        bankEmail.setEditable(status);
        bankFax.setEditable(status);
        bankIdNumber.setEditable(status);
        bankName.setEditable(status);
        bankPhone.setEditable(status);
        accType.setEditable(status);
        credit.setEditable(status);
        debit.setEditable(status);

    }

    public void clearFields() {
        bankCode.setText("");
        bankAdd.setText("");
        bankBran.setText("");
        bankEmail.setText("");
        bankFax.setText("");
        bankIdNumber.setText("");
        bankName.setText("");
        bankPhone.setText("");
        accType.setText("");
        credit.setText("");
        debit.setText("");
        this.mainAccountCombo.setValue(null);
        mainAccountCombo.hide();

    }

    private <T> void filterItems(String filter, ComboBox<Charts> comboBox,
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
