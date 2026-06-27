package com.bookkeeper.csv.view.gui;

import com.bookkeeper.csv.view.model.CsvRecordColumn;
import com.bookkeeper.csv.view.model.CsvRecordItem;
import com.bookkeeper.csv.view.gui.table.column.DefaultCsvRecordTableColumn;
import com.bookkeeper.csv.view.gui.table.column.ErrorsCsvRecordTableColumn;
import com.bookkeeper.csv.view.gui.table.column.StatusCsvRecordTableColumn;

import javafx.collections.ObservableList;
import javafx.scene.control.TableView;

@SuppressWarnings("java:S110")
class CsvRecordTableView extends TableView<CsvRecordItem> {


  CsvRecordTableView(ObservableList<CsvRecordItem> items) {
    super(items);
    init();
  }


  private void init() {
    setEditable(true);
    initTableColumns();
  }


  private void initTableColumns() {
    CsvRecordColumn.stream().forEach(this::addTableColumn);
  }


  private void addTableColumn(CsvRecordColumn csvRecordColumn) {
    var tableColumn = switch (csvRecordColumn) {
      case STATUS -> new StatusCsvRecordTableColumn();
      case ERRORS -> new ErrorsCsvRecordTableColumn();
      default -> new DefaultCsvRecordTableColumn(csvRecordColumn);
    };

    getColumns().add(tableColumn);
  }
}
