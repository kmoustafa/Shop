/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shop;

import daos.BankDAO;
import java.math.BigDecimal;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import model.Banks;
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

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        enableFields(false);
                  bankTree.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observableValue, Object oldValue, Object newValue) {
                //Check whether item is selected and set value of selected item to Label
                if (bankTree.getSelectionModel().getSelectedItem() != null) {
                    TreeItem treeItem = (TreeItem)bankTree.getSelectionModel().getSelectedItem();
                    int id = Integer.parseInt(((Label)treeItem.getGraphic()).getText());
                    Banks banks = venMap.get(id);
                    if(banks.getBankId() != BigDecimal.ZERO )
                        bankCode.setText(banks.getBankId().toString());
                    if(banks.getBankName() != null || !banks.getBankName().isEmpty())
                        bankName.setText(banks.getBankName());
                    if(banks.getAccType() != null || !banks.getAccType().isEmpty())
                        accType.setText(banks.getAccType());
                    if(banks.getBankAddress() != null || !banks.getBankAddress().isEmpty())
                        bankAdd.setText(banks.getBankAddress());                    
                    if(banks.getBankBranch() != null || !banks.getBankBranch().isEmpty())
                        bankBran.setText(banks.getBankBranch());
                    if(banks.getBankEmail() != null || !banks.getBankEmail().isEmpty())
                        bankEmail.setText(banks.getBankEmail());
                    if(banks.getBankFax() != null || !banks.getBankFax().isEmpty())
                        bankFax.setText(banks.getBankFax()); 
                    if(banks.getBankIdNumber() != null || !banks.getBankIdNumber().isEmpty())
                        bankIdNumber.setText(banks.getBankIdNumber());
                    if(banks.getBankPhone() != null || !banks.getBankPhone().isEmpty())
                        bankPhone.setText(banks.getBankPhone());
                    if(banks.getOpenCredit() != null || banks.getOpenCredit() != 0)
                        credit.setText(banks.getOpenCredit().toString());
                    if(banks.getOpenDebit() != null || banks.getOpenDebit() != 0)
                        debit.setText(banks.getOpenDebit().toString());                    
                }
            }
        });

        bankTree.setRoot(treeRoot);

        treeRoot.setExpanded(true);
    }

    public BanksController() {
        instanse = this;
           venMap = new HashMap<>();
    }

    public static BanksController getInstance() {
        return instanse;
    }

    public void handleNew() {
        enableFields(true);
    }

    public void save() {
        BankDAO bankDAO = new BankDAO();
 TreeItem<String> item =  new TreeItem<String>(bankName.getText());
        int id = bankDAO.insertBank(bankName.getText(), bankBran.getText(), bankAdd.getText(), bankPhone.getText(), bankFax.getText(), bankEmail.getText(),
                Double.parseDouble(debit.getText()), Double.parseDouble(credit.getText()), bankIdNumber.getText(), 0, 1, accType.getText());
         Node node = new Label(String.valueOf(id));
         item.setGraphic(node);
        treeRoot.getChildren().add(
               item);
        Banks b = new Banks(new BigDecimal(id), null, null, bankName.getText(), bankBran.getText(), bankAdd.getText(), bankPhone.getText(), bankFax.getText(),
                bankEmail.getText(), Double.parseDouble(debit.getText()), Double.parseDouble(credit.getText()), bankIdNumber.getText(), accType.getText(), null);
        venMap.put(id, b);
        //handleNew();
    }
    public void update() {
        BankDAO bankDAO = new BankDAO();
        TreeItem treeItem = (TreeItem)bankTree.getSelectionModel().getSelectedItem();

        int id = bankDAO.updateBank(Integer.parseInt(bankCode.getText()),bankName.getText(), bankBran.getText(), bankAdd.getText(), bankPhone.getText(), bankFax.getText(), bankEmail.getText(),
                Double.parseDouble(debit.getText()), Double.parseDouble(credit.getText()), bankIdNumber.getText(), 0, 1, accType.getText());
        //System.out.println(costCenterTable.getItems());
    }
    public void delete(){
         BankDAO bankDAO = new BankDAO();
         bankDAO.deleteBank(Integer.parseInt(bankCode.getText()));
         
         treeRoot.getChildren().remove((TreeItem)bankTree.getSelectionModel().getSelectedItem());
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
        bankCode.setEditable(status);
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
}
