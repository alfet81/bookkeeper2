package com.bookkeeper.domain.label;

import com.bookkeeper.domain.label.Label;
import java.util.List;
import java.util.Set;

public interface LabelService {
  Set<Label> getLabels();
  void save(Label label);
  void delete(List<Label> labels);
}
