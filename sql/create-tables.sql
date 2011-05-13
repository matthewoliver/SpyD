
    drop table item_record;

    create table item_record (
        id varchar(255) not null,
        checksum varchar(255),
        checksum_algorithm varchar(255),
        date_created timestamp,
        item_status int4,
        last_checked timestamp,
        primary key (id)
    );
