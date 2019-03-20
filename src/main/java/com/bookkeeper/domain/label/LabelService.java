package com.bookkeeper.domain.label;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class LabelService {

  @Autowired
  private LabelRepository labelRepository;

  public Set<Label> getLabels() {
    return new HashSet<>(labelRepository.findAll());
  }

  public void save(Label label) {
    labelRepository.save(label);
  }

  public void delete(List<Label> labels) {
    labelRepository.deleteAll(labels);
  }
}
