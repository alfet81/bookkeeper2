package com.bookkeeper.ui.dialog;

import static javafx.scene.control.Alert.AlertType.ERROR;

import java.io.PrintWriter;
import java.io.StringWriter;

import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;


public class ExceptionDialog extends Alert {

  private final static String DIALOG_TITLE = "Error";

  private Exception exception;

  public ExceptionDialog(Exception exception) {
    super(ERROR);
    this.exception = exception;
    init();
  }

  private void init() {

    setTitle(DIALOG_TITLE);
    setHeaderText(exception.getClass().getName());
    setContentText(exception.getMessage());

    getDialogPane().setExpandableContent(buildExceptionDetailsPane());
  }

  private String getExceptionStackTrace() {
    StringWriter sw = new StringWriter();
    PrintWriter pw = new PrintWriter(sw);
    exception.printStackTrace(pw);
    return sw.toString();
  }

  private Pane buildExceptionDetailsPane() {
    Label label = new Label("Exception details:");

    TextArea textArea = new TextArea(getExceptionStackTrace());
    textArea.setEditable(false);
    textArea.setWrapText(true);

    textArea.setMaxWidth(Double.MAX_VALUE);
    textArea.setMaxHeight(Double.MAX_VALUE);
    GridPane.setVgrow(textArea, Priority.ALWAYS);
    GridPane.setHgrow(textArea, Priority.ALWAYS);

    GridPane pane = new GridPane();
    pane.setMaxWidth(Double.MAX_VALUE);
    pane.add(label, 0, 0);
    pane.add(textArea, 0, 1);

    return pane;
  }
}
