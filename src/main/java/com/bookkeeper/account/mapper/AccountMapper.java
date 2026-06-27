package com.bookkeeper.account.mapper;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

import com.bookkeeper.account.model.Account;
import com.bookkeeper.common.model.currency.CurrencyMapper;
import org.mapstruct.Context;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = SPRING, uses = CurrencyMapper.class, injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface AccountMapper {

  @Mapping(target = "parent", ignore = true)
  com.bookkeeper.account.jpa.Account toEntity(Account account);

  List<com.bookkeeper.account.jpa.Account> toEntities(List<Account> accounts);

  Account toModel(com.bookkeeper.account.jpa.Account accountEntity, @Context AccountCyclicGraphContext context);

  List<Account> toModel(List<com.bookkeeper.account.jpa.Account> accountEntities,
      @Context AccountCyclicGraphContext context);
}
