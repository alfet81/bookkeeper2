package com.bookkeeper.csv.model;

import static com.bookkeeper.csv.common.CsvTestHelper.VERY_LONG_NOTES;
import static org.assertj.core.api.Assertions.assertThat;

import com.bookkeeper.config.FactoryConfig;
import com.bookkeeper.csv.view.model.CsvRecordItem;
import com.bookkeeper.junit.VariableSource;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.function.Function;
import java.util.stream.Stream;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = FactoryConfig.class)
class CsvRecordItemTest {

  private static final Stream<Arguments> VALID_CSV_RECORDS = Stream.of(
      //date, amount, currency, notes, expected date, expected amount
      Arguments.of("09/13/2025", "10.00", "DEFAULT date format"),
      Arguments.of("2025-09-13", "-99.99", "USD", "ISO 8601 date format"));
  private static final Stream<Arguments> INVALID_CSV_RECORDS = Stream.of(
      //date, amount, currency, notes, expected errors
      Arguments.of("13.09.2025", "1.00", "Unsupported date format", "Date has invalid format"),
      Arguments.of("BAD DATE", "-99,99", "Invalid date format and amount",
          "Date has invalid format, Amount has invalid value"),
      Arguments.of("2025-09-13", "-99.99", VERY_LONG_NOTES, "Notes exceeds 255 chars"));

  @Autowired
  private Function<CsvRecord, CsvRecordItem> csvRecordItemFactory;

  @DisplayName("Testing valid CSV records")
  @ParameterizedTest(name = "[{index}] => {0}, {1}, {2}, {3} -> Status: OK")
  @VariableSource("VALID_CSV_RECORDS")
  void testValidate_ExpectNoErrors(String inputDate, String inputAmount, String notes) {

    // Given
    var csvRecord = CsvRecord.builder()
        .date(inputDate)
        .amount(inputAmount)
        .notes(notes)
        .build();

    var csvRecordItem = csvRecordItemFactory.apply(csvRecord);

    // When
    csvRecordItem.validate();

    // Then
    assertThat(csvRecordItem.getErrors()).isNull();
  }


  @DisplayName("Testing invalid CSV records")
  @ParameterizedTest(name = "[{index}] => {0}, {1}, {2}, {3} -> Status: ERROR ({4})")
  @VariableSource("INVALID_CSV_RECORDS")
  void testValidate_ExpectValidationErrors(String inputDate, String inputAmount, String notes,
      String expectedErrorMessage) {
    // Given
    var csvRecord = CsvRecord.builder().date(inputDate).amount(inputAmount).notes(notes).build();

    var csvRecordItem = csvRecordItemFactory.apply(csvRecord);

    // When
    csvRecordItem.validate();

    // Then
    assertThat(csvRecordItem.getErrors()).isEqualTo(expectedErrorMessage);
  }
}
