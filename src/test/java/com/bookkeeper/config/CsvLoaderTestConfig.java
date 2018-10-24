package com.bookkeeper.config;

import com.bookkeeper.domain.edi.CsvDataImportService;

import org.springframework.boot.test.context.TestConfiguration;

@TestConfiguration
public class CsvLoaderTestConfig {
  public CsvDataImportService csvLoaderService() {
    return new CsvDataImportService();
  }
}
