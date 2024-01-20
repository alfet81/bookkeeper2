package com.bookkeeper.ui.support;

import javafx.scene.control.Alert;
import lombok.Builder;

public class AlertDialog extends Alert {

  @Builder
  public AlertDialog(AlertType alertType, String title, String headerText, String contentText) {
    super(alertType);

    setTitle(title);
    setHeaderText(headerText);
    setContentText(contentText);
  }
}
