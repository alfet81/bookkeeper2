package com.bookkeeper.repository;

import static com.bookkeeper.entry.model.EntryType.CREDIT;
import static com.bookkeeper.utils.MiscUtils.getDefaultCurrency;
import static org.assertj.core.api.Assertions.assertThat;

import com.bookkeeper.category.entity.Category;
import com.bookkeeper.entry.entity.Entry;
import com.bookkeeper.entry.repo.EntryRepository;
import com.bookkeeper.label.entity.Label;
import com.bookkeeper.label.repo.LabelRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@DataJpaTest
@ExtendWith(SpringExtension.class)
public class LabelRepositoryTest extends BaseRepositoryTest {

  @Autowired
  private TestEntityManager entityManager;

  @Autowired
  private LabelRepository labelRepository;

  @Autowired
  private EntryRepository entryRepository;

  private Category category;

  private Entry entry;

  @BeforeEach
  public void initTestData() {

    category = getTestCategory();

    entry = getTestEntry();

    entityManager.persist(category);

    entityManager.persist(entry);

    entityManager.flush();

    rootCategory = buildCategoryTree();
  }

  @Test
  public void whenSearchedByUser_ThenFoundOneLabel() {
    //given
    Label label = new Label("test label");
    entityManager.persistAndFlush(label);

    //when
    List<Label> found = labelRepository.findAll();

    //then
    assertThat(found).hasSize(1);
  }

  @Test
  public void whenDeleteLabel_ThenEntryIsNotFound() {
    //given
    entry = getTestEntry();

    Label label1 = new Label("label1");
    Label label2 = new Label("label2");

    List<Label> labels = new ArrayList<>();

    labels.add(label1);
    labels.add(label2);

    entityManager.persist(label1);
    entityManager.persist(label2);

    entityManager.flush();

    entry.addLabel(label1);

    entityManager.persistAndFlush(entry);

    //when
    labelRepository.delete(label1);

    List<Label> onlyOneLabel = labelRepository.findAll();
    //List<Entry> found = entryRepository.findByLabelsIn(labels);
    Optional<Entry> entryExists = entryRepository.findById(entry.getId());

    //then
    assertThat(onlyOneLabel).hasSize(1);
    //assertThat(found).isEmpty();
    assertThat(entryExists.isPresent()).isTrue();
  }

  @Test
  public void whenDeleteEntry_ThenLabelsStay() {
    //given
    entry = getTestEntry();

    Label label1 = new Label("label1");
    Label label2 = new Label("label2");

    List<Label> labels = new ArrayList<>();

    labels.add(label1);
    labels.add(label2);

    entityManager.persistAndFlush(label2);

    entry.addLabel(label1);

    entityManager.persistAndFlush(entry);

    //when
    entryRepository.delete(entry);
    entryRepository.flush();

    List<Label> foundLabels = labelRepository.findAll();
    Optional<Entry> foundEntry = entryRepository.findById(entry.getId());

    //then
    assertThat(foundLabels).hasSize(2);
    assertThat(foundEntry.isPresent()).isFalse();
  }

  private Category getTestCategory() {
    return Category.builder().entryType(CREDIT).name("Test Category").build();
  }

  private Entry getTestEntry() {
    return Entry.builder()
        .amount(new BigDecimal(10))
        .category(category)
        .currency(getDefaultCurrency())
        .notes("test entry")
        .date(LocalDate.now())
        .build();
  }
}
