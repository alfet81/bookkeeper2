package com.bookkeeper.utils;

import static com.bookkeeper.utils.MiscUtils.asOptional;

import static java.util.Optional.empty;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.XSlf4j;

import java.util.Currency;
import java.util.Locale;
import java.util.Optional;

@XSlf4j
@UtilityClass
public class CurrencyUtils {

  public static Optional<Currency> getCurrency(String currencyCode) {
    try {
      var currency = Currency.getInstance(currencyCode);
      log.info("Found currency {} for code {}", currency, currencyCode);
      return asOptional(currency);
    } catch (Exception e) {
      log.warn("Failed to get currency from currency code: {}", currencyCode);
    }
    return empty();
  }


  public static Currency getDefaultCurrency() {
    var currency = Currency.getInstance(Locale.getDefault());
    log.trace("Using default currency: {}", currency);
    return currency;
  }
}
