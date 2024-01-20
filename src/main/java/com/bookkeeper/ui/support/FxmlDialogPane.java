package com.bookkeeper.ui.support;

import com.bookkeeper.exceptions.ApplicationException;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import java.io.IOException;
import java.net.URL;

public abstract class FxmlDialogPane extends AnchorPane implements Initializable {


  protected FxmlDialogPane() {
    try {

      var loader = new FXMLLoader(getResourceURL());

      loader.setRoot(this);
      loader.setController(this);
      loader.load();

    } catch (IOException e) {
      throw new ApplicationException("Failed to load FXML Dialog Pane.", e);
    }
  }

  private URL getResourceURL() {
    return getClass().getResource(getFxmlResourceFile());
  }

  protected abstract String getFxmlResourceFile();
}
