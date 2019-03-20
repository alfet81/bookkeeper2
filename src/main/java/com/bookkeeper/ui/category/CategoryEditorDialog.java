package com.bookkeeper.ui.category;

import static javafx.scene.control.ButtonType.CANCEL;
import static javafx.scene.control.ButtonType.FINISH;

import com.bookkeeper.domain.category.Category;

import javafx.scene.control.Dialog;

public class CategoryEditorDialog extends Dialog<Category> {

  private CategoryEditorDialogPane dialogPane;
  private Category category;

  public CategoryEditorDialog(Category category) {
    this.category = category;
    initDialog();
  }

  private void initDialog() {
    dialogPane = new CategoryEditorDialogPane();
    dialogPane.setCategory(category);

    getDialogPane().setContent(dialogPane);
    getDialogPane().getButtonTypes().addAll(CANCEL, FINISH);
    setResultConverter();
  }

  private void setResultConverter() {
    setResultConverter(buttonType -> {
      if (buttonType == FINISH) {
        category.setName(dialogPane.getName());
        category.setEntryType(dialogPane.getEntryType());
        return category;
      }
      return null;
    });
  }
}
