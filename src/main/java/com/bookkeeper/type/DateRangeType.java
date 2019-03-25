package com.bookkeeper.type;

import static com.bookkeeper.utils.MiscUtils.asOptional;
import static org.apache.commons.lang3.EnumUtils.getEnum;

import lombok.AllArgsConstructor;
import lombok.Getter;
import java.util.Optional;

@Getter
@AllArgsConstructor
public enum DateRangeType {
  CURRENT_WEEK(null), CURRENT_MONTH(null), CURRENT_QUARTER(null), CURRENT_YEAR(null),
  JAN(1), FEB(2), MAR(3), APR(4), MAY(5), JUN(6), JUL(7), AUG(8), SEP(9), OCT(10), NOV(11), DEC(12),
  QUARTER_1(1), QUARTER_2(2), QUARTER_3(3), QUARTER_4(4),
  PRIOR_YEAR(null), ALL(null);

  private Integer value;

  public static Optional<DateRangeType> optionalOf(String enumName) {
    enumName = asOptional(enumName).map(String::toUpperCase).orElse(null);
    return asOptional(getEnum(DateRangeType.class, enumName));
  }
}
