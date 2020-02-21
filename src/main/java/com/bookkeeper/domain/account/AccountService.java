package com.bookkeeper.domain.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class AccountService {

  @Autowired
  private AccountRepository accountRepository;

  public List<Account> findAllAccounts() {
    return accountRepository.findAll();
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
}
