package com.bookkeeper.account.view.mapper;

import com.bookkeeper.account.model.Account;
import com.bookkeeper.account.view.model.AccountTreeViewItem;
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
public class AccountTreeViewItemMapperImpl implements AccountTreeViewItemMapper {

    @Override
    public AccountTreeViewItem toAccountTreeViewItem(Account account, AccountTreeViewItemCyclicGraphContext context) {
        AccountTreeViewItem target = context.getMappedInstance( account, AccountTreeViewItem.class );
        if ( target != null ) {
            return target;
        }

        if ( account == null ) {
            return null;
        }

        Account value = null;
        String name = null;

        value = account;
        name = account.getName();

        AccountTreeViewItem accountTreeViewItem = new AccountTreeViewItem( name, value );

        accountTreeViewItem.setParent( toAccountTreeViewItem( account.getParent(), context ) );

        context.storeMappedInstance( account, accountTreeViewItem );

        return accountTreeViewItem;
    }

    @Override
    public List<AccountTreeViewItem> toAccountTreeViewItems(List<Account> accounts, AccountTreeViewItemCyclicGraphContext context) {
        if ( accounts == null ) {
            return null;
        }

        List<AccountTreeViewItem> list = new ArrayList<AccountTreeViewItem>( accounts.size() );
        for ( Account account : accounts ) {
            list.add( toAccountTreeViewItem( account, context ) );
        }

        return list;
    }
}
