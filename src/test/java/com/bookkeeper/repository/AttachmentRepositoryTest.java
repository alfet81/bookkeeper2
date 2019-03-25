package com.bookkeeper.repository;

import com.bookkeeper.domain.account.Account;
import com.bookkeeper.domain.attachment.Attachment;
import com.bookkeeper.domain.attachment.AttachmentRepository;
import com.bookkeeper.domain.category.Category;
import com.bookkeeper.domain.entry.Entry;
import com.bookkeeper.type.EntryType;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Currency;
import java.util.List;
import java.util.Locale;
import static org.assertj.core.api.Assertions.assertThat;


@RunWith(SpringRunner.class)
@DataJpaTest
public class AttachmentRepositoryTest extends BaseRepositoryTest {

  @Autowired
  private TestEntityManager entityManager;

  @Autowired
  private AttachmentRepository attachmentRepository;

  private Category category;

  private Entry entry;

  @Before
  public void initTestData() {
    account = getTestAccount();
    category = getTestCategory();
    entry = getTestEntry(account, category);

    entityManager.persistAndFlush(account);
    entityManager.persistAndFlush(category);
    entityManager.persistAndFlush(entry);
  }

  private static Category getTestCategory() {
    return Category.builder()
        .entryType(EntryType.CREDIT)
        .name("Test Category")
        .build();
  }

  private static Entry getTestEntry(Account account, Category category) {
    return Entry.builder()
        .amount(new BigDecimal(10.0))
        .account(account)
        .category(category)
        .currency(Currency.getInstance(Locale.getDefault()))
        .transactionDate(LocalDate.now())
        .build();
  }

  @Test
  public void whenFindByEntry_ThenReturnItsAttachments() {
    //given
    persistNumberOfAttachments(3);

    //when
    List<Attachment> found = attachmentRepository.findByEntry(entry);

    //then
    assertThat(found).hasSize(3);
  }

  private void persistNumberOfAttachments(int numberOfAttachments) {
    for (int i = 0; i < numberOfAttachments; i++) {

      Attachment attachment = new Attachment(entry, "test file " + (i + 1), null);

      entityManager.persist(attachment);
    }

    entityManager.flush();
  }

  @Test
  public void whenDeletedByAttachment_ThenSizeIsLess() {
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
  public void whenAttachmentHasBeenChanged_ThenNewData() {
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