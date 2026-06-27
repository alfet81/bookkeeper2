package com.bookkeeper.csv.utils;

import static com.bookkeeper.utils.MiscUtils.asOptional;
import static org.apache.commons.csv.CSVFormat.RFC4180;
import static org.apache.commons.lang3.StringUtils.isNotEmpty;
import static org.apache.commons.lang3.StringUtils.trimToNull;
import static java.util.Optional.empty;

import com.bookkeeper.common.model.date.DateConverter;
import com.bookkeeper.csv.model.CsvRecordColumn;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.XSlf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import java.io.IOException;
import java.io.Reader;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@XSlf4j
@UtilityClass
public class CsvUtils {

  private final CSVFormat csvFormat = initCSVFormat();


  private CSVFormat initCSVFormat() {
    return RFC4180.builder()
        .setHeader(CsvRecordColumn.class)
        .setSkipHeaderRecord(true)
        .setIgnoreHeaderCase(true)
        .get();
  }


  public static List<CSVRecord> readCsvFile(Reader csvFileReader) throws IOException {
    try (var parser = csvFormat.parse(csvFileReader)) {
      return parser.getRecords();
    }
  }


  public static String getColumnValue(CSVRecord csvRecord, CsvRecordColumn csvRecordColumn) {
    try {
      return trimToNull(csvRecord.get(csvRecordColumn));
    } catch (Exception e) {
      log.warn("Failed to get CSV column value: {}", e.getMessage());
    }
    return null;
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
    } catch (Exception e) {
      log.warn("Failed to convert String object to BigDecimal: {}", e.getMessage());
    }
    return empty();
  }
}
