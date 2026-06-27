package com.bookkeeper.category.view.gui;

import static org.apache.commons.lang3.StringUtils.EMPTY;
import static javafx.collections.FXCollections.observableArrayList;

import com.bookkeeper.category.model.Category;
import com.bookkeeper.common.model.types.EntryType;
import com.bookkeeper.common.gui.support.FxmlDialogPane;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class CategoryEditorDialogPane extends FxmlDialogPane {

  @FXML
  private TextField nameTextField;

  @FXML
  private ComboBox<TypeWrapper> typeComboBox;


  private static ObservableList<TypeWrapper> getTypeItems() {
    var types = new ArrayList<TypeWrapper>();
    types.add(new TypeWrapper());
    types.addAll(EntryType.stream().map(TypeWrapper::new).toList());
    return observableArrayList(types);
  }


  @Override
  protected String getFxmlResourceFile() {
    return "/com/bookkeeper/category/view/CategoryEditor.fxml";
  }


  @FXML
  @Override
  public void initialize(URL location, ResourceBundle resources) {
    typeComboBox.getItems().addAll(getTypeItems());
  }


  public String getName() {
    return nameTextField.getText();
  }


  public EntryType getEntryType() {
    return typeComboBox.getValue().getValue();
  }


  public void setCategory(Category category) {
    var typeWrapper = new TypeWrapper();

    if (category != null) {
      nameTextField.setText(category.getName());
      typeWrapper = new TypeWrapper(category.getEntryType());
    }

    typeComboBox.setValue(typeWrapper);
  }


  private static class TypeWrapper {

    private EntryType value;


    TypeWrapper() {
    }


    TypeWrapper(EntryType value) {
      this.value = value;
    }


    EntryType getValue() {
      return value;
    }


    @Override
    public String toString() {
      return value != null ? value.toString() : EMPTY;
    }
  }
}
