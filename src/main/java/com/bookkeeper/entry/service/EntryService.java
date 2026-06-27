package com.bookkeeper.entry.service;

import com.bookkeeper.entry.jpa.Entry;
import com.bookkeeper.entry.jpa.EntryRepository;
import com.bookkeeper.common.model.date.DateRange;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EntryService {

  private final EntryRepository entryRepository;


  public void save(List<Entry> entries) {
    entryRepository.saveAll(entries);
  }


  public void delete(List<Entry> entries) {
    entryRepository.deleteAll(entries);
  }


  public void save(Entry entry) {
    entryRepository.save(entry);
  }


  public void delete(Entry entry) {
    entryRepository.delete(entry);
  }


  public List<Entry> fetch(DateRange dateRange) {
    if (dateRange == null || dateRange.isEmpty()) {
      return entryRepository.findAll();
    }
    return entryRepository.findByDateBetweenOrderByDateAscIdAsc(dateRange.getStartDate(),
        dateRange.getEndDate());
  }
}
