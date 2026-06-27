package com.bookkeeper.common.model.date;

import static com.bookkeeper.common.model.date.DateRangeShifterType.CURRENT_MONTH;
import static com.bookkeeper.common.model.date.DateRangeShifterType.NEXT_MONTH;
import static com.bookkeeper.common.model.date.DateRangeShifterType.NEXT_YEAR;
import static com.bookkeeper.common.model.date.DateRangeShifterType.PREV_MONTH;
import static com.bookkeeper.common.model.date.DateRangeShifterType.PREV_YEAR;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.params.provider.Arguments.arguments;
import static java.time.LocalDate.now;

import com.bookkeeper.common.model.date.DateRange;
import com.bookkeeper.common.model.date.DateRangeShifterType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;
import java.util.EnumMap;
import java.util.function.BiPredicate;
import java.util.stream.Stream;

class DateRangeShifterTypeTest {

  @DisplayName("Test DateRangeShifterType enums")
  @ParameterizedTest(name = "[{index}] => Shifter type {0} for date range {1} yields {2}")
  @ArgumentsSource(DateRangeShifterTypeArgsProvider.class)
  void testDateRangeShifterType(DateRangeShifterType type, DateRange srcDateRange, DateRange actualValue,
      BiPredicate<DateRange, DateRange> validationPredicate) {
    assertThat(validationPredicate.test(srcDateRange, actualValue)).isTrue();
  }


  private static class DateRangeShifterTypeArgsProvider implements ArgumentsProvider {

    private static final EnumMap<DateRangeShifterType, BiPredicate<DateRange, DateRange>> PREDICATE_MAP
        = new EnumMap<>(DateRangeShifterType.class);

    private static final BiPredicate<DateRange, DateRange> PREV_YEAR_PREDICATE = (srcDateRange, trgDateRange) -> {
      boolean condition1 = (srcDateRange.getStartDate().getYear() - trgDateRange.getStartDate().getYear()) == 1;
      boolean condition2 = (srcDateRange.getEndDate().getYear() - trgDateRange.getEndDate().getYear()) == 1;
      return condition1 && condition2;
    };

    private static final BiPredicate<DateRange, DateRange> NEXT_YEAR_PREDICATE = (srcDateRange, trgDateRange) -> {
      boolean condition1 = (srcDateRange.getStartDate().getYear() - trgDateRange.getStartDate().getYear()) == -1;
      boolean condition2 = (srcDateRange.getEndDate().getYear() - trgDateRange.getEndDate().getYear()) == -1;
      return condition1 && condition2;
    };

    private static final BiPredicate<DateRange, DateRange> PREV_MONTH_PREDICATE = (srcDateRange, trgDateRange) -> {
      boolean condition1 =
          (srcDateRange.getStartDate().getMonthValue() - trgDateRange.getStartDate().getMonthValue()) == 1;
      boolean condition2 =
          (srcDateRange.getEndDate().getMonthValue() - trgDateRange.getEndDate().getMonthValue()) == 1;
      return condition1 && condition2;
    };

    private static final BiPredicate<DateRange, DateRange> NEXT_MONTH_PREDICATE = (srcDateRange, trgDateRange) -> {
      boolean condition1 =
          (srcDateRange.getStartDate().getMonthValue() - trgDateRange.getStartDate().getMonthValue()) == -1;
      boolean condition2 =
          (srcDateRange.getEndDate().getMonthValue() - trgDateRange.getEndDate().getMonthValue()) == -1;
      return condition1 && condition2;
    };

    private static final BiPredicate<DateRange, DateRange> CURRENT_MONTH_PREDICATE = (srcDateRange, trgDateRange) -> {
      var currentDateRange = DateRange.month(now());
      return trgDateRange.equals(currentDateRange);
    };

    static {
      PREDICATE_MAP.put(PREV_YEAR, PREV_YEAR_PREDICATE);
      PREDICATE_MAP.put(NEXT_YEAR, NEXT_YEAR_PREDICATE);
      PREDICATE_MAP.put(PREV_MONTH, PREV_MONTH_PREDICATE);
      PREDICATE_MAP.put(NEXT_MONTH, NEXT_MONTH_PREDICATE);
      PREDICATE_MAP.put(CURRENT_MONTH, CURRENT_MONTH_PREDICATE);
    }

    @Override
    public Stream<Arguments> provideArguments(ExtensionContext extensionContext) {
      var srcDateRange = DateRange.month(now());
      return PREDICATE_MAP.entrySet().stream()
                          .map(entry -> buildArguments(entry.getKey(), srcDateRange, entry.getValue()));
    }


    private Arguments buildArguments(DateRangeShifterType dateRangeShifterType, DateRange srcDateRange,
        BiPredicate<DateRange, DateRange> predicate) {
      //args: dateRangeShifterType, date to shift, actualResultingDateRange, date range validation predicate
      return arguments(dateRangeShifterType, srcDateRange, dateRangeShifterType.shiftDateRange(srcDateRange),
          predicate);
    }
  }
}
