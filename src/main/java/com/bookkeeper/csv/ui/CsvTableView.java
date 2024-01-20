package com.bookkeeper.csv.ui;

import static com.bookkeeper.csv.model.CsvRecordColumn.ERRORS;
import static com.bookkeeper.csv.model.CsvRecordColumn.STATUS;
import static com.bookkeeper.utils.ImageUtils.loadImage;
import static com.bookkeeper.utils.MiscUtils.asOptional;

import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.apache.commons.lang3.StringUtils.trimToNull;

import com.bookkeeper.csv.model.CsvRecordEntry;
import com.bookkeeper.exceptions.ApplicationException;
import com.bookkeeper.csv.model.CsvRecordColumn;
import com.bookkeeper.csv.model.CsvRecordStatus;

import java.util.EnumMap;
import java.util.Map;
import java.util.function.BiConsumer;
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

class CsvTableView extends TableView<CsvRecordEntry> {

  private final Map<CsvRecordStatus, Image> statusImages = new EnumMap<>(CsvRecordStatus.class);

  {
    initStatusImages();
  }

  private void initStatusImages() {
    for (CsvRecordStatus status : CsvRecordStatus.values()) {
      statusImages.put(status, loadImage(status.getImagePath()));
    }
  }

  CsvTableView(ObservableList<CsvRecordEntry> items) {
    super(items);
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
      case STATUS: addStatusColumn(); break;
      default: addRegularColumn(columnType);
    }
  }

  private void addStatusColumn() {

    var column = new TableColumn<CsvRecordEntry, CsvRecordStatus>(EMPTY);

    column.setMinWidth(STATUS.getDefaultWidth());
    column.setPrefWidth(STATUS.getDefaultWidth());
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

    column.setCellValueFactory(new PropertyValueFactory<>(STATUS.name().toLowerCase()));

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

    var column = new TableColumn<CsvRecordEntry, String>(columnType.toString());

    column.setMinWidth(columnType.getDefaultWidth());

    column.setPrefWidth(columnType.getDefaultWidth());

    column.setResizable(columnType.isResizable());

    column.setSortable(columnType.isSortable());

    column.setEditable(columnType.isEditable());

    column.setCellValueFactory(new PropertyValueFactory<>(columnType.name().toLowerCase()));

    if (columnType != ERRORS) {
      column.setCellFactory(TextFieldTableCell.forTableColumn());
      column.setOnEditCommit(getCellEditEventHandler(columnType));
    }

    getColumns().add(column);
  }

  private EventHandler<CellEditEvent<CsvRecordEntry, String>> getCellEditEventHandler(
      CsvRecordColumn column) {

    return handler -> {

      var record = handler.getTableView().getItems().get(handler.getTablePosition().getRow());

      getCsvRecordModifier(column).accept(record, trimToNull(handler.getNewValue()));

      record.process();

      handler.getTableView().refresh();
    };
  }

  private static BiConsumer<CsvRecordEntry, String> getCsvRecordModifier(CsvRecordColumn column) {
    switch (column) {
      case DATE: return CsvRecordEntry::setDate;
      case AMOUNT: return CsvRecordEntry::setAmount;
      case NOTES: return CsvRecordEntry::setNotes;
      default: throw new ApplicationException("Unsupported column type");
    }
  }
}
