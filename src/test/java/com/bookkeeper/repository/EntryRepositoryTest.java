package com.bookkeeper.repository;

import com.bookkeeper.domain.category.Category;
import com.bookkeeper.domain.entry.Entry;
import com.bookkeeper.domain.entry.EntryRepository;
import com.bookkeeper.domain.label.Label;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static com.bookkeeper.type.EntryType.CREDIT;
import static com.bookkeeper.utils.MiscUtils.getDefaultCurrency;
import static java.time.LocalDate.now;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class EntryRepositoryTest extends BaseRepositoryTest {

  @Autowired
  private TestEntityManager entityManager;

  @Autowired
  private EntryRepository entryRepository;

  private Category category;

  @Before
  public void initTestData() {
    account = getTestAccount();
    category = getTestCategory();

    entityManager.persist(account);
    entityManager.persist(category);
    entityManager.flush();

    rootCategory = buildCategoryTree();
  }

  private Category getTestCategory() {
    return Category.builder().entryType(CREDIT).name("Test Category").build();
  }

  private Entry getTestEntry() {
    return Entry.builder()
        .account(account)
        .amount(new BigDecimal(10))
        .category(category)
        .currency(getDefaultCurrency())
        .notes("test entry")
        .transactionDate(LocalDate.now())
        .build();
  }

  protected void persistCategoryTree() {
    entityManager.persist(rootCategory);
    persistCategoryChildren(rootCategory.getChildren());
    entityManager.flush();
  }

  protected void persistCategoryChildren(Set<Category> children) {
    for (Category child : children) {
      entityManager.persist(child);
      persistCategoryChildren(child.getChildren());
    }
  }
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
  public void whenSearchedByCategory_ThenFoundOneEntry() {
    //given
    Entry entry = getTestEntry();

    entityManager.persist(entry);

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
  public void whenDeleteEntry_ThenEntryNotFound() {
    //given
    Entry entry = getTestEntry();

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
  public void whenFoundById_ThenAllDataIsEagerlyLoaded() {
    //given
    Entry entry = getTestEntry();
    entityManager.persistAndFlush(entry);

    //when
    Entry found = entryRepository.findById(entry.getId()).orElseThrow(() ->
    new RuntimeException("Entry not found"));

    //then
    assertThat(found.getAmount()).isEqualTo(new BigDecimal(10));
    assertThat(found.getCategory()).isEqualTo(category);
    assertThat(found.getCategory().getName()).isEqualTo("Test Category");
    assertThat(found.getCurrency()).isEqualTo(getDefaultCurrency());
    assertThat(found.getTransactionDate()).isEqualTo(now());
  }

  @Test
  public void whenSearchedByLabel_ThenFoundOneEntry() {
    //given
    Label label = new Label("test");

    entityManager.persistAndFlush(label);

    Entry entry = getTestEntry();

    entry.addLabel(label);

    entityManager.persistAndFlush(entry);

    //when
    List<Label> labels = new ArrayList<>();
    labels.add(label);
    List<Entry> found = entryRepository.findByLabelsIn(labels);

    //then
    assertThat(found).hasSize(1);
  }
}
