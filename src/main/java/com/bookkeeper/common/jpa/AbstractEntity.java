package com.bookkeeper.common.jpa;

import static jakarta.persistence.GenerationType.IDENTITY;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.hibernate.proxy.HibernateProxy;
import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@ToString
@SuperBuilder
@MappedSuperclass
@NoArgsConstructor
public abstract class AbstractEntity implements Serializable {

  @Id
  @GeneratedValue(strategy = IDENTITY)
  protected Long id;


  private static Class<?> getClassWithoutInitializingProxy(Object object) {
    return (object instanceof HibernateProxy proxy) ?
        proxy.getHibernateLazyInitializer().getPersistentClass() : object.getClass();
  }


  @Override
  public boolean equals(Object other) {
    if (this == other) {
      return true;
    }
    if (other == null || getClassWithoutInitializingProxy(this) != getClassWithoutInitializingProxy(other)) {
      return false;
    }
    if (other instanceof AbstractEntity otherEntity) {
      return Objects.equals(getId(), otherEntity.getId());
    }
    return false;
  }


  @Override
  public final int hashCode() {
    return getClassWithoutInitializingProxy(this).hashCode();
  }
}
