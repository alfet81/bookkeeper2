package com.bookkeeper.account.view.gui;

import static javafx.collections.FXCollections.observableArrayList;

import com.bookkeeper.account.jpa.Account;
import com.bookkeeper.common.model.currency.CurrencyUtils;
import com.bookkeeper.common.gui.support.FxmlDialogPane;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javax.money.CurrencyUnit;

public class AccountEditorDialogPane extends FxmlDialogPane {

  @FXML
  private TextField nameTextField;

  @FXML
  private ComboBox<CurrencyUnit> currencyComboBox;


  private static ObservableList<CurrencyUnit> getCurrencyItems() {
    List<CurrencyUnit> items = new ArrayList<>();
    items.add(null);
    items.addAll(CurrencyUtils.SUPPORTED_CURRENCIES);
    return observableArrayList(items);
  }


  @Override
  protected String getFxmlResourceFile() {
    return "/com/bookkeeper/account/view/AccountEditor.fxml";
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
      //String currencyCode = (account.getCurrency() != null ? account.getCurrency().getCurrencyCode() : null);
      //selectedCurrency = forCurrencyCode(currencyCode).orElse(null);
      //currencyComboBox.setDisable(!account.isLeaf());
    }

    currencyComboBox.setValue(selectedCurrency);
  }
}
