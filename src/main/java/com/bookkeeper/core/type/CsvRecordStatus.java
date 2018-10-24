package com.bookkeeper.core.type;

public enum CsvRecordStatus {
  OK("/images/ok_icon.jpg"),
  ERROR("/images/error_icon.png");

  private String imageUrl;

  CsvRecordStatus(String imageUrl) {
    this.imageUrl = imageUrl;
  }

  public String getImageUrl() {
    return imageUrl;
  }
}
