package com.bookkeeper.account.ui.view;

import static com.bookkeeper.account.model.AccountColumn.CURRENCY;
import static com.bookkeeper.account.model.AccountColumn.NAME;
import static org.apache.commons.lang3.StringUtils.trimToNull;
import static java.util.stream.Collectors.toList;

import com.bookkeeper.account.entity.Account;
import com.bookkeeper.account.model.AccountColumn;
import com.bookkeeper.model.CurrencyUnit;

import java.beans.PropertyChangeSupport;
import java.util.Currency;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.scene.Node;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableCell;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import javafx.scene.control.cell.ComboBoxTreeTableCell;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.util.Callback;

public class AccountTreeTableView extends TreeTableView<Account> /*implements TreeItemChangeSupport*/ {

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
    setEditable(false);
    initTreeTableColumns();
    //getStylesheets().add("/css/tree_view.css");
  }

  private void initTreeTableColumns() {
    AccountColumn.stream().forEach(this::addColumn);
  }

  private void addColumn(AccountColumn column) {
    switch (column) {
      case NAME: addNameColumn(); break;
      case CURRENCY: addCurrencyColumn(); break;
      default: addDefaultColumn(column);
    }
  }

  private void addNameColumn() {
    var column = new TreeTableColumn<Account, String>(NAME.toString());
    column.setCellValueFactory(new TreeItemPropertyValueFactory<>(NAME.getProperty()));
    //column.setCellFactory(TextFieldTreeTableCell.forTreeTableColumn());
    column.setOnEditCommit(handler -> {
      var account = handler.getRowValue().getValue();
      account.setName(trimToNull(handler.getNewValue()));
      System.out.println(account);
      itemChangeSupport.firePropertyChange("account", null, account);
    });

    column.setCellFactory(DEFAULT_CELL_FACTORY);

    getColumns().add(column);
  }

  private void addCurrencyColumn() {
    var column = new TreeTableColumn<Account, Currency>(CURRENCY.toString());
    var data = FXCollections.observableArrayList(getCurrencies());
    column.setCellValueFactory(new TreeItemPropertyValueFactory<>(CURRENCY.getProperty()));
    column.setCellFactory(ComboBoxTreeTableCell.forTreeTableColumn(data));
    getColumns().add(column);
  }

  private void addDefaultColumn(AccountColumn columnType) {
    var column = new TreeTableColumn<Account, String>(columnType.toString());
    column.setCellValueFactory(new TreeItemPropertyValueFactory<>(columnType.getProperty()));
    //column.setCellFactory(TextFieldTreeTableCell.forTreeTableColumn());
    getColumns().add(column);
  }

  private List<Currency> getCurrencies() {
    return CurrencyUnit.stream().map(CurrencyUnit::getCurrency).collect(toList());
  }

/*  @Override
  public PropertyChangeSupport getItemChangeSupport() {
    return itemChangeSupport;
  }*/

  public static final Callback<TreeTableColumn<Account,String>, TreeTableCell<Account,String>> DEFAULT_CELL_FACTORY =  new Callback<>() {


        @Override public TreeTableCell<Account,String> call(TreeTableColumn<Account,String> param) {
          return new TreeTableCell() {


            @Override protected void updateItem(Object item, boolean empty) {

              if (item == getItem()) return;

              super.updateItem(item, empty);

              if (item == null) {
                super.setText(null);
                super.setGraphic(null);
              } else if (item instanceof Node) {
                super.setText(null);
                super.setGraphic((Node)item);
              } else {
                super.setText(item.toString());
                super.setGraphic(null);
              }
            }
          };
        }
      };

}
