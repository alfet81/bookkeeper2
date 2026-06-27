package com.bookkeeper.account.view;

import static com.bookkeeper.account.model.AccountColumn.CURRENCY;
import static com.bookkeeper.account.model.AccountColumn.NAME;
import static org.apache.commons.lang3.StringUtils.trimToNull;

import com.bookkeeper.account.jpa.Account;
import com.bookkeeper.account.model.AccountColumn;
import com.bookkeeper.common.model.currency.CurrencyUtils;

import javafx.collections.FXCollections;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableCell;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import javafx.scene.control.cell.ComboBoxTreeTableCell;
import javafx.scene.control.cell.TextFieldTreeTableCell;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.util.Callback;

import java.beans.PropertyChangeSupport;
import javax.money.CurrencyUnit;

@Deprecated
public class AccountTreeTableView extends
    TreeTableView<Account> /*implements TreeItemChangeSupport*/ {

  public static final Callback<TreeTableColumn<Account, String>, TreeTableCell<Account, String>> DEFAULT_CELL_FACTORY
      = new Callback<>() {


    @Override
    public TreeTableCell<Account, String> call(TreeTableColumn<Account, String> param) {
      return new TreeTableCell<>() {


        @Override
        protected void updateItem(String item, boolean empty) {

          super.updateItem(item, empty);

          if (item == null) {
            super.setText(null);
            super.setGraphic(null);
          } else {
            super.setText(item);
            super.setGraphic(null);
          }
        }
      };
    }
  };

  private PropertyChangeSupport itemChangeSupport;


  public AccountTreeTableView() {
    init();
  }


  public AccountTreeTableView(TreeItem<Account> root) {
    super(root);
    init();
  }


  private void init() {
    //itemChangeSupport = new PropertyChangeSupport(this);
    //getSelectionModel().setSelectionMode(SINGLE);
    setColumnResizePolicy(TreeTableView.CONSTRAINED_RESIZE_POLICY);
    setEditable(true);
    initTreeTableColumns();
  }


  private void initTreeTableColumns() {
    AccountColumn.stream().forEach(this::addColumn);
  }


  private void addColumn(AccountColumn column) {
    switch (column) {
      case NAME -> addNameColumn();
      case CURRENCY -> addCurrencyColumn();
      default -> addDefaultColumn(column);
    }
  }


  private void addNameColumn() {
    var column = new TreeTableColumn<Account, String>(NAME.toString());
    column.setCellValueFactory(new TreeItemPropertyValueFactory<>(NAME.getProperty()));
    column.setCellFactory(TextFieldTreeTableCell.forTreeTableColumn());

    column.setOnEditCommit(handler -> {
      var account = handler.getRowValue().getValue();
      account.setName(trimToNull(handler.getNewValue()));
      itemChangeSupport.firePropertyChange("account", null, account);
    });

    column.setCellFactory(DEFAULT_CELL_FACTORY);

    getColumns().add(column);
  }


  private void addCurrencyColumn() {
    var column = new TreeTableColumn<Account, CurrencyUnit>(CURRENCY.toString());
    var currencies = FXCollections.observableArrayList(CurrencyUtils.SUPPORTED_CURRENCIES);
    column.setCellValueFactory(new TreeItemPropertyValueFactory<>(CURRENCY.getProperty()));
    column.setCellFactory(ComboBoxTreeTableCell.forTreeTableColumn(currencies));
    getColumns().add(column);
  }


  private void addDefaultColumn(AccountColumn columnType) {
    var column = new TreeTableColumn<Account, String>(columnType.toString());
    column.setCellValueFactory(new TreeItemPropertyValueFactory<>(columnType.getProperty()));
    //column.setCellFactory(TextFieldTreeTableCell.forTreeTableColumn());
    getColumns().add(column);
  }

/*  @Override
  public PropertyChangeSupport getItemChangeSupport() {
    return itemChangeSupport;
  }*/
}
