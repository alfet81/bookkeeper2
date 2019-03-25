package com.bookkeeper.repository;

import com.bookkeeper.domain.category.Category;
import com.bookkeeper.domain.category.CategoryGroup;
import com.bookkeeper.domain.category.CategoryRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.List;
import java.util.function.Predicate;
import static com.bookkeeper.type.EntryType.CREDIT;
import static com.bookkeeper.type.EntryType.DEBIT;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class CategoryRepositoryTest extends BaseRepositoryTest {

  @Autowired
  private TestEntityManager entityManager;

  @Autowired
  private CategoryRepository categoryRepository;

  private Category rootCategory;

  private static Predicate<Category> hasNoParent = ctg -> ctg.getParent() == null;

  @Before
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
    Category actualRoot = found.stream().filter(hasNoParent).findAny().orElseThrow(() ->
    new RuntimeException("Category root not fond"));

    assertThat(actualRoot).isEqualTo(rootCategory);
  }

  @Test //Testing EAGER loading of children
  public void whenFindByUser_ThenWholeTreeIsEagerlyLoaded() {
    //given
    persistCategoryTree();

    //when
    List<Category> found = categoryRepository.findAll();

    Category actualRoot = found.stream().filter(hasNoParent).findAny().orElseThrow(() ->
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

    Category actualRoot = found.stream().filter(hasNoParent).findAny().orElseThrow(() ->
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
    rootCategory = CategoryGroup.groupBuilder().name("root").build();

    //initFromAccount 1st child of root
    CategoryGroup child1 = CategoryGroup.groupBuilder().name("child1").type(CREDIT).build();

    rootCategory.addChild(child1);

    //initFromAccount 2nd child of root
    CategoryGroup child2 = CategoryGroup.groupBuilder().name("child2").type(DEBIT).build();

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
