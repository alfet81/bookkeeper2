insert into data.accounts(parent_id, name, currency_code, initial_balance)
values (null, 'General', 'USD', 0.00);

//insert into data.accounts(parent_id, name, currency_code, initial_balance)
//select id, 'Level2', 'USD', 0.00 from data.accounts where name = 'Level1';

//insert into data.accounts(parent_id, name, currency_code, initial_balance)
//select id, 'Level3', 'USD', 0.00 from data.accounts where name = 'Level2';

insert into data.categories(name, entry_type)
values ('Income', 'CREDIT'),
       ('Expense', 'DEBIT'),
       ('Uncategorized', 'NONE');