package com.bookkeeper.settings.service;

import static com.bookkeeper.common.utils.Constants.USER_SETTINGS_CACHE_NAME;
import static com.bookkeeper.utils.CurrencyUtils.getDefaultCurrency;
import static com.bookkeeper.utils.MiscUtils.asOptional;

import static java.util.Optional.empty;

import com.bookkeeper.settings.model.UserSettings;
import lombok.extern.slf4j.XSlf4j;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.Locale;
import java.util.Optional;

@XSlf4j
@Service
public class UserSettingsService {

  private static final String USER_HOME_ENV_VAR = "user.home";
  private static final String APP_ROOT_FOLDER = "/bookkeeper2";
  private static final String USER_SETTINGS_FILE_NAME = "settings.dat";


  private static UserSettings buildDefaultUserSettings() {
    return UserSettings.builder()
        .locale(Locale.getDefault())
        .preferredCurrency(getDefaultCurrency())
        .lastLoginDate(LocalDate.now())
        .dbFilePath(getApplicationFilePath())
        .build();
  }


  private static String getApplicationFilePath() {
    return System.getProperty(USER_HOME_ENV_VAR) + APP_ROOT_FOLDER;
  }


  private static String getUserSettingsFileName() {
    return getApplicationFilePath() + "/" + USER_SETTINGS_FILE_NAME;
  }


  @Cacheable(USER_SETTINGS_CACHE_NAME)
  public UserSettings getUserSettings() {

    var userSettings = loadUserSettings().orElse(null);

    if (userSettings == null) {
      log.debug("No user settings found. Initializing default user settings.");
      userSettings = buildDefaultUserSettings();
      saveUserSettings(userSettings);
    } else {
      log.debug("User settings found: {}", userSettings);
    }

    return userSettings;
  }


  @CachePut("settings")
  public void saveUserSettings(UserSettings userSettings) {
    String fileName = getUserSettingsFileName();
    log.debug("Saving user settings into {}", fileName);

    try {
      Files.createDirectories(Path.of(getApplicationFilePath()));
    } catch (IOException e) {
      log.error("Failed to create directory {}", getApplicationFilePath(), e);
    }

    try (var outputStream = new ObjectOutputStream(new FileOutputStream(fileName))) {
      outputStream.writeObject(userSettings);
      log.debug("Saved user settings into {}. {}", fileName, userSettings);
    } catch (IOException e) {
      log.error("Failed to save user settings into: {}", fileName, e);
    }
  }


  private Optional<UserSettings> loadUserSettings() {
    try (var inputStream = new ObjectInputStream(new FileInputStream(getUserSettingsFileName()))) {
      var settings = (UserSettings) inputStream.readObject();
      log.debug("Successfully loaded user settings from: {}", getUserSettingsFileName());
      return asOptional(settings);
    } catch (IOException | ClassNotFoundException e) {
      log.warn("Failed to load user settings from: {}. Reason: {}", getUserSettingsFileName(), e.getMessage());
    }
    return empty();
  }
}
