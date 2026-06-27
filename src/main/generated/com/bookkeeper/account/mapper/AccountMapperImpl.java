package com.bookkeeper.account.mapper;

import com.bookkeeper.account.model.Account;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-02-12T03:38:43-0500",
    comments = "version: 1.6.3, compiler: javac, environment: Java 23.0.1 (Amazon.com Inc.)"
)
@Component
public class AccountMapperImpl implements AccountMapper {

    @Override
    public com.bookkeeper.account.jpa.Account toEntity(Account account) {
        if ( account == null ) {
            return null;
        }

        com.bookkeeper.account.jpa.Account.AccountBuilder<?, ?> account1 = com.bookkeeper.account.jpa.Account.builder();

        account1.id( account.getId() );
        account1.name( account.getName() );
        account1.currency( account.getCurrency() );
        account1.initialBalance( account.getInitialBalance() );

        return account1.build();
    }

    @Override
    public List<com.bookkeeper.account.jpa.Account> toEntities(List<Account> accounts) {
        if ( accounts == null ) {
            return null;
        }

        List<com.bookkeeper.account.jpa.Account> list = new ArrayList<com.bookkeeper.account.jpa.Account>( accounts.size() );
        for ( Account account : accounts ) {
            list.add( toEntity( account ) );
        }

        return list;
    }

    @Override
    public Account toModel(com.bookkeeper.account.jpa.Account accountEntity, AccountCyclicGraphContext context) {
        Account target = context.getMappedInstance( accountEntity, Account.class );
        if ( target != null ) {
            return target;
        }

        if ( accountEntity == null ) {
            return null;
        }

        Account.AccountBuilder account = Account.builder();

        account.id( accountEntity.getId() );
        account.name( accountEntity.getName() );
        account.currency( accountEntity.getCurrency() );
        account.initialBalance( accountEntity.getInitialBalance() );
        account.parent( toModel( accountEntity.getParent(), context ) );

        Account accountResult = account.build();

        context.storeMappedInstance( accountEntity, accountResult );

        return accountResult;
    }

    @Override
    public List<Account> toModel(List<com.bookkeeper.account.jpa.Account> accountEntities, AccountCyclicGraphContext context) {
        if ( accountEntities == null ) {
            return null;
        }

        List<Account> list = new ArrayList<Account>( accountEntities.size() );
        for ( com.bookkeeper.account.jpa.Account account : accountEntities ) {
            list.add( toModel( account, context ) );
        }

        return list;
    }
}
