create schema library_api;
use library_api;

drop table if exists patrons;
create table patrons (
    patron_id int primary key,
    name      varchar(250) not null,
    fines     numeric(5,2) not null default 0.0
);

drop table if exists books;
create table books (
   book_id            int primary key,
   title              varchar(250) not null,
   author             varchar(250) not null,
   checkout_patron_id int,
   checkout_date      date,

   foreign key (checkout_patron_id) references patrons(patron_id)
);
