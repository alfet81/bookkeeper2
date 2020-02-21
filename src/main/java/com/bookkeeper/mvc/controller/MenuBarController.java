package com.bookkeeper.mvc.controller;

import static com.bookkeeper.ui.support.DialogHelper.showExceptionDialog;

import com.bookkeeper.Main;
import com.bookkeeper.mvc.facade.CsvImportManager;
import com.bookkeeper.mvc.view.AccountsView;

import de.felixroske.jfxsupport.FXMLController;
import org.springframework.beans.factory.annotation.Autowired;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.event.Event;
import javafx.stage.Modality;

@FXMLController
public class MenuBarController implements Initializable {

  @FXML
  private MenuBar menuBar;

  @Autowired
  private CsvImportManager csvImportManager;

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
      Main.showView(AccountsView.class, Modality.NONE);
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
