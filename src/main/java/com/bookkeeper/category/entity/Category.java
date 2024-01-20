package com.bookkeeper.category.entity;

import static javax.persistence.FetchType.LAZY;
import static lombok.AccessLevel.PROTECTED;
import static java.util.Collections.emptySet;
import static javax.persistence.EnumType.ORDINAL;
import static javax.persistence.EnumType.STRING;
import static javax.persistence.InheritanceType.SINGLE_TABLE;

import com.bookkeeper.common.entity.TreeNode;
import com.bookkeeper.category.model.CategoryIcon;
import com.bookkeeper.entry.model.EntryType;
import com.bookkeeper.common.entity.BaseEntity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.ForeignKey;
import javax.persistence.Inheritance;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@Entity
@SuperBuilder
@NoArgsConstructor
@Inheritance(strategy = SINGLE_TABLE)
@AllArgsConstructor(access = PROTECTED)
@DiscriminatorColumn(name = "entityType")
@ToString(callSuper = true, exclude = { "parent" })
public class Category extends BaseEntity implements TreeNode<Category> {

  @ManyToOne(fetch = LAZY)
  @JoinColumn(name = "parentId", foreignKey = @ForeignKey(name = "fk_categories_parent_id"))
  protected Category parent;

  @Column(length = 100, nullable = false)
  protected String name;

  @Enumerated(ORDINAL)
  protected EntryType entryType;

  @Column(scale = 2)
  private BigDecimal defaultAmount;

  @Enumerated(STRING)
  @Column(name = "icon", length = 100)
  private CategoryIcon categoryIcon;

  @Override
  @Transient
  public Set<Category> getChildren() {
    return emptySet();
  }
}