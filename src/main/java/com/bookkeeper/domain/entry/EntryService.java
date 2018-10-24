package com.bookkeeper.domain.entry;

import com.bookkeeper.domain.entry.Entry;
import java.util.List;

public interface EntryService {
  void save(Entry entry);
  void save(List<Entry> entries);
  void delete(Entry entry);
  void delete(List<Entry> entries);
}
