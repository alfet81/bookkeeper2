package com.bookkeeper.domain.category;

import static com.bookkeeper.core.utils.CommonUtils.asOptional;
import static javax.persistence.CascadeType.REMOVE;

import com.bookkeeper.core.type.EntryType;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

@Entity
@ToString(callSuper = true, exclude = {"children"})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CategoryGroup extends Category {

  @OneToMany(mappedBy = "parent", cascade = {REMOVE})
  private List<Category> children = new ArrayList<>();

  protected CategoryGroup(Category parent, String name, EntryType type) {
    super(parent, name, type, null, null);
  }

  @Builder(builderMethodName = "groupBuilder")
  private static CategoryGroup buildCategoryGroup(Category parent, String name, EntryType type) {
    return new CategoryGroup(parent, name, type);
  }

  @Override
  public List<Category> getChildren() {
    return children;
  }

  @Override
  public void addChild(Category child) {
    asOptional(entryType).ifPresent(child::setEntryType);
    super.addChild(child);
  }

  @Override
  public boolean isLeaf() {
    return false;
  }
}