package com.bookkeeper.utils;

import static java.time.DayOfWeek.*;
import static java.time.Month.*;
import static java.time.temporal.TemporalAdjusters.*;

import com.bookkeeper.dto.DateRange;

import java.time.LocalDate;
import java.time.Month;

public final class DateRangeUtils {

  private static final Month[] QUARTER_1ST_MONTHS = { JANUARY, APRIL, JULY, OCTOBER };

  private DateRangeUtils() {}

  public static DateRange getWeekBounds(LocalDate date) {
    return DateRange.builder()
        .startDate(date.with(previousOrSame(MONDAY)))
        .endDate(date.with(nextOrSame(SUNDAY)))
        .build();
  }

  public static DateRange getMonthBounds(LocalDate date) {
    return DateRange.builder()
        .startDate(date.with(firstDayOfMonth()))
        .endDate(date.with(lastDayOfMonth()))
        .build();
  }

  public static DateRange getMonthBounds(LocalDate date, Month month) {

    var startDate = (month == null ? date.with(firstDayOfMonth())
        : date.with(month).with(firstDayOfMonth()));

    return DateRange.builder()
        .startDate(startDate)
        .endDate(startDate.with(lastDayOfMonth()))
        .build();
  }

  public static DateRange getQuarterBounds(LocalDate date, Integer quarterNum) {

    boolean validQuarterNum = (quarterNum != null && quarterNum >= 1 && quarterNum <= 4);

    var stagingDate = validQuarterNum ? date.withMonth(QUARTER_1ST_MONTHS[quarterNum - 1].getValue())
        : date.with(date.getMonth().firstMonthOfQuarter());

    var startDate = stagingDate.with(firstDayOfMonth());
    var endDate = startDate.plusMonths(2).with(lastDayOfMonth());

    return new DateRange(startDate, endDate);
  }

  public static DateRange getYearBounds(LocalDate date, boolean completely) {
    return DateRange.builder()
        .startDate(date.with(firstDayOfYear()))
        .endDate(completely ? date.with(lastDayOfYear()) : date)
        .build();
  }
}
