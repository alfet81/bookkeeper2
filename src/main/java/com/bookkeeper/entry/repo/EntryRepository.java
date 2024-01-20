package com.bookkeeper.entry.repo;

import com.bookkeeper.category.entity.Category;
import com.bookkeeper.entry.entity.Entry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

@Repository
public interface EntryRepository extends JpaRepository<Entry, Long> {

  List<Entry> findByCategoryIn(Collection<Category> categories);

  List<Entry> findByDateBetweenOrderByDateAscIdAsc(LocalDate startDate, LocalDate endDate);
}
