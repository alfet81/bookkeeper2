package com.bookkeeper.category.view.gui;

import static com.bookkeeper.common.gui.support.DialogHelper.buildCustomDialog;

import com.bookkeeper.BookkeeperApplication;
import com.bookkeeper.common.exception.ApplicationException;
import org.springframework.stereotype.Component;

import javafx.stage.Stage;

@Component
public class CategoryManager {

  private Stage categoryDialog;

  public void showDialog() {
    getCategoryDialog().showAndWait();
  }

  private Stage getCategoryDialog() {
    if (categoryDialog == null) {
      try {
        categoryDialog = buildCustomDialog(new CategoryManagerDialog());
        categoryDialog.initOwner(BookkeeperApplication.getStage());
      } catch (Exception e) {
        System.out.println(e);
        throw new ApplicationException("Ooops!!", e);
      }
    }

    return categoryDialog;
  }
}
