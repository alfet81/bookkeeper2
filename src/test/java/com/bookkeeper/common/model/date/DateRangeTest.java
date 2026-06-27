package com.bookkeeper.common.model.date;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchException;

import com.bookkeeper.common.model.date.DateRangeTestData.InvalidQuarterNumberDateRangeArgsProvider;
import com.bookkeeper.common.model.date.DateRangeTestData.LeapYearMonthDateRangeArgsProvider;
import com.bookkeeper.common.model.date.DateRangeTestData.ModifiersTestDataArgsProvider;
import com.bookkeeper.common.model.date.DateRangeTestData.NonLeapYearMonthDateRangeArgsProvider;
import com.bookkeeper.common.model.date.DateRangeTestData.QuarterDateRangeArgsProviderByDateOnly;
import com.bookkeeper.common.model.date.DateRangeTestData.ValidQuarterNumberDateRangeArgsProvider;
import com.bookkeeper.common.model.date.DateRangeTestData.WeekDateRangeArgsProvider;
import com.bookkeeper.common.model.date.DateRangeTestData.YearDateRangeArgsProvider;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import java.time.LocalDate;
import java.time.Month;
import java.util.function.BiPredicate;

@TestMethodOrder(OrderAnnotation.class)
class DateRangeTest {

  @Order(1)
  @DisplayName("Test DateRange week bounds")
  @ParameterizedTest(name = "[{index}] => Week of {0} for date {1} {2} yields {3}")
  @ArgumentsSource(WeekDateRangeArgsProvider.class)
  void testDateRange_VerifyWeekBounds(String monthUnderTest, final LocalDate currentDate,
      String yearType, final DateRange actualDateRange,
      BiPredicate<LocalDate, DateRange> validationPredicate) {

    assertThat(validationPredicate.test(currentDate, actualDateRange)).isTrue();
  }


  @Order(2)
  @DisplayName("Test DateRange non leap year months bounds")
  @ParameterizedTest(name = "[{index}] => {1} of year {2} {3} yields {5}")
  @ArgumentsSource(NonLeapYearMonthDateRangeArgsProvider.class)
  void testDateRange_ValidateNonLeapYearMonthBounds(final Month monthUnderTest, String monthName,
      final int yearUnderTest, String yearType, LocalDate currentDate,
      final DateRange actualDateRange, BiPredicate<LocalDate, DateRange> validationPredicate) {
    assertThat(validationPredicate.test(currentDate, actualDateRange)).isTrue();
  }


  @Order(3)
  @DisplayName("Test DateRange leap year months bounds")
  @ParameterizedTest(name = "[{index}] => {1} of year {2} {3} yields {5}")
  @ArgumentsSource(LeapYearMonthDateRangeArgsProvider.class)
  void testDateRange_ValidateLeapYearMonthBounds(final Month monthUnderTest, String monthName,
      final int yearUnderTest, String yearType, LocalDate currentDate,
      final DateRange actualDateRange, BiPredicate<LocalDate, DateRange> validationPredicate) {
    assertThat(validationPredicate.test(currentDate, actualDateRange)).isTrue();
  }


  @Order(4)
  @DisplayName("Test DateRange quarter bounds by date and #")
  @ParameterizedTest(name = "[{index}] => Quarter # {0} of date {1} yields {2}")
  @ArgumentsSource(ValidQuarterNumberDateRangeArgsProvider.class)
  void testDateRange_ValidateQuarterBoundsByDateAndNumber(final Integer quarterUnderTest,
      final LocalDate currentDate, final DateRange actualDateRange,
      BiPredicate<LocalDate, DateRange> validationPredicate) {
    assertThat(validationPredicate.test(currentDate, actualDateRange)).isTrue();
  }


  @Order(5)
  @DisplayName("Test DateRange quarter bounds by date only")
  @ParameterizedTest(name = "[{index}] => {1} of year {2} yields {4}\"")
  @ArgumentsSource(QuarterDateRangeArgsProviderByDateOnly.class)
  void testDateRange_ValidateQuarterBoundsByDateOnly(final Month month, String monthName,
      final int year, LocalDate date, final DateRange dateRange,
      BiPredicate<LocalDate, DateRange> predicate) {
    assertThat(predicate.test(date, dateRange)).isTrue();
  }


  @Order(6)
  @DisplayName("Test DateRange quarter bounds when invalid # used")
  @ParameterizedTest(name = "[{index}] => Quarter # {0} of date {1} yields error msg \"quarterNum must be between 1 and 4\"")
  @ArgumentsSource(InvalidQuarterNumberDateRangeArgsProvider.class)
  void testDateRange_WhenInvalidQuarterNumberUsed(final Integer quarterUnderTest,
      final LocalDate date) {
    var exception = catchException(() -> DateRange.quarter(date, quarterUnderTest));
    assertThat(exception).isInstanceOf(IllegalArgumentException.class)
                         .hasMessage("quarterNum must be between 1 and 4");
  }


  @Order(7)
  @DisplayName("Test DateRange year bounds")
  @ParameterizedTest(name = "[{index}] => {0} {1} yields {2}")
  @ArgumentsSource(YearDateRangeArgsProvider.class)
  void testDateRange_ValidateYearBounds(String prefix, final LocalDate currentDate,
      final DateRange actualDateRange,
      BiPredicate<LocalDate, DateRange> validationPredicate) {
    assertThat(validationPredicate.test(currentDate, actualDateRange)).isTrue();
  }


  @Order(8)
  @ParameterizedTest(name = "[{index}] => Difference between {0} and {1} is {3} {4}")
  @ArgumentsSource(ModifiersTestDataArgsProvider.class)
  void testDateRangeModifiers(DateRange dateRange1, DateRange dateRange2, int actualDiff,
      int expectedDiff, String description) {
    assertThat(actualDiff).isEqualTo(expectedDiff);
  }
}

