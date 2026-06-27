package com.bookkeeper.category.jpa;

import static jakarta.persistence.EnumType.STRING;

import com.bookkeeper.category.model.CategoryIcon;
import com.bookkeeper.common.jpa.AbstractEntity;
import com.bookkeeper.common.model.types.EntryType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
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
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@Table(name = "categories", schema = "data")
public class Category extends AbstractEntity {

  @ManyToOne
  @ToString.Exclude
  @JoinColumn(name = "parentId")
  protected Category parent;

  protected String name;

  @Enumerated(STRING)
  protected EntryType entryType;

  private BigDecimal defaultAmount;

  @Enumerated(STRING)
  @Column(name = "icon_type", length = 100)
  private CategoryIcon categoryIcon;
}