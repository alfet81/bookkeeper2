package com.bookkeeper.account.jpa;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import javax.money.CurrencyUnit;
import javax.money.Monetary;


@DataJpaTest
@ActiveProfiles("jpa-test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class AccountRepositoryTest {

  private static final CurrencyUnit USD = Monetary.getCurrency("USD");

  @Autowired
  private AccountRepository accountRepository;

  @Autowired
  private TestEntityManager entityManager;


  @Test
  void testSaveAccountWithoutParent() {

    // Given
    var account = Account.builder()
        .name("Checking")
        .initialBalance(BigDecimal.valueOf(1_000))
        .currency(USD)
        .build();

    entityManager.persist(account);
    entityManager.flush();

    // When
    var savedAccount = accountRepository.findByName("Checking").orElseThrow();

    // Then
    assertThat(savedAccount.getId()).isNotNull();
    assertThat(savedAccount.getName()).isEqualTo("Checking");
    assertThat(savedAccount.getCurrency()).isEqualTo(USD);
    assertThat(savedAccount.getInitialBalance()).isEqualTo(BigDecimal.valueOf(1_000));
  }


  @Test
  void testSaveAccountWithParent() {

    // Given
    var parentAccount = Account.builder()
        .name("Parent")
        .build();

    entityManager.persist(parentAccount);
    entityManager.flush();

    var persistedParentAccount = accountRepository.findByName("Parent").orElseThrow();

    var account = Account.builder()
        .parent(persistedParentAccount)
        .name("Checking")
        .initialBalance(BigDecimal.valueOf(1_000))
        .currency(USD)
        .build();

    var savedAccount = accountRepository.save(account);

    // When
    var actualAccount = accountRepository.findById(savedAccount.getId()).orElseThrow();

    // Then
    assertThat(actualAccount.getId()).isEqualTo(savedAccount.getId());
    assertThat(actualAccount.getName()).isEqualTo(savedAccount.getName());
    assertThat(actualAccount.getCurrency()).isEqualTo(savedAccount.getCurrency());
    assertThat(actualAccount.getInitialBalance()).isEqualTo(savedAccount.getInitialBalance());
    assertThat(actualAccount.getParent()).isEqualTo(persistedParentAccount);
  }
}
