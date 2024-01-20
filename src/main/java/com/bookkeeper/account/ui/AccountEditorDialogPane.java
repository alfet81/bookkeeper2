package com.bookkeeper.account.ui;

import static com.bookkeeper.model.CurrencyUnit.forCurrencyCode;
import static javafx.collections.FXCollections.observableArrayList;

import com.bookkeeper.model.CurrencyUnit;
import com.bookkeeper.account.entity.Account;
import com.bookkeeper.ui.support.FxmlDialogPane;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

public class AccountEditorDialogPane extends FxmlDialogPane {

  @FXML
  private TextField nameTextField;

  @FXML
  private ComboBox<CurrencyUnit> currencyComboBox;

  public AccountEditorDialogPane() {}

  @Override
  protected String getFxmlResourceFile() {
    return "/com/bookkeeper/account/ui/view/AccountEditor.fxml";
  }

  @FXML
  @Override
  public void initialize(URL location, ResourceBundle resources) {
    currencyComboBox.getItems().addAll(getCurrencyItems());
  }

  public String getName() {
    return nameTextField.getText();
  }

  public CurrencyUnit getCurrencyUnit() {
    return currencyComboBox.getValue();
  }

  public void setAccount(Account account) {

    CurrencyUnit selectedCurrency = null;

    if (account != null) {

      nameTextField.setText(account.getName());

      String currencyCode = (account.getCurrency() != null ? account.getCurrency().getCurrencyCode()
          : null);

      selectedCurrency = forCurrencyCode(currencyCode).orElse(null);

      currencyComboBox.setDisable(!account.isLeaf());
    }

    currencyComboBox.setValue(selectedCurrency);
  }

  private static ObservableList<CurrencyUnit> getCurrencyItems() {

    var items = new ArrayList<CurrencyUnit>();

    items.add(null);

    items.addAll(CurrencyUnit.getValues());

    return observableArrayList(items);
  }
}
