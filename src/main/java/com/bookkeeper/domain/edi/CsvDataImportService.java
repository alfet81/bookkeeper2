package com.bookkeeper.domain.edi;

import static org.apache.commons.csv.CSVFormat.RFC4180;
import static org.apache.commons.csv.CSVParser.parse;
import static java.util.stream.Collectors.toList;

import com.bookkeeper.csv.CsvRecordEntry;
import com.bookkeeper.exceptions.BookkeeperException;

import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.List;
import java.util.function.Function;

@Service
public class CsvDataImportService {

  @Autowired
  private Function<CSVRecord, CsvRecordEntry> csvRecordEntryFactory;

  public List<CsvRecordEntry> importCsvFile(File file) {
    try {
      return readCsvFile(new FileReader(file));
    } catch (IOException e) {
      throw new BookkeeperException("Failed to import CSV file. " + e.getMessage());
    }
  }

  private List<CsvRecordEntry> readCsvFile(Reader csvFileReader) throws IOException {

    var csvParser = parse(csvFileReader, RFC4180.withFirstRecordAsHeader());

    try(csvParser) {
      return csvParser.getRecords().stream().map(this::buildCsvRecordWrapper).collect(toList());
    }
  }

  private CsvRecordEntry buildCsvRecordWrapper(CSVRecord record) {
    return csvRecordEntryFactory.apply(record);
  }
}
