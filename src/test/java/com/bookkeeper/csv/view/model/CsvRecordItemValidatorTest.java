package com.bookkeeper.csv.view.model;

import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.assertj.core.api.Assertions.assertThat;

import com.bookkeeper.csv.model.CsvRecord;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;
import javax.money.CurrencyUnit;
import javax.money.Monetary;
import jakarta.validation.Validation;
import jakarta.validation.Validator;

class CsvRecordItemValidatorTest {

  private static final String VERY_LONG_NOTES = """
        Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque
        laudantium, totam rem aperiam, eaque ipsa quae ab illo inventore veritatis et quasi architecto
        beatae vitae dicta sunt explicabo. Nemo enim ipsam voluptatem quia voluptas sit aspernatur aut
        odit aut fugit, sed quia
      """;

  private static final CurrencyUnit USD = Monetary.getCurrency("USD");

  private static final Validator VALIDATOR = Validation.buildDefaultValidatorFactory()
      .getValidator();


  private static Stream<Arguments> provideInvalidCsvRecordItems() {
    return Stream.of(
        Arguments.of(
            new CsvRecordItem(VALIDATOR, new CsvRecord(null, "1.00", "Test date validation")),
            "Date is missing"),
        Arguments.of(
            new CsvRecordItem(VALIDATOR, new CsvRecord(EMPTY, "1.00", "Test date validation")),
            "Date is missing"),
        Arguments.of(new CsvRecordItem(VALIDATOR,
            new CsvRecord("2026-01-21", null, "Test amount validation")), "Amount is missing"),
        Arguments.of(new CsvRecordItem(VALIDATOR, new CsvRecord("2026-01-21", EMPTY,
            "Test amount validation")), "Amount is missing"),
        Arguments.of(new CsvRecordItem(VALIDATOR,
            new CsvRecord("1/1/1", "1.00", "Test date validation")), "Date has invalid format"),
        Arguments.of(new CsvRecordItem(VALIDATOR,
                new CsvRecord("2026-01-21", "1,000", "Test amount validation")),
            "Amount has invalid value"),
        Arguments.of(
            new CsvRecordItem(VALIDATOR, new CsvRecord("2026-01-21", "1", VERY_LONG_NOTES)),
            "Notes exceeds 255 chars"),
        Arguments.of(new CsvRecordItem(VALIDATOR, new CsvRecord("Invalid", "Invalid",
                "Test combination of invalid properties")),
            "Date has invalid format, Amount has invalid value")
    );
  }


  @ParameterizedTest(name = "[{index}] => {0} yields {1}")
  @MethodSource("provideInvalidCsvRecordItems")
  void testValidate_WhenAllValuesNull(CsvRecordItem csvRecordItem, String expectedErrorMessage) {
    // Given
    var csvRecordItemValidator = new CsvRecordItemValidator(VALIDATOR, csvRecordItem);

    // When
    csvRecordItemValidator.validate();

    // Then
    assertThat(csvRecordItemValidator.getErrors()).isEqualTo(expectedErrorMessage);
  }
}
