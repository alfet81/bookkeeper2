package com.bookkeeper.mvc.facade;

import static com.bookkeeper.ui.support.DialogHelper.buildCustomDialog;

import com.bookkeeper.Main;
import com.bookkeeper.exceptions.BookkeeperException;
import com.bookkeeper.ui.account.AccountManagerDialog;
import org.springframework.stereotype.Component;

import javafx.stage.Stage;

@Component
public class AccountManager {

  private Stage accountDialog;

  public void showDialog() {
    getAccountDialog().showAndWait();
  }

  private Stage getAccountDialog() {
    if (accountDialog == null) {
      try {
        accountDialog = buildCustomDialog(new AccountManagerDialog());
        accountDialog.initOwner(Main.getStage());
      } catch (Exception e) {
        System.out.println(e);
        throw new BookkeeperException("Ooops!!", e);
      }
    }

    return accountDialog;
  }

}
