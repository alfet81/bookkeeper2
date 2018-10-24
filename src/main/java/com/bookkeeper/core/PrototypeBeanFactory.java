package com.bookkeeper.core;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class PrototypeBeanFactory implements ApplicationContextAware {

  private static ApplicationContext applicationContext;

  public static <T> T getBean(Class<T> beanClass) {
    return applicationContext.getBean(beanClass);
  }

  @Override
  public void setApplicationContext(ApplicationContext appContext) throws BeansException {
    applicationContext = appContext;
  }
}
