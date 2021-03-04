package com.bookkeeper.ui.support;

import static com.bookkeeper.common.AppConstants.CSV_FILE_EXTENSION;
import static com.bookkeeper.common.AppConstants.CSV_FILE_FILTER_DESCRIPTION;
import static com.bookkeeper.utils.MiscUtils.asOptional;

import java.io.File;
import java.util.Optional;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

public class DialogHelper {

  private static ExtensionFilter CSV_FILE_FILTER = new ExtensionFilter(CSV_FILE_FILTER_DESCRIPTION,
      CSV_FILE_EXTENSION);

  public static void showExceptionDialog(Exception e) {
    new ExceptionDialog(e).showAndWait();
  }

  public static void showAlertDialog(AlertType alertType, String errorText) {
    AlertDialog.builder().alertType(alertType).contentText(errorText).build().showAndWait();
  }

  public static Optional<File> showCsvFileChooserDialog() {
    var csvChooser = new FileChooser();
    csvChooser.getExtensionFilters().add(CSV_FILE_FILTER);
    return asOptional(csvChooser.showOpenDialog(null));
  }

  public static Stage buildCustomDialog(Pane dialogPane) {
    var stage = new Stage();
    stage.setScene(new Scene(dialogPane));
    stage.setMinHeight(200);
    stage.setMinWidth(300);
    return stage;
  }
}
