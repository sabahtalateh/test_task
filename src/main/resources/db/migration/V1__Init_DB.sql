create table commands
(
    id              int8         not null,
    command         varchar(255) not null,
    count           int4         not null,
    inserted_minute timestamp    not null,
    primary key (id)
);
create index command_inserted_at_minute_idx on commands (command, inserted_minute);
create sequence command_sequence start 1000 increment 50;