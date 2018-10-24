package com.bookkeeper.domain.label;

import com.bookkeeper.domain.label.Label;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface LabelRepository extends JpaRepository<Label, Long> {
  @Override
  List<Label> findAll();
}
