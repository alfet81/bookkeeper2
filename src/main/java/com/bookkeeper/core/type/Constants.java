package com.bookkeeper.core.type;

import static java.time.format.DateTimeFormatter.ofPattern;

import java.time.format.DateTimeFormatter;

public interface Constants {

  DateTimeFormatter DEFAULT_DATE_FORMATTER = ofPattern("MM/dd/yyyy");

  String CSV_FILE_EXTENSION = "*.csv";

  String CSV_FILE_FILTER_DESCRIPTION = "CSV files (*.csv)";

  String CSV_IMPORT_DIALOG_TITLE = "Import CSV";

  String ACCOUNT_DIALOG_TITLE = "Manage Accounts";

  String CATEGORY_DIALOG_TITLE = "Manage Categories";

  String CSV_ERROR_INVALID_DATE = "Invalid date";

  String CSV_ERROR_INVALID_AMOUNT = "Invalid amount";

  String CSV_ERROR_INVALID_CATEGORY = "Invalid category";
}
