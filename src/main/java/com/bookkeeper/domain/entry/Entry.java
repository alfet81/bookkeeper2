package com.bookkeeper.domain.entry;

import static lombok.AccessLevel.NONE;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.CascadeType.MERGE;
import static javax.persistence.CascadeType.PERSIST;
import static javax.persistence.CascadeType.REFRESH;
import static javax.persistence.FetchType.EAGER;

//import org.hibernate.search.annotations.Field;
//import org.hibernate.search.annotations.Indexed;
import com.bookkeeper.core.type.BaseEntity;
import com.bookkeeper.domain.account.Account;
import com.bookkeeper.domain.category.Category;
import com.bookkeeper.domain.attachment.Attachment;
import com.bookkeeper.domain.label.Label;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Currency;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;


//@Indexed
@Entity
@Table(name = "entries",
indexes = {@Index(name = "idx_entries_transaction_date", columnList = "transaction_date")})
@Getter @Setter
@ToString(callSuper = true)
@NoArgsConstructor
public class Entry extends BaseEntity {

  @ManyToOne
  @JoinColumn(name = "account_id",
  foreignKey = @ForeignKey(name = "fk_entries_account_id"), nullable = false)
  private Account account;

  @OneToOne
  @JoinColumn(
      name = "category_id",
      nullable = false,
      foreignKey = @ForeignKey(name = "fk_entries_category_id"))
  private Category category;

  @Column(nullable = false, precision = 9, scale = 2)
  private BigDecimal amount;

  @Column(nullable = false, length = 3)
  private Currency currency;

  @Column(name = "transaction_date", nullable = false)
  private LocalDate transactionDate;

  //@Field
  private String notes;

  private LocalDate createdDate;

  private LocalDate modifiedDate;

  @ManyToMany(cascade = {PERSIST, MERGE, REFRESH}, fetch = EAGER)
  @JoinTable(name = "xref_entries_labels", joinColumns = @JoinColumn(name = "entry_id"),
  inverseJoinColumns = @JoinColumn(name = "label_id"), uniqueConstraints =
  @UniqueConstraint(name = "idx_entries_id_labels_id", columnNames = {"entry_id", "label_id"}),
  foreignKey = @ForeignKey(name = "fk_xref_entries_labels_entry_id"),
  inverseForeignKey = @ForeignKey(name = "fk_xref_entries_labels_label_id"))
  @Setter(NONE)
  private Set<Label> labels = new HashSet<>();

  @Getter(NONE)
  @Setter(NONE)
  @OneToMany(mappedBy = "entry", cascade = ALL, orphanRemoval = true, fetch = EAGER)
  private List<Attachment> attachments = new ArrayList<>();

  @Builder
  protected Entry(Account account, Category category, BigDecimal amount, Currency currency,
      LocalDate transactionDate, String notes) {
    this.account = account;
    this.category = category;
    this.amount = amount;
    this.currency = currency;
    this.transactionDate = transactionDate;
    this.notes = notes;
  }

  @PrePersist
  protected void onPrePersist() {
    createdDate = LocalDate.now();
  }

  @PreUpdate
  protected void onPreUpdate() {
    modifiedDate = LocalDate.now();
  }

  public List<Attachment> getAttachments() {
    return Collections.unmodifiableList(attachments);
  }

  public void addAttachment(Attachment attachment) {
    attachments.add(attachment);
    attachment.setEntry(this);
  }

  public void deleteAttachment(Attachment attachment) {
    attachments.remove(attachment);
    attachment.setEntry(null);
  }

  public Set<Label> getLabels() {
    return Collections.unmodifiableSet(labels);
  }

  public void addLabel(Label label) {
    labels.add(label);
    label.addEntry(this);
  }

  public void removeLabel(Label label) {
    labels.remove(label);
  }
}