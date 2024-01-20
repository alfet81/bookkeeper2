package com.bookkeeper.csv.service;

import static org.apache.commons.csv.CSVFormat.RFC4180;

import com.bookkeeper.csv.model.CsvRecordColumn;
import com.bookkeeper.csv.model.CsvRecordEntry;
import com.bookkeeper.exceptions.ApplicationException;

import lombok.RequiredArgsConstructor;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.List;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class CsvDataImportService {

  private static final CSVFormat CSV_FORMAT = getCSVFormat();

  private final Function<CSVRecord, CsvRecordEntry> csvRecordEntryFactory;

  private static CSVFormat getCSVFormat() {
    return RFC4180.builder()
      .setHeader(CsvRecordColumn.class)
      .setSkipHeaderRecord(true)
      .setIgnoreHeaderCase(true)
      .build();
  }

  public List<CsvRecordEntry> importCsvFile(File file) {
    try {
      return readCsvFile(new FileReader(file));
    } catch (IOException e) {
      throw new ApplicationException("Failed to import CSV file. Error: " + e.getMessage());
    }
  }

  private List<CsvRecordEntry> readCsvFile(Reader csvFileReader) throws IOException {
    try (var parser = CSV_FORMAT.parse(csvFileReader)) {
      return parser.stream()
        .map(this::buildCsvRecordWrapper)
        .toList();
    }
  }

  private CsvRecordEntry buildCsvRecordWrapper(CSVRecord csvRecord) {
    return csvRecordEntryFactory.apply(csvRecord);
  }
}
