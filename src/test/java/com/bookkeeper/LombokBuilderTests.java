package com.bookkeeper;

import com.bookkeeper.domain.account.Account;
import com.bookkeeper.domain.category.Category;
import com.bookkeeper.domain.category.CategoryGroup;
import com.bookkeeper.domain.entry.Entry;
import com.bookkeeper.core.type.EntryType;
import org.junit.Test;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Currency;
import java.util.Locale;
import static org.junit.Assert.assertEquals;

public class LombokBuilderTests {

  String exptectedName = "test name";
  EntryType expectedType = EntryType.CREDIT;
  boolean expectedIsReadonly = true;
  int expectedLeftBoundary = 1;
  int expectedRightBoundary = 2;

  @Test
  public void testCategoryGroup() {

    CategoryGroup categoryGroup = CategoryGroup.groupBuilder()
        .name("test name")
        .type(EntryType.CREDIT)
        .build();

    assertEquals(exptectedName, categoryGroup.getName());
    assertEquals(expectedType, categoryGroup.getEntryType());
  }

  @Test
  public void testCategory() {

    Category category = Category.builder()
        .name(exptectedName)
        .entryType(expectedType)
        .build();

    assertEquals(exptectedName, category.getName());
    assertEquals(expectedType, category.getEntryType());
  }

  @Test
  public void testAccount() {

    String expectedAccountName = "test account";

    Account account = new Account(null, "test account", null, null, null);

    assertEquals(expectedAccountName, account.getName());
  }

  @Test
  public void testItem() {

    Currency expectedCurrency = Currency.getInstance(Locale.getDefault());
    LocalDate expectedTransactionDate = LocalDate.now();
    String expectedNotes = "test notes";

    Entry item = Entry.builder()
        .currency(expectedCurrency)
        .transactionDate(expectedTransactionDate)
        .amount(BigDecimal.ONE)
        .notes(expectedNotes)
        .build();

    assertEquals(expectedNotes, item.getNotes());
    assertEquals(expectedCurrency, item.getCurrency());
    assertEquals(expectedTransactionDate, item.getTransactionDate());
    assertEquals(BigDecimal.ONE, item.getAmount());
  }
}
