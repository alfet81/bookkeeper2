package com.bookkeeper.csv.view.gui;

import static com.bookkeeper.common.utils.Constants.CSV_IMPORT_DIALOG_TITLE;

import static javafx.collections.FXCollections.observableArrayList;
import static javafx.event.ActionEvent.ACTION;
import static javafx.scene.control.ButtonType.CANCEL;
import static javafx.scene.control.ButtonType.FINISH;
import static javafx.scene.control.ButtonType.NEXT;
import static javafx.stage.Modality.NONE;

import static java.util.Collections.emptyList;

import com.bookkeeper.account.jpa.Account;
import com.bookkeeper.account.view.AccountSelectorView;
import com.bookkeeper.category.view.CategorySelectorView;
import com.bookkeeper.csv.view.model.CsvImportDialogViewModel;
import com.bookkeeper.csv.view.model.CsvRecordItem;

import javafx.beans.binding.Bindings;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.util.List;

public class CsvImportDialog extends Dialog<List<CsvRecordItem>> {

  private final AccountSelectorView accountSelectorView;
  private final CategorySelectorView categorySelectorView;
  private final CsvImportDialogViewModel viewModel;

  private CsvRecordTableView csvRecordTableView;


  public CsvImportDialog(AccountSelectorView accountSelectorView, CategorySelectorView categorySelectorView,
      CsvImportDialogViewModel viewModel) {
    this.accountSelectorView = accountSelectorView;
    this.categorySelectorView = categorySelectorView;
    this.viewModel = viewModel;
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
    setResultConverter(
        buttonType -> (buttonType == FINISH ? viewModel.getCsvRecordItems().stream().toList() : emptyList()));
  }


  private Pane buildContentPane() {
    var accountSelectorPane = buildAccountSelectorPane();
    var accountCurrencyDisplayPane = buildAccountCurrencyDisplayPane();
    var categorySelectorPane = buildCategorySelectorPane();
    csvRecordTableView = buildCsvTableView();
    var tablePane = new ScrollPane(csvRecordTableView);
    return new VBox(accountSelectorPane, accountCurrencyDisplayPane, categorySelectorPane, tablePane);
  }


  private Pane buildAccountSelectorPane() {
    var pane = new Pane();
    pane.getChildren().add(accountSelectorView.getView());
    return pane;
  }


  private Pane buildCategorySelectorPane() {
    var pane = new Pane();
    pane.getChildren().add(categorySelectorView.getView());
    return pane;
  }


  private Pane buildAccountCurrencyDisplayPane() {

    var currencyTextField = new TextField();
    currencyTextField.setEditable(false);
    var label = new Label("Currency: ");

    var pane = new HBox();
    pane.getChildren().add(label);
    pane.getChildren().add(currencyTextField);

    currencyTextField.textProperty().bind(Bindings.createStringBinding(() -> {
      var selectedAccount = viewModel.selectedAccountProperty().get();
      if (selectedAccount == null) {
        return null;
      }
      return selectedAccount.getCurrency().getCurrencyCode();
    }, viewModel.selectedAccountProperty()));

    return pane;
  }


  private CsvRecordTableView buildCsvTableView() {
    return new CsvRecordTableView(viewModel.getCsvRecordItems());
  }


  private void configNextButton() {
    getButton(NEXT).addEventFilter(ACTION, event -> {
      viewModel.validateCsvRecords();
      if (!viewModel.hasCsvRecordValidationErrors()) {
        getDialogPane().getButtonTypes().removeAll(NEXT);
        getDialogPane().getButtonTypes().addAll(FINISH);
        //configFinishButton();
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


  private void updateEntries() {
    //csvRecordItems.forEach(this::updateEntry);
  }


  private void updateEntry(CsvRecordItem record) {
    //var account = getSelectedAccount();
    //var entry = record.getEntry();
    //entry.setAccount(account);
    //entry.setCurrency(account.getCurrency());
  }


  private ObservableList<Account> getAccountComboBoxModel() {
    List<Account> accounts = emptyList();//getAccountRoot().collectLeafChildren();
    return observableArrayList(accounts);
  }


  private Account getSelectedAccount() {
    return null;
  }
}
