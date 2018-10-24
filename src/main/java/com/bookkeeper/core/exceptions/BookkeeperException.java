package com.bookkeeper.core.exceptions;

public class BookkeeperException extends RuntimeException {

  public BookkeeperException() {
    super();
  }

  public BookkeeperException(String message) {
    super(message);
  }

  public BookkeeperException(String message, Throwable cause) {
    super(message, cause);
  }
}
