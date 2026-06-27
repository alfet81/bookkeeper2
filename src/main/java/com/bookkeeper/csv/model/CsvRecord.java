package com.bookkeeper.csv.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * Class for CSV record.
 */
@Data
@Builder
@AllArgsConstructor
public class CsvRecord {

  private String date;
  private String amount;
  private String notes;
}
