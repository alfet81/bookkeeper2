package com.bookkeeper.type;

import static com.bookkeeper.utils.MiscUtils.asOptional;
import static org.apache.commons.lang3.StringUtils.isEmpty;
import static java.time.format.DateTimeFormatter.ofPattern;
import static java.util.Optional.empty;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

public enum DateConverter {
  ISO_8601("yyyy-MM-dd"),
  US("MM/dd/yyyy");

  private final DateTimeFormatter formatter;

  DateConverter(String pattern) {
    formatter = ofPattern(pattern);
  }

  private Optional<LocalDate> parse(String date) {
    try {
      return asOptional(LocalDate.parse(date, formatter));
    } catch (Exception e) {}

    return empty();
  }

  public static LocalDate convert(String dateAsString) {

    if (isEmpty(dateAsString)) {
      return null;
    }

    for (DateConverter parser : values()) {
      var date = parser.parse(dateAsString);
      if (date.isPresent()) {
        return date.get();
      }
    }

    return null;
  }
}