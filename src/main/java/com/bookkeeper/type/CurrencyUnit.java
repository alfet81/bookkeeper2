package com.bookkeeper.type;

import static com.bookkeeper.utils.MiscUtils.asOptional;
import static org.apache.commons.lang3.EnumUtils.getEnum;

import lombok.Getter;
import java.util.Currency;
import java.util.Optional;
import java.util.stream.Stream;

@Getter
public enum CurrencyUnit {
  USD, EUR, RUB;

  private Currency currency;

  CurrencyUnit() {
    currency = Currency.getInstance(name());
  }

  public static Stream<CurrencyUnit> stream() {
    return Stream.of(values());
  }

  public static Optional<CurrencyUnit> optionalOf(String enumName) {
    enumName = asOptional(enumName).map(String::toUpperCase).orElse(null);
    return asOptional(getEnum(CurrencyUnit.class, enumName));
  }

  @Override
  public String toString() {
    return name();
  }
}
