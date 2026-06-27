package com.bookkeeper.attachment.jpa;

import static jakarta.persistence.FetchType.LAZY;

import com.bookkeeper.common.jpa.AbstractEntity;
import com.bookkeeper.entry.jpa.Entry;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Getter
@Setter
@Entity
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@Table(name = "attachments", schema = "data")
public class Attachment extends AbstractEntity {

  @ToString.Exclude
  @ManyToOne(fetch = LAZY)
  @JoinColumn(name = "entryId", nullable = false,
      foreignKey = @ForeignKey(name = "fk_attachments_entry_id"))
  private Entry entry;

  private String name;

  private String filePath;
}