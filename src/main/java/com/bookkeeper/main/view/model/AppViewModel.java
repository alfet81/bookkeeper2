package com.bookkeeper.main.view.model;

import static com.bookkeeper.utils.MiscUtils.asOptional;
import static javafx.collections.FXCollections.observableArrayList;
import static java.util.Collections.emptyList;

import com.bookkeeper.account.jpa.Account;
import com.bookkeeper.account.service.AccountService;
import com.bookkeeper.category.model.Category;
import com.bookkeeper.category.service.CategoryService;
import com.bookkeeper.common.model.date.DateRange;
import com.bookkeeper.entry.jpa.Entry;
import com.bookkeeper.entry.service.EntryService;
import com.bookkeeper.label.jpa.Label;
import com.bookkeeper.settings.model.UserSettings;
import com.bookkeeper.settings.service.UserSettingsService;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.TreeItem;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import java.util.Collections;
import java.util.List;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class AppViewModel implements ApplicationRunner {

  //private static final Account ACCOUNT_ROOT = AccountGroup.getRootInstance();
  //private static final Category CATEGORY_ROOT = CategoryGroup.getRootInstance();

  private final ObjectProperty<DateRange> dateRangeProperty = new SimpleObjectProperty<>();
  private final ObjectProperty<Account> accountProperty = new SimpleObjectProperty<>();
  private final ObjectProperty<Category> categoryProperty = new SimpleObjectProperty<>();
  private final ObjectProperty<Set<Label>> labelsProperty = new SimpleObjectProperty<>();

  @Getter
  private final List<Entry> entries = observableArrayList();

  private final AccountService accountService;
  private final CategoryService categoryService;
  private final EntryService entryService;
  private final UserSettingsService userSettingsService;

  @Getter
  private TreeItem<Account> accountTreeRoot;

  private TreeItem<Category> categoryTreeRoot;


/*  private static Category buildCategoryGroup(EntryType type) {
    return CategoryGroup.builder()
        .name(type.toString())
        .entryType(type)
        .build();
  }*/


  private void init() {
    initDateRange();
    initAccounts();
    initCategories();
    initLabels();
  }


  private void initAccounts() {
    //initAccountTree();
    accountProperty.addListener((ob, old, neo) -> modelChangeHandler());
  }


  private void initCategories() {
    initCategoryTree();
    categoryProperty.addListener((ob, old, neo) -> modelChangeHandler());
  }


  private void initLabels() {
    labelsProperty.addListener((ob, old, neo) -> modelChangeHandler());
  }


  private void initDateRange() {
    dateRangeProperty.set(getInitialDateRange());
    dateRangeProperty.addListener((observable, oldValue, newValue) -> modelChangeHandler());
  }


  private DateRange getInitialDateRange() {
    return DateRange.month(getUserSettings().getLastLoginDate());
  }


  private void modelChangeHandler() {
    var data = entryService.fetch(getSelectedDateRange());
    entries.clear();
    CollectionUtils.addAll(entries, data);
  }


  public void bindDateRange(ObjectProperty<DateRange> dateRangeProperty) {
    dateRangeProperty.bindBidirectional(this.dateRangeProperty);
  }


  public void bindAccount(ObjectProperty<Account> accountProperty) {
    accountProperty.bindBidirectional(this.accountProperty);
  }


  public void bindCategory(ObjectProperty<Category> categoryProperty) {
    categoryProperty.bindBidirectional(this.categoryProperty);
  }


  public void bindLabels(ObjectProperty<Set<Label>> labelsProperty) {
    labelsProperty.bindBidirectional(this.labelsProperty);
  }


  private DateRange getSelectedDateRange() {
    return dateRangeProperty.get();
  }


  private List<Account> getSelectedAccounts() {
    return emptyList(); // asOptional(accountProperty.get()).map(Account::collectLeafChildren).orElseGet(Collections::emptyList);
  }


/*  private List<Category> getSelectedCategories() {
    return asOptional(categoryProperty.get()).map(Category::collectLeafChildren)
        .orElseGet(Collections::emptyList);
  }*/


  private Set<Label> getSelectedLabels() {
    return asOptional(labelsProperty.get()).orElseGet(Collections::emptySet);
  }


  private void initAccountTree() {
    var accounts = accountService.getAllAccounts();
    /*if (accounts.isEmpty()) {
      var account = createDefaultAccount();
      ACCOUNT_ROOT.addChild(account);
    } else {
      buildTreeRoot(accounts, ACCOUNT_ROOT);
    }
    accountTreeRoot = buildItemTree(ACCOUNT_ROOT);*/
  }


  private Account createDefaultAccount() {
    return null;
  }


  public void initCategoryTree() {
    //var categories = categoryService.findAll();
    //if (categories.isEmpty()) {
      //categories = createDefaultCategories();
   // }
    //buildTreeRoot(categories, CATEGORY_ROOT);
    //categoryTreeRoot = buildItemTree(CATEGORY_ROOT);
  }


  protected List<Category> createDefaultCategories() {
    //var income = buildCategoryGroup(CREDIT);
    //var expenses = buildCategoryGroup(DEBIT);
    //categoryService.save(expenses);
    //categoryService.save(income);
    return emptyList();//List.of(expenses, income);
  }


  private UserSettings getUserSettings() {
    return userSettingsService.getUserSettings();
  }


  @Override
  public void run(ApplicationArguments args) throws Exception {
    init();
  }
}
