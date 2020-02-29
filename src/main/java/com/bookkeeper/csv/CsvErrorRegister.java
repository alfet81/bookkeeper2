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

class CsvErrorRegister {

  private static final Set<CsvRecordColumn> CSV_COLUMNS = EnumSet.of(DATE, AMOUNT, NOTES);

  private final Map<CsvRecordColumn, Set<String>> ERRORS = new EnumMap<>(CsvRecordColumn.class);

  CsvErrorRegister() {
    for (CsvRecordColumn column : CSV_COLUMNS) {
      ERRORS.put(column, new LinkedHashSet<>());
    }
  }

  void addError(CsvRecordColumn column, String error) {
    if (isNotEmpty(error) && ERRORS.containsKey(column)) {
      ERRORS.get(column).add(error);
    }
  }

  String getErrors() {
    return hasErrors() ? ERRORS.values().stream().flatMap(Collection::stream).collect(joining(", "))
        : null;
  }

  boolean hasErrors() {
    return ERRORS.values().stream().anyMatch(CollectionUtils::isNotEmpty);
  }

  void clear() {
    ERRORS.forEach((column, errors) -> errors.clear());
  }
}
