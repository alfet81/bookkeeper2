package com.bookkeeper.type;

import static com.bookkeeper.utils.DateRangeUtils.getMonthBounds;
import static java.time.LocalDate.now;

import com.bookkeeper.dto.DateRange;

import lombok.RequiredArgsConstructor;
import java.util.function.Function;

@RequiredArgsConstructor
public enum DateRangeShifterType {
  PREV_YEAR(prevYear()),
  NEXT_YEAR(nextYear()),
  PREV_MONTH(prevMonth()),
  NEXT_MONTH(nextMonth()),
  CURRENT_MONTH(currentMonth());

  private final Function<DateRange, DateRange> function;

  public DateRange shiftDateRange(DateRange dateRange) {
    return function.apply(dateRange);
  }

  private static Function<DateRange, DateRange> prevMonth() {
    return dateRange -> dateRange.minusMonths(1);
  }

  private static Function<DateRange, DateRange> nextMonth() {
    return dateRange -> dateRange.plusMonths(1);
  }

  private static Function<DateRange, DateRange> prevYear() {
    return dateRange -> dateRange.minusYears(1);
  }

  private static Function<DateRange, DateRange> nextYear() {
    return dateRange -> dateRange.plusYears(1);
  }

  private static Function<DateRange, DateRange> currentMonth() {
    return dateRange -> getMonthBounds(now());
  }
}
