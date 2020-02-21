package com.bookkeeper.domain.entry;

//import static com.bookkeeper.domain.entry.QEntry.entry;

import static com.querydsl.core.types.ExpressionUtils.allOf;
import static org.apache.commons.collections4.CollectionUtils.isNotEmpty;

import com.bookkeeper.domain.account.Account;
import com.bookkeeper.domain.category.Category;
import com.bookkeeper.domain.label.Label;
import com.bookkeeper.dto.DateRange;
import com.bookkeeper.dto.FilterParameters;

import com.querydsl.core.types.Predicate;
import java.util.List;
import java.util.Set;

public class EntryFilterPredicateBuilder {

  private DateRange dateRange;
  private List<Account> accounts;
  private List<Category> categories;
  private Set<Label> labels;

  public EntryFilterPredicateBuilder(FilterParameters filterParameters) {
    dateRange = filterParameters.getDateRange();
    accounts = filterParameters.getAccounts();
    categories = filterParameters.getCategories();
    labels = filterParameters.getLabels();
  }

  public Predicate build() {
    return allOf(getDateRangePredicate(), getAccountPredicate(), getCategoryPredicate(), getLabelPredicate());
  }

  private Predicate getDateRangePredicate() {
    return dateRange != null && dateRange.isValid() ? betweenDateRange(dateRange) : null;
  }

  private static Predicate betweenDateRange(DateRange dateRange) {
    return null; //entry.transactionDate.between(dateRange.getStartDate(), dateRange.getEndDate());
  }

  private Predicate getAccountPredicate() {
    return null; //isNotEmpty(accounts) ? entry.account.in(accounts) : null;
  }

  private Predicate getCategoryPredicate() {
    return null; //isNotEmpty(categories) ? entry.category.in(categories) : null;
  }

  private Predicate getLabelPredicate() {
    return null; //isNotEmpty(labels) ? entry.labels.any().in(labels) : null;
  }
}
