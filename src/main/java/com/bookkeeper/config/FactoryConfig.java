package com.bookkeeper.config;

import static org.springframework.beans.factory.config.ConfigurableBeanFactory.SCOPE_PROTOTYPE;

import com.bookkeeper.csv.CsvRecordEntry;

import org.apache.commons.csv.CSVRecord;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import java.util.function.Function;

@Configuration
public class FactoryConfig {

  @Bean
  public Function<CSVRecord, CsvRecordEntry> csvRecordWrapperFactory() {
    return this::csvRecordWrapper;
  }

  @Bean
  @Scope(SCOPE_PROTOTYPE)
  public CsvRecordEntry csvRecordWrapper(CSVRecord csvRecord) {
    return new CsvRecordEntry(csvRecord);
  }
}
