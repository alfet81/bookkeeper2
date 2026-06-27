package com.bookkeeper.account.view.mapper;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

import com.bookkeeper.account.model.Account;
import com.bookkeeper.account.view.model.AccountTreeViewItem;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = SPRING)
public interface AccountTreeViewItemMapper {

  @Mapping(target = "value", source = ".")
  AccountTreeViewItem toAccountTreeViewItem(Account account, @Context AccountTreeViewItemCyclicGraphContext context);

  List<AccountTreeViewItem> toAccountTreeViewItems(List<Account> accounts,
      @Context AccountTreeViewItemCyclicGraphContext context);
}
