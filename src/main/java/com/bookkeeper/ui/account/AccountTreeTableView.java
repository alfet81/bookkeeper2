package com.bookkeeper.ui.account;

import static com.bookkeeper.core.type.AccountColumn.CURRENCY;
import static com.bookkeeper.core.type.AccountColumn.NAME;
import static org.apache.commons.lang3.StringUtils.trimToNull;
import static java.util.stream.Collectors.toList;
import static javafx.scene.control.SelectionMode.SINGLE;

import com.bookkeeper.domain.account.Account;
import com.bookkeeper.core.type.AccountColumn;
import com.bookkeeper.core.type.CurrencyUnit;
import com.bookkeeper.ui.support.TreeItemChangeSupport;

import java.beans.PropertyChangeSupport;
import java.util.Currency;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableCell;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import javafx.scene.control.cell.ComboBoxTreeTableCell;
import javafx.scene.control.cell.TextFieldTreeTableCell;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;

public class AccountTreeTableView extends TreeTableView<Account> implements TreeItemChangeSupport {

  private PropertyChangeSupport itemChangeSupport;

  public AccountTreeTableView() {
    init();
  }

  public AccountTreeTableView(TreeItem<Account> root) {
    super(root);
    init();
  }

  private void init() {
    itemChangeSupport = new PropertyChangeSupport(this);
    getSelectionModel().setSelectionMode(SINGLE);
    setEditable(true);
    initTreeTableColumns();
  }

  private void initTreeTableColumns() {
    AccountColumn.stream().forEach(this::addColumn);
  }

  private void addColumn(AccountColumn column) {
    switch (column) {
      case NAME:
        addNameColumn();
        break;
      case CURRENCY:
        addCurrencyColumn();
        break;
      default:
        addDefaultColumn(column);
    }
  }

  private void addNameColumn() {
    var column = new TreeTableColumn<Account, String>(NAME.getName());
    column.setCellValueFactory(new TreeItemPropertyValueFactory<>(NAME.getProperty()));
    //column.setCellFactory(TextFieldTreeTableCell.forTreeTableColumn());
    column.setCellFactory(cell -> new TreeTableCell<>() {
      //private final ImageView graphic = new ImageView(icon);

      @Override
      protected void updateItem(String item, boolean empty) {
        super.updateItem(item, empty);
        setText(empty ? null : item);
        //setGraphic(empty ? null : graphic);
        //getTreeItem(getIndex()).isExpanded();
      }


    });
    column.setOnEditCommit(handler -> {
      var account = handler.getRowValue().getValue();
      account.setName(trimToNull(handler.getNewValue()));
      System.out.println(account);
      itemChangeSupport.firePropertyChange("account", null, account);
    });

    getColumns().add(column);
  }

  private void addCurrencyColumn() {
    var column = new TreeTableColumn<Account, Currency>(CURRENCY.getName());
    var data = FXCollections.observableArrayList(getCurrencies());
    column.setCellValueFactory(new TreeItemPropertyValueFactory<>(CURRENCY.getProperty()));
    column.setCellFactory(ComboBoxTreeTableCell.forTreeTableColumn(data));
    getColumns().add(column);
  }

  private void addDefaultColumn(AccountColumn columnType) {
    var column = new TreeTableColumn<Account, String>(columnType.getName());
    column.setCellValueFactory(new TreeItemPropertyValueFactory<>(columnType.getProperty()));
    //column.setCellFactory(TextFieldTreeTableCell.forTreeTableColumn());
    getColumns().add(column);
  }

  private List<Currency> getCurrencies() {
    return CurrencyUnit.stream().map(CurrencyUnit::getCurrency).collect(toList());
  }

  @Override
  public PropertyChangeSupport getItemChangeSupport() {
    return itemChangeSupport;
  }
}
