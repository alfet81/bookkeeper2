package com.bookkeeper.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CsvRecordStatus {
  OK("/images/ok_icon.jpg"),
  ERROR("/images/error_icon.png");

  private String imagePath;
}
