package com.bookkeeper.category.model;

import static org.apache.commons.lang3.StringUtils.capitalize;

import lombok.AllArgsConstructor;
import lombok.Getter;
import java.util.stream.Stream;

@Getter
@AllArgsConstructor
public enum CategoryColumn {
  NAME("name"),
  TYPE("entryType"),
  AMOUNT("defaultAmount"),
  ICON("categoryIcon");

  private final String property;

  @Override
  public String toString() {
    return capitalize(name().toLowerCase());
  }

  public static Stream<CategoryColumn> stream() {
    return Stream.of(values());
  }
}
