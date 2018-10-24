package com.bookkeeper.domain.settings;

import com.bookkeeper.domain.settings.Settings;
import com.bookkeeper.core.type.Property;
import java.util.Map;
import java.util.Optional;

public interface SettingsService {
  Map<Property, String> getAllSettings();
  Optional<Settings> findByProperty(Property property);
  void save(Settings settings);
  void delete(Settings settings);
}
