package com.bookkeeper.csv;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

public class CsvErrorMessageBuilderTest {

  @Test
  public void whenThereNoErrors_ThenNull() {
    var builder = new CsvErrorMessageBuilder();
    assertThat(builder.getErrorMessages()).isNull();
  }
}
