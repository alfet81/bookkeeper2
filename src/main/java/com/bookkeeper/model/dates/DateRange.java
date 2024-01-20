package com.bookkeeper.model.dates;

import static java.time.temporal.TemporalAdjusters.lastDayOfMonth;

import java.time.LocalDate;

public record DateRange(LocalDate startDate, LocalDate endDate) {

  public static DateRange empty() {
    return new DateRange(null, null);
  }

  public boolean isValid() {
    return startDate != null && endDate != null;
  }

  public DateRange minusDays(long days) {
    return new DateRange(startDate.minusDays(days), endDate.minusDays(days));
  }

  public DateRange plusDays(long days) {
    return new DateRange(startDate.plusDays(days), endDate.plusDays(days));
  }

  private boolean isLastDayOfMonth() {
    var maxDays = endDate.getMonth().length(endDate.isLeapYear());
    return endDate.getDayOfMonth() == maxDays;
  }

  public DateRange minusMonths(long months) {

    var newStartDate = startDate.minusMonths(months);

    var newEndDate = endDate.minusMonths(months);

    newEndDate = (isLastDayOfMonth() ? newEndDate.with(lastDayOfMonth()) : newEndDate);

    return new DateRange(newStartDate, newEndDate);
  }

  public DateRange plusMonths(long months) {

    var newStartDate = startDate.plusMonths(months);

    var newEndDate = endDate.plusMonths(months);

    newEndDate = isLastDayOfMonth() ? newEndDate.with(lastDayOfMonth()) : newEndDate;

    return new DateRange(newStartDate, newEndDate);
  }

  public DateRange minusYears(long years) {
    return new DateRange(startDate.minusYears(years), endDate.minusYears(years));
  }

  public DateRange plusYears(long years) {
    return new DateRange(startDate.plusYears(years), endDate.plusYears(years));
  }
}
