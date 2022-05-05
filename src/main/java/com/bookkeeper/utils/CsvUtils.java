package com.bookkeeper.utils;

import static com.bookkeeper.type.DateConverter.convert;
import static com.bookkeeper.utils.MiscUtils.asOptional;
import static org.apache.commons.lang3.StringUtils.isEmpty;
import static org.apache.commons.lang3.StringUtils.trimToNull;
import static java.util.Optional.empty;

import com.bookkeeper.type.CsvRecordColumn;
import org.apache.commons.csv.CSVRecord;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

public class CsvUtils {

  public static String getColumnValue(CSVRecord csvRecord, CsvRecordColumn csvRecordColumn) {
    try {
      return trimToNull(csvRecord.get(csvRecordColumn));
    } catch (Exception e) {
      //e.printStackTrace();
    }

    return null;
  }

  public static Optional<CsvRecordColumn> getCsvRecordColumn(String columnName) {
    return CsvRecordColumn.optionalOf(columnName);
  }

  public static Optional<LocalDate> string2Date(String value) {
    return asOptional(convert(value));
  }

  public static Optional<BigDecimal> string2Decimal(String value) {

    if (isEmpty(value)) {
      return empty();
    }

    try {
      return asOptional(new BigDecimal(value));
    } catch (Exception e) {}

    return empty();
  }
}
