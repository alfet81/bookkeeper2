package com.bookkeeper.account.model;

import static org.apache.commons.lang3.StringUtils.capitalize;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.stream.Stream;

@Getter
@RequiredArgsConstructor
public enum AccountColumn {
  NAME("name"),
  CURRENCY("currency"),
  BALANCE("initialBalance"),
  ICON("accountIcon");

  private final String property;

  @Override
  public String toString() {
    return capitalize(name().toLowerCase());
  }

  public static Stream<AccountColumn> stream() {
    return Stream.of(values());
  }
}
