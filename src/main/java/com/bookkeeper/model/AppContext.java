package com.bookkeeper.model;

import com.bookkeeper.settings.UserSettings;
import com.bookkeeper.settings.SettingsService;

import lombok.extern.slf4j.XSlf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDate;
import javax.annotation.PreDestroy;

@XSlf4j
@Component
public class AppContext implements ApplicationContextAware {

  private static ApplicationContext springAppContext;

  private static SettingsService settingsService;

  private static UserSettings userSettings;

  @Autowired
  private AppContext(SettingsService settingsService) {
    AppContext.settingsService = settingsService;
  }

  public static UserSettings getUserSettings() {

    if (userSettings == null) {
      userSettings = getSettingsService().getUserSettings();
    }

    return userSettings;
  }

  private static SettingsService getSettingsService() {
    return getBean(SettingsService.class);
  }

  public void saveUserSettings() {
    try {
      settingsService.saveUserSettings(getUserSettings());
    } catch (Exception e) {
      LOG.error("Failed to save user settings", e);
    }
  }

  @Override
  public void setApplicationContext(ApplicationContext context) throws BeansException {
    springAppContext = context;
  }

  public static <T> T getBean(Class<T> beanClass) {
    return springAppContext.getBean(beanClass);
  }

  public static <T> T getBean(Class<T> beanClass, Object...args) {
    return springAppContext.getBean(beanClass, args);
  }

  @PreDestroy
  public void destroy() {

    LOG.info("Destroying app context.");

    var settings = getUserSettings();

    settings.setLastLoginDate(LocalDate.now());

    saveUserSettings();
  }
}