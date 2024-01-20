package com.bookkeeper.csv.model;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

public class CsvErrorRegistrarTest {

  @Test
  public void whenThereNoErrors_ThenNull() {

    var builder = new CsvErrorRegistrar();

    assertThat(builder.getErrors()).isNull();
  }
}
