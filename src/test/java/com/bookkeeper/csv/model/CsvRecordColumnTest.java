package com.bookkeeper.csv.model;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Objects;

class CsvRecordColumnTest {

  @ParameterizedTest(name = "[{index}] => {0} is valid enum name")
  @EnumSource(CsvRecordColumn.class)
  @DisplayName("Test CsvRecordColumn for valid and supported enum names")
  void testForName_WhenValidEnumName_ThenOptionalHasValue(CsvRecordColumn csvRecordColumn) {
    assertThat(CsvRecordColumn.forName(csvRecordColumn.name())).hasValue(csvRecordColumn);
  }


  @NullAndEmptySource
  @ValueSource(strings = { "INVALID" })
  @ParameterizedTest(name = "[{index}] => {0} is invalid enum name")
  @DisplayName("Test CsvRecordColumn for invalid and unsupported enum names")
  void testForName_WhenInvalidEnumName_ThenOptionalIsEmpty(String invalidEnumName) {
    assertThat(CsvRecordColumn.forName(invalidEnumName)).isEmpty();
  }


  @ParameterizedTest(name = "[{index}] => Enum {0} has column name {1}")
  @CsvSource({ "DATE,Date", "AMOUNT,Amount", "NOTES,Notes" })
  @DisplayName("Test CsvRecordColumn enum to return proper column names")
  void testGetColumnName(String enumName, String stringValue) {
    var optionalEnum = CsvRecordColumn.forName(enumName);
    assertThat(optionalEnum.map(CsvRecordColumn::name)).hasValue(enumName);
    assertThat(optionalEnum.map(CsvRecordColumn::getColumnName)).hasValue(stringValue);
  }
}
