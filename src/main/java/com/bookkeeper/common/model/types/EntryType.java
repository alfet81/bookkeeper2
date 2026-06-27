package com.bookkeeper.common.model.types;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import java.util.stream.Stream;

@Getter
@RequiredArgsConstructor
public enum EntryType {
  NONE("Uncategorized"),
  CREDIT("Income"),
  DEBIT("Expense");

  private final String text;

  public static Stream<EntryType> stream() {
    return Stream.of(values());
  }


  @Override
  public String toString() {
    return text;
  }
}
