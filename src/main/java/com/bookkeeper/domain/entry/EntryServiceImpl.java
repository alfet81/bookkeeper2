package com.bookkeeper.domain.entry;

import com.bookkeeper.domain.entry.Entry;
import com.bookkeeper.domain.entry.EntryRepository;
import com.bookkeeper.domain.entry.EntryService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EntryServiceImpl implements EntryService {

  @Autowired
  private EntryRepository entryRepository;

  @Override
  public void save(List<Entry> entries) {
    entryRepository.saveAll(entries);
  }

  @Override
  public void delete(List<Entry> entries) {
    entryRepository.deleteAll(entries);
  }

  @Override
  public void save(Entry entry) {
    entryRepository.save(entry);
  }

  @Override
  public void delete(Entry entry) {
    entryRepository.delete(entry);
  }
}
