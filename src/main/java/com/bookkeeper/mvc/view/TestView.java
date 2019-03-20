package com.bookkeeper.mvc.view;

import de.felixroske.jfxsupport.AbstractFxmlView;
import de.felixroske.jfxsupport.FXMLView;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;

@FXMLView
public class TestView extends AbstractFxmlView {

  private Pane pane;

  public TestView() {
    pane = new Pane();
    pane.getChildren().add(new Button("Test"));
  }

  @Override
  public Parent getView() {
    return pane;
  }
}
