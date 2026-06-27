package com.bookkeeper.account.view.gui;

import com.bookkeeper.account.jpa.Account;
import com.bookkeeper.common.exception.ApplicationException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import java.math.BigDecimal;
import java.net.URL;
import javax.money.Monetary;


@SuppressWarnings("java:S110")
@Deprecated
public class AccountSelectorBox extends HBox {
  private Account accountRoot;
  private Account selectedAccount;

  @FXML
  private TextField accountTextField;


  public AccountSelectorBox() {
    initFxml();
  }


  public AccountSelectorBox(Account accountRoot) {
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
  protected void showAccountTreeViewPopup() {
    accountRoot = Account.builder()
        .name("General")
        .currency(Monetary.getCurrency("USD"))
        .initialBalance(BigDecimal.ZERO)
        .build();
/*
    var rootItem = new TreeNodeItem<TreeItemRecord<Account>>(new TreeItemRecord<>(null, "Accounts"));

    rootItem.getChildren()
        .add(new TreeNodeItem<>(new TreeItemRecord<>(accountRoot, accountRoot.getName())));

    var accountDialog = new AccountSelectorDialog("Select account", rootItem);
    if (selectedAccount != null) {
      accountDialog.setSelectedItem(selectedAccount.getName());
      accountDialog.expandTreeView(rootItem);
    }
    var accountDialogResult = accountDialog.showAndWait();

    accountDialogResult.map(TreeItemRecord::value).ifPresent(account -> {
      selectedAccount = account;
      accountTextField.setText(account.getName());
    });*/
  }


  private URL getFxmlUrl() {
    return getClass().getResource("/com/bookkeeper/account/view/OutdatedAccountSelector.fxml");
  }
}
