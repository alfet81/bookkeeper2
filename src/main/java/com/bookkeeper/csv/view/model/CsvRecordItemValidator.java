package com.bookkeeper.csv.view.model;

import static com.bookkeeper.csv.view.model.CsvRecordStatus.ERROR;
import static com.bookkeeper.csv.view.model.CsvRecordStatus.OK;

import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;

/**
 * Class for CSV record item validation.
 */
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
class CsvRecordItemValidator {

  private final CsvRecordErrorRegistry errorRegistry = new CsvRecordErrorRegistry();

  @NonNull
  private final Validator validator;

  @NonNull
  private final CsvRecordItem csvRecordItem;


  String getErrors() {
    return errorRegistry.buildErrorMessage().orElse(null);
  }


  CsvRecordStatus getStatus() {
    return errorRegistry.hasErrors() ? ERROR : OK;
  }


  void validate() {
    validator.validate(csvRecordItem).forEach(this::addErrorMessage);
    csvRecordItem.setErrors(getErrors());
    csvRecordItem.setStatus(getStatus());
  }


  private void addErrorMessage(ConstraintViolation<CsvRecordItem> constraintViolation) {
    var propertyName = constraintViolation.getPropertyPath().toString();
    var errorMessage = constraintViolation.getMessage();
    errorRegistry.addError(propertyName, errorMessage);
  }
}
