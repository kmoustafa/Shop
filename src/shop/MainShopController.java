/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shop;

import daos.UserDAO;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Reflection;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import model.Users;
import utils.Loading;

/**
 *
 * @author kareem.moustafa
 */
public class MainShopController implements Initializable {

    private boolean isLoggedIn;
    private int operation;
    @FXML
    private Label label;
    @FXML
    private TabPane tabPane;
    @FXML
    private ImageView saveButton;
    @FXML
    private ImageView newImg;
    @FXML
    private ImageView editImg;
    @FXML
    private ImageView deleteImg;
    @FXML
    private ImageView afterImg;
    @FXML
    private ImageView beforeImg;
    @FXML
    private ImageView lastImg;
    @FXML
    private ImageView firstImg;
    @FXML
    private ImageView preImg;
    @FXML
    private ImageView printImg;
    @FXML
    private GridPane root;
    @FXML
    Label loginMessage;
    @FXML
    private TextField userName;
    @FXML
    private PasswordField password;
    ContextMenu contextMenu;
    @FXML
    private Button btnShow;

    @FXML
    private void handleButtonAction(ActionEvent event) {

//        try {
//
//            Tab tab = new Tab("بيانات الشركة");
//            tab.setContent(FXMLLoader.load(this.getClass().getResource("CompanyInfo.fxml")));
//            this.tabPane.getTabs().add(tab);
//            tab = new Tab("شجرة الحسابات");
//            tab.setContent(FXMLLoader.load(this.getClass().getResource("Charts.fxml")));
//            this.tabPane.getTabs().add(tab);
//            tab = new Tab("العملاء");
//            tab.setContent(FXMLLoader.load(this.getClass().getResource("Customer.fxml")));
//            this.tabPane.getTabs().add(tab);
//            tab = new Tab("الموردون");
//            tab.setContent(FXMLLoader.load(this.getClass().getResource("Vendors.fxml")));
//            this.tabPane.getTabs().add(tab);
//            tab = new Tab("المخازن");
//            tab.setContent(FXMLLoader.load(this.getClass().getResource("Stores.fxml")));
//            this.tabPane.getTabs().add(tab);
//            tab = new Tab("الألوان");
//            tab.setContent(FXMLLoader.load(this.getClass().getResource("Colors.fxml")));
//            this.tabPane.getTabs().add(tab);
//            tab = new Tab("مراكز التكلفة");
//            tab.setContent(FXMLLoader.load(this.getClass().getResource("CostCenters.fxml")));
//            this.tabPane.getTabs().add(tab);
//            tab = new Tab("أقسام وظيفية");
//            tab.setContent(FXMLLoader.load(this.getClass().getResource("Departments.fxml")));
//            this.tabPane.getTabs().add(tab);
//            tab = new Tab("المجموعات");
//            tab.setContent(FXMLLoader.load(this.getClass().getResource("Groups.fxml")));
//            this.tabPane.getTabs().add(tab);
//            tab = new Tab("أقسام");
//            tab.setContent(FXMLLoader.load(this.getClass().getResource("Sections.fxml")));
//            this.tabPane.getTabs().add(tab);
//            tab = new Tab("المقاسات");
//            tab.setContent(FXMLLoader.load(this.getClass().getResource("Sizes.fxml")));
//            this.tabPane.getTabs().add(tab);
//            tab = new Tab("الأنواع");
//            tab.setContent(FXMLLoader.load(this.getClass().getResource("Types.fxml")));
//            this.tabPane.getTabs().add(tab);
//            tab = new Tab("الوحدات");
//            tab.setContent(FXMLLoader.load(this.getClass().getResource("Units.fxml")));
//            this.tabPane.getTabs().add(tab);
//            tab = new Tab("المناطق");
//            tab.setContent(FXMLLoader.load(this.getClass().getResource("Zones.fxml")));
//            this.tabPane.getTabs().add(tab);
//            tab = new Tab("المصروفات");
//            tab.setContent(FXMLLoader.load(this.getClass().getResource("Expenses.fxml")));
//            this.tabPane.getTabs().add(tab);
//            tab = new Tab("البنوك");
//            tab.setContent(FXMLLoader.load(this.getClass().getResource("Banks.fxml")));
//            this.tabPane.getTabs().add(tab);
//            tab = new Tab("الكاشير");
//            tab.setContent(FXMLLoader.load(this.getClass().getResource("Cashier.fxml")));
//            this.tabPane.getTabs().add(tab);
//            //tabPane.getSelectionModel().select(tab);
//        } catch (IOException ex) {
//            Logger.getLogger(MainShopController.class.getName()).log(Level.SEVERE, null, ex);
//        }
        root.setVisible(true);
        DropShadow dropShadow = new DropShadow();
        dropShadow.setOffsetX(2);
        dropShadow.setOffsetY(2);
        Reflection r = new Reflection();
        r.setFraction(0.7f);
        root.setEffect(r);
        //root.setEffect(dropShadow);
    }

    @FXML
    private void handleLogin(ActionEvent event) {

        if (loginMessage.isVisible()) {
            loginMessage.setVisible(false);
        }

        UserDAO userDAO = new UserDAO();
        Users u = userDAO.login(userName.getText(), password.getText());
        if (u != null) {
            btnShow.setText("تسجيل خروج");
            root.setVisible(false);
            tabPane.setVisible(true);
            isLoggedIn = true;
        } else {
            isLoggedIn = false;
            loginMessage.setVisible(true);
        }
    }

    @FXML
    private void handleMenuAction(ActionEvent event) {
        try {
        //    if (isLoggedIn) {
                String st = ((MenuItem) event.getSource()).getId();

                Tab tab;
                switch (st) {
                    case "costCenterMenu":
                        tab = new Tab("مراكز التكلفة");
                        tab.setId("CostCenters");
                        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("CostCenters.fxml"));
                        tab.setContent(loader.load());
                        ((CostCentersController) loader.getController()).fillTable();
                        tab.setContextMenu(contextMenu);
                        this.tabPane.getTabs().add(tab);
                        break;
                    case "expensesMenu":
                        tab = new Tab("المصروفات");
                        tab.setId("Expenses");
                        loader = new FXMLLoader(this.getClass().getResource("Expenses.fxml"));
                        tab.setContent(loader.load());
                        ((ExpensesController) loader.getController()).fillTable();
                        tab.setContextMenu(contextMenu);
                        this.tabPane.getTabs().add(tab);
                        break;
                    case "zonesMenu":
                        tab = new Tab("المناطق");
                        tab.setId("Zones");
                        loader = new FXMLLoader(this.getClass().getResource("Zones.fxml"));
                        tab.setContent(loader.load());
                        ((ZonesController) loader.getController()).fillTable();
                        tab.setContextMenu(contextMenu);
                        this.tabPane.getTabs().add(tab);
                        break;
                    case "purchasesMenu":
                        tab = new Tab("المشتريات");
                        tab.setContent(FXMLLoader.load(this.getClass().getResource("Purchase.fxml")));
                        this.tabPane.getTabs().add(tab);
                        break;
                    case "vendorsMenu":
                        tab = new Tab("الموردون");
                        tab.setId("Vendors");
                        loader = new FXMLLoader(this.getClass().getResource("Vendors.fxml"));
                        tab.setContent(loader.load());
                        ((VendorsController) loader.getController()).fillTree();
                        tab.setContextMenu(contextMenu);
                        this.tabPane.getTabs().add(tab);
                        break;
                    case "rePurchasesMenu":
                        tab = new Tab("مرتد المشتريات");
                        tab.setContent(FXMLLoader.load(this.getClass().getResource("RePurchase.fxml")));
                        this.tabPane.getTabs().add(tab);
                        break;
                    case "paymentsMenu":
                        tab = new Tab("المدفوعات");
                        tab.setContent(FXMLLoader.load(this.getClass().getResource("Payments.fxml")));
                        this.tabPane.getTabs().add(tab);
                        break;
                    case "customersMenu":
                        tab = new Tab("العملاء");
                        tab.setContent(FXMLLoader.load(this.getClass().getResource("Customer.fxml")));
                        this.tabPane.getTabs().add(tab);
                        break;
                    case "salesMenu":
                        tab = new Tab("المبيعات");
                        tab.setContent(FXMLLoader.load(this.getClass().getResource("Sales.fxml")));
                        this.tabPane.getTabs().add(tab);
                        break;
                    case "reSalesMenu":
                        tab = new Tab("مرتد المبيعات");
                        tab.setContent(FXMLLoader.load(this.getClass().getResource("ReSales.fxml")));
                        this.tabPane.getTabs().add(tab);
                        break;
                    case "receiptsMenu":
                        tab = new Tab("المقبوضات");
                        tab.setContent(FXMLLoader.load(this.getClass().getResource("Receipts.fxml")));
                        this.tabPane.getTabs().add(tab);
                        break;
                    case "banksMenu":
                        tab = new Tab("البنك");
                        tab.setId("Banks");
                        loader = new FXMLLoader(this.getClass().getResource("Banks.fxml"));
                        tab.setContent(loader.load());
                        ((BanksController) loader.getController()).fillTree();
                        tab.setContextMenu(contextMenu);
                        this.tabPane.getTabs().add(tab);

                        break;
                    case "convCheqToBankMenu":
                        tab = new Tab("تحويل شيكات إلى بنك");
                        tab.setContent(FXMLLoader.load(this.getClass().getResource("ConvertChequeToBanks.fxml")));
                        this.tabPane.getTabs().add(tab);
                        break;
                    case "EmployeesMenu":
                        tab = new Tab("الموظفين");
                        tab.setId("Employees");
                        loader = new FXMLLoader(this.getClass().getResource("Employees.fxml"));
                        tab.setContent(loader.load());
                        ((EmployeesController) loader.getController()).fillTree();
                        tab.setContextMenu(contextMenu);
                        this.tabPane.getTabs().add(tab);
                        break;
                    case "storesMenu":
                        tab = new Tab("المخازن");
                        tab.setContent(FXMLLoader.load(this.getClass().getResource("Stores.fxml")));
                        this.tabPane.getTabs().add(tab);
                        break;
                    case "sizesMenu":
                        tab = new Tab("المقاسات");
                        tab.setContent(FXMLLoader.load(this.getClass().getResource("Sizes.fxml")));
                        this.tabPane.getTabs().add(tab);
                        break;
                    case "departmentsMenu":
                        tab = new Tab("أقسام وظيفية");
                        tab.setId("Departments");
                        loader = new FXMLLoader(this.getClass().getResource("Departments.fxml"));
                        tab.setContent(loader.load());
                        ((DepartmentsController) loader.getController()).fillTable();
                        tab.setContextMenu(contextMenu);
                        this.tabPane.getTabs().add(tab);
                        break;
                    case "groupsMenu":
                        tab = new Tab("المجموعات");
                        tab.setContent(FXMLLoader.load(this.getClass().getResource("Groups.fxml")));
                        this.tabPane.getTabs().add(tab);
                        break;
                    case "typesMenu":
                        tab = new Tab("الأنواع");
                        tab.setContent(FXMLLoader.load(this.getClass().getResource("Types.fxml")));
                        this.tabPane.getTabs().add(tab);
                        break;
                    case "unitsMenu":
                        tab = new Tab("الوحدات");
                        tab.setContent(FXMLLoader.load(this.getClass().getResource("Units.fxml")));
                        this.tabPane.getTabs().add(tab);
                        break;
                    case "itemsMenu":
                        tab = new Tab("الأصناف");
                        tab.setContent(FXMLLoader.load(this.getClass().getResource("Items.fxml")));
                        this.tabPane.getTabs().add(tab);
                        break;
                    case "chartsMenu":
                        tab = new Tab("شجرة الحسابات");
                        tab.setId("Charts");
                        loader = new FXMLLoader(this.getClass().getResource("Charts.fxml"));
                        tab.setContent(loader.load());
                        ((ChartsController) loader.getController()).fillTree();
                        tab.setContextMenu(contextMenu);
                        this.tabPane.getTabs().add(tab);
                        break;
                    case "companyInfoMenu":
                        tab = new Tab("بيانات الشركة");
                        tab.setContent(FXMLLoader.load(this.getClass().getResource("CompanyInfo.fxml")));
                        this.tabPane.getTabs().add(tab);
                        break;
                    case "vNoticMenu":
                        tab = new Tab("تسوية الموردين");
                        tab.setContent(FXMLLoader.load(this.getClass().getResource("Notic.fxml")));
                        this.tabPane.getTabs().add(tab);
                        break;
                    case "ExpenseFinPaperMenu":
                        tab = new Tab("حافظة اوراق مالية لمورد");
                        tab.setContent(FXMLLoader.load(this.getClass().getResource("ExpenseFinPaper.fxml")));
                        this.tabPane.getTabs().add(tab);
                        break;
                    case "cNoticMenu":
                        tab = new Tab("تسوية عميل");
                        tab.setContent(FXMLLoader.load(this.getClass().getResource("Notic.fxml")));
                        this.tabPane.getTabs().add(tab);
                        break;
                    case "IncomingFinPaper":
                        tab = new Tab("حافظة اوراق مالية لعميل");
                        tab.setContent(FXMLLoader.load(this.getClass().getResource("IncomingFinPaper.fxml")));
                        this.tabPane.getTabs().add(tab);
                        break;
                    case "incheq":
                        tab = new Tab("تحصيل شيك");
                        tab.setContent(FXMLLoader.load(this.getClass().getResource("InCheque.fxml")));
                        this.tabPane.getTabs().add(tab);
                        break;
                    case "bNoticeMenu":
                        tab = new Tab("تسوية بنك");
                        tab.setContent(FXMLLoader.load(this.getClass().getResource("Notic.fxml")));
                        this.tabPane.getTabs().add(tab);
                        break;
                    case "ExpnseN1":
                        tab = new Tab("سلف");
                        tab.setContent(FXMLLoader.load(this.getClass().getResource("ExpnsesN.fxml")));
                        this.tabPane.getTabs().add(tab);
                        break;
                    case "dialy":
                        tab = new Tab("قيود يومية");
                        tab.setContent(FXMLLoader.load(this.getClass().getResource("Dialy.fxml")));
                        this.tabPane.getTabs().add(tab);
                        break;
                    case "ExpnseN":
                        tab = new Tab("مصروفات");
                        tab.setContent(FXMLLoader.load(this.getClass().getResource("ExpnseN.fxml")));
                        this.tabPane.getTabs().add(tab);
                        break;
                    case "cashMenu":
                        tab = new Tab("الكاشير");
                        tab.setContent(FXMLLoader.load(this.getClass().getResource("Cashier.fxml")));
                        this.tabPane.getTabs().add(tab);
                        break;
                    case "colorsMenu":
                        tab = new Tab("الألوان");
                        tab.setContent(FXMLLoader.load(this.getClass().getResource("Colors.fxml")));
                        this.tabPane.getTabs().add(tab);
                        break;
                    case "convCheqLawMenu":
                        tab = new Tab("تحويل شيك لمحامي");
                        tab.setContent(FXMLLoader.load(this.getClass().getResource("ConvertChequeToLaw.fxml")));
                        this.tabPane.getTabs().add(tab);
                        break;
                    case "ExpStoresMenu":
                        tab = new Tab("صادر مخازن");
                        tab.setContent(FXMLLoader.load(this.getClass().getResource("ExpStores.fxml")));
                        this.tabPane.getTabs().add(tab);
                        break;
                    case "ImpStoresMenu":
                        tab = new Tab("وارد مخازن");
                        tab.setContent(FXMLLoader.load(this.getClass().getResource("ImpStores.fxml")));
                        this.tabPane.getTabs().add(tab);
                        break;
                    case "transBwnStores":
                        tab = new Tab("التحويل بين المخازن");
                        tab.setContent(FXMLLoader.load(this.getClass().getResource("TransBtwStores.fxml")));
                        this.tabPane.getTabs().add(tab);
                        break;
                    case "sectionsMenu":
                        tab = new Tab("أقسام");
                        tab.setContent(FXMLLoader.load(this.getClass().getResource("Sections.fxml")));
                        this.tabPane.getTabs().add(tab);
                        break;
                    case "outCheqMenu":
                        tab = new Tab("تسديد شيكات");
                        tab.setContent(FXMLLoader.load(this.getClass().getResource("OutCheque.fxml")));
                        this.tabPane.getTabs().add(tab);
                        break;
                    case "inCheqMenu":
                        tab = new Tab("مرتد شيكات");
                        tab.setContent(FXMLLoader.load(this.getClass().getResource("InCheque.fxml")));
                        this.tabPane.getTabs().add(tab);
                        break;

                }
        //    }
        } catch (IOException ex) {
            ex.printStackTrace();
            Logger.getLogger(MainShopController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void handleSave(MouseEvent event) {
        if (isLoggedIn) {

            String name = this.tabPane.getSelectionModel().getSelectedItem().getId();
            switch (name) {
                case "CostCenters":
                    name += ".fxml";
//                                 CostCentersController ccc = (CostCentersController)loadController(name);
                    CostCentersController ccc = CostCentersController.getInstance();

                    ccc.save();
                    break;
                case "Expenses":
                    ExpensesController controller = ExpensesController.getInstance();
                    controller.save();
                    break;
                case "Zones":
                    ZonesController zcontroller = ZonesController.getInstance();
                    zcontroller.save();
                    break;
                case "Vendors":
                    VendorsController vcontroller = VendorsController.getInstance();
                    vcontroller.save();
                    break;
                case "Banks":
                    BanksController bcontroller = BanksController.getInstance();
                    bcontroller.save();
                    break;
                case "Employees":
                    EmployeesController empcontroller = EmployeesController.getInstance();
                    empcontroller.save();
                    break;
                case "Departments":
                    DepartmentsController depcontroller = DepartmentsController.getInstance();
                    depcontroller.save();
                    break;
                case "Charts":
                    ChartsController chartcontroller = ChartsController.getInstance();
                    chartcontroller.save(operation);
                    break;
            }
        }
        operation = 0;
    }

    @FXML
    private void handleUpdate(MouseEvent event) {
        operation = 2;
        if (isLoggedIn) {

            String name = this.tabPane.getSelectionModel().getSelectedItem().getId();
            switch (name) {
                case "CostCenters":
                    name += ".fxml";
//                                 CostCentersController ccc = (CostCentersController)loadController(name);
                    CostCentersController ccc = CostCentersController.getInstance();
                    ccc.update();
                    break;
                case "Expenses":
                    ExpensesController controller = ExpensesController.getInstance();
                    controller.update();
                    break;
                case "Zones":
                    ZonesController zcontroller = ZonesController.getInstance();
                    zcontroller.update();
                    break;
                case "Banks":
                    BanksController bcontroller = BanksController.getInstance();
                    bcontroller.update();
                    break;
                case "Employees":
                    EmployeesController empcontroller = EmployeesController.getInstance();
                    empcontroller.update();
                    break;
                case "Departments":
                    DepartmentsController depcontroller = DepartmentsController.getInstance();
                    depcontroller.update();
                    break;
                case "Charts":
                    ChartsController chartcontroller = ChartsController.getInstance();
                    chartcontroller.update();
                    break;
            }
        }
              //  operation = 0;

    }

    @FXML
    private void handleNew(MouseEvent event) {
        operation = 1;
        if (isLoggedIn) {

            String name = this.tabPane.getSelectionModel().getSelectedItem().getId();
            switch (name) {
                case "CostCenters":
                    name += ".fxml";
//              CostCentersController ccc = (CostCentersController)loadController(name);
                    CostCentersController ccc = CostCentersController.getInstance();

                    ccc.handleNew();
                    break;
                case "Expenses":
                    ExpensesController controller = ExpensesController.getInstance();
                    controller.handleNew();
                    break;
                case "Zones":
                    ZonesController zcontroller = ZonesController.getInstance();
                    zcontroller.handleNew();
                    break;
                case "Vendors":
                    VendorsController vcontroller = VendorsController.getInstance();
                    vcontroller.handleNew();
                    break;
                case "Banks":
                    BanksController bcontroller = BanksController.getInstance();
                    bcontroller.handleNew();
                    break;
                case "Employees":
                    EmployeesController empcontroller = EmployeesController.getInstance();
                    empcontroller.handleNew();
                    break;
                case "Departments":
                    DepartmentsController depcontroller = DepartmentsController.getInstance();
                    depcontroller.handleNew();
                    break;
                case "Charts":
                    ChartsController chartcontroller = ChartsController.getInstance();
                    chartcontroller.handleNew();
                    break;
            }
        }
               // operation = 0;

    }

    @FXML
    private void handleDelete(MouseEvent event) {
        if (isLoggedIn) {
            
            String name = this.tabPane.getSelectionModel().getSelectedItem().getId();
            switch (name) {
                case "CostCenters":
                    name += ".fxml";
//              CostCentersController ccc = (CostCentersController)loadController(name);
                    CostCentersController ccc = CostCentersController.getInstance();

                    ccc.delete();
                    break;
                case "Expenses":
                    ExpensesController controller = ExpensesController.getInstance();
                    controller.delete();
                    break;
                case "Zones":
                    ZonesController zcontroller = ZonesController.getInstance();
                    zcontroller.delete();
                    break;
                case "Banks":
                    BanksController bcontroller = BanksController.getInstance();
                    bcontroller.delete();
                    break;
                case "Employees":
                    EmployeesController empcontroller = EmployeesController.getInstance();
                    empcontroller.delete();
                    break;
                case "Departments":
                    DepartmentsController depcontroller = DepartmentsController.getInstance();
                    depcontroller.delete();
                    break;
                case "Charts":
                    ChartsController chartcontroller = ChartsController.getInstance();
                    chartcontroller.delete();
                    break;
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        Tooltip t = new Tooltip("حفظ");
        t.install(saveButton, t);
        Tooltip t1 = new Tooltip("جديد");
        t1.install(newImg, t1);
        Tooltip t2 = new Tooltip("تعديل");
        t2.install(editImg, t2);
        Tooltip t3 = new Tooltip("حذف");
        t3.install(deleteImg, t3);
        Tooltip t4 = new Tooltip("الأول");
        t4.install(firstImg, t4);
        Tooltip t5 = new Tooltip("الأخير");
        t5.install(lastImg, t5);
        Tooltip t6 = new Tooltip("السابق");
        t6.install(beforeImg, t6);
        Tooltip t7 = new Tooltip("التالي");
        t7.install(afterImg, t7);
        Tooltip t8 = new Tooltip("معاينة قبل الطباعة");
        t8.install(preImg, t8);
        Tooltip t9 = new Tooltip("طباعة");
        t9.install(printImg, t9);
        isLoggedIn = false;
        tabPane.setVisible(false);
        root.setVisible(false);
        contextMenu = new ContextMenu();
        MenuItem item1 = new MenuItem("إغلاق");
        item1.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                Tab t = tabPane.getSelectionModel().getSelectedItem();
                tabPane.getTabs().remove(t);
            }
        });
        MenuItem item2 = new MenuItem("إغلاق الكل");
        item2.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                tabPane.getTabs().removeAll(tabPane.getTabs());
            }
        });
        contextMenu.getItems().addAll(item1, item2);
    }

    public Object loadController(String name) {
        Object obj = new Object();

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(this.getClass().getResource(name));
        obj = loader.getController();
        return obj;

    }

    public boolean isIsLoggedIn() {
        return isLoggedIn;
    }

    public void setIsLoggedIn(boolean isLoggedIn) {
        this.isLoggedIn = isLoggedIn;
    }

}
