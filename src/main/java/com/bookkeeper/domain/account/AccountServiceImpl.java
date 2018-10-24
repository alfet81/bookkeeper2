package com.bookkeeper.domain.account;

import com.bookkeeper.domain.account.Account;
import com.bookkeeper.domain.account.AccountRepository;
import com.bookkeeper.domain.account.AccountService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountService {

  @Autowired
  private AccountRepository accountRepository;

  @Override
  public Optional<Account> getRootAccount() {
    return accountRepository.findAll().parallelStream()
        .filter(account -> account.getParent() == null)
        .findFirst();
  }

  @Override
  public Optional<Account> getAccount(Long accountId) {
    return accountRepository.findById(accountId);
  }

  @Override
  public void save(Account account) {
    accountRepository.save(account);
  }

  @Override
  public void delete(Account account) {
    accountRepository.delete(account);
  }
}
