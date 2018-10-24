package com.bookkeeper.core.type;

import static java.time.format.FormatStyle.SHORT;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Setter
@Getter
@AllArgsConstructor
public class DateRange {

  private LocalDate lowerBound;
  private LocalDate upperBound;

  public DateRange() {
    this(LocalDate.now(), LocalDate.now());
  }

  public DateRange(DateRange other) {
    this(other.getLowerBound(), other.getUpperBound());
  }

  public Date getLowerBoundAsDate() {
    return new Date(lowerBound.toEpochDay());
  }

  public Date getUpperBoundAsDate() {
    return new Date(upperBound.toEpochDay());
  }

  @Override
  public String toString() {
    var formatter = DateTimeFormatter.ofLocalizedDate(SHORT);
    return "[" + lowerBound.format(formatter) + " --> " + upperBound.format(formatter) + "]";
  }

  public boolean contains(LocalDate dateTime) {
    if (dateTime == null) {
      return false;
    }
    return ((dateTime.isEqual(lowerBound) || dateTime.isAfter(lowerBound))
        && (dateTime.isEqual(upperBound) || dateTime.isBefore(upperBound)));
  }
}
