package com.bookkeeper.type;

import static com.bookkeeper.utils.MiscUtils.asOptional;
import static org.apache.commons.lang3.StringUtils.capitalize;
import static org.apache.commons.lang3.StringUtils.isEmpty;
import static java.util.Optional.empty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.commons.collections4.map.CaseInsensitiveMap;
import java.util.EnumSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

@Getter
@AllArgsConstructor
public enum CsvRecordColumn {
  STATUS(25, false, false, false),
  DATE(80, true, true, true),
  AMOUNT(80, true, true, true),
  CATEGORY(120, true, true, true),
  NOTES(120, true, true, true),
  ERRORS(200, false, false, true);

  private int defaultWidth;
  private boolean editable;
  private boolean sortable;
  private boolean resizable;

  private static final Map<String, CsvRecordColumn> COLUMNS_BY_NAME = new CaseInsensitiveMap<>();
  private static final Set<CsvRecordColumn> CSV_COLUMNS = EnumSet.of(DATE, AMOUNT, CATEGORY, NOTES);

  static {
    for (CsvRecordColumn column : values()) {
      COLUMNS_BY_NAME.put(column.name(), column);
    }
  }

  @Override
  public String toString() {
    return capitalize(name().toLowerCase());
  }

  public static Stream<CsvRecordColumn> stream() {
    return Stream.of(values());
  }

  public static Optional<CsvRecordColumn> findByProperty(String property) {
    if (isEmpty(property)) {
      return empty();
    }
    return asOptional(COLUMNS_BY_NAME.get(property));
  }

  public static Set<CsvRecordColumn> getCsvColumns() {
    return CSV_COLUMNS;
  }
}
