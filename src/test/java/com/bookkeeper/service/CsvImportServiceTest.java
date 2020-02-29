package com.bookkeeper.service;

import com.bookkeeper.config.CsvLoaderTestConfig;
import com.bookkeeper.domain.edi.CsvDataImportService;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.Resource;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@RunWith(SpringRunner.class)
@Import(CsvLoaderTestConfig.class)
public class CsvImportServiceTest {

  @Autowired
  private CsvDataImportService csvLoaderService;

  @Value("classpath:transactions.csv")
  private Resource csvFile;

  @Test
  public void testCsvLoader() {

    try {

      var records = csvLoaderService.importCsvFile(csvFile.getFile());

      assertThat(records).hasSize(33);

    } catch (Exception e) {
      e.printStackTrace();
      fail(e.toString());
    }
  }
}
