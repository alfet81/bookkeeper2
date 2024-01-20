package com.bookkeeper.model;

import com.bookkeeper.account.entity.Account;
import com.bookkeeper.category.entity.Category;
import com.bookkeeper.label.entity.Label;

import com.bookkeeper.model.dates.DateRange;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import java.util.List;
import java.util.Set;

@Getter
@Builder
@ToString
public class FilterParameters {
  private DateRange dateRange;
  private List<Account> accounts;
  private List<Category> categories;
  private Set<Label> labels;
}
