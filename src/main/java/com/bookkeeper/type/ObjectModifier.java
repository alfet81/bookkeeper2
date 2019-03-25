package com.bookkeeper.type;

@FunctionalInterface
public interface ObjectModifier<T, V> {
  void modify(T object, V value);
}
