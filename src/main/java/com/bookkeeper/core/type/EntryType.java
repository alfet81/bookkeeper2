package com.bookkeeper.core.type;

public enum EntryType {
  DEBIT("Income"),
  CREDIT("Expense");

  private String text;

  EntryType(String text) {
    this.text = text;
  }

  public String getText() {
    return text;
  }
}
