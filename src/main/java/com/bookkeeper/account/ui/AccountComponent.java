package com.bookkeeper.account.ui;

import static com.bookkeeper.ui.support.DialogHelper.buildCustomDialog;
import static de.felixroske.jfxsupport.AbstractJavaFxApplicationSupport.showView;

import static javafx.stage.Modality.APPLICATION_MODAL;

import com.bookkeeper.Application;
import com.bookkeeper.account.ui.view.AccountsManagerView;
import com.bookkeeper.exceptions.ApplicationException;
import org.springframework.stereotype.Component;

import javafx.stage.Stage;

@Component
public class AccountComponent {

  private Stage accountDialog;

  public void showDialog() {
    //getAccountDialog().showAndWait();
    showView(AccountsManagerView.class, APPLICATION_MODAL);
  }

  private Stage getAccountDialog() {
    if (accountDialog == null) {
      try {
        accountDialog = buildCustomDialog(new AccountManagerDialog());
        accountDialog.initOwner(Application.getStage());
      } catch (Exception e) {
        System.out.println(e);
        throw new ApplicationException("Ooops!!", e);
      }
    }

    return accountDialog;
  }

}
