/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shop;

import com.sun.jmx.snmp.BerDecoder;
import daos.BankDAO;
import daos.ChartDAO;
import java.math.BigDecimal;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import model.Banks;
import model.Charts;
import model.Persons;

/**
 * FXML Controller class
 *
 * @author Kareem.Moustafa
 */
public class ChartsController implements Initializable {

    /**
     * Initializes the controller class.
     */
    private static ChartsController instanse;
    private TreeItem<String> treeRoot = new TreeItem<String>("شجرة الحسابات");
    private ArrayList<Charts> level1 = new ArrayList<Charts>();
    private ArrayList<Charts> level2 = new ArrayList<Charts>();
    private ArrayList<Charts> level3 = new ArrayList<Charts>();
    private ArrayList<Charts> level4 = new ArrayList<Charts>();

    HashMap<Integer, Charts> chartMap;
    @FXML
    RadioButton radCredit;
    @FXML
    RadioButton radDebit;
    @FXML
    RadioButton radMain;
    @FXML
    RadioButton radSub;
    @FXML
    RadioButton radTitle;
    @FXML
    RadioButton radView;
    @FXML
    RadioButton radNoView;
    @FXML
    RadioButton radBudget;
    @FXML
    RadioButton radHesabMotagra;
    @FXML
    RadioButton radWinLose;
    @FXML
    private TextField accId;
    @FXML
    private TextField accName;
    @FXML
    private TextField target;
    @FXML
    private TextField accRank;
    @FXML
    private TextField debit;
    @FXML
    private TextField credit;
    @FXML
    private Label targetName;
    @FXML
    TreeView chartTree;
    ToggleGroup debitCredit;
    ToggleGroup mainSub;
    ToggleGroup viewMethod;
    ToggleGroup reportType;

    private int veiwMode = -1;
    private int debitOrCredit = -1;
    private int report = -1;
    private int mainOrSub = -1;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        debitCredit = new ToggleGroup();
        mainSub = new ToggleGroup();
        viewMethod = new ToggleGroup();
        reportType = new ToggleGroup();

        radDebit.setToggleGroup(debitCredit);
        radCredit.setToggleGroup(debitCredit);

        radMain.setToggleGroup(mainSub);
        radSub.setToggleGroup(mainSub);

        radTitle.setToggleGroup(viewMethod);
        radView.setToggleGroup(viewMethod);
        radNoView.setToggleGroup(viewMethod);

        radBudget.setToggleGroup(reportType);
        radHesabMotagra.setToggleGroup(reportType);
        radWinLose.setToggleGroup(reportType);

        chartTree.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observableValue, Object oldValue, Object newValue) {
                //Check whether item is selected and set value of selected item to Label
                if (chartTree.getSelectionModel().getSelectedItem() != null) {
                    TreeItem treeItem = (TreeItem) chartTree.getSelectionModel().getSelectedItem();
                    if (treeItem.getGraphic() != null) {
                        int id = Integer.parseInt(((Label) treeItem.getGraphic()).getText());
                        Charts chart = chartMap.get(id);
                        if (chart.getAccId() != BigDecimal.ZERO) {
                            accId.setText(chart.getAccId().toString());
                        }
                        if (chart.getAccName() != null && !chart.getAccName().isEmpty()) {
                            accName.setText(chart.getAccName());
                        }
                        if (chart.getTarget() != null && !chart.getTarget().isEmpty()) {
                            target.setText(chart.getTarget());
                            if (!chart.getTarget().equals("0")) {
                                targetName.setText(chartMap.get(Integer.parseInt(chart.getTarget())).getAccName());
                            } else {
                                targetName.setText("");
                            }
                        }
                        if (chart.getRankAccount() != null && chart.getRankAccount() != BigDecimal.ZERO) {
                            accRank.setText(chart.getRankAccount().toString());
                        }
                        if (chart.getOpenCredt() != null && chart.getOpenCredt() != 0) {
                            credit.setText(chart.getOpenCredt().toString());
                        }
                        if (chart.getOpenDebit() != null && chart.getOpenDebit() != 0) {
                            debit.setText(chart.getOpenDebit().toString());
                        }
                        if (chart.getDebitOCredit() != null && chart.getDebitOCredit() != BigDecimal.valueOf(-1)) {
                            if (chart.getDebitOCredit().intValue() == 0) {
                                // debitCredit.selectToggle(radDebit);
                                radDebit.setSelected(true);
                            } else if (chart.getDebitOCredit().intValue() == 1) {
                                radCredit.setSelected(true);
                            }
                        }
                        if (chart.getIsTarget() != null && chart.getIsTarget() != BigDecimal.valueOf(-1)) {
                            if (chart.getIsTarget().intValue() == 0) {
                                radMain.setSelected(true);
                            } else if (chart.getIsTarget().intValue() == 1) {
                                radSub.setSelected(true);
                            }
                        }
                        if (chart.getReportType() != null && chart.getReportType() != BigDecimal.valueOf(-1)) {
                            if (chart.getReportType().intValue() == 0) {
                                radBudget.setSelected(true);
                            } else if (chart.getReportType().intValue() == 1) {
                                radHesabMotagra.setSelected(true);
                            } else if (chart.getReportType().intValue() == 2) {
                                radWinLose.setSelected(true);
                            }
                        }
                        if (chart.getViewMode() != null && chart.getViewMode() != BigDecimal.valueOf(-1)) {
                            if (chart.getViewMode().intValue() == 0) {
                                radTitle.setSelected(true);
                            } else if (chart.getViewMode().intValue() == 1) {
                                radView.setSelected(true);
                            } else if (chart.getViewMode().intValue() == 2) {
                                radNoView.setSelected(true);
                            }
                        }
                    }
                }
            }
        });

        chartTree.setRoot(treeRoot);

        treeRoot.setExpanded(true);

        debitCredit.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> ov, Toggle t, Toggle t1) {
                RadioButton selectedRadio = new RadioButton();
                // RadioButton selectedRadio = (RadioButton)ov.getValue().getToggleGroup().getSelectedToggle();
                // if (t != null) {
                selectedRadio = (RadioButton) ov.getValue(); // Cast object to radio button
                if (selectedRadio.getId().equals("radDebit")) {
                    debitOrCredit = 0;
                } else if (selectedRadio.getId().equals("radCredit")) {
                    debitOrCredit = 1;
                }
//                } else if (t1 != null) {
//                    selectedRadio = (RadioButton)t1; // Cast object to radio button
//                                    if (selectedRadio.getId().equals("radDebit")) {
//                    debitOrCredit = 0;
//                } else if (selectedRadio.getId().equals("radCredit")) {
//                    debitOrCredit = 1;
//                }
//                }

            }
        });
        mainSub.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> ov, Toggle t, Toggle t1) {
                RadioButton selectedRadio = new RadioButton();
                // if (t != null) 
                selectedRadio = (RadioButton) ov.getValue(); // Cast object to radio button
                //else if(t1 != null)
                //  selectedRadio = (RadioButton) t1;
                if (selectedRadio.getId().equals("radMain")) {
                    mainOrSub = 0;
                } else if (selectedRadio.getId().equals("radSub")) {
                    mainOrSub = 1;
                }
            }
        });
        viewMethod.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> ov, Toggle t, Toggle t1) {
                RadioButton selectedRadio = new RadioButton();

                //if(t != null)
                selectedRadio = (RadioButton) ov.getValue(); // Cast object to radio button
                //else if( t1 != null)
                //     selectedRadio = (RadioButton) t1;
                if (selectedRadio.getId().equals("radTitle")) {
                    veiwMode = 0;
                } else if (selectedRadio.getId().equals("radView")) {
                    veiwMode = 1;
                } else if (selectedRadio.getId().equals("radNoView")) {
                    veiwMode = 2;
                }
            }
        });
        reportType.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> ov, Toggle t, Toggle t1) {
                RadioButton selectedRadio = new RadioButton();

                //if(t != null)
                selectedRadio = (RadioButton) ov.getValue(); // Cast object to radio button
                //else if( t1 != null)
                //     selectedRadio = (RadioButton) t1;

                if (selectedRadio.getId().equals("radBudget")) {
                    report = 0;
                } else if (selectedRadio.getId().equals("radHesabMotagra")) {
                    report = 1;
                } else if (selectedRadio.getId().equals("radWinLose")) {
                    report = 2;
                }
            }
        });

        enableFields(false, true);

    }

    public ChartsController() {
        instanse = this;
        chartMap = new HashMap<>();
    }

    public static ChartsController getInstance() {
        return instanse;
    }

    public void handleNew() {
        clearFields();
        TreeItem treeItem = (TreeItem) chartTree.getSelectionModel().getSelectedItem();
        if (treeItem != null) {
            int id = Integer.parseInt(((Label) treeItem.getGraphic()).getText());
            ChartDAO chartDAO = new ChartDAO();
            int r = chartDAO.getNewId(id);
            accId.setText(String.valueOf(r + 1));
        } else {
            ChartDAO chartDAO = new ChartDAO();
            int r = chartDAO.getNewId(1);
            accId.setText(String.valueOf(r + 1));
        }

        enableFields(true, false);
    }

    public void save(int op) {
        ChartDAO chartDAO = new ChartDAO();

        if (op == 1) {

            int id = chartDAO.insertChart(Integer.parseInt(accId.getText()), accName.getText(), 1, 0, veiwMode, Double.parseDouble(credit.getText()), Double.parseDouble(debit.getText()),
                    Integer.parseInt(accRank.getText()), report, debitOrCredit, target.getText(), mainOrSub);
            Node node = new Label(String.valueOf(id));
            node.setVisible(false);
            node.minWidth(0);

            Charts p = new Charts(new BigDecimal(id), null, null, new BigDecimal(mainOrSub), target.getText(), new BigDecimal(debitOrCredit),
                    new BigDecimal(report), new BigDecimal(accRank.getText()), Double.parseDouble(debit.getText()), Double.parseDouble(credit.getText()),
                    new BigDecimal(veiwMode), accName.getText(), null, null, null, null, null);
            if (String.valueOf(id).length() == 1) {
                treeRoot.getChildren().add(
                        new TreeItem<String>(accName.getText(), node));

            } else if (String.valueOf(id).length() == 3) {

                int i = 0;
                for (TreeItem item1 : treeRoot.getChildren()) {

                    if (((Label) item1.getGraphic()).getText().equals(p.getTarget())) {
                        treeRoot.getChildren().get(i).getChildren().add(new TreeItem<String>(accName.getText(), node));
                        break;
                    }
                    i++;
                }
            } else if (String.valueOf(id).length() == 5) {
                int i = 0;
                int j = 0;
                for (TreeItem item1 : treeRoot.getChildren()) {
                    for (TreeItem item2 : (ObservableList<TreeItem<String>>) item1.getChildren()) {
                        if (((Label) item2.getGraphic()).getText().equals(p.getTarget())) {
                            treeRoot.getChildren().get(i).getChildren().get(j).getChildren().add(new TreeItem<String>(accName.getText(), node));
                            break;
                        }
                        j++;
                    }
                    j = 0;
                    i++;
                }
            } else if (String.valueOf(id).length() == 8) {
                int i = 0;
                int j = 0;
                int k = 0;
                for (TreeItem item1 : treeRoot.getChildren()) {
                    for (TreeItem item2 : (ObservableList<TreeItem<String>>) item1.getChildren()) {
                        for (TreeItem item3 : (ObservableList<TreeItem<String>>) item2.getChildren()) {
                            if (((Label) item3.getGraphic()).getText().equals(p.getTarget())) {
                                treeRoot.getChildren().get(i).getChildren().get(j).getChildren().get(k).getChildren().add(new TreeItem<String>(accName.getText(), node));
                                break;
                            }
                            k++;
                        }
                        k = 0;
                        j++;
                    }
                    j = 0;
                    i++;
                }
            }

            chartMap.put(id, p);
        } else if (op == 2) {
            TreeItem treeItem = (TreeItem) chartTree.getSelectionModel().getSelectedItem();
            ((TreeItem) chartTree.getSelectionModel().getSelectedItem()).setValue(accName.getText());
            int id = chartDAO.updateChart(Integer.parseInt(accId.getText()), accName.getText(), 1, 0, veiwMode, Double.parseDouble(credit.getText()), Double.parseDouble(debit.getText()),
                    Integer.parseInt(accRank.getText()), report, debitOrCredit, target.getText(), mainOrSub);
            Charts p = new Charts(new BigDecimal(Integer.parseInt(accId.getText())), null, null, new BigDecimal(mainOrSub), target.getText(), new BigDecimal(debitOrCredit),
                    new BigDecimal(report), new BigDecimal(accRank.getText()), Double.parseDouble(debit.getText()), Double.parseDouble(credit.getText()),
                    new BigDecimal(veiwMode), accName.getText(), null, null, null, null, null);
            chartMap.remove(Integer.parseInt(accId.getText()));
            chartMap.put(Integer.parseInt(accId.getText()), p);
        }
        //handleNew();
    }

    public void update() {
        enableFields(true, true);

        //System.out.println(costCenterTable.getItems());
    }

    public void delete() {
        ChartDAO chartDAO = new ChartDAO();
        TreeItem parent = ((TreeItem) chartTree.getSelectionModel().getSelectedItem()).getParent();
        chartDAO.deleteChart(Integer.parseInt(accId.getText()));
        parent.getChildren().remove(((TreeItem) chartTree.getSelectionModel().getSelectedItem()));

    }

    public void fillTree() {
        ChartDAO chartDAO = new ChartDAO();
        List<Charts> charts = chartDAO.getAllCharts();
        TreeItem<String> item;
        TreeItem<String> l1 = new TreeItem<>();
        TreeItem<String> l2 = new TreeItem<>();
        TreeItem<String> l3 = new TreeItem<>();
        TreeItem<String> l4 = new TreeItem<>();
        TreeCell<String> cell;
        Node node;
        for (Charts chart : charts) {
            if (chart.getTarget() != null) {
                if (String.valueOf(chart.getAccId()).length() == 1) {

                    level1.add(chart);

                    System.out.println(chart.getAccId());
                } else if (String.valueOf(chart.getAccId()).length() == 3) {

                    level2.add(chart);
                } else if (String.valueOf(chart.getAccId()).length() == 5) {

                    level3.add(chart);
                } else if (String.valueOf(chart.getAccId()).length() == 8) {

                    level4.add(chart);
                }
            }

            node = new Label(chart.getAccId().toString());
            node.setVisible(false);
            node.maxWidth(0.0);
//cell = new TreeCell<>();
            item = new TreeItem<String>(chart.getAccName(), node);
            //    treeRoot.getChildren().add(item);

            chartMap.put(chart.getAccId().intValue(), chart);
        }
        for (Charts charts1 : level1) {
            node = new Label(charts1.getAccId().toString());
            node.setVisible(false);
            node.maxWidth(0.0);
//cell = new TreeCell<>();
            item = new TreeItem<String>(charts1.getAccName(), node);
            treeRoot.getChildren().add(item);

            //chartMap.put(charts1.getAccId().intValue(), charts1);
        }
        for (Charts chart : level2) {

            node = new Label(chart.getAccId().toString());
            node.setVisible(false);
            node.maxWidth(0.0);
//cell = new TreeCell<>();
            item = new TreeItem<String>(chart.getAccName(), node);
            int i = 0;
            for (TreeItem item1 : treeRoot.getChildren()) {

                if (((Label) item1.getGraphic()).getText().equals(chart.getTarget())) {
                    treeRoot.getChildren().get(i).getChildren().add(item);
                    break;
                }
                i++;
            }
                   // treeRoot.getChildren().add(item);

            // chartMap.put(chart.getAccId().intValue(), chart);
        }

        for (Charts chart : level3) {

            node = new Label(chart.getAccId().toString());
            node.setVisible(false);
            node.maxWidth(0.0);
//cell = new TreeCell<>();
            item = new TreeItem<String>(chart.getAccName(), node);
            int i = 0;
            int j = 0;
            for (TreeItem item1 : treeRoot.getChildren()) {
                for (TreeItem item2 : (ObservableList<TreeItem<String>>) item1.getChildren()) {
                    if (((Label) item2.getGraphic()).getText().equals(chart.getTarget())) {
                        treeRoot.getChildren().get(i).getChildren().get(j).getChildren().add(item);
                        break;
                    }
                    j++;
                }
                j = 0;
                i++;
            }
                   // treeRoot.getChildren().add(item);

            // chartMap.put(chart.getAccId().intValue(), chart);
        }

        for (Charts chart : level4) {

            node = new Label(chart.getAccId().toString());
            node.setVisible(false);
            node.maxWidth(0.0);
//cell = new TreeCell<>();
            item = new TreeItem<String>(chart.getAccName(), node);
            int i = 0;
            int j = 0;
            int k = 0;
            for (TreeItem item1 : treeRoot.getChildren()) {
                for (TreeItem item2 : (ObservableList<TreeItem<String>>) item1.getChildren()) {
                    for (TreeItem item3 : (ObservableList<TreeItem<String>>) item2.getChildren()) {
                        if (((Label) item3.getGraphic()).getText().equals(chart.getTarget())) {
                            treeRoot.getChildren().get(i).getChildren().get(j).getChildren().get(k).getChildren().add(item);
                            break;
                        }
                        k++;
                    }
                    k = 0;
                    j++;
                }
                j = 0;
                i++;
            }
                   // treeRoot.getChildren().add(item);

            // chartMap.put(chart.getAccId().intValue(), chart);
        }
    }

    public void enableFields(boolean status, boolean isEdit) {

//        radCredit.set(status);
//        radDebit.setDisable(status);
//        radMain.setDisable(status);
//        radSub.setDisable(status);
//        radTitle.setDisable(status);
//        radView.setDisable(status);
//        radNoView.setDisable(status);
//        radBudget.setDisable(status);
//        radHesabMotagra.setDisable(status);
//        radWinLose.setDisable(status);
        if (isEdit) {
            accId.setEditable(false);
            accName.setEditable(status);
            target.setEditable(status);
            accRank.setEditable(status);
            credit.setEditable(status);
            debit.setEditable(status);
        } else {
            accId.setEditable(status);
            accName.setEditable(status);
            target.setEditable(status);
            accRank.setEditable(status);
            credit.setEditable(status);
            debit.setEditable(status);
        }
    }

    public void clearFields() {
//                radCredit.setSelected(false);
//        radDebit.setSelected(false);
//        radMain.setSelected(false);
//        radSub.setSelected(false);
//        radTitle.setSelected(false);
//        radView.setSelected(false);
//        radNoView.setSelected(false);
//        radBudget.setSelected(false);
//        radHesabMotagra.setSelected(false);
//        radWinLose.setSelected(false);
        accId.setText("");
        accName.setText("");
        target.setText("");
        accRank.setText("");
        credit.setText("");
        debit.setText("");
    }
}
