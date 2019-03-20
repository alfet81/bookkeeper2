package com.bookkeeper.mvc.controller;

import static com.bookkeeper.ui.dialog.DialogHelper.showExceptionDialog;

import com.bookkeeper.Main;
import com.bookkeeper.mvc.facade.AccountManager;
import com.bookkeeper.mvc.facade.CategoryManager;
import com.bookkeeper.mvc.facade.CsvImportManager;
import com.bookkeeper.mvc.view.MainWindowView;
import com.bookkeeper.mvc.view.TestView;

import de.felixroske.jfxsupport.FXMLController;
import org.springframework.beans.factory.annotation.Autowired;

import java.net.URL;
import java.util.ResourceBundle;
import javax.annotation.PostConstruct;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.event.Event;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Window;

@FXMLController
public class MenuController implements Initializable {

  @FXML
  private MenuBar menuBar;

  @FXML
  private BorderPane mainWindow;

  @Autowired
  private CsvImportManager csvImportManager;

  @Autowired
  private AccountManager accountManager;

  @Autowired
  private CategoryManager categoryManager;

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

    Main.showView(TestView.class, Modality.APPLICATION_MODAL);

/*    try {
      menuItem.setDisable(true);
      accountManager.showDialog();
    } catch (Exception e) {
      e.printStackTrace();
      showExceptionDialog(e);
    } finally {
      menuItem.setDisable(false);
    }*/
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
    final String os = System.getProperty("os.name");
    if (os != null && os.startsWith("Mac")) {
      menuBar.useSystemMenuBarProperty().set(true);
      menuBar.setPrefHeight(0);
    }
  }
}
