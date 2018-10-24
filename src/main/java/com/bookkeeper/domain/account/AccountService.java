package com.bookkeeper.domain.account;

import com.bookkeeper.domain.account.Account;

import java.util.Optional;

public interface AccountService {
  Optional<Account> getRootAccount();
  Optional<Account> getAccount(Long accountId);
  void save(Account account);
  void delete(Account account);
}
