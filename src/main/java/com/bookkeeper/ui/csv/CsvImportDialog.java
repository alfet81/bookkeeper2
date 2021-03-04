package com.bookkeeper.ui.csv;

import static com.bookkeeper.app.AppConstants.CSV_IMPORT_DIALOG_TITLE;
import static com.bookkeeper.utils.MiscUtils.asOptional;

import static java.util.stream.Collectors.toList;
import static javafx.collections.FXCollections.observableArrayList;
import static javafx.event.ActionEvent.ACTION;
import static javafx.scene.control.ButtonType.CANCEL;
import static javafx.scene.control.ButtonType.FINISH;
import static javafx.scene.control.ButtonType.NEXT;
import static javafx.stage.Modality.NONE;

import com.bookkeeper.csv.CsvRecordEntry;
import com.bookkeeper.domain.account.Account;
import com.bookkeeper.domain.account.AccountGroup;
import com.bookkeeper.domain.entry.Entry;
import com.bookkeeper.type.CsvRecordStatus;

import java.util.Collections;
import java.util.List;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.util.StringConverter;

public class CsvImportDialog extends Dialog<List<Entry>> {

  private List<CsvRecordEntry> csvRecords;

  private ComboBox<Account> accountComboBox;

  private CsvTableView csvTableView;

  public CsvImportDialog(List<CsvRecordEntry> records) {
    super();
    this.csvRecords = records;
    initDialog();
  }

  private void initDialog() {
    initModality(NONE);
    setTitle(CSV_IMPORT_DIALOG_TITLE);
    getDialogPane().setContent(buildContentPane());
    getDialogPane().getButtonTypes().addAll(CANCEL, NEXT);
    configNextButton();
    setResultConverter();
  }

  private void setResultConverter() {
    setResultConverter(buttonType -> {
      if (buttonType == FINISH) {
        return csvRecords.stream().map(CsvRecordEntry::getEntry).collect(toList());
      }
      return null;
    });
  }

  private Pane buildContentPane() {
    csvTableView = buildCsvTableView();
    var tablePane = new ScrollPane(csvTableView);
    var comboBoxPane = buildComboBoxPane();
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
    var comboBox = new ComboBox<>(getAccountComboBoxModel());
    //comboBox.valueProperty().addListener();
    var account = new AccountGroup();// getCurrentAccount();
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

  private ObservableList<CsvRecordEntry> getModel() {
    return observableArrayList(csvRecords);
  }

  private void configNextButton() {
    getButton(NEXT).addEventFilter(ACTION, event -> {
      if (processCsvRecords()) {
        getDialogPane().getButtonTypes().removeAll(NEXT);
        getDialogPane().getButtonTypes().addAll(FINISH);
        configFinishButton();
      }
      event.consume();
    });
  }

  private void configFinishButton() {
    getButton(FINISH).addEventFilter(ACTION, event -> updateEntries());
  }

  private Button getButton(ButtonType buttonType) {
    return (Button) getDialogPane().lookupButton(buttonType);
  }

  private boolean processCsvRecords() {

    csvRecords.parallelStream().forEach(CsvRecordEntry::process);

    csvTableView.refresh();

    return csvRecords.parallelStream()
        .map(CsvRecordEntry::getStatus)
        .noneMatch(status -> status == CsvRecordStatus.ERROR);
  }

  private void updateEntries() {
    csvRecords.forEach(this::updateEntry);
  }

  private void updateEntry(CsvRecordEntry record) {
    var account = getSelectedAccount();
    var entry = record.getEntry();
    entry.setAccount(account);
    entry.setCurrency(account.getCurrency());
  }

  private ObservableList<Account> getAccountComboBoxModel() {
    List<Account> accounts = Collections.emptyList();//getAccountRoot().collectLeafChildren();
    return observableArrayList(accounts);
  }

  private Account getSelectedAccount() {
    return accountComboBox.getSelectionModel().getSelectedItem();
  }
}
