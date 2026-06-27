package com.bookkeeper.common.model.date;

import static org.assertj.core.api.Assertions.assertThat;

import com.bookkeeper.junit.VariableSource;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import java.time.LocalDate;
import java.util.stream.Stream;

class DateConverterTest {

  private static final Stream<Arguments> VALID_DATES = Stream.of(
      Arguments.of("11/07/2024", LocalDate.of(2024, 11, 7)),
      Arguments.of("2024-11-07", LocalDate.of(2024, 11, 7))
  );


  @DisplayName("Testing DateConverter with valid and supported date values")
  @ParameterizedTest(name = "[{index}] => {0} -> {1}")
  @VariableSource("VALID_DATES")
  void testDateConverter_WhenValidDates_ThenSuccessfulConversion(String dateToConvert, LocalDate expectedDate) {
    // When
    var actualDateAsOptional = DateConverter.convert(dateToConvert);
    // Then
    assertThat(actualDateAsOptional).hasValue(expectedDate);
  }


  @DisplayName("Test DateConverter with invalid date values")
  @ParameterizedTest(name = "[{index}] => {0} -> invalid date format")
  @NullAndEmptySource
  @ValueSource(strings = { "Nov 7 2024", "INVALID" })
  void testDateConverter_WhenInvalidDates_ThenNoConversionHappen(String dateToConvert) {
    // When
    var actualDateAsOptional = DateConverter.convert(dateToConvert);
    // Then
    assertThat(actualDateAsOptional).isEmpty();
  }
}
