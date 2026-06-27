package com.bookkeeper.utils;

import static org.apache.commons.lang3.StringUtils.trimToNull;

import lombok.experimental.UtilityClass;

import java.util.Optional;

@UtilityClass
public class MiscUtils {

  public static <T> Optional<T> asOptional(T value) {
    return Optional.ofNullable(value);
  }


  public static Optional<String> asOptional(String value) {
    return Optional.ofNullable(trimToNull(value));
  }
}
