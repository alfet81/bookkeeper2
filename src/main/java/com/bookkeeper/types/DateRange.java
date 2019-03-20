package com.bookkeeper.types;

import static com.bookkeeper.AppConstants.SHORT_DATE_FRORMATTER;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;

@Setter @Getter
@AllArgsConstructor
public class DateRange {

  private LocalDate startDate;
  private LocalDate endDate;

  public DateRange() {
    this(LocalDate.now(), LocalDate.now());
  }

  public DateRange(DateRange source) {
    this(source.getStartDate(), source.getEndDate());
  }

  @Override
  public String toString() {
    return "[" + startDate.format(SHORT_DATE_FRORMATTER) + " --> "
        + endDate.format(SHORT_DATE_FRORMATTER) + "]";
  }

  public boolean contains(LocalDate date) {
    if (date == null) {
      return false;
    }
    return ((date.isEqual(startDate) || date.isAfter(startDate))
        && (date.isEqual(endDate) || date.isBefore(endDate)));
  }
}
