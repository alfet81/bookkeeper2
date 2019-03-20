package com.bookkeeper.domain.attachment;

import com.bookkeeper.domain.entry.Entry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AttachmentRepository extends JpaRepository<Attachment, Long> {
  List<Attachment> findByEntry(Entry entry);
}
