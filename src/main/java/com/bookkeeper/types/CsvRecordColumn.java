package com.bookkeeper.types;

import static com.bookkeeper.utils.MiscUtils.asOptional;
import static org.apache.commons.lang3.StringUtils.isEmpty;
import static java.util.Optional.empty;

import lombok.Getter;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

@Getter
public enum CsvRecordColumn {
  STATUS("", "status", 25, false, false, false),
  DATE("Date", "date", 80, true, true, true),
  AMOUNT("Amount", "amount", 80, true, true, true),
  CATEGORY("Category", "category", 120, true, true, true),
  NOTES("Notes", "notes", 120, true, true, true),
  ERRORS("Errors", "errors", 200, false, false, true);

  private String name;
  private String property;
  private int defaultWidth;
  private boolean editable;
  private boolean sortable;
  private boolean resizable;

  private static final Map<String, CsvRecordColumn> COLUMNS_BY_PROPERTY_NAME = new HashMap<>();
  private static final Set<CsvRecordColumn> CSV_COLUMNS = EnumSet.of(DATE, AMOUNT, CATEGORY, NOTES);

  static {
    for (CsvRecordColumn column : values()) {
      COLUMNS_BY_PROPERTY_NAME.put(column.getProperty().toLowerCase(), column);
    }
  }

  CsvRecordColumn(String name, String property, int defaultWidth, boolean editable, boolean sortable,
      boolean resizable) {
    this.name = name;
    this.property = property;
    this.defaultWidth = defaultWidth;
    this.editable = editable;
    this.sortable = sortable;
    this.resizable = resizable;
  }

  @Override
  public String toString() {
    return name;
  }

  public static Stream<CsvRecordColumn> stream() {
    return Stream.of(values());
  }

  public static Optional<CsvRecordColumn> findByProperty(String property) {
    if (isEmpty(property)) {
      return empty();
    }
    return asOptional(COLUMNS_BY_PROPERTY_NAME.get(property.toLowerCase()));
  }

  public static Set<CsvRecordColumn> getCsvColumns() {
    return CSV_COLUMNS;
  }
}
