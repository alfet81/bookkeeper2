package com.bookkeeper.main.view.controller;

import static com.bookkeeper.common.gui.support.DialogHelper.showExceptionDialog;

import com.bookkeeper.account.view.gui.AccountComponent;
import com.bookkeeper.category.view.gui.CategoryManager;
import com.bookkeeper.csv.view.gui.CsvImportManager;
import jfx.support.fxml.annotation.FXMLController;
import lombok.RequiredArgsConstructor;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.stage.Window;
import java.net.URL;
import java.util.ResourceBundle;

@FXMLController
@RequiredArgsConstructor
public class ToolBarController implements Initializable {

  private final CsvImportManager csvImportManager;
  private final AccountComponent accountComponent;
  private final CategoryManager categoryManager;

  @FXML
  private MenuBar menuBar;

  @FXML
  private BorderPane mainWindow;


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

    var button = (Button) event.getSource();

    try {
      button.setDisable(true);
      accountComponent.showDialog();
    } catch (Exception e) {
      e.printStackTrace();
      showExceptionDialog(e);
    } finally {
      button.setDisable(false);
    }
  }


  @FXML
  public void manageCategories(final Event event) {

    var menuItem = (MenuItem) event.getSource();

    try {
      menuItem.setDisable(true);
      categoryManager.showDialog();
    } catch (Exception e) {
      e.printStackTrace();
      showExceptionDialog(e);
    } finally {
      menuItem.setDisable(false);
    }
  }


  private Window getParentWindow() {
    return menuBar.getParent().getScene().getWindow();
  }


  @FXML
  @Override
  public void initialize(URL location, ResourceBundle resources) {

  }
}
