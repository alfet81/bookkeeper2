package com.bookkeeper.ui.account;

import static com.bookkeeper.core.PrototypeBeanFactory.getBean;

import static java.util.stream.Collectors.toList;

import com.bookkeeper.core.AppContext;
import com.bookkeeper.domain.account.Account;
import com.bookkeeper.domain.account.AccountService;
import com.bookkeeper.ui.dialog.FxmlDialogPane;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ToolBar;
import javafx.scene.control.TreeItem;

public class AccountDialog extends FxmlDialogPane implements PropertyChangeListener {

  @FXML
  private ToolBar toolBar;

  @FXML
  private ScrollPane scrollPane;

  private AccountService accountService;

  private AppContext appContext;

  private TreeItem<Account> accountTreeRoot;

  private AccountTreeTableView accountTreeTableView;

  public AccountDialog() throws IOException {
  }

  @Override
  protected String getFxmlResourceFile() {
    return "/com/bookkeeper/mvc/view/dialog/TreeTableView.fxml";
  }

  @FXML
  @Override
  public void initialize(URL location, ResourceBundle resources) {
    accountService = getBean(AccountService.class);
    appContext = getBean(AppContext.class);
    accountTreeRoot = getAccountModel();
    accountTreeTableView = new AccountTreeTableView(accountTreeRoot);
    accountTreeTableView.addItemChangeListener(this);
    //box.getChildren().add(accountTreeTableView);
    scrollPane.setContent(accountTreeTableView);
  }

  @Override
  public void propertyChange(PropertyChangeEvent event) {
    accountService.save((Account) event.getNewValue());
  }


  private TreeItem<Account> getAccountModel() {
    return buildTree(appContext.getAccountRoot());
  }

  private static TreeItem<Account> buildTree(Account parent) {

    var item = new TreeItem<Account>(parent);

    var items = parent.getChildren().stream().map(AccountDialog::buildTree).collect(toList());

    item.getChildren().addAll(items);

    return item;
  }
}
