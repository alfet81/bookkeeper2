package com.bookkeeper.utils;

import static com.bookkeeper.app.AppConstants.SHORT_DATE_FORMATTER;

import java.time.LocalDate;
import java.util.Date;

public class DateTimeUtils {

  public static Date convert2LegacyDate(LocalDate date) {
    return date != null ? new Date(date.toEpochDay()) : null;
  }

  public static String date2String(LocalDate date) {
    return date.format(SHORT_DATE_FORMATTER);
  }
}
