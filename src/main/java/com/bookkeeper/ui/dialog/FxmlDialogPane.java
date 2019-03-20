package com.bookkeeper.ui.dialog;

import com.bookkeeper.exceptions.BookkeeperException;

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
      throw new BookkeeperException(e);
    }
  }

  private URL getResourceURL() {
    return getClass().getResource(getFxmlResourceFile());
  }

  protected abstract String getFxmlResourceFile();
}
