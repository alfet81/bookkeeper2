package com.bookkeeper.ui.account;

import static com.bookkeeper.core.PrototypeBeanFactory.getBean;
import static com.bookkeeper.core.type.Constants.ACCOUNT_DIALOG_TITLE;
import static java.util.stream.Collectors.toList;
import static javafx.scene.control.ButtonType.CLOSE;

import com.bookkeeper.core.AppContext;
import com.bookkeeper.domain.account.Account;
import com.bookkeeper.domain.account.AccountService;

import javafx.scene.control.Button;
import javafx.scene.control.Dialog;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TreeItem;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class AccountDialogOld extends Dialog implements PropertyChangeListener {

  private AccountService accountService;

  private AppContext appContext;

  private TreeItem<Account> accountTreeRoot;

  public AccountDialogOld() {
    this.accountService = getBean(AccountService.class);
    this.appContext = getBean(AppContext.class);
    initDialog();
  }

  private void initDialog() {
    setTitle(ACCOUNT_DIALOG_TITLE);
    getDialogPane().setContent(buildContentPane());
    getDialogPane().getButtonTypes().addAll(CLOSE);
  }

  private Pane buildContentPane() {
    accountTreeRoot = getAccountModel();
    var tree = new AccountTreeTableView(accountTreeRoot);
    tree.addItemChangeListener(this);
    tree.getSelectionModel().selectedItemProperty().addListener(((observable, oldValue, newValue) -> {
    }));
    var buttonPane = buildButtonPane();
    var tablePane = new ScrollPane(tree);
    return new VBox(buttonPane, tablePane);
  }

  private TreeItem<Account> getAccountModel() {
    return buildTree(appContext.getAccountRoot());
  }

  private static TreeItem<Account> buildTree(Account parent) {

    var item = new TreeItem<Account>(parent);

    var items = parent.getChildren().stream().map(AccountDialogOld::buildTree).collect(toList());

    item.getChildren().addAll(items);

    return item;
  }

  private Pane buildButtonPane() {

    var pane = new FlowPane();

    var button1 = new Button("B1");

    pane.getChildren().add(button1);
    return pane;
  }

  @Override
  public void propertyChange(PropertyChangeEvent event) {
    accountService.save((Account) event.getNewValue());
  }
}
