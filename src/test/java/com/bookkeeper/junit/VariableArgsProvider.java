package com.bookkeeper.junit;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.support.AnnotationConsumer;
import java.lang.reflect.Field;
import java.util.stream.Stream;

public class VariableArgsProvider implements ArgumentsProvider, AnnotationConsumer<VariableSource> {

  private String variableName;


  @Override
  public void accept(VariableSource variableSource) {
    variableName = variableSource.value();
  }


  @Override
  public Stream<? extends Arguments> provideArguments(ExtensionContext ctx) {
    return ctx.getTestClass()
              .map(this::getField)
              .map(this::getValue)
              .orElseThrow(() -> new IllegalArgumentException("Failed to load test arguments"));
  }


  private Field getField(Class<?> clazz) {
    try {
      var field = clazz.getDeclaredField(variableName);
      field.setAccessible(true);
      return field;
    } catch (Exception e) {
      return null;
    }
  }


  @SuppressWarnings("unchecked")
  private Stream<Arguments> getValue(Field field) {
    try {
      return (Stream<Arguments>) field.get(null);
    } catch (Exception ignored) {
    }

    return null;
  }
}
