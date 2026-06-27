package com.bookkeeper.common.model.currency;

import lombok.experimental.UtilityClass;

import java.util.List;
import javax.money.CurrencyUnit;
import javax.money.Monetary;

@UtilityClass
public class CurrencyUtils {

  public final List<CurrencyUnit> SUPPORTED_CURRENCIES = List.of(Monetary.getCurrency("USD"));
}
