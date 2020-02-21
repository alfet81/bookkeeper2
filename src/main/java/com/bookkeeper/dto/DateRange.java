package com.bookkeeper.dto;

import static java.time.temporal.TemporalAdjusters.lastDayOfMonth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DateRange {

  private LocalDate startDate;
  private LocalDate endDate;

  public boolean isValid() {
    return startDate != null && endDate != null;
  }

  public DateRange minusDays(long days) {
    startDate = startDate.minusDays(days);
    endDate = endDate.minusDays(days);
    return this;
  }

  public DateRange plusDays(long days) {
    startDate = startDate.plusDays(days);
    endDate = endDate.plusDays(days);
    return this;
  }

  public DateRange minusMonths(long months) {

    startDate = startDate.minusMonths(months);

    var maxDays = endDate.getMonth().length(endDate.isLeapYear());
    var lastDay = (endDate.getDayOfMonth() == maxDays);

    endDate = endDate.minusMonths(months);

    endDate = lastDay ? endDate.with(lastDayOfMonth()) : endDate;

    return this;
  }

  public DateRange plusMonths(long months) {

    startDate = startDate.plusMonths(months);

    var maxDays = endDate.getMonth().length(endDate.isLeapYear());
    var lastDay = (endDate.getDayOfMonth() == maxDays);

    endDate = endDate.plusMonths(months);

    endDate = lastDay ? endDate.with(lastDayOfMonth()) : endDate;

    return this;
  }

  public DateRange minusYears(long years) {
    startDate = startDate.minusYears(years);
    endDate = endDate.minusYears(years);
    return this;
  }

  public DateRange plusYears(long years) {
    startDate = startDate.plusYears(years);
    endDate = endDate.plusYears(years);
    return this;
  }
}
