package com.bookkeeper.repository;

import static com.bookkeeper.type.EntryType.CREDIT;
import static com.bookkeeper.type.EntryType.DEBIT;

import com.bookkeeper.domain.account.Account;
import com.bookkeeper.domain.category.Category;
import com.bookkeeper.domain.category.CategoryGroup;

public class BaseRepositoryTest {

  protected Account account;

  protected Category rootCategory;

  protected static Account getTestAccount() {
    return new Account(null, "test account", null, null, null);
  }

  protected Category buildCategoryTree() {

    //initFromAccount root category
    rootCategory = CategoryGroup.creator().name("root").create();

    //initFromAccount 1st child of root
    CategoryGroup child1 = CategoryGroup.creator().name("child1").type(CREDIT).create();

    rootCategory.addChild(child1);

    //initFromAccount 2nd child of root
    CategoryGroup child2 = CategoryGroup.creator().name("child2").type(DEBIT).create();

    rootCategory.addChild(child2);

    //initFromAccount 1st child of child1 node
    Category child1_1 = Category.builder().name("child1_1").build();

    child1.addChild(child1_1);

    //initFromAccount 2nd child of child1 node
    Category child1_2 = Category.builder().name("child1_2").entryType(CREDIT).build();

    child1.addChild(child1_2);

    //initFromAccount 1st child of child2 node
    Category child2_1 = Category.builder().name("child2_1").build();

    child2.addChild(child2_1);

    //initFromAccount 2nd child of child2 node
    Category child2_2 = Category.builder().name("child2_2").entryType(DEBIT).build();

    child2.addChild(child2_2);

    return rootCategory;
  }
}
