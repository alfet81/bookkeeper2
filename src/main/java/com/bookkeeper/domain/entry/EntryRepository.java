package com.bookkeeper.domain.entry;

import com.bookkeeper.domain.category.Category;
import com.bookkeeper.domain.label.Label;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface EntryRepository extends JpaRepository<Entry, Long>//,QuerydslPredicateExecutor<Entry> 
{

  List<Entry> findByCategoryIn(Collection<Category> categories);
  List<Entry> findByLabelsIn(Collection<Label> labels);
  Optional<Entry> findFirstByCategory(Category category);
}
