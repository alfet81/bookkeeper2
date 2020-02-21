package com.bookkeeper.utils;

import static java.time.DayOfWeek.MONDAY;
import static java.time.DayOfWeek.SUNDAY;
import static java.time.LocalDate.now;
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

import com.bookkeeper.dto.DateRange;
import com.bookkeeper.type.DateRangeCategory;
import com.bookkeeper.type.DateRangeShiftAction;
import com.bookkeeper.type.DateRangeType;

import java.time.LocalDate;
import java.time.Month;
import java.util.function.Function;

public final class DateRangeUtils {

  private static final Month[] QUARTER_1ST_MONTHS = { JANUARY, APRIL, JULY, OCTOBER };

  private static final Object[][] SHIFT_ACTION_MATRIX = {
      //PREV_DBL, PREV_SNGL, NEXT_SNGL, NEXT_DBL, CURRENT
      { prevYear(), prevMonth(), nextMonth(), nextYear(), currentMonth() },//NONE
      { prevWeek(), prevWeek(), nextWeek(), nextWeek(), currentWeek() },//WEEK
      { prevYear(), prevMonth(), nextMonth(), nextYear(), currentMonth() },//MONTH
      { prevYear(), prevQuarter(), nextQuarter(), nextYear(), currentQuarter() },//QUARTER
      { prevYear(), prevYear(), nextYear(), nextYear(), currentYear() }//YEAR
  };

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
    var startDate = date.with(month).with(firstDayOfMonth());
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

  public static DateRange getDateRangeByType(DateRangeType type) {
    switch (type) {
      case CURRENT_WEEK: return getWeekBounds(now());
      case CURRENT_MONTH: return getMonthBounds(now());
      case CURRENT_QUARTER: return getQuarterBounds(now(), null);
      case CURRENT_YEAR: return getYearBounds(now(), false);
      case JAN: case FEB: case MAR: case APR: case MAY: case JUN:
      case JUL: case AUG: case SEP: case OCT: case NOV: case DEC:
        return getMonthBounds(now(), Month.of(type.getValue()));
      case QUARTER_1: case QUARTER_2: case QUARTER_3: case QUARTER_4:
        return getQuarterBounds(now(), type.getValue());
      case PRIOR_YEAR: return getYearBounds(now().minusYears(1), true);
      default: return new DateRange();
    }
  }

  public static DateRange shiftDateRange(DateRange dateRange, DateRangeCategory category,
      DateRangeShiftAction shiftAction) {
    return getShiftDateRangeFunction(category.ordinal(), shiftAction.ordinal()).apply(dateRange);
  }

  private static Function<DateRange, DateRange> getShiftDateRangeFunction(int row, int col) {
    try {
      return (Function<DateRange, DateRange>) SHIFT_ACTION_MATRIX[row][col];
    } catch (Exception e) {}
    return doNothing();
  }

  private static Function<DateRange, DateRange> doNothing() {
    return dateRange -> dateRange;
  }

  private static Function<DateRange, DateRange> prevWeek() {
    return dateRange -> dateRange.minusDays(7);
  }

  private static Function<DateRange, DateRange> nextWeek() {
    return dateRange -> dateRange.plusDays(7);
  }

  private static Function<DateRange, DateRange> prevMonth() {
    return dateRange -> dateRange.minusMonths(1);
  }

  private static Function<DateRange, DateRange> nextMonth() {
    return dateRange -> dateRange.plusMonths(1);
  }

  private static Function<DateRange, DateRange> prevQuarter() {
    return dateRange -> dateRange.minusMonths(3);
  }

  private static Function<DateRange, DateRange> nextQuarter() {
    return dateRange -> dateRange.plusMonths(3);
  }

  private static Function<DateRange, DateRange> prevYear() {
    return dateRange -> dateRange.minusYears(1);
  }

  private static Function<DateRange, DateRange> nextYear() {
    return dateRange -> dateRange.plusYears(1);
  }

  private static Function<DateRange, DateRange> currentWeek() {
    return dateRange -> getWeekBounds(now());
  }

  private static Function<DateRange, DateRange> currentMonth() {
    return dateRange -> getMonthBounds(now());
  }

  private static Function<DateRange, DateRange> currentQuarter() {
    return dateRange -> getQuarterBounds(now(), null);
  }

  private static Function<DateRange, DateRange> currentYear() {
    return dateRange -> getYearBounds(now(), false);
  }
}
