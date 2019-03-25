package com.bookkeeper.utils;

import static java.time.DayOfWeek.MONDAY;
import static java.time.LocalDate.now;
import static java.time.Month.JANUARY;

import com.bookkeeper.exceptions.BookkeeperException;
import com.bookkeeper.dto.DateRange;
import com.bookkeeper.type.DateRangeType;

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

  public static DateRange getDateRangeByType(DateRangeType type) {
    return switch (type) {
      case CURRENT_WEEK -> getWeekBounds(now());
      case CURRENT_MONTH -> getMonthBounds(now());
      case CURRENT_QUARTER -> getQuarterBounds(now(), null);
      case CURRENT_YEAR -> getYearBounds(now(), false);
      case JAN, FEB, MAR,APR, MAY, JUN,
          JUL, AUG, SEP, OCT, NOV, DEC -> getMonthBounds(now(), Month.of(type.getValue()));
      case QUARTER_1, QUARTER_2, QUARTER_3, QUARTER_4 -> getQuarterBounds(now(), type.getValue());
      case PRIOR_YEAR -> getYearBounds(now().minusYears(1), true);
      default -> new DateRange();
    };
  }
}
