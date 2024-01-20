package com.bookkeeper.csv.model;

import static com.bookkeeper.csv.model.CsvRecordEntry.COLUMNS;
import static org.hibernate.internal.util.StringHelper.isNotEmpty;
import static java.util.stream.Collectors.joining;

import org.apache.commons.collections4.CollectionUtils;
import java.util.Collection;
import java.util.EnumMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

public class CsvErrorRegistrar {

  private final Map<CsvRecordColumn, Set<String>> errors = new EnumMap<>(CsvRecordColumn.class);

  public CsvErrorRegistrar() {
    for (CsvRecordColumn column : COLUMNS) {
      errors.put(column, new LinkedHashSet<>());
    }
  }

  public void addError(CsvRecordColumn column, String error) {
    if (isNotEmpty(error) && errors.containsKey(column)) {
      errors.get(column).add(error);
    }
  }

  public String getErrors() {
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
