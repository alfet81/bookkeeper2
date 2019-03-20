package com.bookkeeper.utils;

import static com.bookkeeper.utils.MiscUtils.asOptional;
import static org.apache.commons.lang3.StringUtils.isNotEmpty;
import static org.apache.commons.lang3.StringUtils.trimToNull;
import static java.util.Optional.empty;

import com.bookkeeper.exceptions.BookkeeperException;
import com.bookkeeper.types.CsvRecordColumn;
import com.bookkeeper.types.DateConverter;
import com.bookkeeper.types.ObjectModifier;
import com.bookkeeper.csv.CsvRecordWrapper;

import org.apache.commons.csv.CSVRecord;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

public class CsvUtils {

  public static String getColumnValue(CSVRecord csvRecord, CsvRecordColumn csvRecordColumn) {
    try {
      return trimToNull(csvRecord.get(csvRecordColumn));
    } catch (Exception e) {}

    return null;
  }

  public static Optional<CsvRecordColumn> getCsvRecordColumn(String columnName) {
    return CsvRecordColumn.findByProperty(columnName);
  }

  public static Optional<LocalDate> string2Date(String value) {
    return asOptional(DateConverter.convert(value));
  }

  public static Optional<BigDecimal> string2Decimal(String value) {
    try {
      return isNotEmpty(value) ? asOptional(new BigDecimal(value)) : empty();
    } catch (Exception e) {}

    return empty();
  }

  public static ObjectModifier<CsvRecordWrapper, String> getCsvRecordModifier(CsvRecordColumn column) {
    switch (column) {
      case DATE:
        return CsvRecordWrapper::setDate;
      case AMOUNT:
        return CsvRecordWrapper::setAmount;
      case CATEGORY:
        return CsvRecordWrapper::setCategory;
      case NOTES:
        return CsvRecordWrapper::setNotes;
    }
    throw new BookkeeperException("Unsupported column type");
  }
}
