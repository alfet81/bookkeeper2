package com.bookkeeper.utils;

import static com.bookkeeper.utils.DateRangeUtils.getMonthBounds;
import static com.bookkeeper.utils.DateRangeUtils.getQuarterBounds;
import static com.bookkeeper.utils.DateRangeUtils.getWeekBounds;
import static com.bookkeeper.utils.DateRangeUtils.getYearBounds;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static java.time.Month.APRIL;
import static java.time.Month.DECEMBER;
import static java.time.Month.JANUARY;
import static java.time.Month.JULY;
import static java.time.Month.JUNE;
import static java.time.Month.MARCH;
import static java.time.Month.OCTOBER;
import static java.time.Month.SEPTEMBER;

import com.bookkeeper.model.dates.DateRange;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

class DateRangeUtilsTest {

  private final static LocalDate TEST_DATE = LocalDate.of(2017, DECEMBER, 24);

  @Test
  void testWeekBounds() {
    DateRange weekBounds = getWeekBounds(TEST_DATE);
    assertEquals(LocalDate.of(TEST_DATE.getYear(), DECEMBER, 18), weekBounds.startDate());
    assertEquals(LocalDate.of(TEST_DATE.getYear(), DECEMBER, 24), weekBounds.endDate());
  }

  @Test
  void testMonthBoundsWithCurrentMonth() {
    DateRange monthBounds = getMonthBounds(TEST_DATE);
    assertEquals(LocalDate.of(TEST_DATE.getYear(), DECEMBER, 1), monthBounds.startDate());
    assertEquals(LocalDate.of(TEST_DATE.getYear(), DECEMBER, 31), monthBounds.endDate());
  }

  @Test
  void testMonthBoundsWithCurrentMonthByDefault() {
    DateRange monthBounds = getMonthBounds(TEST_DATE, null);
    assertEquals(LocalDate.of(TEST_DATE.getYear(), DECEMBER, 1), monthBounds.startDate());
    assertEquals(LocalDate.of(TEST_DATE.getYear(), DECEMBER, 31), monthBounds.endDate());
  }

  @Test
  void testMonthBoundsWithOtherMonth() {
    LocalDate date = LocalDate.of(TEST_DATE.getYear(), JULY, 1);
    DateRange monthBounds = getMonthBounds(date, JULY);
    assertEquals(LocalDate.of(TEST_DATE.getYear(), JULY, 1), monthBounds.startDate());
    assertEquals(LocalDate.of(TEST_DATE.getYear(), JULY, 31), monthBounds.endDate());
  }

  @Test
  void test1stQuarterBounds() {
    DateRange quarterBounds = getQuarterBounds(TEST_DATE, 1);
    assertEquals(LocalDate.of(TEST_DATE.getYear(), JANUARY, 1), quarterBounds.startDate());
    assertEquals(LocalDate.of(TEST_DATE.getYear(), MARCH, 31), quarterBounds.endDate());
  }

  @Test
  void test2ndQuarterBounds() {
    DateRange quarterBounds = getQuarterBounds(TEST_DATE, 2);
    assertEquals(LocalDate.of(TEST_DATE.getYear(), APRIL, 1), quarterBounds.startDate());
    assertEquals(LocalDate.of(TEST_DATE.getYear(), JUNE, 30), quarterBounds.endDate());
  }

  @Test
  void test3rdQuarterBounds() {
    DateRange quarterBounds = getQuarterBounds(TEST_DATE, 3);
    assertEquals(LocalDate.of(TEST_DATE.getYear(), JULY, 1), quarterBounds.startDate());
    assertEquals(LocalDate.of(TEST_DATE.getYear(), SEPTEMBER, 30), quarterBounds.endDate());
  }

  @Test
  void test4thQuarterBounds() {
    DateRange quarterBounds = getQuarterBounds(TEST_DATE, 4);
    assertEquals(LocalDate.of(TEST_DATE.getYear(), OCTOBER, 1), quarterBounds.startDate());
    assertEquals(LocalDate.of(TEST_DATE.getYear(), DECEMBER, 31), quarterBounds.endDate());
  }

  @Test
  void testYearBounds() {
    DateRange yearBounds = getYearBounds(TEST_DATE, true);
    assertEquals(LocalDate.of(TEST_DATE.getYear(), JANUARY, 1), yearBounds.startDate());
    assertEquals(LocalDate.of(TEST_DATE.getYear(), DECEMBER, 31), yearBounds.endDate());
  }
}

