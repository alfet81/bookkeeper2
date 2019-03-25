package com.bookkeeper.type;

import static org.apache.commons.lang3.StringUtils.capitalize;

import lombok.AllArgsConstructor;
import lombok.Getter;
import java.util.stream.Stream;

@Getter
@AllArgsConstructor
public enum AccountColumn {
  NAME("name"),
  CURRENCY("currency"),
  BALANCE("initialBalance"),
  ICON("accountIcon");

  private String property;

  @Override
  public String toString() {
    return capitalize(name().toLowerCase());
  }

  public static Stream<AccountColumn> stream() {
    return Stream.of(values());
  }
}
