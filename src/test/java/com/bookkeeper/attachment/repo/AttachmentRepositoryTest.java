package com.bookkeeper.attachment.repo;

import static com.bookkeeper.common.model.types.EntryType.CREDIT;
import static org.assertj.core.api.Assertions.assertThat;

import com.bookkeeper.account.jpa.Account;
import com.bookkeeper.attachment.jpa.Attachment;
import com.bookkeeper.attachment.jpa.AttachmentRepository;
import com.bookkeeper.category.jpa.Category;
import com.bookkeeper.entry.jpa.Entry;
import org.javamoney.moneta.Money;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@DataJpaTest
class AttachmentRepositoryTest {

  @Autowired
  private TestEntityManager entityManager;

  @Autowired
  private AttachmentRepository attachmentRepository;

  private Entry entry;


  private static Category getTestCategory() {
    return Category.builder()
                   .entryType(CREDIT)
                   .name("Test Category")
                   .build();
  }


  private static Entry getTestEntry(Account account, Category category) {
    return Entry.builder()
                .amount(Money.of(new BigDecimal("10.0"), "USD"))
                .category(category)
                .date(LocalDate.now())
                .build();
  }


  @BeforeEach
  void init() {
    //var account = getTestAccount();
    var category = getTestCategory();
    //entry = getTestEntry(account, category);
    //entityManager.persistAndFlush(account);
    entityManager.persistAndFlush(category);
    entityManager.persistAndFlush(entry);
  }


  @Test
  void whenFindByEntry_ThenReturnItsAttachments() {
    //given
    persistNumberOfAttachments(3);

    //when
    List<Attachment> found = attachmentRepository.findByEntry(entry);

    //then
    assertThat(found).hasSize(3);
  }


  private void persistNumberOfAttachments(int numberOfAttachments) {
    for (int i = 0; i < numberOfAttachments; i++) {
      var attachment = new Attachment(entry, "test file " + (i + 1), null);
      entityManager.persist(attachment);
    }
    entityManager.flush();
  }


  @Test
  void whenDeletedByAttachment_ThenSizeIsLess() {
    //given
    persistNumberOfAttachments(3);

    //when
    Attachment found = attachmentRepository.findAll().stream()
                                           .findFirst().orElseThrow(() -> new RuntimeException("No attachment found"));

    attachmentRepository.delete(found);

    //then
    List<Attachment> rest = attachmentRepository.findAll();

    assertThat(rest).hasSize(2);
  }


  @Test
  void whenAttachmentHasBeenChanged_ThenNewData() {
    //given
    persistNumberOfAttachments(1);
    String targetName = "changed file name";

    //when
    Attachment found = attachmentRepository.findAll().stream()
                                           .findFirst().orElseThrow(() -> new RuntimeException("No attachment found"));

    found.setName(targetName);

    attachmentRepository.save(found);

    //then
    Attachment actual = attachmentRepository.findById(found.getId())
                                            .orElseThrow(() ->
                                                new RuntimeException("Attachment not found for id" + found.getId()));

    assertThat(actual.getName()).isEqualTo(targetName);
  }
}