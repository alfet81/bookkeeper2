package com.bookkeeper.domain.attachment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AttachmentService {

  @Autowired
  protected AttachmentRepository attachmentRepository;

  public void save(Attachment attachment) {
    attachmentRepository.save(attachment);
  }

  public void delete(Attachment attachment) {
    attachmentRepository.delete(attachment);
  }

  public void delete(List<Attachment> attachments) {
    attachmentRepository.deleteAll(attachments);
  }

  public Optional<Attachment> getAttachment(Long attachmentId) {
    return attachmentRepository.findById(attachmentId);
  }
}
