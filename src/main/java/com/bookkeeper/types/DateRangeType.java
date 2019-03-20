package com.bookkeeper.types;

import static com.bookkeeper.utils.MiscUtils.asOptional;
import static org.apache.commons.lang3.EnumUtils.getEnum;

import lombok.Getter;
import java.util.Optional;

@Getter
public enum DateRangeType {
  CURRENT_WEEK(1), CURRENT_MONTH(2), CURRENT_QUARTER(null), CURRENT_YEAR(4),
  JAN(1), FEB(2), MAR(3), APR(4), MAY(5), JUN(6), JUL(7), AUG(8), SEP(9), OCT(10), NOV(11), DEC(12),
  QUARTER_1(1), QUARTER_2(2), QUARTER_3(3), QUARTER_4(4),
  PRIOR_YEAR(1), ALL(0);

  private Integer num;

  DateRangeType(Integer num) {
    this.num = num;
  }

  public static Optional<DateRangeType> optionalOf(String value) {
    value = asOptional(value).orElse(null);
    return asOptional(getEnum(DateRangeType.class, value));
  }
}
