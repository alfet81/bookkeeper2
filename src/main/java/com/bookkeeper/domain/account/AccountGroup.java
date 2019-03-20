package com.bookkeeper.domain.account;

import static com.bookkeeper.utils.MiscUtils.asOptional;

import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Currency;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.Transient;

@Entity
@ToString(callSuper = true, exclude = {"children"})
@NoArgsConstructor
public class AccountGroup extends Account {

  @Transient
  private Set<Account> children = new HashSet<>();

  protected AccountGroup(Account parent, String name, Currency currency) {
    super(parent, name, currency, null, null);
  }

  @Builder(builderMethodName = "groupBuilder")
  private static AccountGroup buildAccountGroup(Account parent, String name, Currency currency) {
    return new AccountGroup(parent, name, currency);
  }

  @Override
  @Transient
  public Set<Account> getChildren() {
    return children;
  }

  @Override
  @Transient
  public void addChild(Account child) {
    asOptional(currency).ifPresent(child::setCurrency);
    super.addChild(child);
  }

  @Override
  @Transient
  public boolean isLeaf() {
    return false;
  }
}
