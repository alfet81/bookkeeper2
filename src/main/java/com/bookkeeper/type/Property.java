package com.bookkeeper.type;

import static com.bookkeeper.app.AppConstants.DEFAULT_DATE_FORMATTER;
import static com.bookkeeper.utils.MiscUtils.getDefaultCurrency;

import lombok.AllArgsConstructor;
import lombok.Getter;
import java.time.LocalDate;
import java.util.Locale;

@Getter
@AllArgsConstructor
public enum Property {
  USER_NAME(null),
  USER_PASSWORD(null),
  PREFERRED_CURRENCY(getDefaultCurrencyCode()),
  PREFERRED_LOCALE(getDefaultLocale()),
  DEFAULT_ACCOUNT(null),
  LAST_LOGIN_DATE(getCurrentDate()),
  LAST_CSV_FILE_DIR(null);

  private String defaultValue;

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
