create database "bank";

create user "user_bank_account" with password '123456';

grant connect on database bank to user_bank_account;

\c bank;

grant create on schema public to user_bank_account;

alter default privileges in schema public grant select, insert, update, delete on tables to user_bank_account;

alter default privileges in schema public grant usage, select, update on sequences to user_bank_account;