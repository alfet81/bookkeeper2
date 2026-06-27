package com.bookkeeper.account.service;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static java.util.Collections.emptyList;

import com.bookkeeper.account.jpa.AccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class AccountServiceTest {

  @Mock
  private AccountRepository accountRepository;

  private AccountService accountService;


  @BeforeEach
  void init() {
    //accountService = new AccountService(accountRepository);
  }


  @Test
  void testGetAllAccounts() {
    // Given
    doReturn(emptyList()).when(accountRepository).findAll();

    // When
    accountService.getAllAccounts();

    // Then
    verify(accountRepository).findAll();
  }

}
