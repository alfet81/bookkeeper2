package com.bookkeeper.types;

import static com.bookkeeper.AppConstants.DEFAULT_DATE_FORMATTER;
import static com.bookkeeper.utils.MiscUtils.getDefaultCurrency;

import java.time.LocalDate;
import java.util.Locale;

public enum Property {
  USER_NAME(null),
  USER_PASSWORD(null),
  PREFERRED_CURRENCY(getDefaultCurrencyCode()),
  PREFERRED_LOCALE(getDefaultLocale()),
  DEFAULT_ACCOUNT(null),
  LAST_LOGIN_DATE(getCurrentDate()),
  LAST_CSV_FILE_DIR(null);

  private String defaultValue;

  Property(String defaultValue) {
    this.defaultValue = defaultValue;
  }

  public String getDefaultValue() {
    return defaultValue;
  }

  private static String getDefaultCurrencyCode() {
    return getDefaultCurrency().getCurrencyCode();
  }

  private static String getDefaultLocale() {
    return Locale.getDefault().toString();
  }

  private static String getCurrentDate() {
    return LocalDate.now().format(DEFAULT_DATE_FORMATTER);
  }
}
