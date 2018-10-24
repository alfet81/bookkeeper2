package com.bookkeeper.domain.settings;

import com.bookkeeper.domain.settings.Settings;
import com.bookkeeper.core.type.Property;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface SettingsRepository extends JpaRepository<Settings, Long> {
  Optional<Settings> findByProperty(Property property);
}
