package com.bookkeeper.core.utils;

import static org.apache.commons.lang3.StringUtils.trimToNull;

import java.util.Currency;
import java.util.Locale;
import java.util.Optional;

public class CommonUtils {

  public static Currency getDefaultCurrency() {
    return Currency.getInstance(Locale.getDefault());
  }

  public static <T> Optional<T> asOptional(T value) {
    return Optional.ofNullable(value);
  }

  public static Optional<String> asOptional(String value) {
    return Optional.ofNullable(trimToNull(value));
  }
}
