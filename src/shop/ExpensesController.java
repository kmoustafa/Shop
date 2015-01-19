/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shop;

import daos.ChartDAO;
import daos.CostCenterDAO;
import daos.ExpenceDAO;
import java.math.BigDecimal;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;
import model.Charts;
import model.CostCenter;
import model.Expences;

/**
 * FXML Controller class
 *
 * @author Kareem.Moustafa
 */
public class ExpensesController implements Initializable {

    /**
     * Initializes the controller class.
     */
    private static ExpensesController instanse;

    ObservableList<Expences> list;
    public ArrayList<Expences> editList;
    @FXML
    TextField expCode;
    @FXML
    TextField expName;
    @FXML
    TextField personalAccount;
    @FXML
    TableView<Expences> expTable;
    @FXML
    TableColumn codeColumn;
    @FXML
    TableColumn nameColumn;
    @FXML
    TextField search;
    @FXML
    ComboBox mainAccountCombo;
    @FXML
    TextField mainAccount;
    
    private ObservableList<Expences> filteredData = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        list = FXCollections.observableArrayList();
        editList = new ArrayList<>();
        codeColumn.setCellValueFactory(new PropertyValueFactory("expenceId"));
        nameColumn.setCellValueFactory(new PropertyValueFactory("expenceName"));
        // costCenerTable = new TableView<>();
        Callback<TableColumn, TableCell> cellFactory
                = new Callback<TableColumn, TableCell>() {

                    public TableCell call(TableColumn p) {
                        return new EditingCell();
                    }
                };
        codeColumn.setCellFactory(cellFactory);
        nameColumn.setCellFactory(cellFactory);

        list.addListener(new ListChangeListener<Expences>() {
            @Override
            public void onChanged(ListChangeListener.Change<? extends Expences> change) {
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
        expCode.setEditable(false);
        expName.setEditable(false);
        
        expTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observableValue, Object oldValue, Object newValue) {
                //Check whether item is selected and set value of selected item to Label
                if (expTable.getSelectionModel().getSelectedItem() != null) {
                    TableView.TableViewSelectionModel selectionModel = expTable.getSelectionModel();
                    ObservableList selectedCells = selectionModel.getSelectedCells();
                    Expences c = (Expences) selectionModel.getSelectedItem();
                    expCode.setText(c.getExpenceId().toString());
                    expName.setText(c.getExpenceName());
                    TablePosition tablePosition = (TablePosition) selectedCells.get(0);

                    String st = tablePosition.getTableView().getSelectionModel().getTableView().getId();
                    //gives you selected cell value..
                    //selectedColumn = tablePosition.getColumn();
                    Object GetSinglevalue = tablePosition.getTableColumn().getCellData(newValue);

                    BigDecimal getbothvalue = expTable.getSelectionModel().getSelectedItem().getExpenceId();
                    //gives you first column value..
                    String Finalvaluetablerow = getbothvalue.toString().split(",")[0].substring(1);
                    System.out.println("The First column value of row.." + Finalvaluetablerow);
                }
            }
        });
        
        ChartDAO chartDAO = new ChartDAO();
        final List<Charts> charts = chartDAO.getAllCharts();
        ObservableList chartsA = FXCollections.observableList(charts);
        mainAccountCombo.setCellFactory(new Callback<ListView<Charts>,ListCell<Charts>>(){
 
            @Override
            public ListCell<Charts> call(ListView<Charts> p) {
                 
                final ListCell<Charts> cell = new ListCell<Charts>(){
 
                    @Override
                    protected void updateItem(Charts t, boolean bln) {
                        super.updateItem(t, bln);
                         
                        if(t != null){
                            setText(t.getAccName());
                        }else{
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
                    Charts c = (Charts)newValue;
                    mainAccountCombo.setValue(c);
                    mainAccount.setText(String.valueOf(c.getAccId()));
                }
            }
        });
        
        mainAccountCombo.setEditable(true);
//                mainAccount.textProperty().addListener(new ChangeListener<String>() {
//            @Override
//            public void changed(ObservableValue<? extends String> observable,
//                    String oldValue, String newValue) {
//
//                for (Object chart : mainAccountCombo.getItems()) {
//                    Charts c = (Charts)chart;
//                     if (c.getAccId().toString().toLowerCase().indexOf(newValue) != -1) {
//                         mainAccountCombo.setValue(c);
//        }
//                }
//            }
//        });
        mainAccountCombo.getEditor().textProperty()
        .addListener(new ChangeListener<String>() {
          @Override
          public void changed(ObservableValue<? extends String> observable,
              String oldValue, String newValue) {
            final TextField editor = mainAccountCombo.getEditor();
            final Charts selected = (Charts)mainAccountCombo.getSelectionModel()
                .getSelectedItem();
            if (selected == null || !selected.getAccName().equals(editor.getText())) {
              filterItems(newValue, mainAccountCombo, charts);
              mainAccountCombo.show();
              if (mainAccountCombo.getItems().size() == 1) {
                final Charts c = (Charts)mainAccountCombo.getItems().get(0);
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

    public ExpensesController() {
        instanse = this;
    }

    public static ExpensesController getInstance() {
        return instanse;
    }

    public ObservableList<Expences> getList() {
        return list;
    }

    public void setList(ObservableList<Expences> list) {
        this.list = list;
    }

    public ArrayList<Expences> getEditList() {
        return editList;
    }

    public void setEditList(ArrayList<Expences> editList) {
        this.editList = editList;
    }

    public TableColumn getCodeColumn() {
        return codeColumn;
    }

    public void setCodeColumn(TableColumn codeColumn) {
        this.codeColumn = codeColumn;
    }

    public void handleNew() {
        clearFields();
        expCode.setEditable(false);
        expName.setEditable(true);
        ExpenceDAO expenceDAO = new ExpenceDAO();
        this.expCode.setText(String.valueOf(expenceDAO.getLastIndex() + 1));
    }

    public void save(int operation) {
        ExpenceDAO expenceDAO = new ExpenceDAO();
        if (operation == 1) {
            int id = expenceDAO.insertExpence(Integer.parseInt(expCode.getText()), 1, 0, expName.getText());
            Expences expences = expenceDAO.getExpenceById(id);
            list.add(expences);
            //expTable.setItems(list);
            //   fillTable();
            // handleNew();
            updateFilteredData();
            clearFields();
        } else if (operation == 2) {
            expenceDAO.updateExpence(Integer.valueOf(expCode.getText()), 1, 0, expName.getText());
            expTable.getSelectionModel().getSelectedItem().setExpenceName(expName.getText());
            updateFilteredData();
        }
        expName.setEditable(false);
        expCode.setEditable(false);
    }

    public void update() {
        expName.setEditable(true);
//        ExpenceDAO expenceDAO = new ExpenceDAO();
//        if (!editList.isEmpty()) {
//            for (Expences expences : editList) {
//                expenceDAO.updateExpence(expences.getExpenceId().intValue(),1,0, expences.getExpenceName());
//            }
//        }
        //System.out.println(costCenterTable.getItems());
    }

    public void delete() {

        String id = expTable.getSelectionModel().getSelectedItem().getExpenceId().toString();
        ExpenceDAO expenceDAO = new ExpenceDAO();
        expenceDAO.deleteExpence(Integer.parseInt(id));
        list.remove(expTable.getSelectionModel().getSelectedItem());
        //expTable.setItems(list);
        updateFilteredData();
        // fillTable();
    }

    public void fillTable() {
        ExpenceDAO expenceDAO = new ExpenceDAO();
        ArrayList<Expences> expenceses = (ArrayList) expenceDAO.getAllExpences();
        list = FXCollections.observableArrayList();
        for (Expences expences : expenceses) {
            list.add(expences);
        }
        filteredData.addAll(list);

        expTable.setItems(filteredData);
    }

    public class EditingCell extends TableCell<Expences, Object> {

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
            System.out.println();
            String st = getTableColumn().getText();
            Expences c = getTableView().getSelectionModel().getSelectedItem();
            if (st.equals("الكود")) {
                c.setExpenceId(new BigDecimal(Integer.parseInt(String.valueOf(newValue))));
            } else if (st.equals("إسم المصروف")) {
                c.setExpenceName(String.valueOf(newValue));
            }
            String s = getTableRow().getText();
            String l = getTableView().getId();
            editList.add(c);
            System.out.println(editList.size());
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

        for (Expences p : list) {
            if (matchesFilter(p)) {
                filteredData.add(p);
            }
        }

        // Must re-sort table after items changed
        reapplyTableSortOrder();
    }

    private boolean matchesFilter(Expences ex) {
        String filterString = search.getText();
        if (filterString == null || filterString.isEmpty()) {
            // No filter --> Add all.
            return true;
        }

        String lowerCaseFilterString = filterString.toLowerCase();

        if (ex.getExpenceName().toLowerCase().indexOf(lowerCaseFilterString) != -1) {
            return true;
        }

        return false; // Does not match
    }

    private void reapplyTableSortOrder() {
        ArrayList<TableColumn<Expences, ?>> sortOrder = new ArrayList<>(expTable.getSortOrder());
        expTable.getSortOrder().clear();
        expTable.getSortOrder().addAll(sortOrder);
    }

    private void clearFields() {
        this.expCode.setText("");
        this.expName.setText("");
    }
      private <T> void filterItems(String filter, ComboBox<Charts> comboBox,
      List<Charts> items) {
    List<Charts> filteredItems = new ArrayList<>();
    for (Charts item : items) {
      if (item.getAccName().toString().toLowerCase().startsWith(filter.toLowerCase())) {
        filteredItems.add(item);
      }
    }
    comboBox.setItems(FXCollections.observableArrayList(filteredItems));
  }
}
