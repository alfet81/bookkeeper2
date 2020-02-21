package com.bookkeeper.type;

import static com.bookkeeper.type.DateRangeCategory.MONTH;
import static com.bookkeeper.type.DateRangeCategory.NONE;
import static com.bookkeeper.type.DateRangeCategory.QUARTER;
import static com.bookkeeper.type.DateRangeCategory.WEEK;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum DateRangeType {
  CURRENT_WEEK(null, WEEK),
  CURRENT_MONTH(null, MONTH),
  CURRENT_QUARTER(null, QUARTER),
  CURRENT_YEAR(null, NONE),
  JAN(1, MONTH),
  FEB(2, MONTH),
  MAR(3, MONTH),
  APR(4, MONTH),
  MAY(5, MONTH),
  JUN(6, MONTH),
  JUL(7, MONTH),
  AUG(8, MONTH),
  SEP(9, MONTH),
  OCT(10, MONTH),
  NOV(11, MONTH),
  DEC(12, MONTH),
  QUARTER_1(1, QUARTER),
  QUARTER_2(2, QUARTER),
  QUARTER_3(3, QUARTER),
  QUARTER_4(4, QUARTER),
  PRIOR_YEAR(null, NONE),
  ALL(null, NONE);

  private Integer value;
  private DateRangeCategory category;
}
