package com.bookkeeper.common;

import com.bookkeeper.domain.settings.UserSettings;
import com.bookkeeper.domain.settings.SettingsService;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import java.time.LocalDate;
import javax.annotation.PreDestroy;

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
      userSettings = settingsService.getUserSettings();
    }

    return userSettings;
  }

  public void saveUserSettings() {
    settingsService.saveUserSettings(getUserSettings());
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

    System.out.println("Destroying app context.");

    var settings = getUserSettings();

    System.out.println(settings);

    settings.setLastLoginDate(LocalDate.now());

    saveUserSettings();
  }
}