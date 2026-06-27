package com.bookkeeper.account.view.controller;

import com.bookkeeper.account.view.model.AccountSelectorViewModel;
import com.bookkeeper.account.view.model.AccountTreeViewItem;
import com.bookkeeper.account.view.gui.AccountSelectorDialog;
import jfx.support.fxml.annotation.FXMLController;
import lombok.RequiredArgsConstructor;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

@FXMLController
@RequiredArgsConstructor
public class AccountSelectorController implements Initializable {

  private final AccountSelectorViewModel viewModel;

  @FXML
  private TextField accountTextField;


  @FXML
  public void showAccountSelectionDialog() {

    var rootItem = viewModel.getAccountTree();
    var selectedAccount = viewModel.getSelectedAccount();
    var accountDialog = new AccountSelectorDialog("Select account", rootItem);

    if (selectedAccount != null) {
      accountDialog.setSelectedItem(selectedAccount.getName());
      accountDialog.expandTreeView(rootItem);
    }

    var result = accountDialog.showAndWait();
    result.map(AccountTreeViewItem::getValue).ifPresent(viewModel::setSelectedAccount);
  }


  @FXML
  public void clearAccountSelection() {
    viewModel.setSelectedAccount(null);
  }


  @Override
  public void initialize(URL location, ResourceBundle resources) {
    accountTextField.textProperty().bind(Bindings.createStringBinding(() -> {
      var selectedAccount = viewModel.getSelectedAccount();
      return selectedAccount != null ? selectedAccount.getName() : null;
    }, viewModel.selectedAccountProperty()));
  }
}
