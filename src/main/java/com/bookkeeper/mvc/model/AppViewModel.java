package com.bookkeeper.mvc.model;

import static com.bookkeeper.app.AppConstants.DEFAULT_GENERAL_ACCOUNT_NAME;
import static com.bookkeeper.type.EntryType.CREDIT;
import static com.bookkeeper.type.EntryType.DEBIT;
import static com.bookkeeper.type.Property.LAST_LOGIN_DATE;
import static com.bookkeeper.type.Property.PREFERRED_CURRENCY;
import static com.bookkeeper.type.TreeNode.buildTreeRoot;
import static com.bookkeeper.utils.DateRangeUtils.getMonthBounds;
import static com.bookkeeper.utils.MiscUtils.asOptional;
import static java.math.BigDecimal.ZERO;
import static java.util.stream.Collectors.toList;
import static javafx.collections.FXCollections.observableArrayList;

import com.bookkeeper.domain.account.Account;
import com.bookkeeper.domain.account.AccountGroup;
import com.bookkeeper.domain.account.AccountService;
import com.bookkeeper.domain.category.Category;
import com.bookkeeper.domain.category.CategoryGroup;
import com.bookkeeper.domain.category.CategoryService;
import com.bookkeeper.domain.entry.Entry;
import com.bookkeeper.domain.entry.EntryService;
import com.bookkeeper.domain.label.Label;
import com.bookkeeper.domain.settings.SettingsService;
import com.bookkeeper.dto.DateRange;
import com.bookkeeper.type.EntryType;
import com.bookkeeper.type.TreeNode;
import com.bookkeeper.ui.model.TreeNodeItem;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Currency;
import java.util.List;
import java.util.Set;
import javax.annotation.PostConstruct;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.TreeItem;

@Component
public class AppViewModel {

  private static final Account ACCOUNT_ROOT = AccountGroup.getRootInstance();

  private static final Category CATEGORY_ROOT = CategoryGroup.getRootInstance();

  private final ObjectProperty<DateRange> dateRangeProperty = new SimpleObjectProperty<>();

  private final ObjectProperty<Account> accountProperty = new SimpleObjectProperty<>();

  private final ObjectProperty<Category> categoryProperty = new SimpleObjectProperty<>();

  private final ObjectProperty<Set<Label>> labelsProperty = new SimpleObjectProperty<>();

  private final List<Entry> entries = observableArrayList();

  private TreeItem<Account> accountTreeRoot;

  private TreeItem<Category> categoryTreeRoot;

  @Autowired
  private SettingsService settingsService;

  @Autowired
  private AccountService accountService;

  @Autowired
  private CategoryService categoryService;

  @Autowired
  private EntryService entryService;

  @PostConstruct
  protected void init() {
    initDateRange();
    initAccounts();
    initCategories();
    initLabels();
  }

  private void initAccounts() {
    initAccountRoot();
    accountProperty.addListener((ob, old, neo) -> modelChangeHandler());
  }

  private void initCategories() {
    initCategoryRoot();
    categoryProperty.addListener((ob, old, neo) -> modelChangeHandler());
  }

  private void initLabels() {
    labelsProperty.addListener((ob, old, neo) -> modelChangeHandler());
  }

  private void initDateRange() {
    dateRangeProperty.set(getInitialDateRange());
    dateRangeProperty.addListener((ob, old, neo) -> modelChangeHandler());
  }

  private DateRange getInitialDateRange() {
    var date = (LocalDate) settingsService.getProperty(LAST_LOGIN_DATE);
    return getMonthBounds(date);
  }

  private void modelChangeHandler() {
    var data = entryService.fetch(getSelectedDateRange());
    data.forEach(System.out::println);
    entries.clear();
    CollectionUtils.addAll(entries, data);
  }

  public List<Entry> getEntries() {
    return entries;
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

  public TreeItem<Account> getAccountTreeRoot() {
    return accountTreeRoot;
  }

  private DateRange getSelectedDateRange() {
    return dateRangeProperty.get();
  }

  private List<Account> getSelectedAccounts() {
    return asOptional(accountProperty.get()).map(Account::collectLeafChildren)
        .orElseGet(Collections::emptyList);
  }

  private List<Category> getSelectedCategories() {
    return asOptional(categoryProperty.get()).map(Category::collectLeafChildren)
        .orElseGet(Collections::emptyList);
  }

  private Set<Label> getSelectedLabels() {
    return asOptional(labelsProperty.get()).orElseGet(Collections::emptySet);
  }


  private void initAccountRoot() {

    var accounts = accountService.findAllAccounts();

    if (accounts.isEmpty()) {
      var account = createDefaultAccount();
      ACCOUNT_ROOT.addChild(account);
    } else {
      buildTreeRoot(accounts, ACCOUNT_ROOT);
    }
    accountTreeRoot = buildItemTree(ACCOUNT_ROOT);
  }

  private Account createDefaultAccount() {
    var account = Account.builder()
        .name(DEFAULT_GENERAL_ACCOUNT_NAME)
        .currency(getPreferredCurrency())
        .initialBalance(ZERO)
        .build();

    accountService.save(account);

    return account;
  }

  private Currency getPreferredCurrency() {
    return (Currency) settingsService.getProperty(PREFERRED_CURRENCY);
  }

  public void initCategoryRoot() {

    var categories = categoryService.findAll();

    if (categories.isEmpty()) {
      categories = createDefaultCategories();
    }

    buildTreeRoot(categories, CATEGORY_ROOT);
    categoryTreeRoot = buildItemTree(CATEGORY_ROOT);
  }

  protected List<Category> createDefaultCategories() {

    var expenses = buildCategoryGroup(CREDIT);
    var income = buildCategoryGroup(DEBIT);

    categoryService.save(expenses);
    categoryService.save(income);

    return List.of(expenses, income);
  }

  private static Category buildCategoryGroup(EntryType type) {
    return CategoryGroup.creator()
        .name(type.toString())
        .type(type)
        .create();
  }

  private static <T extends TreeNode<T>> TreeItem<T> buildItemTree(T parent) {

    var item = new TreeNodeItem<>(parent);

    var items = parent.getChildren().stream().map(AppViewModel::buildItemTree).collect(toList());

    item.getChildren().addAll(items);

    return item;
  }
}
