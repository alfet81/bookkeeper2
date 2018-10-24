package com.bookkeeper.repository;

import com.bookkeeper.domain.account.Account;
import com.bookkeeper.domain.category.Category;
import com.bookkeeper.domain.category.CategoryGroup;
import static com.bookkeeper.core.type.EntryType.CREDIT;
import static com.bookkeeper.core.type.EntryType.DEBIT;

public class BaseRepositoryTest {

  protected Account account;

  protected Category rootCategory;

  protected static Account getTestAccount() {
    return new Account(null, "test account", null, null, null);
  }

  protected Category buildCategoryTree() {

    //init root category
    rootCategory = CategoryGroup.groupBuilder().name("root").build();

    //init 1st child of root
    CategoryGroup child1 = CategoryGroup.groupBuilder().name("child1").type(CREDIT).build();

    rootCategory.addChild(child1);

    //init 2nd child of root
    CategoryGroup child2 = CategoryGroup.groupBuilder().name("child2").type(DEBIT).build();

    rootCategory.addChild(child2);

    //init 1st child of child1 node
    Category child1_1 = Category.builder().name("child1_1").build();

    child1.addChild(child1_1);

    //init 2nd child of child1 node
    Category child1_2 = Category.builder().name("child1_2").entryType(CREDIT).build();

    child1.addChild(child1_2);

    //init 1st child of child2 node
    Category child2_1 = Category.builder().name("child2_1").build();

    child2.addChild(child2_1);

    //init 2nd child of child2 node
    Category child2_2 = Category.builder().name("child2_2").entryType(DEBIT).build();

    child2.addChild(child2_2);

    return rootCategory;
  }
}
