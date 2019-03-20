package com.bookkeeper.domain.settings;

import com.bookkeeper.types.Property;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface SettingsRepository extends JpaRepository<Settings, Long> {
  Optional<Settings> findByProperty(Property property);
}
