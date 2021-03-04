package com.bookkeeper.mvc.facade;

import static com.bookkeeper.ui.support.DialogHelper.buildCustomDialog;

import com.bookkeeper.Application;
import com.bookkeeper.exceptions.BookkeeperException;
import com.bookkeeper.ui.category.CategoryManagerDialog;
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
        throw new BookkeeperException("Ooops!!", e);
      }
    }

    return categoryDialog;
  }
}
