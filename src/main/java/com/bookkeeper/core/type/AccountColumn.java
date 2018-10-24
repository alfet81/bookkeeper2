package com.bookkeeper.core.type;

import java.util.stream.Stream;

public enum AccountColumn {
  NAME("Name", "name"),
  CURRENCY("Currency", "currency"),
  BALANCE("Balance", "initialBalance"),
  ICON("Icon", "accountIcon");

  private String name;
  private String property;

  AccountColumn(String name, String property) {
    this.name = name;
    this.property = property;
  }

  public String getName() {
    return name;
  }

  public String getProperty() {
    return property;
  }

  public static Stream<AccountColumn> stream() {
    return Stream.of(values());
  }
}
