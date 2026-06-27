package com.bookkeeper.account.view.gui;

import static javafx.scene.control.ButtonType.CANCEL;
import static javafx.scene.control.ButtonType.FINISH;

import com.bookkeeper.account.jpa.Account;
import javafx.scene.control.Dialog;

public class AccountEditorDialog extends Dialog<Account> {

  private final Account account;
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
        return account;
      }
      return null;
    });
  }
}
