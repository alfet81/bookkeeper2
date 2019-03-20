package com.bookkeeper.domain.settings;

import static java.util.stream.Collectors.toMap;

import com.bookkeeper.types.Property;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.EnumMap;
import java.util.Map;
import java.util.Optional;

@Service
public class SettingsService {

  @Autowired
  private SettingsRepository settingsRepository;

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
}
