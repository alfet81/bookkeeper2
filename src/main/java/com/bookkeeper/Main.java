package com.bookkeeper;

import com.bookkeeper.mvc.view.MainWindow;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import de.felixroske.jfxsupport.AbstractJavaFxApplicationSupport;

@SpringBootApplication
public class Main extends AbstractJavaFxApplicationSupport {
  public static void main(String[] args) {
    launch(Main.class, MainWindow.class, args);
  }
}
