package com.bookkeeper.mvc.controller;

import static com.bookkeeper.utils.DateRangeUtils.getDateRange;

import com.bookkeeper.types.DateRange;
import com.bookkeeper.types.DateRangeType;
import de.felixroske.jfxsupport.FXMLController;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;

@FXMLController
public class FilterController {

  @FXML
  private DatePicker startDatePicker;

  @FXML
  private DatePicker endDatePicker;

  @FXML
  private MenuButton dateRangeMenu;

  @FXML
  public void chooseDateRange(final Event event) {

    var item = (MenuItem) event.getSource();

    dateRangeMenu.setText(item.getText());

    DateRangeType.optionalOf(item.getId()).ifPresent(type -> {
      var range = getDateRange(type);
      updateDatePickers(range);
    });
  }

  private void updateDatePickers(DateRange dateRange) {
    if (dateRange != null) {
      startDatePicker.setValue(dateRange.getStartDate());
      endDatePicker.setValue(dateRange.getEndDate());
    } else {
      startDatePicker.setValue(null);
      endDatePicker.setValue(null);
    }
  }
}
