package com.bookkeeper.csv;

import static com.bookkeeper.core.type.CsvRecordColumn.AMOUNT;
import static com.bookkeeper.core.type.CsvRecordColumn.CATEGORY;
import static com.bookkeeper.core.type.CsvRecordColumn.DATE;
import static com.bookkeeper.core.type.CsvRecordColumn.NOTES;
import static com.bookkeeper.core.utils.CsvUtils.getColumnValue;

import com.bookkeeper.domain.entry.Entry;
import com.bookkeeper.core.type.CsvRecordStatus;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import org.apache.commons.csv.CSVRecord;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class CsvRecordWrapper {

  @NotNull(message = "Missing date")
  private String date;

  @NotNull(message = "Missing amount")
  @Digits(integer = 9, fraction = 2, message = "Invalid amount")
  private String amount;

  @NotNull(message = "Missing category name")
  @Size(min = 1, max = 100, message = "Category name exceeds 100 chars")
  private String category;

  @Size(max = 255, message = "Notes exceeds 255 chars")
  private String notes;

  private CsvRecordStatus status;

  private String errors;

  @Getter(AccessLevel.NONE)
  private Entry entry;

  public CsvRecordWrapper(CSVRecord csvRecord) {
    date = getColumnValue(csvRecord, DATE);
    amount = getColumnValue(csvRecord, AMOUNT);
    category = getColumnValue(csvRecord, CATEGORY);
    notes = getColumnValue(csvRecord, NOTES);
  }

  public Entry getEntry() {
    if (entry == null) {
      entry = new Entry();
    }
    return entry;
  }
}
