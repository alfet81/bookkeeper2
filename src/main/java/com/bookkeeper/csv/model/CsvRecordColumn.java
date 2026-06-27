package com.bookkeeper.csv.model;

import static com.bookkeeper.utils.MiscUtils.asOptional;
import static org.apache.commons.lang3.EnumUtils.getEnumIgnoreCase;
import static org.apache.commons.lang3.StringUtils.capitalize;

import java.util.Optional;
import java.util.stream.Stream;

/**
 * Enum of supported CSV record columns.
 */
public enum CsvRecordColumn {
  DATE,
  AMOUNT,
  NOTES;


  /**
   * Returns optional CsvRecordColumn enum for provided enum name.
   *
   * @param name Name of the enum object.
   * @return Optional of CsvRecordColumn enum.
   */
  public static Optional<CsvRecordColumn> forName(String name) {
    return asOptional(name).map(enumName -> getEnumIgnoreCase(CsvRecordColumn.class, enumName));
  }


  /**
   * Returns a stream of enum values.
   *
   * @return Stream of CsvRecordColumn values.
   */
  public static Stream<CsvRecordColumn> stream() {
    return Stream.of(values());
  }


  /**
   * Returns name of the enum with the first letter capitalized and the rest as low-case.
   *
   * @return Name of the enum as a CSV record column name.
   */
  public String getColumnName() {
    return capitalize(name().toLowerCase());
  }
}
