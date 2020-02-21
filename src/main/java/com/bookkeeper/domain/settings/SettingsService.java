package com.bookkeeper.domain.settings;

import static com.bookkeeper.type.Property.LAST_LOGIN_DATE;
import static com.bookkeeper.utils.DateTimeUtils.date2String;
import static com.bookkeeper.utils.MiscUtils.asOptional;
import static java.time.LocalDate.now;
import static java.util.stream.Collectors.toMap;

import com.bookkeeper.type.Property;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.EnumMap;
import java.util.Map;
import java.util.Optional;
import javax.annotation.PostConstruct;

@Service
public class SettingsService {

  @Autowired
  private SettingsRepository settingsRepository;

  private Map<Property, String> settingsCache;

  @PostConstruct
  void init() {
    settingsCache = getAllSettings();
  }

  public Map<Property, String> getAllSettings() {
    return settingsRepository.findAll().stream()
        .collect(toMap(
            Settings::getProperty,
            Settings::getValue,
            (s1,s2) -> s1,
            () -> new EnumMap<>(Property.class)));
  }

  public void save(Settings settings) {
    settingsRepository.save(settings);
  }

  public void delete(Settings settings) {
    settingsRepository.delete(settings);
  }

  public Optional<Settings> findByProperty(Property property) {
    return settingsRepository.findByProperty(property);
  }

  public Object getProperty(Property property) {
    return asOptional(settingsCache.get(property)).map(property::convertValue)
        .orElse(property.getDefaultValue());
  }

  public void recordLoginDate() {
    var setting = new Settings(LAST_LOGIN_DATE, date2String(now()));
    save(setting);
  }
}
