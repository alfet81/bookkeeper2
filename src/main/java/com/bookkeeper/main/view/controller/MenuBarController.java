package com.bookkeeper.main.view.controller;

import static com.bookkeeper.common.gui.support.DialogHelper.showExceptionDialog;

import com.bookkeeper.account.view.gui.AccountComponent;
import com.bookkeeper.csv.view.gui.CsvImportManager;
import jfx.support.fxml.annotation.FXMLController;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
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
  public void importCsvFile(Event event) {

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
  public void manageAccounts(Event event) {

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
  public void manageCategories(Event event) {

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
    String osName = System.getProperty("os.name");
    if (StringUtils.startsWith(osName, "Mac")) {
      menuBar.useSystemMenuBarProperty().set(true);
      menuBar.setPrefHeight(0);
    }
  }
}
