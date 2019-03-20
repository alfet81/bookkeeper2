package com.bookkeeper.types;

public enum CsvRecordStatus {
  OK("/images/ok_icon.jpg"),
  ERROR("/images/error_icon.png");

  private String imagePath;

  CsvRecordStatus(String imagePath) {
    this.imagePath = imagePath;
  }

  public String getImagePath() {
    return imagePath;
  }
}
