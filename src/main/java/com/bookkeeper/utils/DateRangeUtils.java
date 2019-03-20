package com.bookkeeper.utils;

import static java.time.DayOfWeek.MONDAY;
import static java.time.LocalDate.now;
import static java.time.Month.JANUARY;

import com.bookkeeper.exceptions.BookkeeperException;
import com.bookkeeper.types.DateRange;
import com.bookkeeper.types.DateRangeType;

import java.time.LocalDate;
import java.time.Month;
import java.time.Period;
import java.util.stream.Stream;

public class DateRangeUtils {

  private static final Period PERIOD_OF_WEEK = Period.ofDays(6);

  private static final Period PERIOD_OF_MONTH = Period.of(0, 1, -1);

  private static final Period PERIOD_OF_QUARTER = Period.of(0, 3, -1);

  private static final Period PERIOD_OF_YEAR = Period.of(1, 0, -1);

  public static DateRange getWeekBounds(LocalDate date) {

    if (date == null) {
      return null;
    }

    int days = date.getDayOfWeek().ordinal() - MONDAY.ordinal();

    date = date.minusDays(days);

    return new DateRange(date, date.plus(PERIOD_OF_WEEK));
  }

  public static DateRange getMonthBounds(LocalDate date) {

    if(date == null) {
      return null;
    }

    var monthStart = date.withDayOfMonth(1);
    var monthEnd = monthStart.plus(PERIOD_OF_MONTH);

    return new DateRange(monthStart, monthEnd);
  }

  public static DateRange getMonthBounds(LocalDate date, Month month) {

    if(date == null || month == null) {
      return null;
    }

    date = date.withMonth(month.getValue());

    var monthStart = date.withDayOfMonth(1);
    var monthEnd = monthStart.plus(PERIOD_OF_MONTH);

    return new DateRange(monthStart, monthEnd);
  }

  private static DateRange[] prepareQuarters(int year) {

    if (year < 1970) {
      throw new IllegalArgumentException("Year can't be less than 1970");
    }

    var quarters = new DateRange[4];

    var lower = LocalDate.of(year, JANUARY, 1);

    for (int i = 0; i < quarters.length; i++) {

      var upper = lower.plus(PERIOD_OF_QUARTER);

      quarters[i] = new DateRange(lower, upper);

      lower = upper.plusDays(1);
    }

    return quarters;
  }

  public static DateRange getQuarterBounds(LocalDate date, Integer quarterNumber) {

    if (date == null) {
      return null;
    }

    var quarters = prepareQuarters(date.getYear());

    var isValidQuarter = quarterNumber != null && quarterNumber >= 1 && quarterNumber <= 4;

    return isValidQuarter ? quarters[quarterNumber - 1] :
        Stream.of(quarters).filter(quarter -> quarter.contains(date)).findFirst().orElse(null);
  }

  public static DateRange getYearBounds(LocalDate date, boolean fully) {

    if (date == null) {
      return null;
    }

    var yearStart = date.withDayOfYear(1);
    var yearEnd = fully ? yearStart.plus(PERIOD_OF_YEAR) : date;

    return new DateRange(yearStart, yearEnd);
  }

  public static DateRange getDateRange(DateRangeType type) {
    switch (type) {
      case CURRENT_WEEK:
        return getWeekBounds(now());
      case CURRENT_MONTH:
        return getMonthBounds(now());
      case CURRENT_QUARTER:
        return getQuarterBounds(now(), null);
      case CURRENT_YEAR:
        return getYearBounds(now(), false);
      case JAN:
      case FEB:
      case MAR:
      case APR:
      case MAY:
      case JUN:
      case JUL:
      case AUG:
      case SEP:
      case OCT:
      case NOV:
      case DEC:
        return getMonthBounds(now(), Month.of(type.getNum()));
      case QUARTER_1:
      case QUARTER_2:
      case QUARTER_3:
      case QUARTER_4:
        return getQuarterBounds(now(), type.getNum());
      case PRIOR_YEAR:
        return getYearBounds(now().minusYears(1), true);
      case ALL:
        return null;
    }
    throw new BookkeeperException("Unsupported type: " + type);
  }
}
