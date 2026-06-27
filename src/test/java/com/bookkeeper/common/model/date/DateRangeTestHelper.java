package com.bookkeeper.common.model.date;

import static com.bookkeeper.common.model.date.DateRange.getYearQuarters;
import static java.time.DayOfWeek.MONDAY;
import static java.time.DayOfWeek.SUNDAY;
import static java.time.temporal.TemporalAdjusters.firstDayOfMonth;
import static java.time.temporal.TemporalAdjusters.firstDayOfYear;
import static java.time.temporal.TemporalAdjusters.lastDayOfMonth;
import static java.time.temporal.TemporalAdjusters.lastDayOfYear;
import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toSet;

import com.bookkeeper.common.model.date.DateRange;
import lombok.experimental.UtilityClass;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.util.EnumSet;
import java.util.Objects;
import java.util.Set;
import java.util.function.BiPredicate;
import java.util.function.Supplier;
import java.util.stream.Stream;

@UtilityClass
class DateRangeTestHelper {

  static final BiPredicate<LocalDate, DateRange> MONTH_BOUNDS_VALIDATION_PREDICATE = (date, dateRange) -> {
    var firstDayOfMonth = date.with(firstDayOfMonth());
    var lastDayOfMonth = date.with(lastDayOfMonth());
    var expectedDateRange = DateRange.of(firstDayOfMonth, lastDayOfMonth);
    boolean validDateRange = dateRange.isComplete();
    boolean validMonthBounds = dateRange.equals(expectedDateRange);
    return validDateRange && validMonthBounds;
  };
  static final BiPredicate<LocalDate, DateRange> QUARTER_BOUNDS_VALIDATION_PREDICATE = (date, dateRange) -> {
    Month currentQuarterMonth = date.getMonth().firstMonthOfQuarter();
    var firstDayOfQuarter = date.withMonth(currentQuarterMonth.getValue()).with(firstDayOfMonth());
    var lastDayOfQuarter = firstDayOfQuarter.plusMonths(2).with(lastDayOfMonth());
    var expectedQuarterBounds = DateRange.of(firstDayOfQuarter, lastDayOfQuarter);

    boolean validDateRange = dateRange.isComplete();
    boolean validQuarterBounds = dateRange.equals(expectedQuarterBounds);
    boolean hasDate = dateRange.contains(date);
    return validDateRange && validQuarterBounds && hasDate;
  };
  static final BiPredicate<LocalDate, DateRange> YEAR_BOUNDS_VALIDATION_PREDICATE = (date, dateRange) -> {
    var expectedYearStartDate = date.with(firstDayOfYear());
    var expectedDateRange = DateRange.of(expectedYearStartDate, date);

    boolean validDateRange = dateRange.isComplete();
    boolean validYearBounds = dateRange.equals(expectedDateRange);
    boolean hasDate = dateRange.contains(date);
    return validDateRange && validYearBounds && hasDate;
  };
  static final BiPredicate<LocalDate, DateRange> PRIOR_YEAR_BOUNDS_VALIDATION_PREDICATE = (date, dateRange) -> {
    var startDate = date.minusYears(1).with(firstDayOfYear());
    var endDate = startDate.with(lastDayOfYear());
    boolean validDateRange = dateRange.isComplete();
    boolean validYearBounds = dateRange.equals(DateRange.of(startDate, endDate));
    return validDateRange && validYearBounds;
  };
  private static final Supplier<LocalDate> CURRENT_DATE_SUPPLIER = LocalDate::now;
  private static final Set<DayOfWeek> EXPECTED_WEEK_BOUNDS = EnumSet.of(MONDAY, SUNDAY);
  static final BiPredicate<LocalDate, DateRange> WEEK_BOUNDS_VALIDATION_PREDICATE = (date, dateRange) -> {
    var actualWeekBounds = getDayOfWeekBounds(dateRange);
    boolean validDateRange = dateRange.isComplete();
    boolean validWeekBounds = actualWeekBounds.equals(EXPECTED_WEEK_BOUNDS);
    return validDateRange && validWeekBounds;
  };


  static BiPredicate<LocalDate, DateRange> getMonthBoundsValidationPredicate(Month month) {
    return (date, dateRange) -> {
      var startDate = date.withMonth(month.getValue()).with(firstDayOfMonth());
      var endDate = startDate.with(lastDayOfMonth());
      boolean validDateRange = dateRange.isComplete();
      boolean validMonthBounds = dateRange.equals(DateRange.of(startDate, endDate));
      return validDateRange && validMonthBounds;
    };
  }


  static BiPredicate<LocalDate, DateRange> getQuarterBoundsValidationPredicate(Integer quarterNum) {
    return (date, dateRange) -> {
      int index = normalizeYearQuarterIndex(quarterNum);
      var expectedDateRange = getYearQuarters(date.getYear()).get(index);
      boolean validDateRange = dateRange.isComplete();
      boolean validQuarterBounds = dateRange.equals(expectedDateRange);
      return validDateRange && validQuarterBounds;
    };
  }


  private static int normalizeYearQuarterIndex(Integer quarterIndex) {
    return (quarterIndex != null && quarterIndex > 0 ? (quarterIndex - 1) % 4 : 0);
  }


  private static Set<DayOfWeek> getDayOfWeekBounds(DateRange dateRange) {
    return Stream.of(dateRange.getStartDate(), dateRange.getEndDate())
                 .filter(Objects::nonNull)
                 .map(LocalDate::getDayOfWeek)
                 .collect(collectingAndThen(toSet(), EnumSet::copyOf));
  }


  static LocalDate getCurrentDate() {
    return CURRENT_DATE_SUPPLIER.get();
  }
}
