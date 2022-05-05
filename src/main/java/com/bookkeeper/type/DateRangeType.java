package com.bookkeeper.type;

import static com.bookkeeper.utils.DateRangeUtils.*;
import static java.time.LocalDate.now;
import static java.time.Month.*;

import com.bookkeeper.dto.DateRange;

import lombok.RequiredArgsConstructor;
import java.time.Month;
import java.util.function.Supplier;

@RequiredArgsConstructor
public enum DateRangeType {
  CURRENT_WEEK(currentWeek()),
  CURRENT_MONTH(month(null)),
  CURRENT_QUARTER(quarter(null)),
  CURRENT_YEAR(currentYear()),
  JAN(month(JANUARY)),
  FEB(month(FEBRUARY)),
  MAR(month(MARCH)),
  APR(month(APRIL)),
  MAY(month(Month.MAY)),
  JUN(month(JUNE)),
  JUL(month(JULY)),
  AUG(month(AUGUST)),
  SEP(month(SEPTEMBER)),
  OCT(month(OCTOBER)),
  NOV(month(NOVEMBER)),
  DEC(month(DECEMBER)),
  QUARTER_1(quarter(1)),
  QUARTER_2(quarter(2)),
  QUARTER_3(quarter(3)),
  QUARTER_4(quarter(4)),
  PRIOR_YEAR(priorYear()),
  ALL(none());

  private final Supplier<DateRange> supplier;

  public DateRange getDateRange() {
    return supplier.get();
  }

  private static Supplier<DateRange> currentWeek() {
    return () -> getWeekBounds(now());
  }

  private static Supplier<DateRange> month(Month month) {
    return () -> getMonthBounds(now(), month);
  }

  private static Supplier<DateRange> quarter(Integer number) {
    return () -> getQuarterBounds(now(), number);
  }

  private static Supplier<DateRange> currentYear() {
    return () -> getYearBounds(now(), false);
  }

  private static Supplier<DateRange> priorYear() {
    return () -> getYearBounds(now().minusYears(1), true);
  }

  private static Supplier<DateRange> none() {
    return DateRange::new;
  }
}
