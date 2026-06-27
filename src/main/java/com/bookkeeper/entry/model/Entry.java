package com.bookkeeper.entry.model;

import com.bookkeeper.account.model.Account;
import com.bookkeeper.category.model.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import javax.money.MonetaryAmount;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Entry {
  private long id;
  private LocalDate date;
  private MonetaryAmount amount;
  private String notes;
  private LocalDateTime createdDate;
  private LocalDateTime modifiedDate;
  private Account account;
  private Category category;
}
