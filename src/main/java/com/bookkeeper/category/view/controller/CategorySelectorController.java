package com.bookkeeper.category.view.controller;

import com.bookkeeper.category.view.model.CategorySelectorViewModel;
import com.bookkeeper.category.view.model.CategoryTreeViewItem;
import com.bookkeeper.category.view.gui.CategorySelectorDialog;
import jfx.support.fxml.annotation.FXMLController;
import lombok.RequiredArgsConstructor;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

@FXMLController
@RequiredArgsConstructor
public class CategorySelectorController implements Initializable {

  private final CategorySelectorViewModel viewModel;

  @FXML
  private TextField categoryTextField;


  @FXML
  public void showCategorySelectionDialog() {

    var rootItem = viewModel.getCategoryTree();
    var selectedCategory = viewModel.getSelectedCategory();
    var categoryDialog = new CategorySelectorDialog("Select category", rootItem);

    if (selectedCategory != null) {
      categoryDialog.setSelectedItem(selectedCategory.getName());
      categoryDialog.expandTreeView(rootItem);
    }

    var result = categoryDialog.showAndWait();
    result.map(CategoryTreeViewItem::getValue).ifPresent(viewModel::setSelectedCategory);
  }


  @FXML
  public void clearCategorySelection() {
    viewModel.setSelectedCategory(null);
  }


  @Override
  public void initialize(URL location, ResourceBundle resources) {
    categoryTextField.textProperty().bind(Bindings.createStringBinding(() -> {
      var selectedCategory = viewModel.selectedCategoryProperty().get();
      return selectedCategory != null ? selectedCategory.getName() : null;
    }, viewModel.selectedCategoryProperty()));
  }
}
