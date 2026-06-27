package com.bookkeeper.csv.view.model;

import static org.apache.commons.lang3.StringUtils.EMPTY;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.EnumUtils;

import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.stream.Stream;

@Getter
@RequiredArgsConstructor
public enum CsvRecordColumn {
  // column name, width, edit, sort, resize, modifier
  STATUS(EMPTY, 25, false, false, false, null),
  DATE("Date", 80, true, true, true, CsvRecordItem::setDate),
  AMOUNT("Amount", 80, true, true, true, CsvRecordItem::setAmount),
  NOTES("Notes", 120, true, true, true, CsvRecordItem::setNotes),
  ERRORS(EMPTY, 300, false, false, true, null);

  private final String columnName;
  private final int defaultWidth;
  private final boolean editable;
  private final boolean sortable;
  private final boolean resizable;
  private final BiConsumer<CsvRecordItem, String> modifier;


  public static Stream<CsvRecordColumn> stream() {
    return Stream.of(values());
  }


  public static Optional<CsvRecordColumn> forName(String name) {
    return Optional.ofNullable(EnumUtils.getEnumIgnoreCase(CsvRecordColumn.class, name));
  }


  public void setValue(CsvRecordItem item, String value) {
    if (modifier != null) {
      modifier.accept(item, value);
    }
  }
}
