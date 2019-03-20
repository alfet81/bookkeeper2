package com.bookkeeper.types;

import static com.bookkeeper.utils.MiscUtils.asOptional;
import static org.apache.commons.lang3.EnumUtils.getEnum;

import java.util.Currency;
import java.util.Optional;
import java.util.stream.Stream;

public enum CurrencyUnit {
  USD, EUR, RUB;

  private Currency currency;

  static {
    stream().forEach(CurrencyUnit::init);
  }

  private static void init(CurrencyUnit unit) {
    unit.currency = Currency.getInstance(unit.toString());
  }

  public Currency getCurrency() {
    return currency;
  }

  public static Stream<CurrencyUnit> stream() {
    return Stream.of(values());
  }

  public static Optional<CurrencyUnit> optionalOf(String value) {
    value = asOptional(value).orElse(null);
    return asOptional(getEnum(CurrencyUnit.class, value));
  }

  @Override
  public String toString() {
    return name();
  }
}
