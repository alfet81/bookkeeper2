package com.bookkeeper.domain.account;

import static com.bookkeeper.app.AppConstants.DEFAULT_ACCOUNT_ROOT_NAME;
import static com.bookkeeper.app.AppConstants.DEFAULT_GENERAL_ACCOUNT_NAME;
import static com.bookkeeper.app.AppContext.getPreferredCurrency;
import static com.bookkeeper.type.TreeNode.buildTreeRoot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class AccountService {

  @Autowired
  private AccountRepository accountRepository;

  public Account getRootAccount() {
    List<Account> accounts = accountRepository.findAll();
    return buildTreeRoot(accounts).orElseGet(this::createDefaultAccount);
  }

  public Optional<Account> getAccount(Long accountId) {
    return accountRepository.findById(accountId);
  }

  public void save(Account account) {
    accountRepository.save(account);
  }

  public void delete(Account account) {
    accountRepository.delete(account);
  }

  @Transactional
  protected Account createDefaultAccount() {

    var root = AccountGroup.groupBuilder()
        .name(DEFAULT_ACCOUNT_ROOT_NAME)
        .build();

    var account = Account.builder()
        .name(DEFAULT_GENERAL_ACCOUNT_NAME)
        .currency(getPreferredCurrency())
        .initialBalance(BigDecimal.ZERO)
        .build();

    root.addChild(account);

    save(root);
    save(account);

    return root;
  }
}
