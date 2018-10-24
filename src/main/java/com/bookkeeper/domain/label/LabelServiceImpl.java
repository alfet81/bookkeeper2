package com.bookkeeper.domain.label;

import com.bookkeeper.domain.label.Label;
import com.bookkeeper.domain.label.LabelRepository;
import com.bookkeeper.domain.label.LabelService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class LabelServiceImpl implements LabelService {

  @Autowired
  private LabelRepository labelRepository;

  @Override
  public Set<Label> getLabels() {
    return new HashSet<>(labelRepository.findAll());
  }

  @Override
  public void save(Label label) {
    labelRepository.save(label);
  }

  @Override
  public void delete(List<Label> labels) {
    labelRepository.deleteAll(labels);
  }
}
