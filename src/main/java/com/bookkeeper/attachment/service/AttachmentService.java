package com.bookkeeper.attachment.service;

import com.bookkeeper.attachment.jpa.Attachment;
import com.bookkeeper.attachment.jpa.AttachmentRepository;
import com.bookkeeper.common.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AttachmentService {

  protected final AttachmentRepository attachmentRepository;


  public void save(Attachment attachment) {
    attachmentRepository.save(attachment);
  }


  public void delete(Attachment attachment) {
    attachmentRepository.delete(attachment);
  }


  public void delete(List<Attachment> attachments) {
    attachmentRepository.deleteAll(attachments);
  }


  public Attachment getAttachment(Long attachmentId) {
    return attachmentRepository.findById(attachmentId)
        .orElseThrow(() -> new NotFoundException("Attachment with id " + attachmentId + " not found"));
  }
}
