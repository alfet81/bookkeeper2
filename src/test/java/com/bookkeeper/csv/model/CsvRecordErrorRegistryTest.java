package com.bookkeeper.csv.model;

import static com.bookkeeper.csv.view.model.CsvRecordColumn.AMOUNT;
import static com.bookkeeper.csv.view.model.CsvRecordColumn.DATE;
import static org.assertj.core.api.Assertions.assertThat;

import com.bookkeeper.csv.view.model.CsvRecordErrorRegistry;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

@TestMethodOrder(OrderAnnotation.class)
class CsvRecordErrorRegistryTest {


  @Test
  @Order(1)
  void testAddError_WhenErrorMessageIsNull_ThenNoErrorAdded() {
    // Given
    var csvRecordErrorRegistrar = new CsvRecordErrorRegistry();

    // When
    csvRecordErrorRegistrar.addError(DATE.name().toLowerCase(), null);

    // Then
    assertThat(csvRecordErrorRegistrar.hasErrors()).isFalse();
  }


  @Test
  @Order(2)
  void testAddError_WhenErrorMessageIsNotNull_ThenErrorAdded() {
    // Given
    var csvRecordErrorRegistrar = new CsvRecordErrorRegistry();

    // When
    csvRecordErrorRegistrar.addError(DATE.name().toLowerCase(), "is invalid");

    // Then
    assertThat(csvRecordErrorRegistrar.hasErrors()).isTrue();
  }


  @Test
  @Order(3)
  void testBuildErrorMessage_WhenNoErrors_ThenEmpty() {
    // Given
    var csvRecordErrorRegistrar = new CsvRecordErrorRegistry();

    // When
    var errorMessage = csvRecordErrorRegistrar.buildErrorMessage();

    // Then
    assertThat(errorMessage).isEmpty();
  }


  @Test
  @Order(4)
  void testBuildErrorMessage_WhenSingleError_ThenHasMessage() {
    // Given
    var csvRecordErrorRegistrar = new CsvRecordErrorRegistry();
    csvRecordErrorRegistrar.addError(DATE.name().toLowerCase(), "has invalid format");

    // When
    var errorMessage = csvRecordErrorRegistrar.buildErrorMessage();

    // Then
    assertThat(errorMessage).hasValue("Date has invalid format");
  }


  @Test
  @Order(5)
  void testBuildErrorMessage_WhenMultipleErrors_ThenHasMessage() {
    // Given
    var csvRecordErrorRegistrar = new CsvRecordErrorRegistry();
    csvRecordErrorRegistrar.addError(DATE.name().toLowerCase(), "has invalid format");
    csvRecordErrorRegistrar.addError(AMOUNT.name().toLowerCase(), "has invalid value");

    // When
    var errorMessage = csvRecordErrorRegistrar.buildErrorMessage();

    // Then
    assertThat(errorMessage).hasValue("Date has invalid format, Amount has invalid value");
  }


  @Test
  @Order(6)
  void testHasErrors_WhenNoErrors_ThenFalse() {
    // Given
    var csvRecordErrorRegistrar = new CsvRecordErrorRegistry();

    // When
    boolean result = csvRecordErrorRegistrar.hasErrors();

    //Then
    assertThat(result).isFalse();
  }


  @Test
  @Order(7)
  void testHasErrors_WhenErrorsExist_ThenTrue() {
    // Given
    var csvRecordErrorRegistrar = new CsvRecordErrorRegistry();
    csvRecordErrorRegistrar.addError(DATE.name().toLowerCase(), "Invalid date");
    csvRecordErrorRegistrar.addError(AMOUNT.name().toLowerCase(), "Invalid amount");

    // When
    boolean result = csvRecordErrorRegistrar.hasErrors();

    //Then
    assertThat(result).isTrue();
  }
}
