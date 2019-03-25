package com.bookkeeper.type;

import lombok.AllArgsConstructor;
import lombok.Getter;
import java.util.stream.Stream;

@Getter
@AllArgsConstructor
public enum EntryType {
  DEBIT("Income"),
  CREDIT("Expense");

  private String text;

  @Override
  public String toString() {
    return text;
  }

  public static Stream<EntryType> stream() {
    return Stream.of(values());
  }
}
