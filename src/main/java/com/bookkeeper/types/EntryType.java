package com.bookkeeper.types;

import java.util.stream.Stream;

public enum EntryType {
  DEBIT("Income"),
  CREDIT("Expense");

  private String text;

  EntryType(String text) {
    this.text = text;
  }

  public String getText() {
    return text;
  }

  @Override
  public String toString() {
    return text;
  }

  public static Stream<EntryType> stream() {
    return Stream.of(values());
  }
}
