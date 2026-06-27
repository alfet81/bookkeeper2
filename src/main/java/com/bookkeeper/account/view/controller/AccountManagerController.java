package com.bookkeeper.account.view.controller;

import com.bookkeeper.account.view.AccountTreeTableView;
import com.bookkeeper.main.view.model.AppViewModel;
import jfx.support.fxml.annotation.FXMLController;
import lombok.RequiredArgsConstructor;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import java.net.URL;
import java.util.ResourceBundle;

@FXMLController
@RequiredArgsConstructor
public class AccountManagerController implements Initializable {

  private final AppViewModel appViewModel;

  @FXML
  private ScrollPane scrollPane;

  @FXML
  private Button newTopButton;

  @FXML
  private Button newGroupButton;

  @FXML
  private Button newAccountButton;

  @FXML
  private Button editButton;

  @FXML
  private Button deleteButton;


  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {

    var treeTableView = new AccountTreeTableView(appViewModel.getAccountTreeRoot());
    scrollPane.setContent(treeTableView);
  }


  public void createNewAccount() {

  }
}
