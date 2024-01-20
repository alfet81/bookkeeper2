package com.bookkeeper.category.ui;

import static com.bookkeeper.ui.support.DialogHelper.buildCustomDialog;

import com.bookkeeper.Application;
import com.bookkeeper.exceptions.ApplicationException;
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
        categoryDialog.initOwner(Application.getStage());
      } catch (Exception e) {
        System.out.println(e);
        throw new ApplicationException("Ooops!!", e);
      }
    }

    return categoryDialog;
  }
}
