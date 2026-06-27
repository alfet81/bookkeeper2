package com.bookkeeper.common.model.date;

import static com.bookkeeper.utils.MiscUtils.asOptional;
import static org.apache.commons.lang3.StringUtils.isEmpty;
import static java.time.format.DateTimeFormatter.ISO_LOCAL_DATE;
import static java.time.format.DateTimeFormatter.ofPattern;
import static java.util.Optional.empty;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Optional;
import java.util.stream.Stream;


@Slf4j
@RequiredArgsConstructor
public enum DateConverter {
  DEFAULT(ofPattern("MM/dd/yyyy")),
  ISO_8601(ISO_LOCAL_DATE);

  private final DateTimeFormatter formatter;


  public static Optional<LocalDate> convert(String dateAsString) {
    if (isEmpty(dateAsString)) {
      return empty();
    }
    return Stream.of(values())
        .map(converter -> converter.parse(dateAsString))
        .filter(Optional::isPresent)
        .flatMap(Optional::stream)
        .findFirst();
  }


  private Optional<LocalDate> parse(String dateAsString) {
    try {
      var result = LocalDate.parse(dateAsString, formatter);
      return asOptional(result);
    } catch (DateTimeParseException _) {
      log.warn("{} format -> Date conversion failed due to invalid date format: {}", name(), dateAsString);
      return empty();
    }
  }
}