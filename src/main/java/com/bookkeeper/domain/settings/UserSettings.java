package com.bookkeeper.domain.settings;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Currency;
import java.util.Locale;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UserSettings implements Serializable {
  private String name;
  private String password;
  private Currency currency;
  private Locale locale;
  private Integer defaultAccountId;
  private LocalDate lastLoginDate;
  private String csvFilePath;
  private String dbFilePath;
}