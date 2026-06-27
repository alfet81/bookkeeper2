package com.bookkeeper.csv.view.gui.table.column;

import static com.bookkeeper.csv.view.model.CsvRecordColumn.STATUS;

import com.bookkeeper.csv.view.model.CsvRecordColumn;
import com.bookkeeper.csv.view.model.CsvRecordStatus;
import com.bookkeeper.csv.view.gui.table.cell.CsvRecordStatusTableCell;

public class StatusCsvRecordTableColumn extends AbstractCsvRecordTableColumn<CsvRecordStatus> {

  public StatusCsvRecordTableColumn() {
    super(STATUS);
    setCellFactory(_ -> new CsvRecordStatusTableCell());
  }


  @Override
  protected void setCellValueFactory(CsvRecordColumn csvRecordColumn) {
    setCellValueFactory(cellDataFeatures -> cellDataFeatures.getValue().statusProperty());
  }
}
