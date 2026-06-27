package com.bookkeeper.csv.view.model;

import static com.bookkeeper.csv.view.model.CsvRecordColumn.AMOUNT;
import static com.bookkeeper.csv.view.model.CsvRecordColumn.DATE;
import static com.bookkeeper.csv.view.model.CsvRecordColumn.NOTES;
import static com.bookkeeper.utils.MiscUtils.asOptional;
import static org.apache.commons.lang3.StringUtils.SPACE;
import static org.apache.commons.lang3.StringUtils.capitalize;
import static org.hibernate.internal.util.StringHelper.isNotEmpty;

import static java.util.Collections.emptySet;
import static java.util.stream.Collectors.joining;

import com.bookkeeper.common.exception.ApplicationException;
import org.apache.commons.collections4.CollectionUtils;

import java.util.Collection;
import java.util.EnumMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

/**
 * Class for CSV record error registry.
 */
public class CsvRecordErrorRegistry {

  private static final List<CsvRecordColumn> COLUMNS = List.of(DATE, AMOUNT, NOTES);

  private final Map<CsvRecordColumn, Set<String>> errors = new EnumMap<>(CsvRecordColumn.class);


  public void addError(String propertyName, String errorMessage) {
    if (isNotEmpty(errorMessage)) {
      var csvRecordColumn = CsvRecordColumn.forName(propertyName)
          .orElseThrow(() -> new ApplicationException("Invalid property: " + propertyName));
      errors.computeIfAbsent(csvRecordColumn, _ -> new LinkedHashSet<>())
          .add(capitalize(propertyName) + SPACE + errorMessage);
    }
  }


  public Optional<String> buildErrorMessage() {
    if (!hasErrors()) {
      return Optional.empty();
    }
    String errorMessage = COLUMNS.stream()
        .map((CsvRecordColumn column) -> errors.getOrDefault(column, emptySet()))
        .filter(CollectionUtils::isNotEmpty)
        .flatMap(Collection::stream)
        .collect(joining(", "));
    return asOptional(errorMessage);
  }


  public boolean hasErrors() {
    return !errors.isEmpty();
  }
}
