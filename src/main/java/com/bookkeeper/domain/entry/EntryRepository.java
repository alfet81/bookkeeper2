package com.bookkeeper.domain.entry;

import com.bookkeeper.domain.category.Category;
import com.bookkeeper.domain.label.Label;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

@Repository
public interface EntryRepository extends JpaRepository<Entry, Long> {
  List<Entry> findByCategoryIn(Collection<Category> categories);
  List<Entry> findByLabelsIn(Collection<Label> labels);
  List<Entry> findByDateBetweenOrderByDateAscIdAsc(LocalDate startDate, LocalDate endDate);
}
