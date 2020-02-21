package com.bookkeeper.mvc.controller;

import static com.bookkeeper.type.DateRangeCategory.MONTH;
import static com.bookkeeper.type.DateRangeCategory.NONE;
import static com.bookkeeper.utils.DateRangeUtils.getDateRangeByType;
import static com.bookkeeper.utils.DateRangeUtils.shiftDateRange;
import static com.bookkeeper.utils.MiscUtils.asOptional;

import com.bookkeeper.domain.account.Account;
import com.bookkeeper.domain.category.Category;
import com.bookkeeper.dto.DateRange;
import com.bookkeeper.mvc.model.AppViewModel;
import com.bookkeeper.type.DateRangeCategory;
import com.bookkeeper.type.DateRangeShiftAction;
import com.bookkeeper.type.DateRangeType;
import com.bookkeeper.ui.controls.TreeViewComboBox;

import de.felixroske.jfxsupport.FXMLController;
import org.springframework.beans.factory.annotation.Autowired;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.Set;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;

@FXMLController
public class FilterBarController implements Initializable {

  @FXML
  private MenuButton dateRangeMenu;

  @FXML
  private DatePicker startDatePicker;

  @FXML
  private DatePicker endDatePicker;

  @FXML
  private TreeViewComboBox<Account> accountComboBox;

  @Autowired
  private AppViewModel appViewModel;

  private final ObjectProperty<DateRange> dateRangeProperty = new SimpleObjectProperty<>();

  private final ObjectProperty<Account> accountProperty = new SimpleObjectProperty<>();

  private final ObjectProperty<Category> categoryProperty = new SimpleObjectProperty<>();

  private final ObjectProperty<Set<Label>> labelsProperty = new SimpleObjectProperty<>();

  private boolean ignoreAction;

  private DateRangeCategory dateRangeCategory = MONTH;

  public FilterBarController() {
    dateRangeProperty.addListener((ob, old, neo) -> updateDatePickers());
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
    dateRangeCategory = type.getCategory();
    dateRangeMenu.setText(item.getText());
  }

  public void dateShiftHandler(Event event) {
    var shifter = (Node) event.getSource();
    var shiftAction = DateRangeShiftAction.valueOf(shifter.getId());
    updateDatePickers(shiftAction);
  }

  private void updateDatePickers(DateRangeType type) {
    var dateRange = getDateRangeByType(type);
    ignoreAction = true;
    setDateRange(dateRange);
    ignoreAction = false;
    setDateRangeProperty();
  }

  private void updateDatePickers(DateRangeShiftAction shiftAction) {
    var dateRange = getDateRange(shiftAction);
    ignoreAction = true;
    setDateRange(dateRange);
    ignoreAction = false;
    setDateRangeProperty();
  }

  private void updateDatePickers() {
    var dateRange = asOptional(dateRangeProperty.get()).orElseGet(DateRange::new);
    ignoreAction = true;
    setDateRange(dateRange);
    ignoreAction = false;
  }

  public void datePickHandler(Event event) {
    if (!ignoreAction) {
      dateRangeCategory = NONE;
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

  private void setDateRange(DateRange dateRange) {
    startDatePicker.setValue(dateRange.getStartDate());
    endDatePicker.setValue(dateRange.getEndDate());
  }

  private DateRange getDateRange() {
    return new DateRange(startDatePicker.getValue(), endDatePicker.getValue());
  }

  private DateRange getDateRange(DateRangeShiftAction action) {
    return shiftDateRange(getDateRange(), dateRangeCategory, action);
  }
}
