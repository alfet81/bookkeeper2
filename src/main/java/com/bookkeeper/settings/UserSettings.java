package com.bookkeeper.settings;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Currency;
import java.util.Locale;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserSettings implements Serializable {

  private String name;

  private String password;

  private Currency currency;

  private Locale locale;

  private Long defaultAccountId;

  private LocalDate lastLoginDate;

  private String csvFilePath;

  private String dbFilePath;
}