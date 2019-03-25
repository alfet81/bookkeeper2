package com.bookkeeper.dto;

import static com.bookkeeper.utils.DateTimeUtils.date2String;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDate;

@Setter @Getter
@NoArgsConstructor
@AllArgsConstructor
public class DateRange {

  private LocalDate startDate;
  private LocalDate endDate;

  public boolean contains(LocalDate date) {
    return date != null
        && ((date.isEqual(startDate) || date.isAfter(startDate))
        && (date.isEqual(endDate) || date.isBefore(endDate)));
  }

  @Override
  public String toString() {
    return "[" + date2String(startDate) + " --> " + date2String(endDate) + "]";
  }
}
