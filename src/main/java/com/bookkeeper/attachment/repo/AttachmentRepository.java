package com.bookkeeper.attachment.repo;

import com.bookkeeper.attachment.entity.Attachment;
import com.bookkeeper.entry.entity.Entry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AttachmentRepository extends JpaRepository<Attachment, Long> {

  List<Attachment> findByEntry(Entry entry);

}
