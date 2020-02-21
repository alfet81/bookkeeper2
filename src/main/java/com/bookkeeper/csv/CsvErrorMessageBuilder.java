package com.bookkeeper.csv;

import static com.bookkeeper.type.CsvRecordColumn.getCsvColumns;
import static com.bookkeeper.utils.MiscUtils.asOptional;
import static org.hibernate.internal.util.StringHelper.isNotEmpty;
import static java.util.stream.Collectors.joining;

import com.bookkeeper.type.CsvRecordColumn;

import java.util.Collection;
import java.util.EnumMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

class CsvErrorMessageBuilder {

  private Map<CsvRecordColumn, Set<String>> COLUMN_ERRORS = new EnumMap<>(CsvRecordColumn.class);

  CsvErrorMessageBuilder() {
    for (CsvRecordColumn column : getCsvColumns()) {
      COLUMN_ERRORS.put(column, new LinkedHashSet<>());
    }
  }

  void addError(CsvRecordColumn column, String error) {
    if (isNotEmpty(error) && COLUMN_ERRORS.containsKey(column)) {
      COLUMN_ERRORS.get(column).add(error);
    }
  }

  String getErrorMessages() {
    return asOptional(COLUMN_ERRORS.values().stream()
        .flatMap(Collection::stream)
        .collect(joining(", ")))
        .orElse(null);
  }

  void clear() {
    COLUMN_ERRORS.forEach((column, errors) -> errors.clear());
  }
}
