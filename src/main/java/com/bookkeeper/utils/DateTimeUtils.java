package com.bookkeeper.utils;

import static com.bookkeeper.common.utils.Constants.SHORT_DATE_FORMATTER;

import lombok.experimental.UtilityClass;

import java.time.LocalDate;
import java.util.Date;

@UtilityClass
public class DateTimeUtils {

  public static Date convert2LegacyDate(LocalDate date) {
    return date != null ? new Date(date.toEpochDay()) : null;
  }


  public static String date2String(LocalDate date) {
    return date != null ? date.format(SHORT_DATE_FORMATTER) : "N/A";
  }


  public static LocalDate string2Date(String date) {
    try {
      return LocalDate.parse(date, SHORT_DATE_FORMATTER);
    } catch (Exception ignored) {
    }
    return null;
  }
}
