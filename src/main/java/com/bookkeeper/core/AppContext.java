package com.bookkeeper.core;

import static com.bookkeeper.core.type.Property.DEFAULT_ACCOUNT;
import static com.bookkeeper.core.type.Property.PREFERRED_CURRENCY;
import static com.bookkeeper.core.utils.CommonUtils.asOptional;

import com.bookkeeper.domain.account.Account;
import com.bookkeeper.domain.account.AccountGroup;
import com.bookkeeper.domain.category.Category;
import com.bookkeeper.domain.label.Label;
import com.bookkeeper.core.exceptions.BookkeeperException;
import com.bookkeeper.core.type.Property;
import com.bookkeeper.domain.account.AccountService;
import com.bookkeeper.domain.category.CategoryService;
import com.bookkeeper.domain.label.LabelService;
import com.bookkeeper.domain.settings.SettingsService;

import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import lombok.AccessLevel;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import javax.annotation.PostConstruct;

@Component
@Getter
public class AppContext {

  @Getter(AccessLevel.NONE)
  private SettingsService settingsService;

  @Getter(AccessLevel.NONE)
  private AccountService accountService;

  @Getter(AccessLevel.NONE)
  private LabelService labelService;

  @Getter(AccessLevel.NONE)
  private CategoryService categoryService;

  @Getter(AccessLevel.NONE)
  private Map<Property, String> settings;

  private Set<Label> labels;

  private Category categoryRoot;

  private Account accountRoot;

  @Getter(AccessLevel.NONE)
  @Setter
  private Account currentAccount;

  @Autowired
  private AppContext(SettingsService settingsService, LabelService labelService,
      CategoryService categoryService, AccountService accountService) {

    this.settingsService = settingsService;
    this.labelService = labelService;
    this.categoryService = categoryService;
    this.accountService = accountService;
  }

  @PostConstruct
  public void init() {
    initSettings();
    initAccountRoot();
    initCategoryRoot();
    initLabels();
  }

  public Optional<String> getProperty(Property property) {
    return asOptional(settings.get(property)).or(() -> asOptional(property.getDefaultValue()));
  }

  public Account getCurrentAccount() {
    if (currentAccount == null) {
      currentAccount = getDefaultAccount();
    }
    return currentAccount;
  }

  private Account getDefaultAccount() {

    var accountId = getProperty(DEFAULT_ACCOUNT).map(Long::valueOf).orElse(null);

    return asOptional(accountId).flatMap(accountService::getAccount)
        .orElseGet(this::getAnyAvailableAccount);
  }

  private Account getAnyAvailableAccount() {
    return getAccountRoot().collectLeafChildren().stream().findAny().orElseThrow(() ->
      new BookkeeperException("No any account has been found"));
  }

  private void initSettings() {
    settings = settingsService.getAllSettings();
  }

  private void initAccountRoot() {
    accountRoot = accountService.getRootAccount().orElseGet(this::createDefaultAccountModel);
  }

  private void initCategoryRoot() {
    categoryRoot = categoryService.getRootCategory().orElse(null);
  }

  private void initLabels() {
    labels = labelService.getLabels();
  }

  private Account createDefaultAccountModel() {
    var root = AccountGroup.groupBuilder()
        .name("My accounts")
        .build();

    var account = Account.builder()
        .name("General account")
        .currency(getPreferredCurrency())
        .initialBalance(BigDecimal.ZERO)
        .build();

    root.addChild(account);

    accountService.save(root);

    return root;
  }

  public Currency getPreferredCurrency() {
    return getProperty(PREFERRED_CURRENCY).map(Currency::getInstance).orElse(null);
  }
}