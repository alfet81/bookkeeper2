package com.bookkeeper.dto;

import com.bookkeeper.domain.account.Account;
import com.bookkeeper.domain.category.Category;
import com.bookkeeper.domain.label.Label;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import java.util.List;
import java.util.Set;

@Builder @Getter @ToString
public class FilterParameters {
  private DateRange dateRange;
  private List<Account> accounts;
  private List<Category> categories;
  private Set<Label> labels;
}
