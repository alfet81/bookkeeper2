package com.bookkeeper.ui.dialog;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.DialogPane;
import java.io.IOException;

public abstract class FxmlDialogPane extends DialogPane implements Initializable {

  public FxmlDialogPane() throws IOException {
    var loader = new FXMLLoader(getClass().getResource(getFxmlResourceFile()));
    loader.setRoot(this);
    loader.setController(this);
    loader.load();
  }

  protected abstract String getFxmlResourceFile();
}
