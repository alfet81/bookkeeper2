package com.bookkeeper.ui.category;

import static com.bookkeeper.types.CategoryColumn.ENTRY_TYPE;
import static com.bookkeeper.types.CategoryColumn.NAME;
import static org.apache.commons.lang3.StringUtils.trimToNull;

import com.bookkeeper.types.CategoryColumn;
import com.bookkeeper.domain.category.Category;
import java.beans.PropertyChangeSupport;

import java.util.Currency;
import javafx.scene.Node;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableCell;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.util.Callback;

public class CategoryTreeTableView extends TreeTableView<Category> /*implements TreeItemChangeSupport*/ {

  private PropertyChangeSupport itemChangeSupport;

  public CategoryTreeTableView() {
    init();
  }

  public CategoryTreeTableView(TreeItem<Category> root) {
    super(root);
    init();
  }

  private void init() {
    //itemChangeSupport = new PropertyChangeSupport(this);
    //getSelectionModel().setSelectionMode(SINGLE);
    setColumnResizePolicy(TreeTableView.CONSTRAINED_RESIZE_POLICY);
    setEditable(false);
    initTreeTableColumns();
    //getStylesheets().add("/css/tree_view.css");
  }

  private void initTreeTableColumns() {
    CategoryColumn.stream().forEach(this::addColumn);
  }

  private void addColumn(CategoryColumn column) {
    switch (column) {
      case NAME:
        addNameColumn();
        break;
      case ENTRY_TYPE:
        addEntryTypeColumn();
        break;
      default:
        addDefaultColumn(column);
    }
  }

  private void addNameColumn() {
    var column = new TreeTableColumn<Category, String>(NAME.getName());
    column.setCellValueFactory(new TreeItemPropertyValueFactory<>(NAME.getProperty()));
    //column.setCellFactory(TextFieldTreeTableCell.forTreeTableColumn());
    column.setOnEditCommit(handler -> {
      var account = handler.getRowValue().getValue();
      account.setName(trimToNull(handler.getNewValue()));
      System.out.println(account);
      itemChangeSupport.firePropertyChange("category", null, account);
    });

    column.setCellFactory(DEFAULT_CELL_FACTORY);

    getColumns().add(column);
  }

  private void addEntryTypeColumn() {
    var column = new TreeTableColumn<Category, Currency>(ENTRY_TYPE.getName());
    //var data = observableArrayList(getCurrencies());
    column.setCellValueFactory(new TreeItemPropertyValueFactory<>(ENTRY_TYPE.getProperty()));
    //column.setCellFactory(ComboBoxTreeTableCell.forTreeTableColumn(data));
    getColumns().add(column);
  }

  private void addDefaultColumn(CategoryColumn columnType) {
    var column = new TreeTableColumn<Category, String>(columnType.getName());
    column.setCellValueFactory(new TreeItemPropertyValueFactory<>(columnType.getProperty()));
    //column.setCellFactory(TextFieldTreeTableCell.forTreeTableColumn());
    getColumns().add(column);
  }

/*  @Override
  public PropertyChangeSupport getItemChangeSupport() {
    return itemChangeSupport;
  }*/

  public static final Callback<TreeTableColumn<Category,String>, TreeTableCell<Category,String>> DEFAULT_CELL_FACTORY =  new Callback<>() {


        @Override public TreeTableCell<Category,String> call(TreeTableColumn<Category,String> param) {
          return new TreeTableCell() {


            @Override protected void updateItem(Object item, boolean empty) {

              if (item == getItem()) return;

              super.updateItem(item, empty);

              if (item == null) {
                super.setText(null);
                super.setGraphic(null);
              } else if (item instanceof Node) {
                super.setText(null);
                super.setGraphic((Node)item);
              } else {
                super.setText(item.toString());
                super.setGraphic(null);
              }
            }
          };
        }
      };

}
