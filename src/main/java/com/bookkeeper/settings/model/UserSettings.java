package com.bookkeeper.settings.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Currency;
import java.util.Locale;

@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UserSettings implements Serializable {
  private String loginName;
  @ToString.Exclude
  private String passwordHash;
  private Currency preferredCurrency;
  private Locale locale;
  private Long defaultAccountId;
  private LocalDate lastLoginDate;
  private String csvFilePath;
  private String dbFilePath;
}