package com.bookkeeper;

import com.bookkeeper.config.AppContext;
import com.bookkeeper.main.view.MainWindowView;
import jfx.support.AbstractJavaFxApplication;
import jfx.support.fxml.annotation.FXMLController;
import jfx.support.fxml.annotation.FXMLView;
import lombok.Setter;
import lombok.extern.slf4j.XSlf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

@XSlf4j
@EnableCaching
@SpringBootApplication
public class BookkeeperApplication extends AbstractJavaFxApplication implements CommandLineRunner {

  private static final String MENU_ABOUT_NAME_KEY = "com.apple.mrj.application.apple.menu.about.name";
  private static final String MENU_ABOUT_NAME_VALUE = "Personal Bookkeeper";
  private static final String BASE_PACKAGE = "com.bookkeeper";
  private static final String MAPPER_BEAN_SUFFIX = "MapperImpl";
  private static final String VIEW_MODEL_BEAN_SUFFIX = "ViewModel";

  private static final Predicate<String> MAPSTRUCT_MAPPERS = (String beanName) ->
      isBookkeeperBean(beanName) && beanName.endsWith(MAPPER_BEAN_SUFFIX);

  private static final Predicate<String> VIEW_MODELS = (String beanName) -> isBookkeeperBean(beanName)
      && beanName.endsWith(VIEW_MODEL_BEAN_SUFFIX);

  private static final Predicate<String> OTHER_COMPONENTS = (String beanName) -> isBookkeeperBean(beanName)
      && !(beanName.endsWith(MAPPER_BEAN_SUFFIX) || beanName.endsWith(VIEW_MODEL_BEAN_SUFFIX));

  @Setter(onMethod_ = { @Autowired(required = false) })
  private CacheManager cacheManager;


  public static void main(String... args) {
    System.setProperty(MENU_ABOUT_NAME_KEY, MENU_ABOUT_NAME_VALUE);
    launch(BookkeeperApplication.class, MainWindowView.class, args);
  }


  private static List<String> getMapstructMapperBeanNames() {
    return Arrays.stream(AppContext.getBeanNames())
        .filter(MAPSTRUCT_MAPPERS)
        .toList();
  }


  private static List<String> getViewModelBeanNames() {
    return Arrays.stream(AppContext.getBeanNames())
        .filter(VIEW_MODELS)
        .toList();
  }


  private static List<String> getOtherBeanNames() {
    return Arrays.stream(AppContext.getBeanNames())
        .filter(OTHER_COMPONENTS)
        .toList();
  }


  private static boolean isBookkeeperBean(String beanName) {
    return AppContext.getType(beanName).map(beanType -> beanType.getPackage().getName().startsWith(BASE_PACKAGE))
        .orElse(false);
  }


  private static List<String> getAnnotatedBeanNames(Class<? extends Annotation> annotation) {
    return List.of(Arrays.stream(AppContext.getBeanNamesForAnnotation(annotation)).sorted().toArray(String[]::new));
  }


  @Override
  public void run(String... args) {

    var registeredFxmlViews = getAnnotatedBeanNames(FXMLView.class);
    if (!registeredFxmlViews.isEmpty()) {
      log.info("Registered FXMLView beans: {}", registeredFxmlViews);
    } else {
      log.warn("No any FXMLView beans registered");
    }

    var registeredFxmlControllers = getAnnotatedBeanNames(FXMLController.class);
    if (!registeredFxmlControllers.isEmpty()) {
      log.info("Registered FXMLController beans: {}", registeredFxmlControllers);
    } else {
      log.warn("No any FXMLController beans registered");
    }

    var registeredServices = getAnnotatedBeanNames(Service.class);
    log.info("Registered Service beans: {}", registeredServices);

    var registeredRepositories = getAnnotatedBeanNames(Repository.class);
    log.info("Registered JPA Repository beans: {}", registeredRepositories);

    var mappers = getMapstructMapperBeanNames();
    log.info("Registered Mapper beans: {}", mappers);

    var viewModels = getViewModelBeanNames();
    log.info("Registered ViewModel beans: {}", viewModels);

    var otherComponents = new ArrayList<>(getOtherBeanNames());
    otherComponents.removeAll(registeredFxmlViews);
    otherComponents.removeAll(registeredFxmlControllers);
    otherComponents.removeAll(registeredServices);
    otherComponents.removeAll(registeredRepositories);
    otherComponents.removeAll(mappers);
    otherComponents.removeAll(viewModels);

    log.info("Other beans (configs, custom beans, etc): {}", otherComponents);
  }
}
