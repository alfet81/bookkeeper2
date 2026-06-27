package com.bookkeeper.csv.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.extern.slf4j.XSlf4j;
import java.math.BigDecimal;

@XSlf4j
public class AmountValidator implements ConstraintValidator<ValidAmount, String> {

  @Override
  public boolean isValid(String value, ConstraintValidatorContext context) {
    log.entry(value);

    if (value == null || value.isEmpty()) {
      return true;
    }
    try {
      new BigDecimal(value);
      return true;
    } catch (NumberFormatException e) {
      log.warn(e.getMessage());
      return false;
    }
  }
}
