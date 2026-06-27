package com.bookkeeper.csv.service;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.Resource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import java.io.IOException;

@ExtendWith(SpringExtension.class)
@Import(CsvDataImportService.class)
class CsvDataImportServiceTest {

  private static final int TOTAL_CSV_RECORDS_COUNT = 6;

  @Value("classpath:csv/transactions.csv")
  private Resource csvFile;

  @Autowired
  private CsvDataImportService csvDataImportService;

  @Test
  void testLoadCsvFile() throws IOException {
    // Given
    var file = csvFile.getFile();

    // When
    var csvRecords = csvDataImportService.loadCsvFile(file);

    // Then
    assertThat(csvRecords).hasSize(TOTAL_CSV_RECORDS_COUNT);

    var csvRecord = csvRecords.get(0);
    assertThat(csvRecord.getDate()).isEqualTo("2018-05-25");
    assertThat(csvRecord.getAmount()).isEqualTo("2880.67");
    assertThat(csvRecord.getNotes()).isEqualTo("MOREDIRECT INC");

    csvRecord = csvRecords.get(1);
    assertThat(csvRecord.getDate()).isEqualTo("INVALID");
    assertThat(csvRecord.getAmount()).isEqualTo("189.16");
    assertThat(csvRecord.getNotes()).isEqualTo("Car insurance");

    csvRecord = csvRecords.get(2);
    assertThat(csvRecord.getDate()).isEqualTo("2018-05-17");
    assertThat(csvRecord.getAmount()).isEqualTo("INVALID");
    assertThat(csvRecord.getNotes()).isEqualTo("Gym");

    csvRecord = csvRecords.get(3);
    assertThat(csvRecord.getDate()).isEqualTo("INVALID");
    assertThat(csvRecord.getAmount()).isEqualTo("INVALID");
    assertThat(csvRecord.getNotes()).isEqualTo("Interest payment");

    csvRecord = csvRecords.get(4);
    assertThat(csvRecord.getDate()).isEqualTo("05/23/2018");
    assertThat(csvRecord.getAmount()).isEqualTo("-49.28");
    assertThat(csvRecord.getNotes()).isEqualTo(
        "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged.");

    csvRecord = csvRecords.get(5);
    assertThat(csvRecord.getDate()).isEqualTo("11/07/2024");
    assertThat(csvRecord.getAmount()).isEqualTo("-0.2");
    assertThat(csvRecord.getNotes()).isNull();
  }
}
