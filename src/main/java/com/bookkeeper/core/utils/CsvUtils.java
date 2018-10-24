package com.bookkeeper.core.utils;

import static com.bookkeeper.core.utils.CommonUtils.asOptional;
import static org.apache.commons.lang3.StringUtils.isEmpty;
import static org.apache.commons.lang3.StringUtils.trimToNull;
import static java.time.format.DateTimeFormatter.ofPattern;
import static java.util.Optional.empty;

import com.bookkeeper.core.type.CsvRecordColumn;
import org.apache.commons.csv.CSVRecord;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

public class CsvUtils {

  enum DateConverter {

    ISO_8601("yyyy-MM-dd"),
    US("MM/dd/yyyy");

    private DateTimeFormatter formatter;

    DateConverter(String pattern) {
      formatter = ofPattern(pattern);
    }

    private Optional<LocalDate> parse(String date) {
      try {
        return asOptional(LocalDate.parse(date, formatter));
      } catch (Exception ignored) {}

      return empty();
    }

    static LocalDate convert(String dateAsString) {

      for (DateConverter format : values()) {
        var date = format.parse(dateAsString);
        if (date.isPresent()) {
          return date.get();
        }
      }

      return null;
    }
  }

  public static String getColumnValue(CSVRecord csvRecord, CsvRecordColumn csvRecordColumn) {
    try {
      return trimToNull(csvRecord.get(csvRecordColumn));
    } catch (Exception e) {
    }
    return null;
  }

  public static Optional<LocalDate> string2Date(String value) {
    if (isEmpty(value)) {
      return empty();
    }
    return asOptional(DateConverter.convert(value));
  }

  public static Optional<BigDecimal> string2Decimal(String value) {
    if (isEmpty(value)) {
      return empty();
    }
    try {
      return asOptional(new BigDecimal(value));
    } catch (Exception e) {
    }
    return empty();
  }
}
