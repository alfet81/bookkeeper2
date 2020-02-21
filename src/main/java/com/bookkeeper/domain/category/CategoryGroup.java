package com.bookkeeper.domain.category;

import static com.bookkeeper.app.AppConstants.DEFAULT_CATEGORY_ROOT_NAME;
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
@NoArgsConstructor
@ToString(callSuper = true, exclude = {"children"})
public class CategoryGroup extends Category {

  @Transient
  private Set<Category> children = new HashSet<>();

  @Builder(builderMethodName = "creator", buildMethodName = "create")
  private CategoryGroup(Category parent, String name, EntryType type) {
    super(parent, name, type, null, null);
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

  public static Category getRootInstance() {
    return new CategoryGroup(null, DEFAULT_CATEGORY_ROOT_NAME, null);
  }
}