package com.bookkeeper.mvc.controller;

import com.bookkeeper.mvc.model.AppViewModel;
import com.bookkeeper.ui.account.AccountTreeTableView;

import de.felixroske.jfxsupport.FXMLController;
import org.springframework.beans.factory.annotation.Autowired;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;

@FXMLController
public class AccountsController implements Initializable {

  @Autowired
  private AppViewModel appViewModel;

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
    var treeTableView = new AccountTreeTableView();
    scrollPane.setContent(treeTableView);
  }
}
