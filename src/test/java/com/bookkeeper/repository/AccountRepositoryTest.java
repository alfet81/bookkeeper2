package com.bookkeeper.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.bookkeeper.account.entity.Account;
import com.bookkeeper.account.repo.AccountRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;


@DataJpaTest
@ExtendWith(SpringExtension.class)
public class AccountRepositoryTest extends BaseRepositoryTest {

  @Autowired
  private TestEntityManager entityManager;

  @Autowired
  private AccountRepository accountRepository;

  @BeforeEach
  public void initData() {

    Account account = getTestAccount();

    entityManager.persistAndFlush(account);
  }

  @Test
  public void whenSelectUserByName_ThenFoundUser() {
    //given
    //initData

    //when
    Account account = accountRepository.findByName("test account").orElseThrow(() ->
    new RuntimeException("Account not found"));

    //then
    assertThat(account.getName()).isEqualTo("test account");
  }
}
