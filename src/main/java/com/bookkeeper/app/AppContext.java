package com.bookkeeper.app;

import static com.bookkeeper.types.Property.DEFAULT_ACCOUNT;
import static com.bookkeeper.types.Property.PREFERRED_CURRENCY;
import static com.bookkeeper.utils.MiscUtils.asOptional;

import com.bookkeeper.domain.account.Account;
import com.bookkeeper.domain.account.AccountService;
import com.bookkeeper.domain.category.Category;
import com.bookkeeper.domain.category.CategoryService;
import com.bookkeeper.domain.entry.EntryService;
import com.bookkeeper.domain.label.Label;
import com.bookkeeper.exceptions.BookkeeperException;
import com.bookkeeper.types.Property;
import com.bookkeeper.domain.label.LabelService;
import com.bookkeeper.domain.settings.SettingsService;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.Currency;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import javax.annotation.PostConstruct;

@Component
public class AppContext implements ApplicationContextAware {

  private static ApplicationContext springAppContext;

  private static SettingsService settingsService;

  private static AccountService accountService;

  private static CategoryService categoryService;

  private static EntryService entryService;

  private static LabelService labelService;

  private static Map<Property, String> settings;

  private static Set<Label> labels;

  private static Category categoryRoot;

  private static Account accountRoot;

  private static Account currentAccount;

  @Autowired
  private AppContext(SettingsService settingsService, LabelService labelService,
      CategoryService categoryService, AccountService accountService) {

    AppContext.settingsService = settingsService;
    AppContext.labelService = labelService;
    AppContext.categoryService = categoryService;
    AppContext.accountService = accountService;
  }

  @PostConstruct
  public static void init() {
    initSettings();
    initAccountRoot();
    initCategoryRoot();
    initLabels();
  }

  public static Optional<String> getProperty(Property property) {
    return asOptional(settings.get(property)).or(() -> asOptional(property.getDefaultValue()));
  }

  public static Account getCurrentAccount() {
    if (currentAccount == null) {
      currentAccount = getDefaultAccount();
    }
    return currentAccount;
  }

  private static Account getDefaultAccount() {

    var accountId = getProperty(DEFAULT_ACCOUNT).map(Long::valueOf).orElse(null);

    return asOptional(accountId).flatMap(accountService::getAccount)
        .orElseGet(AppContext::getAnyAvailableAccount);
  }

  private static Account getAnyAvailableAccount() {
    return accountRoot.collectLeafChildren().stream().findAny().orElseThrow(() ->
      new BookkeeperException("No any account has been found"));
  }

  private static void initSettings() {
    settings = settingsService.getAllSettings();
  }

  private static void initAccountRoot() {
    accountRoot = accountService.getRootAccount();
  }

  private static void initCategoryRoot() {
    categoryRoot = categoryService.getRootCategory();
  }

  private static void initLabels() {
    labels = labelService.getLabels();
  }

  public static Currency getPreferredCurrency() {
    return getProperty(PREFERRED_CURRENCY).map(Currency::getInstance).orElse(null);
  }

  public static Account getAccountRoot() {
    return accountRoot;
  }

  public static Category getCategoryRoot() {
    return categoryRoot;
  }

  public static Set<Label> getLabels() {
    return labels;
  }

  @Override
  public void setApplicationContext(ApplicationContext context) throws BeansException {
    springAppContext = context;
  }

  public static <T> T getBean(Class<T> beanClass) {
    return springAppContext.getBean(beanClass);
  }

  public static <T> T getBean(Class<T> beanClass, Object...args) {
    return springAppContext.getBean(beanClass, args);
  }

  public static AccountService getAccountService() {
    return accountService;
  }

  public static CategoryService getCategoryService() {
    return categoryService;
  }

  public static EntryService getEntryService() {
    return entryService;
  }

  public static SettingsService getSettingsService() {
    return settingsService;
  }

  public static LabelService getLabelService() {
    return labelService;
  }
}