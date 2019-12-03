create table commands
(
    id               serial primary key,
    command          varchar(255) not null,
    count            int4         not null,
    requested_minute timestamp    not null
);

create index command_requested_at_minute_idx on commands (command, requested_minute);