package com.bookkeeper.account.view.model;

import static com.bookkeeper.common.utils.Constants.ACCOUNT_ROOT_NAME;

import com.bookkeeper.account.model.Account;
import com.bookkeeper.account.service.AccountService;
import com.bookkeeper.account.view.mapper.AccountTreeViewItemCyclicGraphContext;
import com.bookkeeper.account.view.mapper.AccountTreeViewItemMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

import java.util.List;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class AccountSelectorViewModel {

  private final AccountService accountService;
  private final AccountTreeViewItemMapper accountTreeViewItemMapper;

  private final ObjectProperty<Account> selectedAccount = new SimpleObjectProperty<>(this, "selectedAccount");


  public ObjectProperty<Account> selectedAccountProperty() {
    return selectedAccount;
  }


  public Account getSelectedAccount() {
    return selectedAccount.get();
  }


  public void setSelectedAccount(Account account) {
    selectedAccount.set(account);
  }


  public AccountTreeNodeItem getAccountTree() {
    var rootItem = new AccountTreeNodeItem(new AccountTreeViewItem(ACCOUNT_ROOT_NAME, null));
    var accountTreeViewItems = getAccountTreeViewItems();
    var accountRootTree = accountTreeViewItems.stream().map(AccountTreeNodeItem::new).toList();
    rootItem.getChildren().setAll(accountRootTree);
    return rootItem;
  }


  private List<AccountTreeViewItem> getAccountTreeViewItems() {
    var accounts = accountService.getAllAccounts();
    var items = mapAccounts(accounts);
    for (var item : items) {
      if (item.getParent() != null) {
        item.getParent().addChild(item);
      }
    }
    return items.stream().filter(item -> Objects.isNull(item.getParent())).toList();
  }


  private List<AccountTreeViewItem> mapAccounts(List<Account> accounts) {
    return accountTreeViewItemMapper.toAccountTreeViewItems(accounts, new AccountTreeViewItemCyclicGraphContext());
  }
}
