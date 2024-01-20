package com.bookkeeper.csv.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CsvRecordStatus {
  OK("/images/ok_icon.jpg"),
  ERROR("/images/error_icon.png");

  private final String imagePath;
}
