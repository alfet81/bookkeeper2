package com.bookkeeper.utils;

import com.bookkeeper.common.exception.ApplicationException;
import lombok.experimental.UtilityClass;

import javafx.scene.image.Image;

@UtilityClass
public class ImageUtils {

  public static Image createImage(String imagePath) {

    var imageUrl = ImageUtils.class.getResource(imagePath);

    if (imageUrl != null) {
      return new Image(imageUrl.toExternalForm());
    }

    throw new ApplicationException("Failed to load image with path: " + imagePath);
  }
}
