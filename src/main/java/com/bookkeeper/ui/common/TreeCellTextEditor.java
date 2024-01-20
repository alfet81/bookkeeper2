package com.bookkeeper.ui.common;

import com.bookkeeper.account.entity.Account;

import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeTableCell;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;

public class TreeCellTextEditor extends TreeTableCell<Account, String> {
  private HBox panel;
  private Label imageLabel;
  private TextField textField;

  @Override
  public void startEdit() {
    super.startEdit();


/*    if (panel == null) {
      createTextEditor();
    }
    setText(null);
    setGraphic(panel);
    textField.selectAll();*/
  }

  @Override
  public void cancelEdit() {
    super.cancelEdit();
/*    setText(getItem());
    setGraphic(getTreeTableRow().getTreeItem().getGraphic());*/
  }

  @Override
  public void updateItem(String item, boolean empty) {
    super.updateItem(item, empty);

/*    if (empty) {
      setText(null);
      setGraphic(null);
    } else {
      if (isEditing()) {
        if (textField != null) {
          textField.setText(getString());
        }
        setText(null);
        setGraphic(panel);
      } else {
        setText(getString());
        setGraphic(getTreeTableRow().getTreeItem().getGraphic());
      }
    }*/
  }

  private void createTextEditor() {
    textField = new TextField(getString());
    textField.setOnKeyReleased(new EventHandler<KeyEvent>() {

      @Override
      public void handle(KeyEvent t) {
        if (t.getCode() == KeyCode.ENTER) {
          commitEdit(textField.getText());
        } else if (t.getCode() == KeyCode.ESCAPE) {
          cancelEdit();
        }
      }
    });

    imageLabel = new Label();
    imageLabel.setGraphic(getTreeTableRow().getTreeItem().getGraphic());
    panel = new HBox(imageLabel, textField);
  }

  private String getString() {
    return getItem() == null ? "" : getItem();
  }
}
