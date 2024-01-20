package com.bookkeeper.label.service;

import com.bookkeeper.label.entity.Label;
import com.bookkeeper.label.repo.LabelRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class LabelService {

  private final LabelRepository labelRepository;

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
