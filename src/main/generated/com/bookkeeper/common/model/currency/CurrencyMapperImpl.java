package com.bookkeeper.common.model.currency;

import javax.annotation.processing.Generated;
import javax.money.CurrencyUnit;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-02-12T03:38:43-0500",
    comments = "version: 1.6.3, compiler: javac, environment: Java 23.0.1 (Amazon.com Inc.)"
)
@Component
public class CurrencyMapperImpl implements CurrencyMapper {

    @Override
    public String toCurrencyCode(CurrencyUnit currencyUnit) {
        if ( currencyUnit == null ) {
            return null;
        }

        String string = new String();

        return string;
    }
}
