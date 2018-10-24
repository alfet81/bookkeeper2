package com.bookkeeper.domain.account;

import static com.bookkeeper.core.utils.CommonUtils.asOptional;
import static javax.persistence.CascadeType.PERSIST;
import static javax.persistence.CascadeType.REMOVE;
import static javax.persistence.FetchType.EAGER;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;

@Entity
@ToString(callSuper = true, exclude = {"children"})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AccountGroup extends Account {

  @OneToMany(mappedBy = "parent", cascade = {PERSIST, REMOVE}, fetch = EAGER)
  private List<Account> children = new ArrayList<>();

  protected AccountGroup(Account parent, String name, Currency currency) {
    super(parent, name, currency, null, null);
  }

  @Builder(builderMethodName = "groupBuilder")
  private static AccountGroup buildAccountGroup(Account parent, String name, Currency currency) {
    return new AccountGroup(parent, name, currency);
  }

  @Override
  public List<Account> getChildren() {
    return children;
  }

  @Override
  public void addChild(Account child) {
    asOptional(currency).ifPresent(child::setCurrency);
    super.addChild(child);
  }

  @Override
  public boolean isLeaf() {
    return false;
  }
}
