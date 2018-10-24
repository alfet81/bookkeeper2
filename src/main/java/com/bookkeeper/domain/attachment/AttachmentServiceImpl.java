package com.bookkeeper.domain.attachment;

import com.bookkeeper.domain.attachment.Attachment;
import com.bookkeeper.domain.attachment.AttachmentRepository;
import com.bookkeeper.domain.attachment.AttachmentService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AttachmentServiceImpl implements AttachmentService {

  @Autowired
  protected AttachmentRepository attachmentRepository;

  @Override
  public void save(Attachment attachment) {
    attachmentRepository.save(attachment);
  }

  @Override
  public void delete(Attachment attachment) {
    attachmentRepository.delete(attachment);
  }

  @Override
  public void delete(List<Attachment> attachments) {
    attachmentRepository.deleteAll(attachments);
  }

  @Override
  public Optional<Attachment> getAttachment(Long attachmentId) {
    return attachmentRepository.findById(attachmentId);
  }
}
