package com.bookkeeper.ui.model;

import static com.bookkeeper.core.type.Property.DEFAULT_ACCOUNT;

import com.bookkeeper.core.AppContext;
import com.bookkeeper.domain.account.Account;
import com.bookkeeper.domain.settings.Settings;
import com.bookkeeper.domain.settings.SettingsService;

import javafx.beans.value.ChangeListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AppGuiMediator {

  @Autowired
  private AppContext appContext;

  @Autowired
  private SettingsService settingsService;

  public ChangeListener<Account> getAccountListener() {
    return (observableValue, oldValue, newValue) -> {
      appContext.setCurrentAccount(newValue);
      updateDefaultAccountSettings(newValue);
    };
  }

  private void updateDefaultAccountSettings(Account account) {
    var defaultAccount = settingsService.findByProperty(DEFAULT_ACCOUNT).orElseGet(() ->
        new Settings(DEFAULT_ACCOUNT, account.getId().toString()));
    settingsService.save(defaultAccount);
  }
}
