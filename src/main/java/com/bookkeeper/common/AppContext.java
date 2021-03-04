package com.bookkeeper.common;

import com.bookkeeper.mvc.model.AppViewModel;
import com.bookkeeper.domain.settings.SettingsService;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class AppContext implements ApplicationContextAware {

  private static ApplicationContext springAppContext;

  private static SettingsService settingsService;

  private static AppViewModel appViewModel;

  @Autowired
  private AppContext(SettingsService settingsService, AppViewModel appViewModel) {
    AppContext.settingsService = settingsService;
    AppContext.appViewModel = appViewModel;
  }

  public static AppViewModel getAppViewModel() {
    return appViewModel;
  }

  public static SettingsService getSettingsService() {
    return settingsService;
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
}