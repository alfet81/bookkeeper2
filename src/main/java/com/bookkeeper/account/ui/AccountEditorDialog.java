package com.bookkeeper.account.ui;

import static javafx.scene.control.ButtonType.CANCEL;
import static javafx.scene.control.ButtonType.FINISH;

import com.bookkeeper.account.entity.Account;

import javafx.scene.control.Dialog;

public class AccountEditorDialog extends Dialog<Account> {

  private Account account;

  private AccountEditorDialogPane dialogPane;

  public AccountEditorDialog(Account account) {
    this.account = account;
    initDialog();
  }

  private void initDialog() {

    dialogPane = new AccountEditorDialogPane();

    dialogPane.setAccount(account);

    getDialogPane().setContent(dialogPane);

    getDialogPane().getButtonTypes().addAll(CANCEL, FINISH);

    setResultConverter();
  }

  private void setResultConverter() {
    setResultConverter(buttonType -> {
      if (buttonType == FINISH) {
        account.setName(dialogPane.getName());
        account.setCurrency(dialogPane.getCurrencyUnit() != null ? dialogPane.getCurrencyUnit().getCurrency() : null);
        return account;
      }
      return null;
    });
  }

}
