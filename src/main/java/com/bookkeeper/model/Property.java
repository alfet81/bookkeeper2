package com.bookkeeper.model;

import static com.bookkeeper.utils.MiscUtils.getDefaultCurrency;

import com.bookkeeper.utils.DateTimeUtils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import java.time.LocalDate;
import java.util.Currency;
import java.util.Locale;
import java.util.function.Function;

@Deprecated
@Getter
@AllArgsConstructor
public enum Property {
  USER_NAME(null, none()),
  USER_PASSWORD(null, none()),
  PREFERRED_CURRENCY(getDefaultCurrency(), currency()),
  PREFERRED_LOCALE(Locale.getDefault(), locale()),
  DEFAULT_ACCOUNT(null, none()),
  LAST_LOGIN_DATE(LocalDate.now(), date()),
  LAST_CSV_FILE_DIR(null, none());

  private final Object defaultValue;
  private final Function<String, Object> dataConverter;

  public Object convertValue(String value) {
    return dataConverter != null ? dataConverter.apply(value) : value;
  }

  private static Function<String, Object> none() {
    return s -> s;
  }

  private static Function<String, Object> currency() {
    return Currency::getInstance;
  }

  private static Function<String, Object> locale() {
    return Locale::new;
  }

  private static Function<String, Object> date() {
    return DateTimeUtils::string2Date;
  }
}
