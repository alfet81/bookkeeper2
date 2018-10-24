package com.bookkeeper.core.type;

import static javax.persistence.GenerationType.IDENTITY;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import java.io.Serializable;
import java.util.Objects;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
@Getter @Setter @NoArgsConstructor
@ToString
public abstract class BaseEntity implements Serializable {

  @Id
  @GeneratedValue(strategy = IDENTITY)
  protected Long id;

  @Override
  public boolean equals(Object obj) {

    if (this == obj) {
      return true;
    }

    if (!(obj instanceof BaseEntity)) {
      return false;
    }

    BaseEntity other = (BaseEntity) obj;
    return Objects.equals(id, other.id);
  }

  @Override
  public int hashCode() {
    return id == null ? super.hashCode() : id.hashCode();
  }
}
