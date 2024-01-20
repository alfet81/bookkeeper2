package com.bookkeeper.repository;

import static com.bookkeeper.entry.model.EntryType.CREDIT;
import static com.bookkeeper.entry.model.EntryType.DEBIT;
import static org.assertj.core.api.Assertions.assertThat;

import com.bookkeeper.category.entity.Category;
import com.bookkeeper.category.entity.CategoryGroup;
import com.bookkeeper.category.repo.CategoryRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.function.Predicate;

@DataJpaTest
@ExtendWith(SpringExtension.class)
public class CategoryRepositoryTest extends BaseRepositoryTest {

  @Autowired
  private TestEntityManager entityManager;

  @Autowired
  private CategoryRepository categoryRepository;

  private Category rootCategory;

  private static final Predicate<Category> HAS_NO_PARENT = ctg -> ctg.getParent() == null;

  @BeforeEach
  public void initTestData() {
    rootCategory = buildCategoryTree();
  }

  @Test //Testing that root node the same for the user
  public void whenFindByUser_ThenCategoryIsRoot() {
    //given
    persistCategoryTree();

    //when
    List<Category> found = categoryRepository.findAll();

    //then
    Category actualRoot = found.stream().filter(HAS_NO_PARENT).findAny().orElseThrow(() ->
    new RuntimeException("Category root not fond"));

    assertThat(actualRoot).isEqualTo(rootCategory);
  }

  @Test //Testing EAGER loading of children
  public void whenFindByUser_ThenWholeTreeIsEagerlyLoaded() {
    //given
    persistCategoryTree();

    //when
    List<Category> found = categoryRepository.findAll();

    Category actualRoot = found.stream().filter(HAS_NO_PARENT).findAny().orElseThrow(() ->
    new RuntimeException("Category root not fond"));

    //then
    assertThat(actualRoot.getChildren()).hasSize(2);
  }

  @Test //Testing the finding of leaf nodes
  public void whenFindByUser_ThenLeafNodesSizeEquals4() {
    //given
    persistCategoryTree();

    //when
    List<Category> found = categoryRepository.findAll();

    Category actualRoot = found.stream().filter(HAS_NO_PARENT).findAny().orElseThrow(() ->
    new RuntimeException("Category root not fond"));

    //then
    assertThat(actualRoot.collectLeafChildren()).hasSize(4);
  }
/*
  @Test //Testing cascade deletion of children
  public void whenChild1IsDeleted_ThenLeafSizeEquals2() {
    //given
    persistCategoryTree();

    //when
    List<Category> foundBeforeDelete = categoryRepository.findAll();

    Category originalRoot = foundBeforeDelete.stream().filter(hasNoParent).findAny().orElseThrow(() ->
    new RuntimeException("Category root not fond"));

    Category deleteCategory = originalRoot.getChildren().get(0);

    originalRoot.removeChild(deleteCategory);
    categoryRepository.delete(deleteCategory);
    deleteCategory = null;

    assertThat(categoryRepository.count()).isEqualTo(4);


    List<Category> foundAfterDelete = categoryRepository.findAll();

    Category newRoot = foundAfterDelete.stream().filter(hasNoParent).findAny().orElseThrow(() ->
    new RuntimeException("Category root not fond"));
    //then
    assertThat(foundAfterDelete).hasSize(4);
    assertThat(newRoot.getChildren()).hasSize(1);
    assertThat(newRoot.collectLeafChildren()).hasSize(2);
  }*/

  protected void persistCategoryTree() {
    entityManager.persist(rootCategory);
    //persistCategoryChildren(rootCategory.getChildren());
    entityManager.flush();
    assertThat(categoryRepository.count()).isEqualTo(7);
  }

  protected void persistCategoryChildren(List<Category> children) {
    for (Category child : children) {
      entityManager.persist(child);
      //persistCategoryChildren(child.getChildren());
    }
  }

  @Override
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
