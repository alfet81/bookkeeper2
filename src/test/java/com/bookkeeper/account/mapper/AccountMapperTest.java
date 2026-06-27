package com.bookkeeper.account.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import com.bookkeeper.account.model.Account;
import org.junit.jupiter.api.Test;

import java.util.List;
import javax.money.CurrencyUnit;
import javax.money.Monetary;

class AccountMapperTest {

  private static final CurrencyUnit USD = Monetary.getCurrency("USD");
  private static final AccountMapper accountMapper = new AccountMapperImpl();


  @Test
  void testToModel() {
    // Given
    var parentAccountEntity = com.bookkeeper.account.jpa.Account.builder()
        .id(1L)
        .name("parent")
        .currency(USD)
        .build();

    var accountEntity = com.bookkeeper.account.jpa.Account.builder()
        .id(2L)
        .name("account")
        .currency(USD)
        .parent(parentAccountEntity)
        .build();

    // When
    var account = accountMapper.toModel(accountEntity, new AccountCyclicGraphContext());

    // Then
    assertThat(account.getId()).isEqualTo(2L);
    assertThat(account.getName()).isEqualTo("account");
    assertThat(account.getCurrency()).isEqualTo(USD);
    assertThat(account.getParent().getId()).isEqualTo(parentAccountEntity.getId());
    assertThat(account.getParent().getName()).isEqualTo(parentAccountEntity.getName());
  }


  @Test
  void testToModelList() {
    // Given
    var parentAccountEntity = com.bookkeeper.account.jpa.Account.builder()
        .id(1L)
        .name("parent")
        .currency(USD)
        .build();

    var accountEntity1 = com.bookkeeper.account.jpa.Account.builder()
        .id(2L)
        .name("account1")
        .currency(USD)
        .parent(parentAccountEntity)
        .build();

    var accountEntity2 = com.bookkeeper.account.jpa.Account.builder()
        .id(3L)
        .name("account2")
        .currency(USD)
        .parent(accountEntity1)
        .build();

    // When
    var accounts = accountMapper.toModel(List.of(parentAccountEntity, accountEntity1, accountEntity2),
        new AccountCyclicGraphContext());

    // Then
    assertThat(accounts).hasSize(3);
    var parentAccount = accounts.getFirst();

    assertThat(parentAccount.getId()).isEqualTo(1L);
    assertThat(parentAccount.getName()).isEqualTo("parent");
    assertThat(parentAccount.getCurrency()).isEqualTo(USD);
    assertThat(parentAccount.getParent()).isNull();

    var account1 = accounts.get(1);
    assertThat(account1.getId()).isEqualTo(2L);
    assertThat(account1.getName()).isEqualTo("account1");
    assertThat(account1.getCurrency()).isEqualTo(USD);
    assertThat(account1.getParent()).isEqualTo(parentAccount);

    var account2 = accounts.get(2);
    assertThat(account2.getId()).isEqualTo(3L);
    assertThat(account2.getName()).isEqualTo("account2");
    assertThat(account2.getCurrency()).isEqualTo(USD);
    assertThat(account2.getParent()).isEqualTo(account1);
  }


  @Test
  void testToEntity() {
    // Given
    var parentAccount = Account.builder()
        .id(1L)
        .name("parent")
        .currency(USD)
        .build();

    var account = Account.builder()
        .id(2L)
        .name("account")
        .currency(USD)
        .parent(parentAccount)
        .build();

    // When
    var accountEntity = accountMapper.toEntity(account);

    // Then
    assertThat(accountEntity.getId()).isEqualTo(2L);
    assertThat(accountEntity.getName()).isEqualTo("account");
    assertThat(accountEntity.getCurrency()).isEqualTo(USD);
    assertThat(accountEntity.getParent()).isNull();
  }


  @Test
  void testToEntities() {
    // Given
    var parentAccount = Account.builder()
        .id(1L)
        .name("parent")
        .currency(USD)
        .build();

    var account = Account.builder()
        .id(2L)
        .name("account")
        .currency(USD)
        .parent(parentAccount)
        .build();

    // When
    var accountEntities = accountMapper.toEntities(List.of(account));

    // Then
    assertThat(accountEntities).hasSize(1);
    var accountEntity = accountEntities.getFirst();
    assertThat(accountEntity.getId()).isEqualTo(account.getId());
    assertThat(accountEntity.getName()).isEqualTo(account.getName());
    assertThat(accountEntity.getCurrency()).isEqualTo(account.getCurrency());
    assertThat(accountEntity.getParent()).isNull();
  }
}
