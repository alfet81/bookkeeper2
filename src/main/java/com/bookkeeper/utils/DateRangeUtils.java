package com.bookkeeper.utils;

import static java.time.DayOfWeek.MONDAY;
import static java.time.DayOfWeek.SUNDAY;
import static java.time.Month.APRIL;
import static java.time.Month.JANUARY;
import static java.time.Month.JULY;
import static java.time.Month.OCTOBER;
import static java.time.temporal.TemporalAdjusters.firstDayOfMonth;
import static java.time.temporal.TemporalAdjusters.firstDayOfYear;
import static java.time.temporal.TemporalAdjusters.lastDayOfMonth;
import static java.time.temporal.TemporalAdjusters.lastDayOfYear;
import static java.time.temporal.TemporalAdjusters.nextOrSame;
import static java.time.temporal.TemporalAdjusters.previousOrSame;

import com.bookkeeper.model.dates.DateRange;

import java.time.LocalDate;
import java.time.Month;

public final class DateRangeUtils {

  private static final Month[] QUARTER_1ST_MONTHS = {JANUARY, APRIL, JULY, OCTOBER};

  private DateRangeUtils() {
  }

  public static DateRange getWeekBounds(LocalDate date) {
    return new DateRange(date.with(previousOrSame(MONDAY)), date.with(nextOrSame(SUNDAY)));
  }

  public static DateRange getMonthBounds(LocalDate date) {
    return new DateRange(date.with(firstDayOfMonth()), date.with(lastDayOfMonth()));
  }

  public static DateRange getMonthBounds(LocalDate date, Month month) {

    var startDate = (month == null ? date.with(firstDayOfMonth())
      : date.with(month).with(firstDayOfMonth()));

    return new DateRange(startDate, startDate.with(lastDayOfMonth()));
  }

  public static DateRange getQuarterBounds(LocalDate date, Integer quarterNum) {

    boolean validQuarterNum = (quarterNum != null && quarterNum >= 1 && quarterNum <= 4);

    var stagingDate =
      validQuarterNum ? date.withMonth(QUARTER_1ST_MONTHS[quarterNum - 1].getValue())
        : date.with(date.getMonth().firstMonthOfQuarter());

    var startDate = stagingDate.with(firstDayOfMonth());
    var endDate = startDate.plusMonths(2).with(lastDayOfMonth());

    return new DateRange(startDate, endDate);
  }

  public static DateRange getYearBounds(LocalDate date, boolean wholeYear) {
    var endDate = (wholeYear ? date.with(lastDayOfYear()) : date);
    return new DateRange(date.with(firstDayOfYear()), endDate);
  }
}
