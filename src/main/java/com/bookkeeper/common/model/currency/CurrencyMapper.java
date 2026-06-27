package com.bookkeeper.common.model.currency;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants.ComponentModel;

import javax.money.CurrencyUnit;
import javax.money.Monetary;

@Mapper(componentModel = ComponentModel.SPRING)
public interface CurrencyMapper {

  default CurrencyUnit toCurrencyUnit(String currencyCode) {
    return Monetary.getCurrency(currencyCode);
  }

  @Mapping(target = ".", source = "currencyCode")
  String toCurrencyCode(CurrencyUnit currencyUnit);
}
