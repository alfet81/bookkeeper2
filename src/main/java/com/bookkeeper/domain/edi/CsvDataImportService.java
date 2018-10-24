package com.bookkeeper.domain.edi;

import static org.apache.commons.csv.CSVFormat.RFC4180;
import static org.apache.commons.csv.CSVParser.parse;
import static java.util.stream.Collectors.toList;

import com.bookkeeper.core.PrototypeBeanFactory;
import com.bookkeeper.csv.CsvEntryBuilder;
import com.bookkeeper.csv.CsvRecordWrapper;
import com.bookkeeper.core.exceptions.BookkeeperException;

import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.List;

@Service
public class CsvDataImportService {

  public List<CsvRecordWrapper> importCsvFile(File file) {

    try {
      var records = readCsvFile(new FileReader(file));
      processCsvRecords(records);
      return records;
    } catch (IOException e) {
      throw new BookkeeperException("Failed to import CSV file. " + e.getMessage());
    }
  }

  private List<CsvRecordWrapper> readCsvFile(Reader csvFileReader) throws IOException {

    var csvParser = parse(csvFileReader, RFC4180.withFirstRecordAsHeader());

    try(csvParser) {
      return csvParser.getRecords().stream().map(CsvRecordWrapper::new).collect(toList());
    }
  }

  private void processCsvRecords(List<CsvRecordWrapper> records) {
    var entryBuilder = getCsvEntryBuilder();
    records.forEach(entryBuilder::build);
  }

  private CsvEntryBuilder getCsvEntryBuilder() {
    return PrototypeBeanFactory.getBean(CsvEntryBuilder.class);
  }
}
