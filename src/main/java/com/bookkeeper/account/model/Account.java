package com.bookkeeper.account.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;
import javax.money.CurrencyUnit;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Account {

  private Long id;
  private String name;
  private CurrencyUnit currency;
  private BigDecimal initialBalance;
  private AccountIcon icon;

  @ToString.Exclude
  private Account parent;
}
