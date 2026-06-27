package com.bookkeeper.common.utils;

import static lombok.AccessLevel.PRIVATE;

import static java.time.format.DateTimeFormatter.ofLocalizedDate;
import static java.time.format.DateTimeFormatter.ofPattern;
import static java.time.format.FormatStyle.SHORT;

import lombok.NoArgsConstructor;

import java.time.format.DateTimeFormatter;

@NoArgsConstructor(access = PRIVATE)
public final class Constants {

  public static final String USER_SETTINGS_CACHE_NAME = "user-settings";

  public static final DateTimeFormatter DEFAULT_DATE_FORMATTER = ofPattern("MM/dd/yyyy");

  public static final DateTimeFormatter SHORT_DATE_FORMATTER = ofLocalizedDate(SHORT);

  public static final String CSV_FILE_EXTENSION = "*.csv";

  public static final String CSV_FILE_FILTER_DESCRIPTION = "CSV files (*.csv)";

  public static final String CSV_IMPORT_DIALOG_TITLE = "Import CSV";

  public static final String ACCOUNT_DIALOG_TITLE = "Manage Accounts";

  public static final String CATEGORY_DIALOG_TITLE = "Manage Categories";

  public static final String CSV_ERROR_INVALID_DATE = "Invalid date";

  public static final String CSV_ERROR_INVALID_AMOUNT = "Invalid amount";

  public static final String CSV_ERROR_INVALID_CURRENCY_CODE = "Invalid currency code";

  public static final String CSV_ERROR_INVALID_CATEGORY = "Invalid category";

  public static final String ACCOUNT_ROOT_NAME = "My Accounts";

  public static final String CATEGORY_ROOT_NAME = "My Categories";

  public static final String DEFAULT_CATEGORY_CREDIT_NAME = "Expenses";

  public static final String DEFAULT_CATEGORY_DEBIT_NAME = "Income";

  public static final String DEFAULT_GENERAL_ACCOUNT_NAME = "General";
}
