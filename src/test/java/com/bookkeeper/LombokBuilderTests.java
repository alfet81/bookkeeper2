package com.bookkeeper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.bookkeeper.account.entity.Account;
import com.bookkeeper.category.entity.Category;
import com.bookkeeper.category.entity.CategoryGroup;
import com.bookkeeper.entry.entity.Entry;
import com.bookkeeper.entry.model.EntryType;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Currency;
import java.util.Locale;


public class LombokBuilderTests {

  String expectedName = "test name";
  EntryType expectedType = EntryType.CREDIT;
  boolean expectedIsReadonly = true;
  int expectedLeftBoundary = 1;
  int expectedRightBoundary = 2;

  @Test
  public void testCategoryGroup() {

    CategoryGroup categoryGroup = CategoryGroup.creator()
        .name("test name")
        .type(EntryType.CREDIT)
        .create();

    assertEquals(expectedName, categoryGroup.getName());
    assertEquals(expectedType, categoryGroup.getEntryType());
  }

  @Test
  public void testCategory() {

    Category category = Category.builder()
        .name(expectedName)
        .entryType(expectedType)
        .build();

    assertEquals(expectedName, category.getName());
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
    LocalDate expectedDate = LocalDate.now();
    String expectedNotes = "test notes";

    Entry item = Entry.builder()
        .currency(expectedCurrency)
        .date(expectedDate)
        .amount(BigDecimal.ONE)
        .notes(expectedNotes)
        .build();

    assertEquals(expectedNotes, item.getNotes());
    assertEquals(expectedCurrency, item.getCurrency());
    assertEquals(expectedDate, item.getDate());
    assertEquals(BigDecimal.ONE, item.getAmount());
  }
}
