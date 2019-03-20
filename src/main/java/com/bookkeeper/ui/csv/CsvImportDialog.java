package com.bookkeeper.ui.csv;

import static com.bookkeeper.AppConstants.CSV_IMPORT_DIALOG_TITLE;
import static com.bookkeeper.app.AppContext.getAccountRoot;
import static com.bookkeeper.app.AppContext.getCurrentAccount;
import static com.bookkeeper.utils.MiscUtils.asOptional;
import static com.bookkeeper.ui.dialog.DialogHelper.showAlertDialog;

import static java.util.stream.Collectors.toList;
import static javafx.collections.FXCollections.observableArrayList;
import static javafx.scene.control.Alert.AlertType.ERROR;
import static javafx.event.ActionEvent.ACTION;
import static javafx.scene.control.ButtonType.CANCEL;
import static javafx.scene.control.ButtonType.FINISH;
import static javafx.stage.Modality.NONE;

import com.bookkeeper.csv.CsvRecordWrapper;
import com.bookkeeper.domain.account.Account;
import com.bookkeeper.domain.entry.Entry;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.util.StringConverter;

public class CsvImportDialog extends Dialog<List<Entry>> {

  private List<CsvRecordWrapper> csvRecords;

  private ComboBox<Account> accountComboBox;

  public CsvImportDialog(List<CsvRecordWrapper> records) {
    super();
    this.csvRecords = records;
    initDialog();
  }

  private void initDialog() {
    initModality(NONE);
    setTitle(CSV_IMPORT_DIALOG_TITLE);
    getDialogPane().setContent(buildContentPane());
    getDialogPane().getButtonTypes().addAll(CANCEL, FINISH);
    configFinishButton();
    setResultConverter();
  }

  private void setResultConverter() {
    setResultConverter(buttonType -> {
      if (buttonType == FINISH) {
        return csvRecords.stream().map(CsvRecordWrapper::getEntry).collect(toList());
      }
      return null;
    });
  }

  private Pane buildContentPane() {
    var comboBoxPane = buildComboBoxPane();
    var tableView = buildCsvTableView();
    var tablePane = new ScrollPane(tableView);
    return new VBox(comboBoxPane, tablePane);
  }

  private Pane buildComboBoxPane() {

    var accountLabel = new Label("Account:");
    accountComboBox = buildAccountComboBox();
    var pane = new FlowPane();

    pane.getChildren().add(accountLabel);
    pane.getChildren().add(accountComboBox);

    return pane;
  }

  private ComboBox<Account> buildAccountComboBox() {
    var comboBox = new ComboBox<Account>(getAccountComboBoxModel());
    //comboBox.valueProperty().addListener();
    var account = getCurrentAccount();
    //System.out.println(account);
    comboBox.getSelectionModel().select(account);
    comboBox.setConverter(new StringConverter<>() {
      @Override
      public String toString(Account value) {
        return asOptional(value).map(Account::getName).orElse(null);
      }

      @Override
      public Account fromString(String string) {
        return null;
      }
    });
    return comboBox;
  }

  private CsvTableView buildCsvTableView() {
    return new CsvTableView(getModel());
  }

  private ObservableList<CsvRecordWrapper> getModel() {
    return observableArrayList(csvRecords);
  }

  private void configFinishButton() {
    getFinishButton().addEventFilter(ACTION, event -> {
      if (isValidInput()) {
        updateEntries();
      } else {
        event.consume();
      }
    });
  }

  private Button getFinishButton() {
    return (Button) getDialogPane().lookupButton(FINISH);
  }

  private boolean isValidInput() {
    if (hasValidationErrors()) {
      showAlertDialog(ERROR,"There are records with unresolved errors.");
      return false;
    }
    return true;
  }

  private boolean hasValidationErrors() {
    return csvRecords.stream().map(CsvRecordWrapper::getErrors).anyMatch(StringUtils::isNotEmpty);
  }

  private void updateEntries() {
    csvRecords.forEach(this::updateEntry);
  }

  private void updateEntry(CsvRecordWrapper record) {
    var account = getSelectedAccount();
    var entry = record.getEntry();
    entry.setAccount(account);
    entry.setCurrency(account.getCurrency());
  }

  private ObservableList<Account> getAccountComboBoxModel() {
    var accounts = getAccountRoot().collectLeafChildren();
    return observableArrayList(accounts);
  }

  private Account getSelectedAccount() {
    return accountComboBox.getSelectionModel().getSelectedItem();
  }
}
