package com.bookkeeper.account.entity;

import static java.util.Collections.emptySet;
import static javax.persistence.EnumType.STRING;
import static javax.persistence.FetchType.LAZY;
import static javax.persistence.InheritanceType.SINGLE_TABLE;

import com.bookkeeper.common.entity.BaseEntity;
import com.bookkeeper.common.entity.TreeNode;
import com.bookkeeper.account.model.AccountIcon;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.DynamicUpdate;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Currency;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.ForeignKey;
import javax.persistence.Inheritance;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

@Getter
@Setter
@Entity
@SuperBuilder
@DynamicUpdate
@NoArgsConstructor
@AllArgsConstructor
@Inheritance(strategy = SINGLE_TABLE)
@DiscriminatorColumn(name = "entityType")
@ToString(callSuper = true, exclude = { "parent" })
public class Account extends BaseEntity implements TreeNode<Account> {

  @ManyToOne(fetch = LAZY)
  @JoinColumn(name = "parentId", foreignKey = @ForeignKey(name = "fk_accounts_parent_id"))
  private Account parent;

  @Column(length = 100, nullable = false)
  private String name;

  @Column(length = 3)
  private Currency currency;

  @Column(scale = 2)
  private BigDecimal initialBalance;

  @Enumerated(STRING)
  @Column(name = "icon", length = 100)
  private AccountIcon accountIcon;

  @Override
  public void setParent(Account parent) {
    this.parent = parent;
  }

  @Override
  @Transient
  public Collection<Account> getChildren() {
    return emptySet();
  }
}
