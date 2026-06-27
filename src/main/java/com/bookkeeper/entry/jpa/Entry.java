package com.bookkeeper.entry.jpa;

import static lombok.AccessLevel.NONE;

import static java.time.LocalDateTime.now;
import static java.util.Collections.unmodifiableList;
import static jakarta.persistence.CascadeType.ALL;
import static jakarta.persistence.FetchType.LAZY;

import com.bookkeeper.account.jpa.Account;
import com.bookkeeper.attachment.jpa.Attachment;
import com.bookkeeper.category.jpa.Category;
import com.bookkeeper.common.jpa.AbstractEntity;
import com.bookkeeper.label.jpa.Label;
import io.hypersistence.utils.hibernate.type.money.MonetaryAmountType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.CompositeType;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import javax.money.MonetaryAmount;
import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;


//@Indexed
@Entity
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@Table(name = "entries", schema = "data")
public class Entry extends AbstractEntity {

  @ManyToOne(fetch = LAZY)
  @JoinColumn(name = "account_id")
  private Account account;

  @ManyToOne(fetch = LAZY)
  @JoinColumn(name = "category_id")
  private Category category;

  @Column(name = "transaction_date")
  private LocalDate date;

  @Embedded
  @AttributeOverride(name = "amount", column = @Column(name = "transaction_amount"))
  @AttributeOverride(name = "currency", column = @Column(name = "transaction_currency_code"))
  @CompositeType(MonetaryAmountType.class)
  private MonetaryAmount amount;


  //@Field
  private String notes;

  private LocalDateTime createdDate;

  private LocalDateTime modifiedDate;

  @ManyToMany(cascade = ALL)
  @JoinTable(
      name = "entry_labels",
      joinColumns = @JoinColumn(name = "entry_id"),
      inverseJoinColumns = @JoinColumn(name = "label_id")
  )
  @ToString.Exclude
  private Set<Label> labels;

  @Getter(NONE)
  @Setter(NONE)
  @ToString.Exclude
  @OneToMany(mappedBy = "entry", cascade = ALL, orphanRemoval = true)
  private List<Attachment> attachments = new ArrayList<>();


  @PrePersist
  protected void onPrePersist() {
    createdDate = now();
  }


  @PreUpdate
  protected void onPreUpdate() {
    modifiedDate = now();
  }


  public List<Attachment> getAttachments() {
    return unmodifiableList(attachments);
  }


  public void addAttachment(Attachment attachment) {
    attachments.add(attachment);
    attachment.setEntry(this);
  }


  public void deleteAttachment(Attachment attachment) {
    attachments.remove(attachment);
    attachment.setEntry(null);
  }
}