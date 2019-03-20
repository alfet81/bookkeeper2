package com.bookkeeper.utils;

import static com.bookkeeper.utils.DateRangeUtils.getMonthBounds;
import static com.bookkeeper.utils.DateRangeUtils.getQuarterBounds;
import static com.bookkeeper.utils.DateRangeUtils.getWeekBounds;
import static com.bookkeeper.utils.DateRangeUtils.getYearBounds;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.bookkeeper.types.DateRange;

import org.junit.Test;
import java.time.LocalDate;
import java.time.Month;

public class DateRangeUtilsTest {

  private final static LocalDate TEST_DATE = LocalDate.of(2017, Month.DECEMBER, 24);

  @Test
  public void testWeekBounds() {
    DateRange weekBounds = getWeekBounds(TEST_DATE);
    assertEquals(LocalDate.of(TEST_DATE.getYear(), Month.DECEMBER, 18), weekBounds.getStartDate());
    assertEquals(LocalDate.of(TEST_DATE.getYear(), Month.DECEMBER, 24), weekBounds.getEndDate());
  }

  @Test
  public void testMonthBoundsWithCurrentMonth() {
    DateRange monthBounds = getMonthBounds(TEST_DATE);
    assertEquals(LocalDate.of(TEST_DATE.getYear(), Month.DECEMBER, 1), monthBounds.getStartDate());
    assertEquals(LocalDate.of(TEST_DATE.getYear(), Month.DECEMBER, 31), monthBounds.getEndDate());
  }

  @Test
  public void testMonthBoundsWithOtherMonth() {
    LocalDate date = LocalDate.of(TEST_DATE.getYear(), Month.JULY, 1);
    DateRange monthBounds = getMonthBounds(date);
    assertEquals(LocalDate.of(TEST_DATE.getYear(), Month.JULY, 1), monthBounds.getStartDate());
    assertEquals(LocalDate.of(TEST_DATE.getYear(), Month.JULY, 31), monthBounds.getEndDate());
  }

  @Test
  public void test1stQuarterBounds() {
    DateRange quarterBounds = getQuarterBounds(TEST_DATE, 1);
    assertEquals(LocalDate.of(TEST_DATE.getYear(), Month.JANUARY, 1), quarterBounds.getStartDate());
    assertEquals(LocalDate.of(TEST_DATE.getYear(), Month.MARCH, 31), quarterBounds.getEndDate());
  }

  @Test
  public void test2ndQuarterBounds() {
    DateRange quarterBounds = getQuarterBounds(TEST_DATE, 2);
    assertEquals(LocalDate.of(TEST_DATE.getYear(), Month.APRIL, 1), quarterBounds.getStartDate());
    assertEquals(LocalDate.of(TEST_DATE.getYear(), Month.JUNE, 30), quarterBounds.getEndDate());
  }

  @Test
  public void test3rdQuarterBounds() {
    DateRange quarterBounds = getQuarterBounds(TEST_DATE, 3);
    assertEquals(LocalDate.of(TEST_DATE.getYear(), Month.JULY, 1), quarterBounds.getStartDate());
    assertEquals(LocalDate.of(TEST_DATE.getYear(), Month.SEPTEMBER, 30), quarterBounds.getEndDate());
  }

  @Test
  public void test4thQuarterBounds() {
    DateRange quarterBounds = getQuarterBounds(TEST_DATE, 4);
    assertEquals(LocalDate.of(TEST_DATE.getYear(), Month.OCTOBER, 1), quarterBounds.getStartDate());
    assertEquals(LocalDate.of(TEST_DATE.getYear(), Month.DECEMBER, 31), quarterBounds.getEndDate());
  }

  @Test
  public void testYearBounds() {
    DateRange yearBounds = getYearBounds(TEST_DATE, true);
    assertEquals(LocalDate.of(TEST_DATE.getYear(), Month.JANUARY, 1), yearBounds.getStartDate());
    assertEquals(LocalDate.of(TEST_DATE.getYear(), Month.DECEMBER, 31), yearBounds.getEndDate());
  }

  @Test
  public void testDateRange() {
    LocalDate date = LocalDate.of(TEST_DATE.getYear(), Month.DECEMBER, 23);
    DateRange bounds4 = getQuarterBounds(date, 0);
    assertTrue(bounds4.contains(date));

    DateRange bounds2 = getQuarterBounds(date, 2);
    assertFalse(bounds2.contains(date));
  }
}

