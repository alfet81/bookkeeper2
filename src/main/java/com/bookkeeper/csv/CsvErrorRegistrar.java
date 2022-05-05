package com.bookkeeper.csv;

import static com.bookkeeper.type.CsvRecordColumn.AMOUNT;
import static com.bookkeeper.type.CsvRecordColumn.DATE;
import static com.bookkeeper.type.CsvRecordColumn.NOTES;
import static org.hibernate.internal.util.StringHelper.isNotEmpty;
import static java.util.stream.Collectors.joining;

import com.bookkeeper.type.CsvRecordColumn;

import org.apache.commons.collections4.CollectionUtils;
import java.util.Collection;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

class CsvErrorRegistrar {

  private static final Set<CsvRecordColumn> CSV_COLUMNS = EnumSet.of(DATE, AMOUNT, NOTES);

  private final Map<CsvRecordColumn, Set<String>> errors = new EnumMap<>(CsvRecordColumn.class);

  CsvErrorRegistrar() {
    for (CsvRecordColumn column : CSV_COLUMNS) {
      errors.put(column, new LinkedHashSet<>());
    }
  }

  void addError(CsvRecordColumn column, String error) {
    if (isNotEmpty(error) && errors.containsKey(column)) {
      errors.get(column).add(error);
    }
  }

  String getErrors() {
    return hasErrors() ? errors.values().stream()
        .flatMap(Collection::stream)
        .collect(joining(", "))
        : null;
  }

  boolean hasErrors() {
    return errors.values().stream().anyMatch(CollectionUtils::isNotEmpty);
  }

  void clear() {
    errors.values().forEach(Set::clear);
  }
}
