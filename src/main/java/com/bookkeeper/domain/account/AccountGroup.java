package com.bookkeeper.domain.account;

import static com.bookkeeper.common.AppConstants.DEFAULT_ACCOUNT_ROOT_NAME;
import static com.bookkeeper.utils.MiscUtils.asOptional;

import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.DynamicUpdate;

import java.util.Currency;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.Transient;

@Entity
@DynamicUpdate
@NoArgsConstructor
@ToString(callSuper = true, exclude = {"children"})
public class AccountGroup extends Account {

  @Transient
  private Set<Account> children = new HashSet<>();

  @Builder(builderMethodName = "creator", buildMethodName = "create")
  private AccountGroup(Account parent, String name, Currency currency) {
    super(parent, name, currency, null, null);
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

  public static Account getRootInstance() {
    return new AccountGroup(null, DEFAULT_ACCOUNT_ROOT_NAME, null);
  }
}
