package com.bookkeeper.common.model.date;

import static com.bookkeeper.common.model.date.DateRangeTestHelper.MONTH_BOUNDS_VALIDATION_PREDICATE;
import static com.bookkeeper.common.model.date.DateRangeTestHelper.PRIOR_YEAR_BOUNDS_VALIDATION_PREDICATE;
import static com.bookkeeper.common.model.date.DateRangeTestHelper.QUARTER_BOUNDS_VALIDATION_PREDICATE;
import static com.bookkeeper.common.model.date.DateRangeTestHelper.WEEK_BOUNDS_VALIDATION_PREDICATE;
import static com.bookkeeper.common.model.date.DateRangeTestHelper.YEAR_BOUNDS_VALIDATION_PREDICATE;
import static com.bookkeeper.common.model.date.DateRangeTestHelper.getCurrentDate;
import static com.bookkeeper.common.model.date.DateRangeTestHelper.getQuarterBoundsValidationPredicate;
import static org.junit.jupiter.params.provider.Arguments.arguments;

import static java.time.format.TextStyle.FULL;

import lombok.experimental.UtilityClass;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

import java.time.LocalDate;
import java.time.Month;
import java.time.Period;
import java.util.Locale;
import java.util.stream.Stream;

@UtilityClass
class DateRangeTestData {

  private String getMonthDisplayName(Month month) {
    return month.getDisplayName(FULL, Locale.getDefault());
  }


  private String getYearType(LocalDate date) {
    return date.isLeapYear() ? "(leap)" : "(non leap)";
  }


  private Arguments getWeekDateRangeArgs(LocalDate date, Month month) {
    return arguments(getMonthDisplayName(month), date.with(month), getYearType(date),
        DateRange.week(date.with(month)), WEEK_BOUNDS_VALIDATION_PREDICATE);
  }


  private Arguments getSuppliedYearMonthDateRangeArgs(LocalDate date, Month month) {
    return arguments(month, getMonthDisplayName(month), date.getYear(), getYearType(date),
        date.withMonth(month.getValue()), DateRange.month(date.withMonth(month.getValue())),
        MONTH_BOUNDS_VALIDATION_PREDICATE);
  }


  private Arguments getQuarterDateRangeArgs(LocalDate date, Month month) {
    return arguments(month, getMonthDisplayName(month), date.getYear(),
        date.withMonth(month.getValue()), DateRange.quarter(date.withMonth(month.getValue())),
        QUARTER_BOUNDS_VALIDATION_PREDICATE);
  }


  class WeekDateRangeArgsProvider implements ArgumentsProvider {
    @Override
    public Stream<Arguments> provideArguments(ExtensionContext extensionContext) {
      var date = getCurrentDate();
      return Stream.of(Month.values())
          .map(month -> getWeekDateRangeArgs(date, month));

    }
  }

  class NonLeapYearMonthDateRangeArgsProvider implements ArgumentsProvider {
    @Override
    public Stream<Arguments> provideArguments(ExtensionContext extensionContext) {
      var date = getCurrentDate().withYear(2025);
      return Stream.of(Month.values())
          .map(month -> getSuppliedYearMonthDateRangeArgs(date, month));

    }
  }

  class LeapYearMonthDateRangeArgsProvider implements ArgumentsProvider {
    @Override
    public Stream<Arguments> provideArguments(ExtensionContext extensionContext) {
      var date = getCurrentDate().withYear(2024);
      return Stream.of(Month.values())
          .map(month -> getSuppliedYearMonthDateRangeArgs(date, month));
    }
  }

  class ValidQuarterNumberDateRangeArgsProvider implements ArgumentsProvider {
    @Override
    public Stream<Arguments> provideArguments(ExtensionContext extensionContext) {
      var date = getCurrentDate();
      return Stream.of(
          arguments(1, date, DateRange.quarter(date, 1), getQuarterBoundsValidationPredicate(1)),
          arguments(2, date, DateRange.quarter(date, 2), getQuarterBoundsValidationPredicate(2)),
          arguments(3, date, DateRange.quarter(date, 3), getQuarterBoundsValidationPredicate(3)),
          arguments(4, date, DateRange.quarter(date, 4), getQuarterBoundsValidationPredicate(4))
      );
    }
  }


  class InvalidQuarterNumberDateRangeArgsProvider implements ArgumentsProvider {
    @Override
    public Stream<Arguments> provideArguments(ExtensionContext extensionContext) {
      var date = getCurrentDate();
      return Stream.of(
          arguments(null, date.withMonth(2)),
          arguments(-1, date.withMonth(5)),
          arguments(0, date.withMonth(8)),
          arguments(5, date.withMonth(11))
      );
    }
  }

  class QuarterDateRangeArgsProviderByDateOnly implements ArgumentsProvider {
    @Override
    public Stream<Arguments> provideArguments(ExtensionContext extensionContext) {
      var date = getCurrentDate();
      return Stream.of(Month.values())
          .map(month -> getQuarterDateRangeArgs(date, month));
    }
  }

  class YearDateRangeArgsProvider implements ArgumentsProvider {
    @Override
    public Stream<Arguments> provideArguments(ExtensionContext extensionContext) {
      var date = getCurrentDate();
      return Stream.of(
          arguments("Current year of ", date, DateRange.year(date, false),
              YEAR_BOUNDS_VALIDATION_PREDICATE),
          arguments("Prior year of ", date, DateRange.year(date.minusYears(1), true),
              PRIOR_YEAR_BOUNDS_VALIDATION_PREDICATE)
      );
    }
  }


  class ModifiersTestDataArgsProvider implements ArgumentsProvider {

    @Override
    public Stream<Arguments> provideArguments(ExtensionContext extensionContext) {
      var date = getCurrentDate();
      var dateRange = DateRange.week(date);
      return Stream.of(
          //minus
          arguments(dateRange, dateRange.minusDays(2),
              Period.between(dateRange.minusDays(2).getStartDate(), dateRange.getStartDate())
                  .getDays(), 2, "days (startDate)"),
          arguments(dateRange, dateRange.minusDays(2),
              Period.between(dateRange.minusDays(2).getEndDate(), dateRange.getEndDate()).getDays(),
              2, "days (endDate)"),
          arguments(dateRange, dateRange.minusMonths(3),
              Period.between(dateRange.minusMonths(3).getStartDate(), dateRange.getStartDate())
                  .getMonths(), 3, "months (startDate)"),
          arguments(dateRange, dateRange.minusMonths(3),
              Period.between(dateRange.minusMonths(3).getEndDate(), dateRange.getEndDate())
                  .getMonths(), 3, "months (endDate)"),
          arguments(dateRange, dateRange.minusYears(1),
              Period.between(dateRange.minusYears(1).getStartDate(), dateRange.getStartDate())
                  .getYears(), 1, "years (startDate)"),
          arguments(dateRange, dateRange.minusYears(1),
              Period.between(dateRange.minusYears(1).getEndDate(), dateRange.getEndDate())
                  .getYears(), 1, "years (endDate)"),

          //plus
          arguments(dateRange, dateRange.plusDays(4),
              Period.between(dateRange.getStartDate(), dateRange.plusDays(4).getStartDate())
                  .getDays(), 4, "days (startDate)"),
          arguments(dateRange, dateRange.plusDays(4),
              Period.between(dateRange.getEndDate(), dateRange.plusDays(4).getEndDate()).getDays(),
              4, "days (endDate)"),
          arguments(dateRange, dateRange.plusMonths(6),
              Period.between(dateRange.getStartDate(), dateRange.plusMonths(6).getStartDate())
                  .getMonths(), 6, "months (startDate)"),
          arguments(dateRange, dateRange.plusMonths(6),
              Period.between(dateRange.getEndDate(), dateRange.plusMonths(6).getEndDate())
                  .getMonths(), 6, "months (endDate)"),
          arguments(dateRange, dateRange.plusYears(10),
              Period.between(dateRange.getStartDate(), dateRange.plusYears(10).getStartDate())
                  .getYears(), 10, "years (startDate)"),
          arguments(dateRange, dateRange.plusYears(10),
              Period.between(dateRange.getEndDate(), dateRange.plusYears(10).getEndDate())
                  .getYears(), 10, "years (endDate)")
      );
    }
  }
}
