package com.bookkeeper.account.view.gui;

import static com.bookkeeper.common.gui.support.DialogHelper.buildCustomDialog;

import static javafx.stage.Modality.APPLICATION_MODAL;

import com.bookkeeper.BookkeeperApplication;
import com.bookkeeper.account.view.AccountsManagerView;
import com.bookkeeper.common.exception.ApplicationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import javafx.stage.Stage;

@Component
@RequiredArgsConstructor
public class AccountComponent {

  private final AccountsManagerView accountsManagerView;

  private Stage accountDialog;


  public void showDialog() {
    //getAccountDialog().showAndWait();
    accountsManagerView.showView(APPLICATION_MODAL);
  }


  private Stage getAccountDialog() {
    if (accountDialog == null) {
      try {
        accountDialog = buildCustomDialog(new AccountManagerDialog());
        accountDialog.initOwner(BookkeeperApplication.getStage());
      } catch (Exception e) {
        System.out.println(e);
        throw new ApplicationException("Ooops!!", e);
      }
    }

    return accountDialog;
  }

}
