package com.bookkeeper.account.jpa;

import static jakarta.persistence.EnumType.STRING;

import com.bookkeeper.account.model.AccountIcon;
import com.bookkeeper.common.jpa.AbstractEntity;
import io.hypersistence.utils.hibernate.type.money.CurrencyUnitType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Type;

import java.math.BigDecimal;
import javax.money.CurrencyUnit;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Getter
@Setter
@Entity
@SuperBuilder
@DynamicUpdate
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@Table(name = "accounts", schema = "data")
public class Account extends AbstractEntity {

  private String name;

  @Column(name = "currency_code")
  @Type(CurrencyUnitType.class)
  private CurrencyUnit currency;

  private BigDecimal initialBalance;

  @Enumerated(STRING)
  @Column(name = "icon_type")
  private AccountIcon accountIcon;

  @ManyToOne
  @ToString.Exclude
  @JoinColumn(name = "parent_id")
  private Account parent;
}
