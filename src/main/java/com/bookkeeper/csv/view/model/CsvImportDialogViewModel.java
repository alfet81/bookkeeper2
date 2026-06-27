package com.bookkeeper.csv.view.model;

import static com.bookkeeper.csv.view.model.CsvRecordStatus.ERROR;

import com.bookkeeper.account.model.Account;
import com.bookkeeper.account.view.model.AccountSelectorViewModel;
import com.bookkeeper.category.model.Category;
import com.bookkeeper.category.view.model.CategorySelectorViewModel;
import com.bookkeeper.csv.model.CsvRecord;
import com.bookkeeper.csv.service.CsvDataImportService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.XSlf4j;
import org.springframework.stereotype.Component;

import javafx.beans.property.ObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.File;
import java.util.function.Function;

@XSlf4j
@Component
@RequiredArgsConstructor
public class CsvImportDialogViewModel {

  private final CsvDataImportService csvDataImportService;
  private final AccountSelectorViewModel accountSelectorViewModel;
  private final CategorySelectorViewModel categorySelectorViewModel;
  private final Function<CsvRecord, CsvRecordItem> csvRecordItemFactory;

  @Getter
  private ObservableList<CsvRecordItem> csvRecordItems;


  public ObjectProperty<Account> selectedAccountProperty() {
    return accountSelectorViewModel.selectedAccountProperty();
  }


  public ObjectProperty<Category> selectedCategoryProperty() {
    return categorySelectorViewModel.selectedCategoryProperty();
  }


  public void loadFile(File csvFile) {
    log.entry(csvFile.getName());
    var csvRecords = csvDataImportService.loadCsvFile(csvFile);

    if (csvRecords.isEmpty()) {
      log.info("No entries found in csv file");
      csvRecordItems = FXCollections.emptyObservableList();
    } else {

      log.info("Found {} records in CSV file '{}'", csvRecords.size(), csvFile.getName());

      csvRecords.forEach(csvRecord -> log.trace("{}", csvRecord));

      var items = csvRecords.stream()
          .map(this::buildCsvRecordItem)
          .toList();

      csvRecordItems = FXCollections.observableList(items);
      for (var item : csvRecordItems) {
        item.accountProperty().bind(selectedAccountProperty());
        item.categoryProperty().bind(selectedCategoryProperty());
      }
    }
  }


  private CsvRecordItem buildCsvRecordItem(CsvRecord csvRecord) {
    return csvRecordItemFactory.apply(csvRecord);
  }


  public void validateCsvRecords() {
    csvRecordItems.parallelStream().forEach(CsvRecordItem::validate);
  }


  public boolean hasCsvRecordValidationErrors() {
    return csvRecordItems.stream()
        .map(CsvRecordItem::getStatus)
        .anyMatch(status -> status == ERROR);
  }


  public void validateSelectedAccount() {
    if (selectedAccountProperty().getValue() == null) {

    }
  }
  public void validateSelectedCategory() {}
}
