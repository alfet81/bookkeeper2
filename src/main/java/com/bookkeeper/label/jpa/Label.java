package com.bookkeeper.label.jpa;

import com.bookkeeper.common.jpa.AbstractEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@Table(name = "labels", schema = "data")
public class Label extends AbstractEntity implements Comparable<Label> {

  private String name;


  @Override
  public int compareTo(Label label) {
    return name.compareTo(label.getName());
  }
}