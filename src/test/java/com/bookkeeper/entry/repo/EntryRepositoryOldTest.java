package com.bookkeeper.entry.repo;

import static com.bookkeeper.common.model.types.EntryType.CREDIT;
import static com.bookkeeper.utils.CurrencyUtils.getDefaultCurrency;
import static org.assertj.core.api.Assertions.assertThat;
import static java.time.LocalDate.now;

import com.bookkeeper.account.jpa.Account;
import com.bookkeeper.category.jpa.Category;
import com.bookkeeper.entry.jpa.Entry;
import com.bookkeeper.entry.jpa.EntryRepository;
import com.bookkeeper.label.jpa.Label;
import org.javamoney.moneta.Money;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@DataJpaTest
class EntryRepositoryOldTest {

  @Autowired
  private TestEntityManager entityManager;

  @Autowired
  private EntryRepository entryRepository;


  private static Category getTestCategory() {
    return Category.builder()
                   .id(1L)
                   .entryType(CREDIT)
                   .name("Test Category")
                   .build();
  }


  private static Entry getTestEntry(Account account, Category category) {
    return Entry.builder()
                .account(account)
                .amount(Money.of(BigDecimal.TEN, "USD"))
                .category(category)
                .notes("test entry")
                .date(LocalDate.now())
                .build();
  }


  @BeforeEach
  void initTestData() {
    //var account = getTestAccount();
    var category = getTestCategory();
    //entityManager.persist(account);
    entityManager.persist(category);
    entityManager.flush();
  }

/*
  private void persistCategoryTree(Category rootCategory) {
    entityManager.persist(rootCategory);
    persistCategoryChildren(rootCategory.getChildren());
    entityManager.flush();
  }


  private void persistCategoryChildren(Set<Category> children) {
    for (Category child : children) {
      entityManager.persist(child);
      persistCategoryChildren(child.getChildren());
    }
  }*/
/*
  private void persist3Entries() {
    Category[] categories = new Category[3];

    categories[0] = rootCategory.getChildren().get(0).getChildren().get(0);
    categories[1] = rootCategory.getChildren().get(0).getChildren().get(1);
    categories[2] = rootCategory.getChildren().get(1).getChildren().get(0);

    for (int i = 0; i < 3; i++) {
      Entry entry = Entry.builder()
          .account(account)
          .amount(new BigDecimal(i))
          .category(categories[i])
          .currency(getDefaultCurrency())
          .transactionDate(now())
          .build();

      entityManager.persist(entry);
    }

    entityManager.flush();
  }*/


  @Test
  void whenSearchedByCategory_ThenFoundOneEntry() {
    //given
    Category category = null;
    //Account account = getTestAccount();
    //Entry entry = getTestEntry(account, category);

    //entityManager.persist(entry);

    List<Category> categories = new ArrayList<>();
    categories.add(category);

    //when
    List<Entry> found = entryRepository.findByCategoryIn(categories);

    //then
    assertThat(found).hasSize(1);
  }
/*
  @Test
  public void whenSearchedByCategories_ThenFound3Entries() {
    //given
    persistCategoryTree();

    persist3Entries();

    Category[] categories = new Category[3];

    categories[0] = rootCategory.getChildren().get(0).getChildren().get(0);
    categories[1] = rootCategory.getChildren().get(0).getChildren().get(1);
    categories[2] = rootCategory.getChildren().get(1).getChildren().get(0);

    //when
    List<Entry> found = entryRepository.findByCategoryIn(asList(categories));

    //then
    assertThat(found).hasSize(3);
  }*/


  @Test
  void whenDeleteEntry_ThenEntryNotFound() {
    //given
    Entry entry = getTestEntry(null, null);

    entityManager.persistAndFlush(entry);

    assertThat(entryRepository.count()).isEqualTo(1);

    //when
    entryRepository.delete(entry);

    assertThat(entryRepository.count()).isEqualTo(0);

    //then
    entry = entryRepository.findById(entry.getId()).orElse(null);

    assertThat(entry).isNull();
  }


  @Test
  void whenFoundById_ThenAllDataIsEagerlyLoaded() {
    //given
    Category category = null;
    Entry entry = getTestEntry(null, null);
    entityManager.persistAndFlush(entry);

    //when
    Entry found = entryRepository.findById(entry.getId()).orElseThrow(() ->
        new RuntimeException("Entry not found"));

    //then
    //assertThat(found.getAmount().getAmount()).isEqualTo(BigDecimal.valueOf(10));
    assertThat(found.getCategory()).isEqualTo(category);
    assertThat(found.getCategory().getName()).isEqualTo("Test Category");
    assertThat(found.getAmount().getCurrency()).isEqualTo(getDefaultCurrency());
    assertThat(found.getDate()).isEqualTo(now());
  }


  @Test
  void whenSearchedByLabel_ThenFoundOneEntry() {
    //given
    Label label = new Label("test");

    entityManager.persistAndFlush(label);

    Entry entry = getTestEntry(null, null);

    entry.setLabels(Set.of(label));

    entityManager.persistAndFlush(entry);

    //when
    List<Label> labels = new ArrayList<>();
    labels.add(label);
    //List<Entry> found = entryRepository.findByLabelsIn(labels);

    //then
    //assertThat(found).hasSize(1);
  }
}
