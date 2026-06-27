package com.bookkeeper.common.model.date;

import static com.bookkeeper.common.model.date.DateRangeTestHelper.MONTH_BOUNDS_VALIDATION_PREDICATE;
import static com.bookkeeper.common.model.date.DateRangeTestHelper.PRIOR_YEAR_BOUNDS_VALIDATION_PREDICATE;
import static com.bookkeeper.common.model.date.DateRangeTestHelper.QUARTER_BOUNDS_VALIDATION_PREDICATE;
import static com.bookkeeper.common.model.date.DateRangeTestHelper.WEEK_BOUNDS_VALIDATION_PREDICATE;
import static com.bookkeeper.common.model.date.DateRangeTestHelper.YEAR_BOUNDS_VALIDATION_PREDICATE;
import static com.bookkeeper.common.model.date.DateRangeTestHelper.getCurrentDate;
import static com.bookkeeper.common.model.date.DateRangeTestHelper.getMonthBoundsValidationPredicate;
import static com.bookkeeper.common.model.date.DateRangeTestHelper.getQuarterBoundsValidationPredicate;
import static com.bookkeeper.common.model.date.DateRangeType.ALL;
import static com.bookkeeper.common.model.date.DateRangeType.CURRENT_MONTH;
import static com.bookkeeper.common.model.date.DateRangeType.CURRENT_QUARTER;
import static com.bookkeeper.common.model.date.DateRangeType.CURRENT_WEEK;
import static com.bookkeeper.common.model.date.DateRangeType.CURRENT_YEAR;
import static com.bookkeeper.common.model.date.DateRangeType.PRIOR_YEAR;
import static org.apache.commons.lang3.StringUtils.left;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.params.provider.Arguments.arguments;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.time.LocalDate;
import java.time.Month;
import java.util.Collections;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiPredicate;
import java.util.stream.IntStream;
import java.util.stream.Stream;

class DateRangeTypeTest {
  private static final String QUARTER_PREFIX = "QUARTER_";
  private static final BiPredicate<LocalDate, DateRange> EMPTY_DATE_RANGE_PREDICATE = (date, dateRange) ->
      dateRange.isEmpty();
  static final Map<DateRangeType, BiPredicate<LocalDate, DateRange>> PREDICATE_MAP = getPredicateMap();


  private static Map<DateRangeType, BiPredicate<LocalDate, DateRange>> getPredicateMap() {
    var map = new EnumMap<DateRangeType, BiPredicate<LocalDate, DateRange>>(DateRangeType.class);
    map.put(CURRENT_WEEK, WEEK_BOUNDS_VALIDATION_PREDICATE);
    map.put(CURRENT_MONTH, MONTH_BOUNDS_VALIDATION_PREDICATE);
    map.put(CURRENT_QUARTER, QUARTER_BOUNDS_VALIDATION_PREDICATE);
    map.put(CURRENT_YEAR, YEAR_BOUNDS_VALIDATION_PREDICATE);
    List.of(Month.values()).forEach(month -> getDateRangeTypeByMonth(month).ifPresent(
        type -> map.put(type, getMonthBoundsValidationPredicate(month))));
    IntStream.range(1, 5).forEach(num -> getDateRangeTypeByQuarterNumber(num).ifPresent(
        type -> map.put(type, getQuarterBoundsValidationPredicate(num))));
    map.put(PRIOR_YEAR, PRIOR_YEAR_BOUNDS_VALIDATION_PREDICATE);
    map.put(ALL, EMPTY_DATE_RANGE_PREDICATE);
    return Collections.unmodifiableMap(map);
  }


  private static Optional<DateRangeType> getDateRangeTypeByMonth(Month month) {
    return DateRangeType.getDateRangeType(left(month.name(), 3));
  }


  private static Optional<DateRangeType> getDateRangeTypeByQuarterNumber(Integer quarterNum) {
    return DateRangeType.getDateRangeType(QUARTER_PREFIX + quarterNum);
  }


  static Stream<Arguments> provideDateRangeTypeArgs() {
    var currentDate = getCurrentDate();
    return PREDICATE_MAP.entrySet().stream()
                        .map(entry -> arguments(entry.getKey(), currentDate,
                            entry.getKey().getDateRange(), entry.getValue()));
  }


  @ParameterizedTest(name = "[{index}] => {0} date range type for date {1} yields {2}")
  @MethodSource("provideDateRangeTypeArgs")
  void testDateRangeType(DateRangeType dateRangeType, LocalDate date,
      final DateRange actualDateRange, BiPredicate<LocalDate, DateRange> validationPredicate) {
    assertThat(validationPredicate.test(date, actualDateRange)).isTrue();
  }

}
