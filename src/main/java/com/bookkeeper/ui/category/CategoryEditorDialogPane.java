package com.bookkeeper.ui.category;

import static org.apache.commons.lang3.StringUtils.EMPTY;
import static java.util.stream.Collectors.toList;
import static javafx.collections.FXCollections.observableArrayList;

import com.bookkeeper.type.EntryType;
import com.bookkeeper.domain.category.Category;
import com.bookkeeper.ui.support.FxmlDialogPane;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

public class CategoryEditorDialogPane extends FxmlDialogPane {

  @FXML
  private TextField nameTextField;

  @FXML
  private ComboBox<TypeWrapper> typeComboBox;

  public CategoryEditorDialogPane() {}

  @Override
  protected String getFxmlResourceFile() {
    return "/com/bookkeeper/ui/fxml/CategoryEditorView.fxml";
  }

  @FXML
  @Override
  public void initialize(URL location, ResourceBundle resources) {
    typeComboBox.getItems().addAll(getTypeItems());
  }

  private static class TypeWrapper {

    private EntryType value;

    TypeWrapper() {}

    TypeWrapper(EntryType value) {
      this.value = value;
    }

    EntryType getValue() {
      return value;
    }

    @Override
    public String toString() {
      return value == null ? EMPTY : value.toString();
    }
  }

  public String getName() {
    return nameTextField.getText();
  }

  public EntryType getEntryType() {
    return typeComboBox.getValue().getValue();
  }

  public void setCategory(Category category) {
    TypeWrapper type = new TypeWrapper();

    if (category != null) {

      nameTextField.setText(category.getName());

      type = new TypeWrapper(category.getEntryType());

    }

    typeComboBox.setValue(type);
  }

  private static ObservableList<TypeWrapper> getTypeItems() {
    var types = new ArrayList<TypeWrapper>();

    types.add(new TypeWrapper());

    types.addAll(EntryType.stream().map(TypeWrapper::new).collect(toList()));

    return observableArrayList(types);
  }
}
