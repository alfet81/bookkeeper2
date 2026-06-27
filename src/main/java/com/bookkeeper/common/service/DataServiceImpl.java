package com.bookkeeper.common.service;

import com.bookkeeper.account.service.AccountService;
import com.bookkeeper.attachment.service.AttachmentService;
import com.bookkeeper.category.service.CategoryService;
import com.bookkeeper.entry.service.EntryService;
import com.bookkeeper.settings.service.UserSettingsService;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.XSlf4j;

@XSlf4j
@Setter
@RequiredArgsConstructor
public class DataServiceImpl implements DataService {
  private final UserSettingsService userSettingsService;
  private final AccountService accountService;
  private final CategoryService categoryService;
  private final EntryService entryService;
  private final AttachmentService attachmentService;
}
