package com.bookkeeper.domain.account;

import static java.util.Collections.emptySet;
import static javax.persistence.EnumType.STRING;
import static javax.persistence.InheritanceType.SINGLE_TABLE;

import com.bookkeeper.type.BaseEntity;
import com.bookkeeper.type.TreeNode;
import com.bookkeeper.type.AccountIcon;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.DynamicUpdate;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.Inheritance;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

@Getter
@Setter
@Entity
@Builder
@DynamicUpdate
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "accounts")
@Inheritance(strategy = SINGLE_TABLE)
@DiscriminatorColumn(name = "entity_type")
@ToString(callSuper = true, exclude = {"parent"})
public class Account extends BaseEntity implements TreeNode<Account> {

  @ManyToOne(fetch = FetchType.LAZY)
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

  @Override
  @Transient
  public Set<Account> getChildren() {
    return emptySet();
  }
}
