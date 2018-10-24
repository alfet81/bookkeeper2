package com.bookkeeper.mvc.facade;

import static com.bookkeeper.ui.dialog.DialogHelper.buildCustomDialog;

import com.bookkeeper.core.exceptions.BookkeeperException;
import com.bookkeeper.ui.account.AccountDialogOld;
import com.bookkeeper.ui.account.AccountDialog;

import org.springframework.stereotype.Component;

import javafx.stage.Stage;

@Component
public class AccountManager {

  private AccountDialogOld accountDialog;

  public void showDialog() {
    //getAccountDialog().showAndWait();
    getTestDialog().showAndWait();
  }

  private AccountDialogOld getAccountDialog() {
    if (accountDialog == null) {
      accountDialog = new AccountDialogOld();
    }
    return accountDialog;
  }

  private Stage getTestDialog() {
    try {
      return buildCustomDialog(new AccountDialog());
    } catch (Exception e) {
      System.out.println(e);
      throw new BookkeeperException("Ooops!!", e);
    }
  }
}
