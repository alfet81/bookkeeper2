package com.bookkeeper.category.ui;

import static java.util.stream.Collectors.toList;

import com.bookkeeper.category.ui.view.CategoryTreeTableView;
import com.bookkeeper.common.entity.TreeNode;
import com.bookkeeper.category.entity.Category;
import com.bookkeeper.category.entity.CategoryGroup;
import com.bookkeeper.ui.support.FxmlDialogPane;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ToolBar;
import javafx.scene.control.TreeItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

public class CategoryManagerDialog extends FxmlDialogPane {

  static Image folderCollapseImage = new Image("/images/folder_col.png");

  static Image folderExpandImage = new Image("/images/folder_exp.png");

  @FXML
  private ToolBar toolBar;

  @FXML
  private ScrollPane scrollPane;

  @FXML
  private VBox box;

  private TreeItem<Category> categoryTreeRoot;

  private CategoryTreeTableView categoryTreeTableView;

  private Button addGroup;

  private Button addCategory;

  private Button edit;

  private Button delete;

  @Override
  protected String getFxmlResourceFile() {
    return "/com/bookkeeper/category/ui/view/CategoryTreeTableView.fxml";
  }

  @FXML
  @Override
  public void initialize(URL location, ResourceBundle resources) {

    addGroup = (Button) toolBar.getItems().get(1);
    addCategory = (Button) toolBar.getItems().get(2);
    edit = (Button) toolBar.getItems().get(3);
    delete = (Button) toolBar.getItems().get(4);

    categoryTreeRoot = getCategoryModel();

    categoryTreeTableView = new CategoryTreeTableView(categoryTreeRoot);

    categoryTreeTableView.getSelectionModel().selectedItemProperty()
        .addListener(((observable, oldValue, newValue) -> selectionHandler(newValue)));

    scrollPane.setContent(categoryTreeTableView);

    categoryTreeTableView.getSelectionModel().selectFirst();

    //box.setStyle("-fx-background-color: #336699;");

    //this.setStyle("-fx-background-color: #225577;");
  }

  private void selectionHandler(TreeItem<Category> item) {

    boolean disableAddGroup = false;
    boolean disableAddAccount = false;
    boolean disableEdit = false;
    boolean disableDelete = false;

    boolean root = item.getValue().getParent() == null;

    if ((item.getValue() instanceof CategoryGroup)) {
      disableAddGroup = root;
      disableEdit = root;
      disableDelete = root;
    } else {
      disableAddGroup = true;
      disableAddAccount = true;
    }

    addGroup.setDisable(disableAddGroup);
    addCategory.setDisable(disableAddAccount);
    edit.setDisable(disableEdit);
    delete.setDisable(disableDelete);
  }

  private TreeItem<Category> getCategoryModel() {
    return new TreeItem<>();
  }

  private static TreeItem<Category> buildTree(Category parent) {

    var item = new TreeNodeItem<Category>(parent);

    var items = parent.getChildren().stream().map(CategoryManagerDialog::buildTree).collect(toList());

    item.getChildren().addAll(items);

    return item;
  }

  static class TreeNodeItem<T extends TreeNode> extends TreeItem<T> {

    private T item;

    TreeNodeItem(T item) {
      super(item);
      this.item = item;

      if (!item.isLeaf()) {
        setGraphic(new ImageView(folderCollapseImage));
      }

      addEventHandler(TreeItem.branchExpandedEvent(), handler -> {

        TreeNodeItem source = (TreeNodeItem) handler.getSource();

        if(!source.isLeaf() && source.isExpanded()) {

          ImageView iv = (ImageView) source.getGraphic();

          iv.setImage(folderExpandImage);

        }
      });

      addEventHandler(TreeItem.branchCollapsedEvent(), handler -> {

        TreeNodeItem source = (TreeNodeItem) handler.getSource();

        if(!source.isLeaf() && !source.isExpanded()) {

          ImageView iv = (ImageView) source.getGraphic();

          iv.setImage(folderCollapseImage);
        }
      });
    }

    @Override
    public boolean isLeaf() {
      return item.isLeaf();
    }
  }

  @FXML
  public void addTopLevelGroup(final Event event) {
    try {
      new CategoryEditorDialog(new CategoryGroup()).showAndWait().ifPresent(category -> {
        try {
          Category root = new CategoryGroup();
          root.addChild(category);
          //getCategoryService().save(category);
          addToModel(categoryTreeRoot, category);
          categoryTreeRoot.setExpanded(true);
        } catch (Exception e) {
          //getCategoryRoot().removeChild(category);
          e.printStackTrace();
          //showExceptionDialog(e);
        }
      });
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @FXML
  public void addGroup(final Event event) {

    var item = getSelectedItem();
    var parentCategory = item.getValue();
    var newGroup = CategoryGroup.creator().type(parentCategory.getEntryType()).create();

    new CategoryEditorDialog(newGroup).showAndWait().ifPresent(category -> {
      try {
        parentCategory.addChild(category);
        //getCategoryService().save(category);
        addToModel(item, category);
        item.setExpanded(true);
      } catch (Exception e) {
        parentCategory.removeChild(category);
        e.printStackTrace();
      }
    });
  }

  @FXML
  public void addCategory(final Event event) {

    var item = getSelectedItem();
    var parentCategory = item.getValue();
    var newCategory = Category.builder().entryType(parentCategory.getEntryType()).build();

    new CategoryEditorDialog(newCategory).showAndWait().ifPresent(category -> {
      try {
        item.setExpanded(true);
        parentCategory.addChild(category);
        //getCategoryService().save(category);
        addToModel(item, category);
      } catch (Exception e) {
        parentCategory.removeChild(category);
        e.printStackTrace();
      }
    });
  }

  @FXML
  public void edit(final Event event) {

    var item = getSelectedItem();
    var category = item.getValue();

    new CategoryEditorDialog(category).showAndWait().ifPresent(cat -> {
      try {
        //getCategoryService().save(cat);
        categoryTreeTableView.refresh();
      } catch (Exception e) {
        e.printStackTrace();
      }
    });
  }

  @FXML
  public void delete(final Event event) {

    var item = getSelectedItem();
    var category = item.getValue();
    var parent = category.getParent();

    try {
      parent.removeChild(category);
      //getCategoryService().delete(category);
      item.getParent().getChildren().remove(item);
      categoryTreeTableView.getSelectionModel().select(categoryTreeTableView.getSelectionModel().getSelectedIndex() - 1);
    } catch (Exception e) {
      parent.addChild(category);
      e.printStackTrace();
    }
  }

  private TreeItem<Category> getSelectedItem() {
    return categoryTreeTableView.getSelectionModel().getSelectedItem();
  }

  private void addToModel(TreeItem<Category> root, Category value) {
    root.getChildren().add(new TreeNodeItem<>(value));
  }
}
