package com.bookkeeper.account.entity;

import static com.bookkeeper.common.AppConstants.DEFAULT_ACCOUNT_ROOT_NAME;

import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.DynamicUpdate;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.Transient;

@Entity
@SuperBuilder
@DynamicUpdate
@NoArgsConstructor
@ToString(callSuper = true, exclude = { "children" })
public class AccountGroup extends Account {

  @Transient
  private final Set<Account> children = new HashSet<>();

  @Override
  @Transient
  public Set<Account> getChildren() {
    return children;
  }

  @Override
  @Transient
  public void addChild(Account child) {

    child.setCurrency(getCurrency());

    super.addChild(child);
  }

  @Override
  @Transient
  public boolean isLeaf() {
    return false;
  }

  public static Account getRootInstance() {
    return AccountGroup.builder()
      .name(DEFAULT_ACCOUNT_ROOT_NAME)
      .build();
  }
}
