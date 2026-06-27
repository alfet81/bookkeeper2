package com.bookkeeper.config;

import static org.springframework.beans.factory.config.ConfigurableBeanFactory.SCOPE_PROTOTYPE;

import com.bookkeeper.csv.model.CsvRecord;
import com.bookkeeper.csv.view.model.CsvRecordItem;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.messageinterpolation.ParameterMessageInterpolator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import java.util.function.Function;
import jakarta.validation.Validation;
import jakarta.validation.Validator;

@Configuration
@RequiredArgsConstructor
public class FactoryConfig {

  @Bean
  Validator validator() {
    return Validation.byDefaultProvider()
        .configure().messageInterpolator(new ParameterMessageInterpolator())
        .buildValidatorFactory()
        .getValidator();
  }


  @Bean
  Function<CsvRecord, CsvRecordItem> csvRecordItemFactory() {
    return this::buildCsvRecordItem;
  }


  @Bean
  @Scope(SCOPE_PROTOTYPE)
  CsvRecordItem buildCsvRecordItem(CsvRecord csvRecord) {
    return new CsvRecordItem(validator(), csvRecord);
  }
}
