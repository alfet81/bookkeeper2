package com.bookkeeper.types;

@FunctionalInterface
public interface ObjectModifier<T, V> {
  void modify(T object, V value);
}
