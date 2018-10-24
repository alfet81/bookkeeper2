package com.bookkeeper.csv;

import static com.bookkeeper.core.type.Constants.CSV_ERROR_INVALID_AMOUNT;
import static com.bookkeeper.core.type.Constants.CSV_ERROR_INVALID_CATEGORY;
import static com.bookkeeper.core.type.Constants.CSV_ERROR_INVALID_DATE;
import static com.bookkeeper.core.type.CsvRecordColumn.AMOUNT;
import static com.bookkeeper.core.type.CsvRecordColumn.CATEGORY;
import static com.bookkeeper.core.type.CsvRecordColumn.DATE;
import static com.bookkeeper.core.type.CsvRecordStatus.ERROR;
import static com.bookkeeper.core.type.CsvRecordStatus.OK;
import static com.bookkeeper.core.utils.CommonUtils.asOptional;
import static com.bookkeeper.core.utils.CsvUtils.string2Date;
import static com.bookkeeper.core.utils.CsvUtils.string2Decimal;
import static org.apache.commons.lang3.StringUtils.isEmpty;
import static java.util.Collections.emptyMap;
import static java.util.Optional.empty;
import static java.util.stream.Collectors.toMap;

import com.bookkeeper.core.AppContext;
import com.bookkeeper.core.type.CsvRecordColumn;
import com.bookkeeper.domain.category.Category;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;

@Component
@Scope("prototype")
public class CsvEntryBuilder {

  private Map<String, Category> categories;

  @Autowired
  private Validator validator;

  @Autowired
  private AppContext appContext;

  public void build(CsvRecordWrapper record) {
    new Builder(record).build();
  }

  private Optional<Category> searchCategory(String categoryName) {
    if (isEmpty(categoryName)) {
      return empty();
    }

    return asOptional(getCategories().get(categoryName.toLowerCase()));
  }

  private Map<String, Category> getCategories() {
    if (categories == null) {
      categories = getLeafCategories();
    }
    return categories;
  }

  private Map<String, Category> getLeafCategories() {
    if (appContext.getCategoryRoot() == null) {
      return emptyMap();
    }
    return appContext.getCategoryRoot().collectLeafChildren().stream()
        .collect(toMap(c -> c.getName().toLowerCase(), c -> c, (c1, c2) -> c1));
  }

  private class Builder {

    private final CsvRecordWrapper record;

    private CsvErrorMessageBuilder csvErrorMessageBuilder = new CsvErrorMessageBuilder();

    Builder(CsvRecordWrapper record) {
      this.record = record;
    }

    void build() {
      validate(record);
      populateEntry(record);
      updateStatus(record);
    }

    private void validate(CsvRecordWrapper record) {
      validator.validate(record).forEach(this::buildErrorMessage);
    }

    private void buildErrorMessage(ConstraintViolation<?> error) {
      getColumn(error).ifPresent(col -> csvErrorMessageBuilder.addError(col, error.getMessage()));
    }

    private Optional<CsvRecordColumn> getColumn(ConstraintViolation<?> constraintViolation) {
      return CsvRecordColumn.findByProperty(constraintViolation.getPropertyPath().toString());
    }

    private void populateEntry(CsvRecordWrapper record) {
      CsvRecordColumn.getDataColumns().forEach(column -> populateEntry(column, record));
    }

    private void populateEntry(CsvRecordColumn column, CsvRecordWrapper record) {
      switch (column) {
        case DATE:
          populateDate(record);
          break;
        case AMOUNT:
          populateAmount(record);
          break;
        case CATEGORY:
          populateCategory(record);
          break;
        case NOTES:
          populateNotes(record);
      }
    }

    private void populateDate(CsvRecordWrapper record) {
      asOptional(record.getDate()).ifPresent(date ->
          string2Date(date).ifPresentOrElse(record.getEntry()::setTransactionDate, () ->
              csvErrorMessageBuilder.addError(DATE, CSV_ERROR_INVALID_DATE)));
    }

    private void populateAmount(CsvRecordWrapper record) {
      asOptional(record.getAmount()).ifPresent(amount ->
          string2Decimal(amount).ifPresentOrElse(record.getEntry()::setAmount, () ->
              csvErrorMessageBuilder.addError(AMOUNT, CSV_ERROR_INVALID_AMOUNT)));
    }

    private void populateCategory(CsvRecordWrapper record) {
      asOptional(record.getCategory()).ifPresent(category ->
          searchCategory(category).ifPresentOrElse(record.getEntry()::setCategory, () ->
              csvErrorMessageBuilder.addError(CATEGORY, CSV_ERROR_INVALID_CATEGORY)));
    }

    private void populateNotes(CsvRecordWrapper record) {
      record.getEntry().setNotes(record.getNotes());
    }

    private void updateStatus(CsvRecordWrapper record) {
      var errors = getValidationErrors();

      record.setStatus(errors.isPresent() ? ERROR : OK);
      record.setErrors(errors.orElse(null));
    }

    private Optional<String> getValidationErrors() {
      return csvErrorMessageBuilder.getErrorMessages();
    }
  }
}
