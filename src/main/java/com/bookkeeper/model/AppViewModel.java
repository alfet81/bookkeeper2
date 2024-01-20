package com.bookkeeper.model;

import static com.bookkeeper.common.AppConstants.DEFAULT_GENERAL_ACCOUNT_NAME;
import static com.bookkeeper.common.entity.TreeNode.buildTreeRoot;
import static com.bookkeeper.entry.model.EntryType.CREDIT;
import static com.bookkeeper.entry.model.EntryType.DEBIT;
import static com.bookkeeper.utils.DateRangeUtils.getMonthBounds;
import static com.bookkeeper.utils.MiscUtils.asOptional;
import static javafx.collections.FXCollections.observableArrayList;
import static java.math.BigDecimal.ZERO;

import com.bookkeeper.account.entity.Account;
import com.bookkeeper.account.entity.AccountGroup;
import com.bookkeeper.account.service.AccountService;
import com.bookkeeper.category.entity.Category;
import com.bookkeeper.category.entity.CategoryGroup;
import com.bookkeeper.category.service.CategoryService;
import com.bookkeeper.common.entity.TreeNode;
import com.bookkeeper.entry.entity.Entry;
import com.bookkeeper.entry.model.EntryType;
import com.bookkeeper.entry.service.EntryService;
import com.bookkeeper.label.entity.Label;
import com.bookkeeper.model.dates.DateRange;
import com.bookkeeper.ui.model.TreeNodeItem;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.TreeItem;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javax.annotation.PostConstruct;
import java.util.Collections;
import java.util.List;
import java.util.Set;

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
  private AccountService accountService;

  @Autowired
  private CategoryService categoryService;

  @Autowired
  private EntryService entryService;

  @Autowired
  private AppContext appContext;


  private static Category buildCategoryGroup(EntryType type) {
    return CategoryGroup.creator()
        .name(type.toString())
        .type(type)
        .create();
  }


  private static <T extends TreeNode<T>> TreeItem<T> buildItemTree(T parent) {

    var item = new TreeNodeItem<>(parent);

    var items = parent.getChildren().stream()
        .map(AppViewModel::buildItemTree)
        .toList();

    item.getChildren().addAll(items);

    return item;
  }


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
    return getMonthBounds(AppContext.getUserSettings().getLastLoginDate());
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
        .currency(AppContext.getUserSettings().getCurrency())
        .initialBalance(ZERO)
        .build();

    accountService.save(account);

    return account;
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
}
