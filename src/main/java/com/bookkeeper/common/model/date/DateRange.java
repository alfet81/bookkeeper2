package com.bookkeeper.common.model.date;

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

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Getter
@EqualsAndHashCode
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class DateRange {

  private static final List<Month> QUARTER_1ST_MONTHS = List.of(JANUARY, APRIL, JULY, OCTOBER);
  private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE;

  private final LocalDate startDate;
  private final LocalDate endDate;


  public static DateRange of(LocalDate startDate, LocalDate endDate) {
    return new DateRange(startDate, endDate);
  }


  public static DateRange none() {
    return DateRange.of(null, null);
  }


  public static DateRange week(LocalDate date) {
    var startDate = date.with(previousOrSame(MONDAY));
    var endDate = date.with(nextOrSame(SUNDAY));
    return DateRange.of(startDate, endDate);
  }


  public static DateRange month(LocalDate date) {
    var startDate = date.with(firstDayOfMonth());
    var endDate = date.with(lastDayOfMonth());
    return DateRange.of(startDate, endDate);
  }


  public static DateRange month(LocalDate date, Month month) {
    return month != null ? month(date.with(month)) : month(date);
  }


  public static DateRange quarter(LocalDate date) {
    var stagingDate = date.with(date.getMonth().firstMonthOfQuarter());
    var startDate = stagingDate.with(firstDayOfMonth());
    var endDate = startDate.plusMonths(2).with(lastDayOfMonth());
    return DateRange.of(startDate, endDate);
  }


  public static DateRange quarter(LocalDate date, Integer quarterNum) {
    if (quarterNum == null || quarterNum < 1 || quarterNum > 4) {
      throw new IllegalArgumentException("quarterNum must be between 1 and 4");
    }
    int index = (quarterNum - 1) % 4;
    return getYearQuarters(date.getYear()).get(index);
  }


  public static DateRange year(LocalDate date, boolean fullYear) {
    var startDate = date.with(firstDayOfYear());
    var endDate = (fullYear ? date.with(lastDayOfYear()) : date);
    return DateRange.of(startDate, endDate);
  }


  public static List<DateRange> getYearQuarters(int year) {
    var date = LocalDate.now().withYear(year);
    return QUARTER_1ST_MONTHS.stream()
                             .map(Month::getValue)
                             .map(monthNum -> getMonthQuarter(date, monthNum)).toList();
  }


  private static DateRange getMonthQuarter(LocalDate date, int monthNum) {
    var startDate = date.withMonth(monthNum).with(firstDayOfMonth());
    var endDate = startDate.plusMonths(2).with(lastDayOfMonth());
    return DateRange.of(startDate, endDate);
  }


  private static String formatDate(LocalDate date) {
    return date != null ? DATE_FORMATTER.format(date) : null;
  }


  public boolean isComplete() {
    return startDate != null && endDate != null;
  }


  public boolean isEmpty() {
    return startDate == null && endDate == null;
  }


  public DateRange minusDays(long days) {
    return DateRange.of(startDate.minusDays(days), endDate.minusDays(days));
  }


  public DateRange plusDays(long days) {
    return DateRange.of(startDate.plusDays(days), endDate.plusDays(days));
  }


  private boolean isLastDayOfMonth() {
    var maxDays = endDate.getMonth().length(endDate.isLeapYear());
    return endDate.getDayOfMonth() == maxDays;
  }


  public DateRange minusMonths(long months) {
    var newStartDate = startDate.minusMonths(months);
    var newEndDate = endDate.minusMonths(months);
    newEndDate = (isLastDayOfMonth() ? newEndDate.with(lastDayOfMonth()) : newEndDate);
    return DateRange.of(newStartDate, newEndDate);
  }


  public DateRange plusMonths(long months) {
    var newStartDate = startDate.plusMonths(months);
    var newEndDate = endDate.plusMonths(months);
    newEndDate = isLastDayOfMonth() ? newEndDate.with(lastDayOfMonth()) : newEndDate;
    return DateRange.of(newStartDate, newEndDate);
  }


  public DateRange minusYears(long years) {
    return DateRange.of(startDate.minusYears(years), endDate.minusYears(years));
  }


  public DateRange plusYears(long years) {
    return DateRange.of(startDate.plusYears(years), endDate.plusYears(years));
  }


  public boolean contains(LocalDate date) {
    return date != null && isComplete() && ((startDate.isBefore(date) || startDate.equals(date))
        && (endDate.isAfter(date) || endDate.equals(date)));
  }


  @Override
  public String toString() {
    return "[" + formatDate(startDate) + ":" + formatDate(endDate) + "]";
  }
}
