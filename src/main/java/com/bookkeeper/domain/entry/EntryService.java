package com.bookkeeper.domain.entry;

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
}
