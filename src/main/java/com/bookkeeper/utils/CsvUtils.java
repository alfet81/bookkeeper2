package com.bookkeeper.utils;

import static com.bookkeeper.type.CsvRecordColumn.optionalOf;
import static com.bookkeeper.type.DateConverter.convert;
import static com.bookkeeper.utils.MiscUtils.asOptional;
import static org.apache.commons.lang3.StringUtils.isNotEmpty;
import static org.apache.commons.lang3.StringUtils.trimToNull;
import static java.util.Optional.empty;

import com.bookkeeper.exceptions.BookkeeperException;
import com.bookkeeper.type.CsvRecordColumn;
import com.bookkeeper.csv.CsvRecordEntry;

import org.apache.commons.csv.CSVRecord;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;
import java.util.function.BiConsumer;

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
    return optionalOf(columnName);
  }

  public static Optional<LocalDate> string2Date(String value) {
    return asOptional(convert(value));
  }

  public static Optional<BigDecimal> string2Decimal(String value) {
    try {
      return isNotEmpty(value) ? asOptional(new BigDecimal(value)) : empty();
    } catch (Exception e) {}

    return empty();
  }

  public static BiConsumer<CsvRecordEntry, String> getCsvRecordModifier(CsvRecordColumn column) {
    switch (column) {
      case DATE: return CsvRecordEntry::setDate;
      case AMOUNT: return CsvRecordEntry::setAmount;
      case NOTES: return CsvRecordEntry::setNotes;
      default: throw new BookkeeperException("Unsupported column type");
    }
  }
}
