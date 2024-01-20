package com.bookkeeper.csv.ui;

import static com.bookkeeper.ui.support.DialogHelper.showCsvFileChooserDialog;
import static com.bookkeeper.ui.support.DialogHelper.showExceptionDialog;

import com.bookkeeper.csv.service.CsvDataImportService;
import com.bookkeeper.entry.service.EntryService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import java.io.File;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CsvImportManager {

  private final CsvDataImportService csvDataImportService;

  private final EntryService entryService;

  public void importCsvFile() {
    pickupFile().ifPresent(this::loadFile);
  }

  private Optional<File> pickupFile() {
    return showCsvFileChooserDialog();
  }

  private void loadFile(File csvFile) {
    try {

      var records = csvDataImportService.importCsvFile(csvFile);

      if (records.isEmpty()) {
        //TODO: show alert "not found"
        return;
      }

      records.forEach(System.out::println);

      var result = new CsvImportDialog(records).showAndWait();

      result.ifPresent(entryService::save);

    } catch (Exception e) {
      e.printStackTrace();
      showExceptionDialog(e);
    }
  }
}
