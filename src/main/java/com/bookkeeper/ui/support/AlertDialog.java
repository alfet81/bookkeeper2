package com.bookkeeper.ui.support;

import lombok.Builder;
import javafx.scene.control.Alert;

public class AlertDialog extends Alert {

  @Builder
  public AlertDialog(AlertType alertType, String title, String headerText, String contentText) {
    super(alertType);
    setTitle(title);
    setHeaderText(headerText);
    setContentText(contentText);
  }
}
