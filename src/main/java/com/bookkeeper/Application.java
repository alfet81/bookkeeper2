package com.bookkeeper;

import com.bookkeeper.mvc.view.MainWindowView;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import de.felixroske.jfxsupport.AbstractJavaFxApplicationSupport;

@SpringBootApplication
public class Application extends AbstractJavaFxApplicationSupport {
  public static void main(String...args) {
    System.setProperty("com.apple.mrj.application.apple.menu.about.name", "Personal Bookkeeper");
    launch(Application.class, MainWindowView.class, args);
  }
}
