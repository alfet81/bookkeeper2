package com.bookkeeper.domain.settings;

import com.bookkeeper.domain.settings.Settings;
import com.bookkeeper.core.type.Property;
import com.bookkeeper.domain.settings.SettingsRepository;
import com.bookkeeper.domain.settings.SettingsService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.EnumMap;
import java.util.Map;
import java.util.Optional;
import static java.util.stream.Collectors.toMap;

@Service
public class SettingsServiceImpl implements SettingsService {

  @Autowired
  private SettingsRepository settingsRepository;

  @Override
  public Map<Property, String> getAllSettings() {
    return settingsRepository.findAll().stream()
        .collect(toMap(
            Settings::getProperty,
            Settings::getValue,
            (s1,s2) -> s1,
            () -> new EnumMap<>(Property.class)));
  }

  @Override
  public void save(Settings settings) {
    settingsRepository.save(settings);
  }

  @Override
  public void delete(Settings settings) {
    settingsRepository.delete(settings);
  }

  @Override
  public Optional<Settings> findByProperty(Property property) {
    return settingsRepository.findByProperty(property);
  }
}
