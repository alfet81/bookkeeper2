package com.bookkeeper.account.service;

import com.bookkeeper.account.jpa.AccountRepository;
import com.bookkeeper.account.mapper.AccountCyclicGraphContext;
import com.bookkeeper.account.mapper.AccountMapper;
import com.bookkeeper.account.model.Account;
import com.bookkeeper.common.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import jakarta.transaction.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class AccountService {

  private final AccountRepository accountRepository;
  private final AccountMapper accountMapper;


  public List<Account> getAllAccounts() {
    var accountEntities = accountRepository.findAll();
    return accountMapper.toModel(accountEntities, new AccountCyclicGraphContext());
  }


  public Account getAccount(Long id) {
    var accountEntity = findAccount(id);
    return accountMapper.toModel(accountEntity, new AccountCyclicGraphContext());
  }


  private com.bookkeeper.account.jpa.Account findAccount(Long id) {
    return accountRepository.findById(id).orElseThrow(() -> new NotFoundException("Account not found for id: " + id));
  }


  public Account save(Account account) {
    com.bookkeeper.account.jpa.Account accountEntity;
    com.bookkeeper.account.jpa.Account parentAccountEntity;

    if (account.getId() != null) {
      accountEntity = findAccount(account.getId());
    } else {
      accountEntity = accountMapper.toEntity(account);
    }

    if (account.getParent() != null) {
      var parentAccount = account.getParent();
      if (parentAccount.getId() != null) {
        parentAccountEntity = findAccount(parentAccount.getId());
      } else {
        parentAccountEntity = accountMapper.toEntity(parentAccount);
        parentAccountEntity = accountRepository.save(parentAccountEntity);
      }
      accountEntity.setParent(parentAccountEntity);
    }
    var savedAccountEntity = accountRepository.save(accountEntity);
    var context = new AccountCyclicGraphContext();
    return accountMapper.toModel(savedAccountEntity, context);
  }


  public void delete(Account account) {
    var accountEntity = findAccount(account.getId());
    accountRepository.delete(accountEntity);
  }
}
