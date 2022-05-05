package com.bookkeeper.domain.settings;

import static com.bookkeeper.utils.MiscUtils.asOptional;

import org.springframework.stereotype.Service;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.LocalDate;
import java.util.Currency;
import java.util.Locale;
import java.util.Optional;

@Service
public class SettingsService {

  private static final String USER_SETTINGS_FILE_NAME = "settings.dat";

  public UserSettings getUserSettings() {
    return load().orElseGet(SettingsService::buildDefaultUserSettings);
  }

  public void saveUserSettings(UserSettings userSettings) {

    String filePath = getUserSettingsFilePath();
    String fileName = getUserSettingsFileName();

    System.out.println("Saving settings into " + fileName);

    File fileFolder = new File(filePath);

    if (!fileFolder.exists()) {
      fileFolder.mkdir();
    }

    try(var objOutput = new ObjectOutputStream(new FileOutputStream(fileName))) {

      System.out.println(userSettings);

      objOutput.writeObject(userSettings);

    } catch (IOException e) {
      System.out.println(e);
      //TODO: add logging
    }
  }

  private Optional<UserSettings> load() {

    try(var objInput = new ObjectInputStream(new FileInputStream(getUserSettingsFileName()))) {

      var settings = (UserSettings) objInput.readObject();

      return asOptional(settings);

    } catch (IOException | ClassNotFoundException e) {
      //TODO: add logging
    }
    return Optional.empty();
  }

  private static UserSettings buildDefaultUserSettings() {

    String dbFilePath = System.getProperty("user.home") + "/bookkeeper";

    return UserSettings.builder()
        .locale(Locale.getDefault())
        .currency(Currency.getInstance(Locale.getDefault()))
        .lastLoginDate(LocalDate.now())
        .dbFilePath(dbFilePath)
        .build();
  }

  private static String getUserSettingsFilePath() {
    return System.getProperty("user.home") + "/bookkeeper";
  }

  private static String getUserSettingsFileName() {
    return getUserSettingsFilePath() + "/" + USER_SETTINGS_FILE_NAME;
  }
}
