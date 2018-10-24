package com.bookkeeper.domain.attachment;

import com.bookkeeper.domain.attachment.Attachment;
import java.util.List;
import java.util.Optional;

public interface AttachmentService {
  void save(Attachment attachment);
  void delete(Attachment attachment);
  void delete(List<Attachment> attachments);
  Optional<Attachment> getAttachment(Long attachmentId);
}
