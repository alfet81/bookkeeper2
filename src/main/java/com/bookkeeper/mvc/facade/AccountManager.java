package com.bookkeeper.mvc.facade;

import static com.bookkeeper.ui.support.DialogHelper.buildCustomDialog;

import com.bookkeeper.Application;
import com.bookkeeper.exceptions.BookkeeperException;
import com.bookkeeper.mvc.view.AccountsView;
import com.bookkeeper.ui.account.AccountManagerDialog;
import org.springframework.stereotype.Component;

import javafx.stage.Modality;
import javafx.stage.Stage;

@Component
public class AccountManager {

  private Stage accountDialog;

  public void showDialog() {
    //getAccountDialog().showAndWait();
    Application.showView(AccountsView.class, Modality.NONE);
  }

  private Stage getAccountDialog() {
    if (accountDialog == null) {
      try {
        accountDialog = buildCustomDialog(new AccountManagerDialog());
        accountDialog.initOwner(Application.getStage());
      } catch (Exception e) {
        System.out.println(e);
        throw new BookkeeperException("Ooops!!", e);
      }
    }

    return accountDialog;
  }

}
