package com.bookkeeper.csv.view.gui;

import static com.bookkeeper.common.gui.support.DialogHelper.showCsvFileChooserDialog;
import static com.bookkeeper.common.gui.support.DialogHelper.showExceptionDialog;

import com.bookkeeper.account.view.AccountSelectorView;
import com.bookkeeper.category.view.CategorySelectorView;
import com.bookkeeper.csv.view.model.CsvImportDialogViewModel;
import com.bookkeeper.entry.service.EntryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.XSlf4j;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.Optional;

@XSlf4j
@Component
@RequiredArgsConstructor
public class CsvImportManager {
  private final EntryService entryService;
  private final AccountSelectorView accountSelectorView;
  private final CategorySelectorView categorySelectorView;
  private final CsvImportDialogViewModel csvImportDialogViewModel;


  public void importCsvFile() {
    pickupFile().ifPresent(this::loadFile);
  }


  private Optional<File> pickupFile() {
    return showCsvFileChooserDialog();
  }


  private void loadFile(File csvFile) {
    log.entry(csvFile.getName());

    try {

      csvImportDialogViewModel.loadFile(csvFile);

      var result = new CsvImportDialog(accountSelectorView, categorySelectorView, csvImportDialogViewModel)
          .showAndWait();

      result.ifPresent(items -> {
        log.trace("Saving {} items", items.size());
        items.forEach(item -> log.trace("{}", item));
        //entryService.save(items);
      });

    } catch (Exception e) {
      log.error("Failed to load file", e);
      showExceptionDialog(e);
    }
  }
}
