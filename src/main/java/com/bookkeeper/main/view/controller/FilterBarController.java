package com.bookkeeper.main.view.controller;

import static com.bookkeeper.utils.MiscUtils.asOptional;

import com.bookkeeper.account.jpa.Account;
import com.bookkeeper.account.view.gui.AccountSelectorBox;
import com.bookkeeper.category.model.Category;
import com.bookkeeper.main.view.model.AppViewModel;
import com.bookkeeper.common.model.date.DateRange;
import com.bookkeeper.common.model.date.DateRangeShifterType;
import com.bookkeeper.common.model.date.DateRangeType;
import jfx.support.fxml.annotation.FXMLController;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.Set;

@FXMLController
public class FilterBarController implements Initializable {

  private final AppViewModel appViewModel;

  private final ObjectProperty<DateRange> dateRangeProperty = new SimpleObjectProperty<>();
  private final ObjectProperty<Account> accountProperty = new SimpleObjectProperty<>();
  private final ObjectProperty<Category> categoryProperty = new SimpleObjectProperty<>();
  private final ObjectProperty<Set<Label>> labelsProperty = new SimpleObjectProperty<>();

  @FXML
  private MenuButton dateRangeMenu;
  @FXML
  private DatePicker startDatePicker;
  @FXML
  private DatePicker endDatePicker;
  @FXML
  private AccountSelectorBox accountComboBox;

  private boolean ignoreAction;


  public FilterBarController(AppViewModel appViewModel) {
    this.appViewModel = appViewModel;
    dateRangeProperty.addListener((observable, oldValue, newValue) -> updateDatePickers());
  }


  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    appViewModel.bindDateRange(dateRangeProperty);
  }


  @FXML
  public void dateRangeHandler(Event event) {
    var item = (MenuItem) event.getSource();
    var type = DateRangeType.valueOf(item.getId());
    updateDatePickers(type);
    dateRangeMenu.setText(item.getText());
  }


  public void dateShiftHandler(Event event) {
    var shifter = (Node) event.getSource();
    var shiftAction = DateRangeShifterType.valueOf(shifter.getId());
    updateDatePickers(shiftAction);
  }


  private void updateDatePickers(DateRangeType type) {
    var dateRange = (type != null ? type.getDateRange() : DateRange.none());
    ignoreAction = true;
    setDateRange(dateRange);
    ignoreAction = false;
    setDateRangeProperty();
  }


  private void updateDatePickers(DateRangeShifterType dateRangeShifter) {
    var dateRange = getDateRange(dateRangeShifter);
    ignoreAction = true;
    setDateRange(dateRange);
    ignoreAction = false;
    setDateRangeProperty();
  }


  private void updateDatePickers() {
    var dateRange = asOptional(dateRangeProperty.get()).orElseGet(DateRange::none);
    ignoreAction = true;
    setDateRange(dateRange);
    ignoreAction = false;
  }


  public void datePickHandler(Event event) {
    if (!ignoreAction) {
      setDateRangeProperty();
    }
  }


  @FXML
  public void accountHandler(Event event) {

  }


  public void categoryHandler(Event event) {

  }


  public void labelsHandler(Event event) {
    var source = (Node) event.getSource();
    System.out.println(source.getId());
  }


  private void setDateRangeProperty() {
    dateRangeProperty.set(getDateRange());
  }


  private DateRange getDateRange() {
    return DateRange.of(startDatePicker.getValue(), endDatePicker.getValue());
  }


  private void setDateRange(DateRange dateRange) {
    startDatePicker.setValue(dateRange.getStartDate());
    endDatePicker.setValue(dateRange.getEndDate());
  }


  private DateRange getDateRange(DateRangeShifterType dateRangeShifter) {
    return (dateRangeShifter != null ? dateRangeShifter.shiftDateRange(getDateRange())
        : DateRange.none());
  }
}
