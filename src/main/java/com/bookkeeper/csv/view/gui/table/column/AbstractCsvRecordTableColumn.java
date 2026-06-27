package com.bookkeeper.csv.view.gui.table.column;

import com.bookkeeper.csv.view.model.CsvRecordColumn;
import com.bookkeeper.csv.view.model.CsvRecordItem;

import javafx.scene.control.TableColumn;

abstract class AbstractCsvRecordTableColumn<T> extends TableColumn<CsvRecordItem, T> {

  AbstractCsvRecordTableColumn(CsvRecordColumn csvRecordColumn) {
    super(csvRecordColumn.getColumnName());
    setColumnProperties(csvRecordColumn);
    setCellValueFactory(csvRecordColumn);
  }


  protected void setColumnProperties(CsvRecordColumn csvRecordColumn) {
    setMinWidth(csvRecordColumn.getDefaultWidth());
    setPrefWidth(csvRecordColumn.getDefaultWidth());
    setResizable(csvRecordColumn.isResizable());
    setSortable(csvRecordColumn.isSortable());
    setEditable(csvRecordColumn.isEditable());
  }

  protected abstract void setCellValueFactory(CsvRecordColumn csvRecordColumn);
}
