package com.bookkeeper.csv.view.gui.table.column;

import com.bookkeeper.csv.view.model.CsvRecordColumn;
import com.bookkeeper.csv.view.gui.table.cell.CsvErrorTooltipTableCell;

public class ErrorsCsvRecordTableColumn extends DefaultCsvRecordTableColumn {
  public ErrorsCsvRecordTableColumn() {
    super(CsvRecordColumn.ERRORS);
    setCellFactory(_ -> new CsvErrorTooltipTableCell());
  }
}
