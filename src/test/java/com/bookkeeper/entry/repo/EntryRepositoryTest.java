package com.bookkeeper.entry.repo;

import static org.assertj.core.api.Assertions.assertThat;

import com.bookkeeper.account.jpa.AccountRepository;
import com.bookkeeper.account.jpa.Account;
import com.bookkeeper.category.jpa.Category;
import com.bookkeeper.category.jpa.CategoryRepository;
import com.bookkeeper.entry.jpa.Entry;
import com.bookkeeper.common.model.types.EntryType;
import com.bookkeeper.entry.jpa.EntryRepository;
import org.javamoney.moneta.Money;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.time.LocalDate;

@DataJpaTest
@ActiveProfiles("jpa-test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class EntryRepositoryTest {

  @Autowired
  private CategoryRepository categoryRepository;

  @Autowired
  private EntryRepository entryRepository;

  @Autowired
  private AccountRepository accountRepository;

  @Autowired
  private TestEntityManager entityManager;


  @BeforeEach
  void setup() {
    var account = Account.builder()
        .name("General")
        .build();

    var category = Category.builder()
        .name("Bills")
        .entryType(EntryType.CREDIT)
        .build();
    entityManager.persist(account);
    entityManager.persist(category);
    entityManager.flush();
  }


  @AfterEach
  void teardown() {
    entityManager.clear();
  }


  @Test
  void testEntry() {

    // Given
    var account = accountRepository.findByName("General").orElseThrow();
    var category = categoryRepository.findByName("Bills").orElseThrow();

    var entry = Entry.builder()
        .account(account)
        .amount(Money.of(BigDecimal.valueOf(100), "USD"))
        .date(LocalDate.now())
        .notes("Test")
        .category(category)
        .build();

    entryRepository.save(entry);
    entityManager.flush();

    // When
    var entries = entryRepository.findAll();

    // Then
    assertThat(entries).hasSize(1);
    var actualEntry = entries.get(0);
    assertThat(actualEntry.getId()).isEqualTo(entry.getId());
    assertThat(actualEntry.getAccount()).isEqualTo(account);
    assertThat(actualEntry.getCategory()).isEqualTo(category);
    assertThat(actualEntry.getAmount()).isEqualTo(entry.getAmount());
    assertThat(actualEntry.getDate()).isEqualTo(entry.getDate());
    assertThat(actualEntry.getNotes()).isEqualTo(entry.getNotes());
  }
}
