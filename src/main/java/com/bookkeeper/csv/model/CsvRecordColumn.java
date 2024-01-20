package com.bookkeeper.csv.model;

import static com.bookkeeper.utils.MiscUtils.asOptional;
import static org.apache.commons.lang3.EnumUtils.getEnum;
import static org.apache.commons.lang3.StringUtils.capitalize;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Optional;
import java.util.stream.Stream;

@Getter
@RequiredArgsConstructor
public enum CsvRecordColumn {
  STATUS(25, false, false, false),
  DATE(80, true, true, true),
  AMOUNT(80, true, true, true),
  NOTES(120, true, true, true),
  ERRORS(200, false, false, true);

  private final int defaultWidth;

  private final boolean editable;

  private final boolean sortable;

  private final boolean resizable;

  @Override
  public String toString() {
    return capitalize(name().toLowerCase());
  }

  public static Stream<CsvRecordColumn> stream() {
    return Stream.of(values());
  }

  public static Optional<CsvRecordColumn> forName(String name) {

    name = asOptional(name).map(String::toUpperCase).orElse(null);

    return asOptional(getEnum(CsvRecordColumn.class, name));
  }
}
