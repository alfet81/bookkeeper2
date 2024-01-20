package com.bookkeeper.utils;

import static org.apache.commons.lang3.StringUtils.trimToNull;

import static java.util.Optional.empty;

import java.util.Currency;
import java.util.Locale;
import java.util.Optional;

public final class MiscUtils {

  private MiscUtils() {
  }

  public static Optional<Currency> getCurrency(String currencyCode) {
    try {
      return asOptional(Currency.getInstance(currencyCode));
    } catch (Exception ignored) {
      return empty();
    }
  }

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
