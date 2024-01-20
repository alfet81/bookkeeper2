package com.bookkeeper.account.service;

import com.bookkeeper.account.entity.Account;
import com.bookkeeper.account.repo.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AccountService {

  private final AccountRepository accountRepository;

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
