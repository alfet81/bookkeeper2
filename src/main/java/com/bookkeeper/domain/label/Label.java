package com.bookkeeper.domain.label;

import static javax.persistence.FetchType.LAZY;

import com.bookkeeper.types.BaseEntity;
import com.bookkeeper.domain.entry.Entry;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.PreRemove;
import javax.persistence.Table;

@Entity
@Table(name = "labels")
@Getter @Setter
@NoArgsConstructor
@ToString(callSuper = true, of = "name")
public class Label extends BaseEntity implements Comparable<Label> {

  @Column(length = 100, nullable = false)
  private String name;

  @Setter(AccessLevel.NONE)
  @Getter(AccessLevel.NONE)
  @ManyToMany(mappedBy = "labels", fetch = LAZY)
  private Set<Entry> entries = new HashSet<>();

  public Label(String name) {
    this.name = name;
  }

  public Set<Entry> getEntries() {
    return Collections.unmodifiableSet(entries);
  }

  public void addEntry(Entry entry) {
    entries.add(entry);
  }

  public void removeEntry(Entry entry) {
    entries.remove(entry);
  }

  @Override
  public int compareTo(Label label) {
    return name.compareTo(label.getName());
  }

  @PreRemove
  private void removeLabelFromEntries() {
    entries.parallelStream().forEach(entry -> entry.removeLabel(this));
  }
}