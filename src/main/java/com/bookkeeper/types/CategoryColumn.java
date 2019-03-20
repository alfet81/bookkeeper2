package com.bookkeeper.types;

import java.util.stream.Stream;

public enum CategoryColumn {
  NAME("Name", "name"),
  ENTRY_TYPE("Type", "entryType"),
  AMOUNT("Amount", "defaultAmount"),
  ICON("Icon", "categoryIcon");

  private String name;
  private String property;

  CategoryColumn(String name, String property) {
    this.name = name;
    this.property = property;
  }

  public String getName() {
    return name;
  }

  public String getProperty() {
    return property;
  }

  public static Stream<CategoryColumn> stream() {
    return Stream.of(values());
  }
}
