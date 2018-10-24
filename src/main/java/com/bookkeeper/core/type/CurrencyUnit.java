package com.bookkeeper.core.type;

import java.util.Currency;
import java.util.stream.Stream;

public enum CurrencyUnit {
  USD, EUR, RUB;

  private Currency currency;

  static {
    stream().forEach(CurrencyUnit::init);
  }

  private static void init(CurrencyUnit unit) {
    unit.currency = Currency.getInstance(unit.name());
  }

  public Currency getCurrency() {
    return currency;
  }

  public static Stream<CurrencyUnit> stream() {
    return Stream.of(values());
  }
}
