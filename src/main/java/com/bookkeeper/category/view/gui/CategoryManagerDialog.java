package com.bookkeeper.category.view.gui;

import com.bookkeeper.category.model.Category;
import com.bookkeeper.category.view.CategoryTreeTableView;
import com.bookkeeper.common.gui.support.FxmlDialogPane;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ToolBar;
import javafx.scene.control.TreeItem;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class CategoryManagerDialog extends FxmlDialogPane {

  private static final Image FOLDER_COLLAPSE_IMAGE = new Image("/images/folder_col.png");
  private static final Image FOLDER_EXPAND_IMAGE = new Image("/images/folder_exp.png");

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


/*  private static TreeItem<Category> buildTree(Category parent) {
    var item = new TreeNodeItem<>(parent);
    var items = parent.getChildren().stream()
        .map(CategoryManagerDialog::buildTree)
        .toList();
    item.getChildren().addAll(items);
    return item;
  }*/


  @Override
  protected String getFxmlResourceFile() {
    return "/com/bookkeeper/category/view/CategoryTreeTable.fxml";
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

/*    if ((item.getValue() instanceof CategoryGroup)) {
      disableAddGroup = root;
      disableEdit = root;
      disableDelete = root;
    } else {
      disableAddGroup = true;
      disableAddAccount = true;
    }*/

    addGroup.setDisable(disableAddGroup);
    addCategory.setDisable(disableAddAccount);
    edit.setDisable(disableEdit);
    delete.setDisable(disableDelete);
  }


  private TreeItem<Category> getCategoryModel() {
    return new TreeItem<>();
  }


  /*@FXML
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
  }*/


  /*@FXML
  public void addGroup(final Event event) {
    var item = getSelectedItem();
    var parentCategory = item.getValue();
    var newGroup = CategoryGroup.builder().entryType(parentCategory.getEntryType()).build();

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
  }*/


  @FXML
  public void addCategory(final Event event) {
    var item = getSelectedItem();
    var parentCategory = item.getValue();
    var newCategory = Category.builder().entryType(parentCategory.getEntryType()).build();

    new CategoryEditorDialog(newCategory).showAndWait().ifPresent(category -> {
      try {
        item.setExpanded(true);
        //parentCategory.addChild(category);
        //getCategoryService().save(category);
        //addToModel(item, category);
      } catch (Exception e) {
        //parentCategory.removeChild(category);
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
      //parent.removeChild(category);
      //getCategoryService().delete(category);
      item.getParent().getChildren().remove(item);
      categoryTreeTableView.getSelectionModel()
          .select(categoryTreeTableView.getSelectionModel().getSelectedIndex() - 1);
    } catch (Exception e) {
      //parent.addChild(category);
      e.printStackTrace();
    }
  }


  private TreeItem<Category> getSelectedItem() {
    return categoryTreeTableView.getSelectionModel().getSelectedItem();
  }

}
