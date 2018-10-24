package com.bookkeeper.mvc.facade;

import static com.bookkeeper.ui.dialog.DialogHelper.showCsvFileChooserDialog;
import static com.bookkeeper.ui.dialog.DialogHelper.showExceptionDialog;

import com.bookkeeper.domain.edi.CsvDataImportService;
import com.bookkeeper.domain.entry.EntryService;
import com.bookkeeper.ui.csv.CsvImportDialog;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.io.File;
import java.util.Optional;

@Component
public class CsvImportManager {

  @Autowired
  private CsvDataImportService csvDataImportService;

  @Autowired
  private EntryService entryService;

  public void importCsvFile() {
    pickupFile().ifPresent(this::loadFile);
  }

  private Optional<File> pickupFile() {
    return showCsvFileChooserDialog();
  }

  private void loadFile(File csvFile) {
    try {
      var records = csvDataImportService.importCsvFile(csvFile);
      records.forEach(System.out::println);

      if (records.isEmpty()) {
        //TODO: show alert "not found"
        return;
      }

      new CsvImportDialog(records)
          .showAndWait()
          .ifPresent(l -> l.forEach(System.out::println));

      //result.ifPresent(entryService::save);
    } catch (Exception e) {
      showExceptionDialog(e);
    }
  }
}
