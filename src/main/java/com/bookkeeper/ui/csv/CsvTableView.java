package com.bookkeeper.ui.csv;

import static com.bookkeeper.core.type.CsvRecordColumn.ERRORS;
import static com.bookkeeper.core.type.CsvRecordColumn.STATUS;
import static com.bookkeeper.core.utils.CommonUtils.asOptional;

import static org.apache.commons.lang3.StringUtils.trimToNull;

import com.bookkeeper.csv.CsvEntryBuilder;
import com.bookkeeper.core.PrototypeBeanFactory;
import com.bookkeeper.csv.CsvRecordWrapper;
import com.bookkeeper.core.type.CsvRecordColumn;
import com.bookkeeper.core.type.CsvRecordStatus;

import java.util.EnumMap;
import java.util.Map;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

class CsvTableView extends TableView<CsvRecordWrapper> {

  private final Map<CsvRecordStatus, Image> statusImages = new EnumMap<>(CsvRecordStatus.class);

  private CsvEntryBuilder entryBuilder;

  {
    initStatusImages();
  }

  private void initStatusImages() {
    for (CsvRecordStatus status : CsvRecordStatus.values()) {
      statusImages.put(status, new Image(status.getImageUrl()));
    }
  }

  CsvTableView(ObservableList<CsvRecordWrapper> items) {
    super(items);
    this.entryBuilder = PrototypeBeanFactory.getBean(CsvEntryBuilder.class);
    init();
  }

  private void init() {
    setEditable(true);
    initTableColumns();
  }

  private void initTableColumns() {
    CsvRecordColumn.stream().forEach(this::addColumn);
  }

  private void addColumn(CsvRecordColumn columnType) {
    switch (columnType) {
      case STATUS:
        addStatusColumn();
        break;
      default:
        addRegularColumn(columnType);
    }
  }

  private void addStatusColumn() {

    var column = new TableColumn<CsvRecordWrapper, CsvRecordStatus>(STATUS.getName());

    column.setMinWidth(STATUS.getWidth());
    column.setPrefWidth(STATUS.getWidth());
    column.setResizable(STATUS.isResizable());
    column.setSortable(STATUS.isSortable());
    column.setEditable(STATUS.isEditable());

    column.setCellFactory(cell -> new TableCell<>() {
      @Override
      protected void updateItem(CsvRecordStatus status, boolean empty) {
        super.updateItem(status, empty);

        if (empty) {
          setText(null);
          setGraphic(null);
          return;
        }

        asOptional(status).ifPresent(s -> setGraphic(buildStatusImageView(s)));
      }
    });

    column.setCellValueFactory(new PropertyValueFactory<>(STATUS.getProperty()));

    getColumns().add(column);
  }

  private ImageView buildStatusImageView(CsvRecordStatus status) {
    return asOptional(statusImages.get(status)).map(this::buildImageSmallIcon).orElse(null);
  }

  private ImageView buildImageSmallIcon(Image image) {
    var imageView = new ImageView(image);
    imageView.setFitWidth(16);
    imageView.setFitHeight(16);
    imageView.setSmooth(true);
    return imageView;
  }

  private void addRegularColumn(CsvRecordColumn columnType) {

    var column = new TableColumn<CsvRecordWrapper, String>(columnType.getName());

    column.setMinWidth(columnType.getWidth());

    column.setPrefWidth(columnType.getWidth());

    column.setResizable(columnType.isResizable());

    column.setSortable(columnType.isSortable());

    column.setEditable(columnType.isEditable());

    column.setCellValueFactory(new PropertyValueFactory<>(columnType.getProperty()));

    if (columnType != ERRORS) {
      column.setCellFactory(TextFieldTableCell.forTableColumn());
      column.setOnEditCommit(getCellEditEventHandler(columnType));
    }

    getColumns().add(column);
  }

  private static CsvRecordModifier<CsvRecordWrapper, String> getCsvRecordModifier(CsvRecordColumn column) {
    switch (column) {
      case DATE:
        return CsvRecordWrapper::setDate;
      case AMOUNT:
        return CsvRecordWrapper::setAmount;
      case CATEGORY:
        return CsvRecordWrapper::setCategory;
      case NOTES:
        return CsvRecordWrapper::setNotes;
    }
    return null;
  }

  private EventHandler<CellEditEvent<CsvRecordWrapper, String>> getCellEditEventHandler(CsvRecordColumn column) {

    var recordModifier = getCsvRecordModifier(column);

    return recordModifier == null ? null : handler -> {

      var record = handler.getTableView().getItems().get(handler.getTablePosition().getRow());

      recordModifier.modify(record, trimToNull(handler.getNewValue()));

      entryBuilder.build(record);

      handler.getTableView().refresh();
    };
  }

  @FunctionalInterface
  private interface CsvRecordModifier<R, V> {
    void modify(R record, V value);
  }
}
