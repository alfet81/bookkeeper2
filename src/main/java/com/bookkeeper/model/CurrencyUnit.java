package com.bookkeeper.model;

import static com.bookkeeper.utils.MiscUtils.asOptional;
import static org.apache.commons.lang3.EnumUtils.getEnum;

import static java.util.Arrays.asList;

import lombok.Getter;
import java.util.Currency;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Getter
public enum CurrencyUnit {
  USD, EUR, RUB;

  private final Currency currency;

  CurrencyUnit() {
    currency = Currency.getInstance(name());
  }

  public static Stream<CurrencyUnit> stream() {
    return Stream.of(values());
  }

  public static Optional<CurrencyUnit> forCurrencyCode(String currencyCode) {

    currencyCode = asOptional(currencyCode).map(String::toUpperCase).orElse(null);

    return asOptional(getEnum(CurrencyUnit.class, currencyCode));
  }

  public static List<CurrencyUnit> getValues() {
    return asList(values());
  }
}
