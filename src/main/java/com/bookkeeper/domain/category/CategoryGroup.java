package com.bookkeeper.domain.category;

import static com.bookkeeper.utils.MiscUtils.asOptional;

import com.bookkeeper.type.EntryType;

import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.Transient;

@Entity
@ToString(callSuper = true, exclude = {"children"})
@NoArgsConstructor
public class CategoryGroup extends Category {

  @Transient
  private Set<Category> children = new HashSet<>();

  protected CategoryGroup(Category parent, String name, EntryType type) {
    super(parent, name, type, null, null);
  }

  @Builder(builderMethodName = "groupBuilder")
  private static CategoryGroup buildCategoryGroup(Category parent, String name, EntryType type) {
    return new CategoryGroup(parent, name, type);
  }

  @Override
  @Transient
  public Set<Category> getChildren() {
    return children;
  }

  @Override
  @Transient
  public void addChild(Category child) {
    asOptional(entryType).ifPresent(child::setEntryType);
    super.addChild(child);
  }

  @Override
  @Transient
  public boolean isLeaf() {
    return false;
  }
}