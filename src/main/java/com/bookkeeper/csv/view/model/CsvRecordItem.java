package com.bookkeeper.csv.view.model;

import static org.apache.commons.lang3.builder.ToStringStyle.SHORT_PREFIX_STYLE;

import com.bookkeeper.account.model.Account;
import com.bookkeeper.category.model.Category;
import com.bookkeeper.csv.model.CsvRecord;
import com.bookkeeper.csv.validation.ValidAmount;
import com.bookkeeper.csv.validation.ValidDate;
import lombok.NonNull;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import jakarta.validation.Validator;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class CsvRecordItem {

  @NonNull
  private final Validator validator;

  private final StringProperty date = new SimpleStringProperty(this, "date");
  private final StringProperty amount = new SimpleStringProperty(this, "amount");
  private final StringProperty notes = new SimpleStringProperty(this, "notes");

  private final ObjectProperty<Account> account = new SimpleObjectProperty<>(this, "account");
  private final ObjectProperty<Category> category = new SimpleObjectProperty<>(this, "category");

  private final ObjectProperty<CsvRecordStatus> status = new SimpleObjectProperty<>(this, "status");
  private final StringProperty errors = new SimpleStringProperty(this, "errors");


  public CsvRecordItem(@NonNull Validator validator, @NonNull CsvRecord csvRecord) {
    this.validator = validator;
    setDate(csvRecord.getDate());
    setAmount(csvRecord.getAmount());
    setNotes(csvRecord.getNotes());
  }


  public StringProperty dateProperty() {
    return date;
  }


  @NotEmpty(message = "is missing")
  @ValidDate
  public String getDate() {
    return dateProperty().get();
  }


  public void setDate(String date) {
    dateProperty().set(date);
  }


  public StringProperty amountProperty() {
    return amount;
  }


  @NotEmpty(message = "is missing")
  @ValidAmount
  public String getAmount() {
    return amountProperty().get();
  }


  public void setAmount(String amount) {
    amountProperty().set(amount);
  }


  public StringProperty notesProperty() {
    return notes;
  }


  @Size(max = 255, message = "exceeds 255 chars")
  public String getNotes() {
    return notesProperty().get();
  }


  public void setNotes(String notes) {
    notesProperty().set(notes);
  }


  public ObjectProperty<Account> accountProperty() {
    return account;
  }


  public Account getAccount() {
    return accountProperty().get();
  }


  public void setAccount(Account account) {
    accountProperty().set(account);
  }


  public ObjectProperty<Category> categoryProperty() {
    return category;
  }


  public Category getCategory() {
    return categoryProperty().get();
  }


  public void setCategory(Category category) {
    categoryProperty().set(category);
  }


  public ObjectProperty<CsvRecordStatus> statusProperty() {
    return status;
  }


  public CsvRecordStatus getStatus() {
    return statusProperty().get();
  }


  public void setStatus(CsvRecordStatus status) {
    statusProperty().set(status);
  }


  public StringProperty errorsProperty() {
    return errors;
  }


  public String getErrors() {
    return errorsProperty().get();
  }


  public void setErrors(String errors) {
    errorsProperty().set(errors);
  }


  public void validate() {
    new CsvRecordItemValidator(validator, this).validate();
  }


  @Override
  public String toString() {
    return new ToStringBuilder(this, SHORT_PREFIX_STYLE)
        .append("date", getDate())
        .append("amount", getAmount())
        .append("notes", getNotes())
        .append("account", getAccount())
        .append("category", getCategory())
        .build();
  }
}
