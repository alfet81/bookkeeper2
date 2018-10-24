package com.bookkeeper.core.type;

import static com.bookkeeper.core.utils.CommonUtils.asOptional;
import static org.apache.commons.lang3.StringUtils.isEmpty;
import static java.util.Optional.empty;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

public enum CsvRecordColumn {
  STATUS("", "status", 25, false, false, false),
  DATE("Date", "date", 80, true, true, true),
  AMOUNT("Amount", "amount", 80, true, true, true),
  CATEGORY("Category", "category", 120, true, true, true),
  NOTES("Notes", "notes", 120, true, true, true),
  ERRORS("Errors", "errors", 200, false, false, true);

  private String name;
  private String property;
  private int width;
  private boolean editable;
  private boolean sortable;
  private boolean resizable;

  private static final Map<String, CsvRecordColumn> PROPERTIES_X_COLUMNS = new HashMap<>();
  private static final Set<CsvRecordColumn> DATA_COLUMNS = EnumSet.of(DATE, AMOUNT, CATEGORY, NOTES);

  static {
    for (CsvRecordColumn column : values()) {
      PROPERTIES_X_COLUMNS.put(column.getProperty().toLowerCase(), column);
    }
  }

  CsvRecordColumn(String name, String property, int width, boolean editable, boolean sortable,
      boolean resizable) {
    this.name = name;
    this.property = property;
    this.width = width;
    this.editable = editable;
    this.sortable = sortable;
    this.resizable = resizable;
  }

  public String getName() {
    return name;
  }

  public String getProperty() {
    return property;
  }

  public int getWidth() {
    return width;
  }

  public boolean isEditable() {
    return editable;
  }

  public boolean isSortable() {
    return sortable;
  }

  public boolean isResizable() {
    return resizable;
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
    return asOptional(PROPERTIES_X_COLUMNS.get(property.toLowerCase()));
  }

  public static Set<CsvRecordColumn> getDataColumns() {
    return DATA_COLUMNS;
  }
}
