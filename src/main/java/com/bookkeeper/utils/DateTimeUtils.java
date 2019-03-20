package com.bookkeeper.utils;

import java.time.LocalDate;
import java.util.Date;

public class DateTimeUtils {

  public static Date convert2LegacyDate(LocalDate date) {
    return date != null ? new Date(date.toEpochDay()) : null;
  }
}
