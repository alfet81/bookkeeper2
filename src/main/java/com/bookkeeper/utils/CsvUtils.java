package com.bookkeeper.utils;

import static com.bookkeeper.utils.MiscUtils.asOptional;
import static org.apache.commons.lang3.StringUtils.isNotEmpty;
import static org.apache.commons.lang3.StringUtils.trimToNull;
import static java.util.Optional.empty;

import com.bookkeeper.csv.model.CsvRecordColumn;
import com.bookkeeper.model.dates.DateConverter;
import org.apache.commons.csv.CSVRecord;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

public final class CsvUtils {

  private CsvUtils() {
  }

  public static String getColumnValue(CSVRecord csvRecord, CsvRecordColumn csvRecordColumn) {
    try {
      return trimToNull(csvRecord.get(csvRecordColumn));
    } catch (Exception e) {
      return null;
    }
  }

  public static Optional<CsvRecordColumn> getCsvRecordColumn(String columnName) {
    return CsvRecordColumn.forName(columnName);
  }

  public static Optional<LocalDate> string2Date(String value) {
    return DateConverter.convert(value);
  }

  public static Optional<BigDecimal> string2Decimal(String value) {
    try {
      return isNotEmpty(value) ? asOptional(new BigDecimal(value)) : empty();
    } catch (Exception ignored) {
      return empty();
    }
  }
}
