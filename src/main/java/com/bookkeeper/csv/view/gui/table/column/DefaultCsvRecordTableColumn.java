package com.bookkeeper.csv.view.gui.table.column;

import static org.apache.commons.lang3.StringUtils.trimToNull;

import com.bookkeeper.csv.view.model.CsvRecordColumn;
import com.bookkeeper.csv.view.model.CsvRecordItem;
import com.bookkeeper.common.exception.ApplicationException;

import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.TextFieldTableCell;

public class DefaultCsvRecordTableColumn extends AbstractCsvRecordTableColumn<String> {

  public DefaultCsvRecordTableColumn(CsvRecordColumn csvRecordColumn) {
    super(csvRecordColumn);

    if (csvRecordColumn.isEditable()) {
      setCellFactory(TextFieldTableCell.forTableColumn());
      addEventHandler(TableColumn.editCommitEvent(), (TableColumn.CellEditEvent<CsvRecordItem, String> event) ->
          handleCellEditEvent(csvRecordColumn, event));
    }
  }


  private static void handleCellEditEvent(CsvRecordColumn column, CellEditEvent<CsvRecordItem, String> event) {
    var csvRecordItem = event.getRowValue();
    column.setValue(csvRecordItem, trimToNull(event.getNewValue()));
    csvRecordItem.validate();
  }


  @Override
  protected void setCellValueFactory(CsvRecordColumn csvRecordColumn) {
    switch (csvRecordColumn) {
      case DATE -> setCellValueFactory(cellDataFeatures -> cellDataFeatures.getValue().dateProperty());
      case AMOUNT -> setCellValueFactory(cellDataFeatures -> cellDataFeatures.getValue().amountProperty());
      case NOTES -> setCellValueFactory(cellDataFeatures -> cellDataFeatures.getValue().notesProperty());
      case ERRORS -> setCellValueFactory(cellDataFeatures -> cellDataFeatures.getValue().errorsProperty());
      default -> throw new ApplicationException("Unsupported column type: " + csvRecordColumn);
    }
  }
}
