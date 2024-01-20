package com.bookkeeper.ui.view;

import static com.bookkeeper.ui.support.DialogHelper.showExceptionDialog;

import com.bookkeeper.account.ui.AccountComponent;
import com.bookkeeper.csv.ui.CsvImportManager;
import de.felixroske.jfxsupport.FXMLController;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import lombok.RequiredArgsConstructor;
import java.net.URL;
import java.util.ResourceBundle;

@FXMLController
@RequiredArgsConstructor
public class MenuBarController implements Initializable {

  private final CsvImportManager csvImportManager;
  private final AccountComponent accountComponent;
  @FXML
  private MenuBar menuBar;


  @FXML
  public void importCsvFile(final Event event) {

    var menuItem = (MenuItem) event.getSource();

    try {
      menuItem.setDisable(true);
      csvImportManager.importCsvFile();
    } catch (Exception e) {
      e.printStackTrace();
      showExceptionDialog(e);
    } finally {
      menuItem.setDisable(false);
    }
  }


  @FXML
  public void manageAccounts(final Event event) {

    var menuItem = (MenuItem) event.getSource();

    try {

      menuItem.setDisable(true);

      accountComponent.showDialog();
    } catch (Exception e) {
      e.printStackTrace();
      showExceptionDialog(e);
    } finally {
      menuItem.setDisable(false);
    }
  }


  @FXML
  public void manageCategories(final Event event) {

    var menuItem = (MenuItem) event.getSource();

    try {
      menuItem.setDisable(true);
      //categoryManager.showDialog();
    } catch (Exception e) {
      e.printStackTrace();
      showExceptionDialog(e);
    } finally {
      menuItem.setDisable(false);
    }
  }


  @Override
  public void initialize(URL location, ResourceBundle resources) {
    final String os = System.getProperty("os.name");
    if (os != null && os.startsWith("Mac")) {
      menuBar.useSystemMenuBarProperty().set(true);
      menuBar.setPrefHeight(0);
    }
  }
}
