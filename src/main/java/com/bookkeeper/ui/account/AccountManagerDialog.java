package com.bookkeeper.ui.account;

import com.bookkeeper.domain.account.Account;
import com.bookkeeper.domain.account.AccountGroup;
import com.bookkeeper.ui.support.FxmlDialogPane;
import com.bookkeeper.ui.model.TreeNodeItem;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ToolBar;
import javafx.scene.control.TreeItem;
import javafx.scene.layout.VBox;

public class AccountManagerDialog extends FxmlDialogPane {

  @FXML
  private ToolBar toolBar;

  @FXML
  private ScrollPane scrollPane;

  @FXML
  private VBox box;

  private TreeItem<Account> accountTreeRoot;

  private AccountTreeTableView accountTreeTableView;

  private Button addGroup;

  private Button addAccount;

  private Button edit;

  private Button delete;

  @Override
  protected String getFxmlResourceFile() {
    return "/com/bookkeeper/ui/fxml/AccountTreeTableView.fxml";
  }

  @FXML
  @Override
  public void initialize(URL location, ResourceBundle resources) {

    addGroup = (Button) toolBar.getItems().get(1);
    addAccount = (Button) toolBar.getItems().get(2);
    edit = (Button) toolBar.getItems().get(3);
    delete = (Button) toolBar.getItems().get(4);

    accountTreeRoot = getAccountModel();

    accountTreeTableView = new AccountTreeTableView(accountTreeRoot);

    accountTreeTableView.getSelectionModel().selectedItemProperty()
        .addListener(((observable, oldValue, newValue) -> selectionHandler(newValue)));

    scrollPane.setContent(accountTreeTableView);

    accountTreeTableView.getSelectionModel().selectFirst();

    //box.setStyle("-fx-background-color: #336699;");

    //this.setStyle("-fx-background-color: #225577;");
  }

  private void selectionHandler(TreeItem<Account> item) {

    boolean disableAddGroup = false;
    boolean disableAddAccount = false;
    boolean disableEdit = false;
    boolean disableDelete = false;

    boolean root = item.getValue().getParent() == null;

    if ((item.getValue() instanceof AccountGroup)) {
      disableAddGroup = root;
      disableEdit = root;
      disableDelete = root;
    } else {
      disableAddGroup = true;
      disableAddAccount = true;
    }

    addGroup.setDisable(disableAddGroup);
    addAccount.setDisable(disableAddAccount);
    edit.setDisable(disableEdit);
    delete.setDisable(disableDelete);
  }

  private TreeItem<Account> getAccountModel() {
    return new TreeItem<>();
  }

  @FXML
  public void addTopLevelGroup(final Event event) {
    try {
      new AccountEditorDialog(new AccountGroup()).showAndWait().ifPresent(account -> {
        try {
          Account root = new AccountGroup();//getAccountRoot();
          root.addChild(account);
          //getAccountService().save(account);
          addToModel(accountTreeRoot, account);
          accountTreeRoot.setExpanded(true);
        } catch (Exception e) {
          //getAccountRoot().removeChild(account);
          e.printStackTrace();
          //showExceptionDialog(e);
        }
      });
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @FXML
  public void addGroup(final Event event) {

    TreeItem<Account> item = getSelectedItem();
    Account parentAccount = item.getValue();

    new AccountEditorDialog(new AccountGroup()).showAndWait().ifPresent(account -> {
      try {
        parentAccount.addChild(account);
        //getAccountService().save(account);
        addToModel(item, account);
        item.setExpanded(true);
      } catch (Exception e) {
        parentAccount.removeChild(account);
        e.printStackTrace();
      }
    });
  }

  @FXML
  public void addAccount(final Event event) {

    TreeItem<Account> item = getSelectedItem();
    Account parentAccount = item.getValue();

    new AccountEditorDialog(new Account()).showAndWait().ifPresent(account -> {
      try {
        item.setExpanded(true);
        parentAccount.addChild(account);
        //getAccountService().save(account);
        addToModel(item, account);
      } catch (Exception e) {
        parentAccount.removeChild(account);
        e.printStackTrace();
      }
    });
  }

  @FXML
  public void edit(final Event event) {

    TreeItem<Account> item = getSelectedItem();
    Account account = item.getValue();

    new AccountEditorDialog(account).showAndWait().ifPresent(acc -> {
      try {
        //getAccountService().save(acc);
        accountTreeTableView.refresh();
      } catch (Exception e) {
        e.printStackTrace();
      }
    });
  }

  @FXML
  public void delete(final Event event) {

    TreeItem<Account> item = getSelectedItem();
    Account account = item.getValue();
    Account parent = account.getParent();

    try {
      parent.removeChild(account);
      //getAccountService().delete(account);
      item.getParent().getChildren().remove(item);
      accountTreeTableView.getSelectionModel().select(accountTreeTableView.getSelectionModel().getSelectedIndex() - 1);
    } catch (Exception e) {
      parent.addChild(account);
      e.printStackTrace();
    }
  }

  private TreeItem<Account> getSelectedItem() {
    return accountTreeTableView.getSelectionModel().getSelectedItem();
  }

  private void addToModel(TreeItem<Account> root, Account value) {
    root.getChildren().add(new TreeNodeItem<>(value));
  }
}
