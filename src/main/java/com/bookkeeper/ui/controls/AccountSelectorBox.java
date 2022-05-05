package com.bookkeeper.ui.controls;

import com.bookkeeper.domain.account.Account;
import com.bookkeeper.exceptions.ApplicationException;

import java.net.URL;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeView;
import javafx.scene.layout.HBox;
import javafx.stage.Popup;

public class AccountSelectorBox extends HBox {

  private Account accountRoot;

  private Account selectedAccount;

  @FXML
  private TextField textField;

  private Popup popup = new Popup();

  public AccountSelectorBox() {
    initFxml();
    //initPopup();
  }

  public AccountSelectorBox(Account accountRoot) {
    this();
    this.accountRoot = accountRoot;
  }

  private void initFxml() {
    var fxmlLoader = new FXMLLoader(getFxmlUrl());
    fxmlLoader.setRoot(this);
    fxmlLoader.setController(this);

    try {
      fxmlLoader.load();
    } catch (Exception e) {
      throw new ApplicationException(e);
    }
  }

  private void initPopup() {
    var scrollPane = new ScrollPane(new TreeView<>());
    popup.getContent().add(scrollPane);
  }

  @FXML
  protected void showDealog() {

  }

  private URL getFxmlUrl() {
    return getClass().getResource("/com/bookkeeper/ui/fxml/common/DataSelectorBox.fxml");
  }
}
