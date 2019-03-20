package com.bookkeeper.config;

import static org.springframework.beans.factory.config.ConfigurableBeanFactory.SCOPE_PROTOTYPE;

import com.bookkeeper.csv.CsvEntryBuilder;
import com.bookkeeper.csv.CsvRecordWrapper;

import org.apache.commons.csv.CSVRecord;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import java.util.function.Function;

@Configuration
public class FactoryConfig {

  @Bean
  public Function<CSVRecord, CsvRecordWrapper> csvRecordWrapperFactory() {
    return this::csvRecordWrapper;
  }

  @Bean
  @Scope(SCOPE_PROTOTYPE)
  public CsvRecordWrapper csvRecordWrapper(CSVRecord csvRecord) {
    return new CsvRecordWrapper(csvRecord);
  }

  @Bean
  public Function<CsvRecordWrapper, CsvEntryBuilder> entryBuilderFactory() {
    return this::entryBuilder;
  }

  @Bean
  @Scope(SCOPE_PROTOTYPE)
  public CsvEntryBuilder entryBuilder(CsvRecordWrapper wrapper) {
    return new CsvEntryBuilder(wrapper);
  }
}
