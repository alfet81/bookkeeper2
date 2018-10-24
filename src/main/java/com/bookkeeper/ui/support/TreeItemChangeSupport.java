package com.bookkeeper.ui.support;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public interface TreeItemChangeSupport {

  PropertyChangeSupport getItemChangeSupport();

  default void addItemChangeListener(PropertyChangeListener listener) {
    getItemChangeSupport().addPropertyChangeListener(listener);
  }

  default void removeItemChangeListener(PropertyChangeListener listener) {
    getItemChangeSupport().removePropertyChangeListener(listener);
  }
}
