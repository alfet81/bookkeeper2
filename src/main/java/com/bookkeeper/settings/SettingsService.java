package com.bookkeeper.settings;

import static com.bookkeeper.utils.MiscUtils.asOptional;
import static java.util.Optional.empty;

import lombok.extern.slf4j.XSlf4j;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.Currency;
import java.util.Locale;
import java.util.Optional;

@XSlf4j
@Service
public class SettingsService {

  private static final String USER_SETTINGS_FILE_NAME = "settings.dat";

  private static UserSettings buildDefaultUserSettings() {
    return UserSettings.builder()
      .locale(Locale.getDefault())
      .currency(Currency.getInstance(Locale.getDefault()))
      .lastLoginDate(LocalDate.now())
      .dbFilePath(getApplicationFilePath())
      .build();
  }

  private static String getApplicationFilePath() {
    return System.getProperty("user.home") + "/bookkeeper2";
  }

  private static String getUserSettingsFileName() {
    return getApplicationFilePath() + "/" + USER_SETTINGS_FILE_NAME;
  }

  public UserSettings getUserSettings() {
    return load().orElseGet(SettingsService::buildDefaultUserSettings);
  }

  public void saveUserSettings(UserSettings userSettings) throws IOException {

    String fileName = getUserSettingsFileName();

    System.out.println("Saving settings into " + fileName);

    Files.createDirectories(Path.of(getApplicationFilePath()));

    try (var objOutput = new ObjectOutputStream(new FileOutputStream(fileName))) {

      LOG.info("Saving: {}", userSettings);

      objOutput.writeObject(userSettings);

    } catch (IOException e) {
      LOG.catching(e);
    }
  }

  private Optional<UserSettings> load() {

    try (var objInput = new ObjectInputStream(new FileInputStream(getUserSettingsFileName()))) {

      var settings = (UserSettings) objInput.readObject();

      return asOptional(settings);

    } catch (IOException | ClassNotFoundException e) {
      LOG.catching(e);
    }

    return empty();
  }
}
