package com.bookkeeper.csv.validation;

import static com.bookkeeper.csv.utils.CsvUtils.string2Date;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.extern.slf4j.XSlf4j;

@XSlf4j
public class DateValidator implements ConstraintValidator<ValidDate, String> {

  @Override
  public boolean isValid(String value, ConstraintValidatorContext context) {
    log.entry(value);

    if (value == null || value.isEmpty()) {
      return true;
    }
    return string2Date(value).isPresent();
  }
}
