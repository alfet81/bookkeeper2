package com.bookkeeper.csv;

import static com.bookkeeper.core.utils.CommonUtils.asOptional;
import static org.hibernate.internal.util.StringHelper.isNotEmpty;
import static java.util.stream.Collectors.joining;

import com.bookkeeper.core.type.CsvRecordColumn;

import java.util.Collection;
import java.util.EnumMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

class CsvErrorMessageBuilder {

  private Map<CsvRecordColumn, Set<String>> COLUMN_ERRORS = new EnumMap<>(CsvRecordColumn.class);

  CsvErrorMessageBuilder() {
    for (CsvRecordColumn column : CsvRecordColumn.getDataColumns()) {
      COLUMN_ERRORS.put(column, new HashSet<>());
    }
  }

  void addError(CsvRecordColumn column, String error) {
    if (COLUMN_ERRORS.containsKey(column) && isNotEmpty(error)) {
      var errors = COLUMN_ERRORS.get(column);
      errors.add(error);
    }
  }

  Optional<String> getErrorMessages() {
    return asOptional(COLUMN_ERRORS.entrySet().stream()
        .map(Map.Entry::getValue)
        .flatMap(Collection::stream)
        .collect(joining(", ")));
  }
}
