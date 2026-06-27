package com.bookkeeper.csv.view.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CsvRecordStatus {
  OK("/images/ok_icon.png"),
  ERROR("/images/error_icon.png");

  private final String imagePath;
}
