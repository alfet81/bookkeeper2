package com.bookkeeper.csv.view.gui.table.cell;

import static com.bookkeeper.utils.ImageUtils.createImage;
import static com.bookkeeper.utils.MiscUtils.asOptional;

import com.bookkeeper.csv.view.model.CsvRecordItem;
import com.bookkeeper.csv.view.model.CsvRecordStatus;

import javafx.scene.control.TableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.EnumMap;
import java.util.Map;

public class CsvRecordStatusTableCell extends TableCell<CsvRecordItem, CsvRecordStatus> {

  private static final int STATUS_IMAGE_SIZE = 16;

  private static final Map<CsvRecordStatus, Image> statusImages = new EnumMap<>(CsvRecordStatus.class);

  static {
    initStatusImages();
  }

  private static ImageView buildImageSmallIcon(Image image) {
    var imageView = new ImageView(image);
    imageView.setFitWidth(STATUS_IMAGE_SIZE);
    imageView.setFitHeight(STATUS_IMAGE_SIZE);
    imageView.setSmooth(true);
    return imageView;
  }


  private static void initStatusImages() {
    for (var status : CsvRecordStatus.values()) {
      statusImages.put(status, createImage(status.getImagePath()));
    }
  }


  @Override
  protected void updateItem(CsvRecordStatus item, boolean empty) {
    super.updateItem(item, empty);

    if (empty) {
      setText(null);
      setGraphic(null);
    } else {
      asOptional(item).ifPresent(status -> setGraphic(buildStatusImageView(status)));
    }
  }


  private ImageView buildStatusImageView(CsvRecordStatus status) {
    return asOptional(statusImages.get(status)).map(CsvRecordStatusTableCell::buildImageSmallIcon)
        .orElse(null);
  }
}
