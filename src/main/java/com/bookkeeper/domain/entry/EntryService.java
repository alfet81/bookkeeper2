package com.bookkeeper.domain.entry;

import com.bookkeeper.dto.DateRange;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EntryService {

  @Autowired
  private EntryRepository entryRepository;

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
    return entryRepository.findByDateBetweenOrderByDateAscIdAsc(dateRange.getStartDate(),
        dateRange.getEndDate());
  }
}
