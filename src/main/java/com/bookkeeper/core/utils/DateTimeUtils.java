package com.bookkeeper.core.utils;

import static java.time.DayOfWeek.MONDAY;
import static java.time.Month.JANUARY;

import com.bookkeeper.core.type.DateRange;

import java.time.LocalDate;
import java.util.stream.Stream;

public class DateTimeUtils {

  public static DateRange getWeekBounds(LocalDate date) {

    if (date == null) {
      return null;
    }

    while(date.getDayOfWeek() != MONDAY)
    {
      date = date.minusDays(1);
    }

    return new DateRange(date, date.plusDays(6));
  }

  public static DateRange getMonthBounds(LocalDate date) {

    if(date == null) {
      return null;
    }

    var monthStart = date.withDayOfMonth(1);
    var monthEnd = monthStart.plusMonths(1).minusDays(1);

    return new DateRange(monthStart, monthEnd);
  }

  private static DateRange[] prepareQuarters(int year) {

    if (year < 1970) {
      throw new IllegalArgumentException("Year can't be less than 1970");
    }

    var quarters = new DateRange[4];

    var lower = LocalDate.of(year, JANUARY, 1);

    for (int i = 0; i < quarters.length; i++) {

      var upper = lower.plusMonths(3).minusDays(1);

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

  public static DateRange getYearBounds(LocalDate date) {

    if (date == null) {
      return null;
    }

    var yearStart = date.withDayOfYear(1);
    var yearEnd = yearStart.plusYears(1).minusDays(1);

    return new DateRange(yearStart, yearEnd);
  }
}
