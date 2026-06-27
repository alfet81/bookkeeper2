package com.bookkeeper.config;

import lombok.extern.slf4j.XSlf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.util.Optional;

@XSlf4j
@Component
public class AppContext implements ApplicationContextAware {

  private static ApplicationContext springAppContext;


  public static <T> T getBean(Class<T> beanClass) {
    return springAppContext.getBean(beanClass);
  }


  public static <T> T getBean(Class<T> beanClass, Object... args) {
    return springAppContext.getBean(beanClass, args);
  }


  public static String[] getBeanNames() {
    return springAppContext.getBeanDefinitionNames();
  }


  public static String[] getBeanNamesForType(Class<?> type) {
    return springAppContext.getBeanNamesForType(type);
  }


  public static String[] getBeanNamesForAnnotation(Class<? extends Annotation> annotationClass) {
    return springAppContext.getBeanNamesForAnnotation(annotationClass);
  }


  public static Optional<Class<?>> getType(String beanName) {
    try {
      var beanType = springAppContext.getType(beanName);
      return Optional.ofNullable(beanType);
    } catch (NoSuchBeanDefinitionException e) {
      log.warn("No such bean definition for name {}", beanName);
      return Optional.empty();
    }
  }


  @Override
  public void setApplicationContext(ApplicationContext context) throws BeansException {
    springAppContext = context;
  }
}