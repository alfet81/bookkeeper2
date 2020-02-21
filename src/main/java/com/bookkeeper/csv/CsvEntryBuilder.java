package com.bookkeeper.csv;

import static com.bookkeeper.app.AppConstants.CSV_ERROR_INVALID_AMOUNT;
import static com.bookkeeper.app.AppConstants.CSV_ERROR_INVALID_CATEGORY;
import static com.bookkeeper.app.AppConstants.CSV_ERROR_INVALID_DATE;
import static com.bookkeeper.type.CsvRecordColumn.AMOUNT;
import static com.bookkeeper.type.CsvRecordColumn.CATEGORY;
import static com.bookkeeper.type.CsvRecordColumn.DATE;
import static com.bookkeeper.type.CsvRecordColumn.getCsvColumns;
import static com.bookkeeper.type.CsvRecordStatus.ERROR;
import static com.bookkeeper.type.CsvRecordStatus.OK;
import static com.bookkeeper.utils.CsvUtils.getCsvRecordColumn;
import static com.bookkeeper.utils.MiscUtils.asOptional;
import static com.bookkeeper.utils.CsvUtils.string2Date;
import static com.bookkeeper.utils.CsvUtils.string2Decimal;
import static org.apache.commons.lang3.StringUtils.isEmpty;
import static org.springframework.beans.factory.config.ConfigurableBeanFactory.SCOPE_PROTOTYPE;
import static java.util.Optional.empty;

import com.bookkeeper.type.CsvRecordColumn;
import com.bookkeeper.domain.category.Category;
import com.bookkeeper.domain.entry.Entry;

import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;

@Scope(SCOPE_PROTOTYPE)
public class CsvEntryBuilder {

  @Autowired
  private Validator validator;

  @Setter
  private Map<String, Category> categories = new HashMap<>();

  private CsvRecordWrapper record;

  private Entry entry = new Entry();

  private CsvErrorMessageBuilder errorMessageBuilder = new CsvErrorMessageBuilder();

  public CsvEntryBuilder(CsvRecordWrapper wrapper) {
    this.record = wrapper;
    wrapper.setEntry(entry);
  }

  public void build() {
    reset();
    validate();
    parse();
    setStatus();
  }

  private void reset() {
    errorMessageBuilder.clear();
  }

  private void validate() {
    validator.validate(record).forEach(this::addErrorMessage);
  }

  private void addErrorMessage(ConstraintViolation<CsvRecordWrapper> constraintViolation) {

    String columnName = constraintViolation.getPropertyPath().toString();
    String errorMessage = constraintViolation.getMessage();

    getCsvRecordColumn(columnName).ifPresent(column -> addErrorMessage(column, errorMessage));
  }

  private void addErrorMessage(CsvRecordColumn column, String errorMessage) {
    errorMessageBuilder.addError(column, errorMessage);
  }

  private void parse() {
    getCsvColumns().forEach(this::parse);
  }

  private void parse(CsvRecordColumn column) {
    switch (column) {
      case DATE: parseDate(); break;
      case AMOUNT: parseAmount(); break;
      case CATEGORY: matchCategory(); break;
      case NOTES: setNotes();
    }
  }

  private void parseDate() {
    if (record.getDate() != null) {
      string2Date(record.getDate()).ifPresentOrElse(entry::setTransactionDate, () ->
          addErrorMessage(DATE, CSV_ERROR_INVALID_DATE));
    }
  }

  private void parseAmount() {
    if (record.getAmount() != null) {
      string2Decimal(record.getAmount()).ifPresentOrElse(entry::setAmount, () ->
          addErrorMessage(AMOUNT, CSV_ERROR_INVALID_AMOUNT));
    }
  }

  private void matchCategory() {
    if (record.getCategory() != null) {
      searchCategory(record.getCategory()).ifPresentOrElse(entry::setCategory, () ->
          addErrorMessage(CATEGORY, CSV_ERROR_INVALID_CATEGORY));
    }
  }

  private void setNotes() {
    entry.setNotes(record.getNotes());
  }

  private Optional<Category> searchCategory(String categoryName) {
    if (isEmpty(categoryName)) {
      return empty();
    }

    return asOptional(categories.get(categoryName.toLowerCase()));
  }

  private void setStatus() {
    record.setErrors(errorMessageBuilder.getErrorMessages());
    record.setStatus(record.getErrors() == null ? OK : ERROR);
  }
}
