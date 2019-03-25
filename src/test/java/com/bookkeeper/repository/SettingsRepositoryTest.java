package com.bookkeeper.repository;

import com.bookkeeper.domain.settings.Settings;
import com.bookkeeper.domain.settings.SettingsRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.List;
import static com.bookkeeper.type.Property.PREFERRED_CURRENCY;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class SettingsRepositoryTest {

  @Autowired
  private TestEntityManager entityManager;

  @Autowired
  private SettingsRepository settingsRepository;

  @Before
  public void initSettings() {
    Settings settings = new Settings(PREFERRED_CURRENCY, "en_US");
    entityManager.persistAndFlush(settings);
  }

  @Test
  public void whenSearchedForPreferredCurrency_ThenFoundSettings() {
    //given
    //initSettings

    //when
    Settings found = settingsRepository.findAll().stream()
        .findFirst().orElseThrow(() ->
        new RuntimeException("Settings not found"));

    //then
    assertThat(found.getProperty()).isEqualTo(PREFERRED_CURRENCY);
    assertThat(found.getValue()).isEqualTo("en_US");
  }

  @Test
  public void whenDeletePreferredCurrencySettings_ThenNoSettingsFound() {
    //given
    //initSettings

    //when
    Settings settings = settingsRepository.findAll().stream().findFirst().orElseThrow(() ->
    new RuntimeException("Settings not found"));
    settingsRepository.delete(settings);

    //then
    List<Settings> found = settingsRepository.findAll();
    assertThat(found).isEmpty();
  }

  @Test
  public void whenUpdatePreferredCurrencySettings_ThenUpdated() {
    //give
    //initSettings

    //when
    Settings settings = settingsRepository.findAll().stream().findFirst().orElseThrow(() ->
    new RuntimeException("Settings not found"));

    settings.setValue("new value");
    settingsRepository.save(settings);

    //then
    Settings newValue = settingsRepository.findAll().stream().findFirst().orElseThrow(() ->
    new RuntimeException("Settings not found"));

    assertThat(newValue.getValue()).isEqualTo("new value");
  }
}
