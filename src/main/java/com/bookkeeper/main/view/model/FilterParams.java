package com.bookkeeper.main.view.model;

import com.bookkeeper.account.model.Account;
import com.bookkeeper.category.model.Category;
import com.bookkeeper.label.jpa.Label;

import com.bookkeeper.common.model.date.DateRange;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import java.util.List;
import java.util.Set;

@Getter
@Builder
@ToString
@AllArgsConstructor
public class FilterParams {
  private DateRange dateRange;
  private List<Account> accounts;
  private List<Category> categories;
  private Set<Label> labels;
}
