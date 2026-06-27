package com.bookkeeper.csv.service;

import static com.bookkeeper.csv.model.CsvRecordColumn.AMOUNT;
import static com.bookkeeper.csv.model.CsvRecordColumn.DATE;
import static com.bookkeeper.csv.model.CsvRecordColumn.NOTES;
import static com.bookkeeper.csv.utils.CsvUtils.getColumnValue;
import static com.bookkeeper.csv.utils.CsvUtils.readCsvFile;

import static java.util.Objects.nonNull;

import com.bookkeeper.common.exception.ApplicationException;
import com.bookkeeper.csv.model.CsvRecord;
import com.bookkeeper.csv.model.CsvRecordColumn;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.XSlf4j;
import org.apache.commons.csv.CSVRecord;
import org.javatuples.Pair;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@XSlf4j
@Service
@RequiredArgsConstructor
public class CsvDataImportService {

  private static Map<CsvRecordColumn, String> parseCSVRecord(CSVRecord csvRecord) {
    return Stream.of(DATE, AMOUNT, NOTES)
        .map((CsvRecordColumn column) -> {
          String value = getColumnValue(csvRecord, column);
          return Pair.with(column, value);
        })
        .filter(pair -> nonNull(pair.getValue1()))
        .collect(Collectors.toMap(
            Pair::getValue0,
            Pair::getValue1,
            (key1, key2) -> key1,
            () -> new EnumMap<>(CsvRecordColumn.class)
        ));
  }


  private CsvRecord buildCsvRecord(CSVRecord csvRecord) {
    var valuesMap = parseCSVRecord(csvRecord);
    return CsvRecord.builder()
        .date(valuesMap.get(DATE))
        .amount(valuesMap.get(AMOUNT))
        .notes(valuesMap.get(NOTES))
        .build();
  }


  public List<CsvRecord> loadCsvFile(File file) {
    log.entry(file.getName());

    try {
      var fileReader = new BufferedReader(new FileReader(file));
      var csvRows = readCsvFile(fileReader);

      log.trace("Loaded {} CSV rows from file '{}'", csvRows.size(), file.getName());
      csvRows.forEach(csvRecord -> log.trace("CSVRecord: {}", (Object) csvRecord.values()));

      return csvRows.stream()
          .map(this::buildCsvRecord)
          .toList();

    } catch (IOException e) {
      log.error("Failed to import CSV file", e);
      throw new ApplicationException("Failed to import CSV file. Error: " + e.getMessage());
    }
  }
}
