package com.bookkeeper.common.model.types;

import org.mapstruct.AfterMapping;
import org.mapstruct.BeforeMapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.TargetType;

import java.util.IdentityHashMap;
import java.util.Map;

public abstract class AbstractCyclicGraphContext<T> {

  private final Map<Object, Object> storage = new IdentityHashMap<>();


  @BeforeMapping
  public T getMappedInstance(Object source, @TargetType Class<T> targetType) {
    return (T) storage.get(source);
  }


  @AfterMapping
  public void storeMappedInstance(Object source, @MappingTarget T target) {
    storage.put(source, target);
  }
}