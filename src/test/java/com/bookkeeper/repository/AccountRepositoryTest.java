package com.bookkeeper.repository;

import com.bookkeeper.domain.account.Account;
import com.bookkeeper.domain.account.AccountRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class AccountRepositoryTest extends BaseRepositoryTest {

  @Autowired
  private TestEntityManager entityManager;

  @Autowired
  private AccountRepository accountRepository;

  @Before
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
