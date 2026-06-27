package com.bookkeeper.common.model.date;

import static java.time.LocalDate.now;
import static java.time.Month.APRIL;
import static java.time.Month.AUGUST;
import static java.time.Month.DECEMBER;
import static java.time.Month.FEBRUARY;
import static java.time.Month.JANUARY;
import static java.time.Month.JULY;
import static java.time.Month.JUNE;
import static java.time.Month.MARCH;
import static java.time.Month.NOVEMBER;
import static java.time.Month.OCTOBER;
import static java.time.Month.SEPTEMBER;

import lombok.RequiredArgsConstructor;
import java.time.Month;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Stream;

@RequiredArgsConstructor
public enum DateRangeType {
  CURRENT_WEEK(currentWeek()),
  CURRENT_MONTH(month()),
  CURRENT_QUARTER(quarter()),
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


  private static Supplier<DateRange> currentWeek() {
    return () -> DateRange.week(now());
  }


  private static Supplier<DateRange> month() {
    return () -> DateRange.month(now());
  }


  private static Supplier<DateRange> month(Month month) {
    return () -> DateRange.month(now(), month);
  }


  private static Supplier<DateRange> quarter() {
    return () -> DateRange.quarter(now());
  }


  private static Supplier<DateRange> quarter(Integer number) {
    return () -> DateRange.quarter(now(), number);
  }


  private static Supplier<DateRange> currentYear() {
    return () -> DateRange.year(now(), false);
  }


  private static Supplier<DateRange> priorYear() {
    return () -> DateRange.year(now().minusYears(1), true);
  }


  private static Supplier<DateRange> none() {
    return DateRange::none;
  }


  static Optional<DateRangeType> getDateRangeType(String prefix) {
    return Stream.of(values())
        .filter(type -> type.name().startsWith(prefix))
        .findAny();
  }


  public DateRange getDateRange() {
    return supplier.get();
  }
}
