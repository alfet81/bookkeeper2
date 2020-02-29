package com.bookkeeper.csv;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

public class CsvErrorRegisterTest {

  @Test
  public void whenThereNoErrors_ThenNull() {
    var builder = new CsvErrorRegister();
    assertThat(builder.getErrors()).isNull();
  }
}
