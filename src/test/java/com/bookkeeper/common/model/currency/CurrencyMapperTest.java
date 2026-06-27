package com.bookkeeper.common.model.currency;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchException;

import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import javax.money.Monetary;
import javax.money.UnknownCurrencyException;

class CurrencyMapperTest {
  private static final CurrencyMapper currencyMapper = Mappers.getMapper(CurrencyMapper.class);


  @Test
  void testToCurrencyUnit() {
    var currencyUnit = currencyMapper.toCurrencyUnit("USD");
    assertThat(currencyUnit).isEqualTo(Monetary.getCurrency("USD"));
  }


  @Test
  void testToCurrencyUnit_WhenInvalidCurrencyCode() {
    var exception = catchException(() -> currencyMapper.toCurrencyUnit("BAD"));
    assertThat(exception).isInstanceOf(UnknownCurrencyException.class).hasMessage("Unknown currency code: BAD");
  }
}
