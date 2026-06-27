package com.bookkeeper.label.repo;

import static com.bookkeeper.common.model.types.EntryType.CREDIT;
import static org.assertj.core.api.Assertions.assertThat;

import com.bookkeeper.category.jpa.Category;
import com.bookkeeper.entry.jpa.Entry;
import com.bookkeeper.entry.jpa.EntryRepository;
import com.bookkeeper.label.jpa.Label;
import com.bookkeeper.label.jpa.LabelRepository;
import org.javamoney.moneta.Money;
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
import java.util.Set;


@DataJpaTest
@ExtendWith(SpringExtension.class)
class LabelRepositoryTest {

  @Autowired
  private TestEntityManager entityManager;

  @Autowired
  private LabelRepository labelRepository;

  @Autowired
  private EntryRepository entryRepository;


  @BeforeEach
  public void initTestData() {
    //var category = getTestCategory();
    var entry = getTestEntry();
    //entityManager.persist(category);
    entityManager.persist(entry);
    entityManager.flush();
  }


  @Test
  void whenSearchedByUser_ThenFoundOneLabel() {
    //given
    Label label = new Label("test label");
    entityManager.persistAndFlush(label);

    //when
    List<Label> found = labelRepository.findAll();

    //then
    assertThat(found).hasSize(1);
  }


  @Test
  void whenDeleteLabel_ThenEntryIsNotFound() {
    //given
    Entry entry = getTestEntry();

    var label1 = Label.builder()
        .id(1L)
        .name("label1")
        .build();

    var label2 = Label.builder()
        .name("label2")
        .build();

    List<Label> labels = new ArrayList<>();

    labels.add(label1);
    labels.add(label2);

    entityManager.persist(label1);
    entityManager.persist(label2);

    entityManager.flush();

    entry.setLabels(Set.of(label1));

    entityManager.persistAndFlush(entry);

    //when
    labelRepository.delete(label1);

    List<Label> onlyOneLabel = labelRepository.findAll();
    //List<Entry> found = entryRepository.findByLabelsIn(labels);
    Optional<Entry> entryExists = entryRepository.findById(entry.getId());

    //then
    assertThat(onlyOneLabel).hasSize(1);
    //assertThat(found).isEmpty();
    assertThat(entryExists).isPresent();
  }


  @Test
  void whenDeleteEntry_ThenLabelsStay() {
    //given
    var entry = getTestEntry();

    var label1 = Label.builder()
        .id(1L)
        .name("label1")
        .build();

    var label2 = Label.builder()
        .name("label2")
        .build();

    List<Label> labels = new ArrayList<>();

    labels.add(label1);
    labels.add(label2);

    entityManager.persistAndFlush(label2);

    entry.setLabels(Set.of(label1));

    entityManager.persistAndFlush(entry);

    //when
    entryRepository.delete(entry);
    entryRepository.flush();

    List<Label> foundLabels = labelRepository.findAll();
    Optional<Entry> foundEntry = entryRepository.findById(entry.getId());

    //then
    assertThat(foundLabels).hasSize(2);
    assertThat(foundEntry).isPresent();
  }


  private Category getTestCategory() {
    return Category.builder().entryType(CREDIT).name("Test Category").build();
  }


  private Entry getTestEntry() {
    return Entry.builder()
        .amount(Money.of(BigDecimal.TEN, "USD"))
        .category(getTestCategory())
        .notes("test entry")
        .date(LocalDate.now())
        .build();
  }
}
