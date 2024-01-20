package com.bookkeeper.utils;

import static javafx.embed.swing.SwingFXUtils.toFXImage;

import com.bookkeeper.exceptions.ApplicationException;
import javafx.scene.image.Image;
import javax.imageio.ImageIO;

import java.io.IOException;

public final class ImageUtils {

  private ImageUtils() {
  }

  public static Image loadImage(String imagePath) {

    try {

      var bufferedImage = ImageIO.read(ImageUtils.class.getResource(imagePath));

      var writableImage = toFXImage(bufferedImage, null);

      if (writableImage.isError()) {
        throw new ApplicationException(writableImage.getException());
      }
      return writableImage;
    } catch (IOException e) {
      throw new ApplicationException(e);
    }
  }
}
