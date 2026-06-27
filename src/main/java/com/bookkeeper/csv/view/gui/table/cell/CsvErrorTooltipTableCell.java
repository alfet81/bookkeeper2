package com.bookkeeper.csv.view.gui.table.cell;

import com.bookkeeper.csv.view.model.CsvRecordItem;

import javafx.scene.control.TableCell;
import javafx.scene.control.Tooltip;

public class CsvErrorTooltipTableCell extends TableCell<CsvRecordItem, String> {

  @Override
  protected void updateItem(String item, boolean empty) {

    super.updateItem(item, empty);

    if (empty || item == null) {
      setText(null);
      setTooltip(null); // Ensure no tooltip is shown for empty cells
    } else {
      setText(item);
      // Set the tooltip text based on the cell's item
      setTooltip(new Tooltip(item));
      // If you need the whole row's data for a more complex hint,
      // you can access it via getTableRow().getItem()
    }
  }
}
