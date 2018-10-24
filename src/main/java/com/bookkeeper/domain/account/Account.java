package com.bookkeeper.domain.account;

import static java.util.Collections.emptyList;
import static javax.persistence.EnumType.STRING;
import static javax.persistence.InheritanceType.SINGLE_TABLE;

import com.bookkeeper.core.type.BaseEntity;
import com.bookkeeper.core.type.TreeNode;
import com.bookkeeper.core.type.AccountIcon;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.ForeignKey;
import javax.persistence.Inheritance;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "accounts")
@Inheritance(strategy = SINGLE_TABLE)
@DiscriminatorColumn(name = "entity_type", discriminatorType = DiscriminatorType.STRING)
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true, exclude = {"parent"})
public class Account extends BaseEntity implements TreeNode<Account> {

  @ManyToOne
  @JoinColumn(name = "parent_id", foreignKey = @ForeignKey(name = "fk_accounts_parent_id"))
  protected Account parent;

  @Column(length = 100, nullable = false)
  protected String name;

  @Column(length = 3)
  protected Currency currency;

  @Column(name = "initial_balance", scale = 2)
  protected BigDecimal initialBalance;

  @Enumerated(STRING)
  @Column(name = "icon", length = 100)
  protected AccountIcon accountIcon;

  @Builder
  private static Account buildAccount(Account parent, String name, Currency currency,
      BigDecimal initialBalance, AccountIcon accountIcon) {
    return new Account(parent, name, currency, initialBalance, accountIcon);
  }

  @Override
  public List<Account> getChildren() {
    return emptyList();
  }
}
