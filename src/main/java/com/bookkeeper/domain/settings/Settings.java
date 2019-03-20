package com.bookkeeper.domain.settings;

import static javax.persistence.EnumType.STRING;

import com.bookkeeper.types.BaseEntity;
import com.bookkeeper.types.Property;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "settings",
    uniqueConstraints = @UniqueConstraint(name = "idx_settings_property", columnNames = {"property"}))
@Getter @Setter 
@ToString(callSuper = true)
@NoArgsConstructor @AllArgsConstructor
public class Settings extends BaseEntity {

  @Enumerated(STRING)
  @Column(length = 50, nullable = false)
  private Property property;

  private String value;
}