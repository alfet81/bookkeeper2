package com.bookkeeper.csv.model;

import static com.bookkeeper.common.AppConstants.CSV_ERROR_INVALID_AMOUNT;
import static com.bookkeeper.common.AppConstants.CSV_ERROR_INVALID_DATE;
import static com.bookkeeper.csv.model.CsvRecordColumn.AMOUNT;
import static com.bookkeeper.csv.model.CsvRecordColumn.DATE;
import static com.bookkeeper.csv.model.CsvRecordColumn.NOTES;
import static com.bookkeeper.csv.model.CsvRecordStatus.ERROR;
import static com.bookkeeper.csv.model.CsvRecordStatus.OK;
import static com.bookkeeper.utils.CsvUtils.getColumnValue;
import static com.bookkeeper.utils.CsvUtils.getCsvRecordColumn;
import static com.bookkeeper.utils.CsvUtils.string2Date;
import static com.bookkeeper.utils.CsvUtils.string2Decimal;
import static org.springframework.beans.factory.config.ConfigurableBeanFactory.SCOPE_PROTOTYPE;

import com.bookkeeper.entry.entity.Entry;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import java.util.EnumSet;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@Scope(SCOPE_PROTOTYPE)
@ToString(of = { "date", "amount", "notes" })
public class CsvRecordEntry {

  public static final Set<CsvRecordColumn> COLUMNS = EnumSet.of(DATE, AMOUNT, NOTES);

  @NotNull(message = "Missing date")
  private String date;

  @NotNull(message = "Missing amount")
  @Digits(integer = 9, fraction = 2, message = "Invalid amount")
  private String amount;

  @Size(max = 255, message = "Notes exceeds 255 chars")
  private String notes;

  private final Entry entry = new Entry();

  @Autowired
  @Getter(AccessLevel.NONE)
  private Validator validator;

  @Getter(AccessLevel.NONE)
  private CsvErrorRegistrar errorRegister;

  @SuppressWarnings("ConstantConditions")
  public CsvRecordEntry(CSVRecord csvRecord) {
    date = getColumnValue(csvRecord, DATE);
    amount = getColumnValue(csvRecord, AMOUNT);
    notes = getColumnValue(csvRecord, NOTES);
  }

  public void process() {
    reset();
    validate();
    parse();
  }

  public String getErrors() {
    return errorRegister != null ? errorRegister.getErrors() : null;
  }

  public CsvRecordStatus getStatus() {
    return errorRegister != null ? (errorRegister.hasErrors() ? ERROR : OK) : null;
  }

  private void reset() {
    errorRegister = new CsvErrorRegistrar();
  }

  private void validate() {
    validator.validate(this).forEach(this::addErrorMessage);
  }

  private void addErrorMessage(ConstraintViolation<CsvRecordEntry> constraintViolation) {

    var columnName = constraintViolation.getPropertyPath().toString();
    var errorMessage = constraintViolation.getMessage();

    getCsvRecordColumn(columnName).ifPresent(column -> addErrorMessage(column, errorMessage));
  }

  private void addErrorMessage(CsvRecordColumn column, String errorMessage) {
    errorRegister.addError(column, errorMessage);
  }

  private void addInvalidDateError() {
    addErrorMessage(DATE, CSV_ERROR_INVALID_DATE);
  }

  private void addInvalidAmountError() {
    addErrorMessage(AMOUNT, CSV_ERROR_INVALID_AMOUNT);
  }

  private void parse() {
    COLUMNS.forEach(this::parse);
  }

  private void parse(CsvRecordColumn column) {
    switch (column) {
      case DATE -> parseDate();
      case AMOUNT -> parseAmount();
      case NOTES -> setNotes();
    }
  }

  private void parseDate() {
    if (date != null) {
      string2Date(date).ifPresentOrElse(entry::setDate, this::addInvalidDateError);
    }
  }

  private void parseAmount() {
    if (amount != null) {
      string2Decimal(amount).ifPresentOrElse(entry::setAmount, this::addInvalidAmountError);
    }
  }

  private void setNotes() {
    entry.setNotes(notes);
  }
}
