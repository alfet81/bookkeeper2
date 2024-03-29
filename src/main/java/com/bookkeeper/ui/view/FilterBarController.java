package com.bookkeeper.ui.view;

import static com.bookkeeper.utils.MiscUtils.asOptional;

import com.bookkeeper.account.entity.Account;
import com.bookkeeper.account.ui.AccountSelectorBox;
import com.bookkeeper.category.entity.Category;
import com.bookkeeper.model.AppViewModel;
import com.bookkeeper.model.dates.DateRange;
import com.bookkeeper.model.dates.DateRangeShifterType;
import com.bookkeeper.model.dates.DateRangeType;
import de.felixroske.jfxsupport.FXMLController;
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

    dateRangeMenu.setText(item.getText());
  }


  public void dateShiftHandler(Event event) {

    var shifter = (Node) event.getSource();

    var shiftAction = DateRangeShifterType.valueOf(shifter.getId());

    updateDatePickers(shiftAction);
  }


  private void updateDatePickers(DateRangeType type) {

    var dateRange = (type != null ? type.getDateRange() : DateRange.empty());

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
    var dateRange = asOptional(dateRangeProperty.get()).orElseGet(DateRange::empty);
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
    return new DateRange(startDatePicker.getValue(), endDatePicker.getValue());
  }


  private void setDateRange(DateRange dateRange) {
    startDatePicker.setValue(dateRange.startDate());
    endDatePicker.setValue(dateRange.endDate());
  }


  private DateRange getDateRange(DateRangeShifterType dateRangeShifter) {
    return (dateRangeShifter != null ? dateRangeShifter.shiftDateRange(getDateRange())
        : DateRange.empty());
  }
}
