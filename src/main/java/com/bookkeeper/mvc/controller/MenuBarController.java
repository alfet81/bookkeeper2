package com.bookkeeper.mvc.controller;

import static com.bookkeeper.ui.dialog.DialogHelper.showExceptionDialog;

import com.bookkeeper.mvc.facade.AccountManager;
import com.bookkeeper.mvc.facade.CsvImportManager;

import de.felixroske.jfxsupport.FXMLController;
import org.springframework.beans.factory.annotation.Autowired;

import javafx.fxml.FXML;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.event.Event;
import javafx.stage.Window;

@FXMLController
public class MenuBarController {

  @FXML
  private MenuBar menuBar;

  @Autowired
  private CsvImportManager csvImportManager;

  @Autowired
  private AccountManager accountManager;

  @FXML
  public void importCsvFile(final Event event) {

    var menuItem = (MenuItem) event.getSource();

    try {
      menuItem.setDisable(true);
      csvImportManager.importCsvFile();
    } catch (Exception e) {
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
      accountManager.showDialog();
    } catch (Exception e) {
      showExceptionDialog(e);
    } finally {
      menuItem.setDisable(false);
    }
  }

  private Window getParentWindow() {
    return menuBar.getParent().getScene().getWindow();
  }
}
