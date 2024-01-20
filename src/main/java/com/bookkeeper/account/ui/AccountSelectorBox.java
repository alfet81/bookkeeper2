package com.bookkeeper.account.ui;

import com.bookkeeper.account.entity.Account;
import com.bookkeeper.exceptions.ApplicationException;
import com.bookkeeper.ui.common.TreeItemRecord;

import java.math.BigDecimal;
import java.net.URL;
import java.util.Currency;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.layout.HBox;

public class AccountSelectorBox extends HBox {

  private Account accountRoot;

  private Account selectedAccount;

  @FXML
  private TextField textField;

  public AccountSelectorBox() {
    initFxml();
  }

  public AccountSelectorBox(Account accountRoot) {
    this();

    this.accountRoot = accountRoot;
  }

  private void initFxml() {

    var fxmlLoader = new FXMLLoader(getFxmlUrl());

    fxmlLoader.setRoot(this);

    fxmlLoader.setController(this);

    try {
      fxmlLoader.load();
    } catch (Exception e) {
      throw new ApplicationException(e);
    }
  }

  @FXML
  protected void showDialog() {

    accountRoot = Account.builder()
      .name("General")
      .currency(Currency.getInstance("USD"))
      .initialBalance(BigDecimal.ZERO)
      .build();

    var rootItem = new TreeItem<TreeItemRecord<Account>>(new TreeItemRecord<>(null, "Accounts"));

    rootItem.getChildren().add(new TreeItem<>(new TreeItemRecord<>(accountRoot, accountRoot.getName())));


    new AccountSelectorDialog("Select account", rootItem).showAndWait();
  }

  private URL getFxmlUrl() {
    return getClass().getResource("/com/bookkeeper/ui/view/DataSelectorBox.fxml");
  }
}
