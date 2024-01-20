package com.bookkeeper.attachment.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import java.sql.Blob;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import static javax.persistence.FetchType.LAZY;

import com.bookkeeper.common.entity.BaseEntity;
import com.bookkeeper.entry.entity.Entry;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@Entity
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true, of = "name")
public class Attachment extends BaseEntity {

  @ManyToOne(fetch = LAZY)
  @JoinColumn(name = "entryId", nullable = false,
    foreignKey = @ForeignKey(name = "fk_attachments_entry_id"))
  private Entry entry;

  @Column(nullable = false)
  private String name;

  @Lob
  @Basic(fetch = LAZY)
  private Blob file;
}