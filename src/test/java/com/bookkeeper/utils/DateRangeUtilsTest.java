package com.bookkeeper.utils;

import static com.bookkeeper.utils.DateRangeUtils.getMonthBounds;
import static com.bookkeeper.utils.DateRangeUtils.getQuarterBounds;
import static com.bookkeeper.utils.DateRangeUtils.getWeekBounds;
import static com.bookkeeper.utils.DateRangeUtils.getYearBounds;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import static java.time.Month.APRIL;
import static java.time.Month.DECEMBER;
import static java.time.Month.JANUARY;
import static java.time.Month.JULY;
import static java.time.Month.JUNE;
import static java.time.Month.MARCH;
import static java.time.Month.OCTOBER;
import static java.time.Month.SEPTEMBER;

import com.bookkeeper.dto.DateRange;

import org.junit.Test;
import java.time.LocalDate;

public class DateRangeUtilsTest {

  private final static LocalDate TEST_DATE = LocalDate.of(2017, DECEMBER, 24);

  @Test
  public void testWeekBounds() {
    DateRange weekBounds = getWeekBounds(TEST_DATE);
    assertEquals(LocalDate.of(TEST_DATE.getYear(), DECEMBER, 18), weekBounds.getStartDate());
    assertEquals(LocalDate.of(TEST_DATE.getYear(), DECEMBER, 24), weekBounds.getEndDate());
  }

  @Test
  public void testMonthBoundsWithCurrentMonth() {
    DateRange monthBounds = getMonthBounds(TEST_DATE);
    assertEquals(LocalDate.of(TEST_DATE.getYear(), DECEMBER, 1), monthBounds.getStartDate());
    assertEquals(LocalDate.of(TEST_DATE.getYear(), DECEMBER, 31), monthBounds.getEndDate());
  }

  @Test
  public void testMonthBoundsWithCurrentMonthByDefault() {
    DateRange monthBounds = getMonthBounds(TEST_DATE, null);
    assertEquals(LocalDate.of(TEST_DATE.getYear(), DECEMBER, 1), monthBounds.getStartDate());
    assertEquals(LocalDate.of(TEST_DATE.getYear(), DECEMBER, 31), monthBounds.getEndDate());
  }

  @Test
  public void testMonthBoundsWithOtherMonth() {
    LocalDate date = LocalDate.of(TEST_DATE.getYear(), JULY, 1);
    DateRange monthBounds = getMonthBounds(date, JULY);
    assertEquals(LocalDate.of(TEST_DATE.getYear(), JULY, 1), monthBounds.getStartDate());
    assertEquals(LocalDate.of(TEST_DATE.getYear(), JULY, 31), monthBounds.getEndDate());
  }

  @Test
  public void test1stQuarterBounds() {
    DateRange quarterBounds = getQuarterBounds(TEST_DATE, 1);
    assertEquals(LocalDate.of(TEST_DATE.getYear(), JANUARY, 1), quarterBounds.getStartDate());
    assertEquals(LocalDate.of(TEST_DATE.getYear(), MARCH, 31), quarterBounds.getEndDate());
  }

  @Test
  public void test2ndQuarterBounds() {
    DateRange quarterBounds = getQuarterBounds(TEST_DATE, 2);
    assertEquals(LocalDate.of(TEST_DATE.getYear(), APRIL, 1), quarterBounds.getStartDate());
    assertEquals(LocalDate.of(TEST_DATE.getYear(), JUNE, 30), quarterBounds.getEndDate());
  }

  @Test
  public void test3rdQuarterBounds() {
    DateRange quarterBounds = getQuarterBounds(TEST_DATE, 3);
    assertEquals(LocalDate.of(TEST_DATE.getYear(), JULY, 1), quarterBounds.getStartDate());
    assertEquals(LocalDate.of(TEST_DATE.getYear(), SEPTEMBER, 30), quarterBounds.getEndDate());
  }

  @Test
  public void test4thQuarterBounds() {
    DateRange quarterBounds = getQuarterBounds(TEST_DATE, 4);
    assertEquals(LocalDate.of(TEST_DATE.getYear(), OCTOBER, 1), quarterBounds.getStartDate());
    assertEquals(LocalDate.of(TEST_DATE.getYear(), DECEMBER, 31), quarterBounds.getEndDate());
  }

  @Test
  public void testYearBounds() {
    DateRange yearBounds = getYearBounds(TEST_DATE, true);
    assertEquals(LocalDate.of(TEST_DATE.getYear(), JANUARY, 1), yearBounds.getStartDate());
    assertEquals(LocalDate.of(TEST_DATE.getYear(), DECEMBER, 31), yearBounds.getEndDate());
  }
}

