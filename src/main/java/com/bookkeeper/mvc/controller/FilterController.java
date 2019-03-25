package com.bookkeeper.mvc.controller;

import static com.bookkeeper.utils.DateRangeUtils.getDateRangeByType;

import com.bookkeeper.domain.account.Account;
import com.bookkeeper.dto.DateRange;
import com.bookkeeper.dto.FilterParameters;
import com.bookkeeper.type.DateRangeType;
import de.felixroske.jfxsupport.FXMLController;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;

@FXMLController
public class FilterController {

  @FXML
  private MenuButton dateRangeMenu;

  @FXML
  private DatePicker startDatePicker;

  @FXML
  private DatePicker endDatePicker;

  @FXML
  private ComboBox<Account> accountComboBox;

  @FXML
  public void dateRangeHandler(final Event event) {
    var item = (MenuItem) event.getSource();
    DateRangeType.optionalOf(item.getId()).ifPresent(this::updateDatePickers);
    dateRangeMenu.setText(item.getText());
  }

  private void updateDatePickers(DateRangeType type) {
    var dateRange = getDateRangeByType(type);
    startDatePicker.setValue(dateRange.getStartDate());
    endDatePicker.setValue(dateRange.getEndDate());
  }

  public void datePickHandler(final Event event) {

  }

  public void accountHandler(final Event event) {

  }

  public void categoryHandler(final Event event) {

  }

  public void labelsHandler(final Event event) {
    var source = (Node) event.getSource();
    System.out.println(source.getId());
  }

  private FilterParameters buildFilterParams() {
    return FilterParameters.builder()
        .dateRange(getDateRange())
        .account(null)
        .category(null)
        .labels(null)
        .build();
  }

  private DateRange getDateRange() {
    return new DateRange(startDatePicker.getValue(), endDatePicker.getValue());
  }
}
