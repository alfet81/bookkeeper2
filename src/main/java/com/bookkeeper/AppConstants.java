package com.bookkeeper;

import static java.time.format.DateTimeFormatter.ofPattern;
import static java.time.format.FormatStyle.SHORT;

import java.time.format.DateTimeFormatter;

public interface AppConstants {

  DateTimeFormatter DEFAULT_DATE_FORMATTER = ofPattern("MM/dd/yyyy");

  DateTimeFormatter SHORT_DATE_FRORMATTER = DateTimeFormatter.ofLocalizedDate(SHORT);

  String CSV_FILE_EXTENSION = "*.csv";

  String CSV_FILE_FILTER_DESCRIPTION = "CSV files (*.csv)";

  String CSV_IMPORT_DIALOG_TITLE = "Import CSV";

  String ACCOUNT_DIALOG_TITLE = "Manage Accounts";

  String CATEGORY_DIALOG_TITLE = "Manage Categories";

  String CSV_ERROR_INVALID_DATE = "Invalid date";

  String CSV_ERROR_INVALID_AMOUNT = "Invalid amount";

  String CSV_ERROR_INVALID_CATEGORY = "Invalid category";

  String DEFAULT_ACCOUNT_ROOT_NAME  = "My Accounts";

  String DEFAULT_CATEGORY_ROOT_NAME  = "My Categories";

  String DEFAULT_CATEGORY_CREDIT_NAME  = "Expenses";

  String DEFAULT_CATEGORY_DEBIT_NAME  = "Income";

  String DEFAULT_GENERAL_ACCOUNT_NAME = "General";
}
