drop table if exists stock_data;
CREATE TABLE IF NOT EXISTS stock_data
(
    id         BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name       VARCHAR                     not null,
    trade_date TIMESTAMP WITHOUT TIME ZONE not null,
    close      VARCHAR                     not null,
    volume     BIGINT                      not null,
    open       VARCHAR                     not null,
    high       VARCHAR                     not null,
    low        VARCHAR                     not null
);
CREATE TABLE IF NOT EXISTS users
(
    id       BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name     VARCHAR not null,
    password VARCHAR not null,
    UNIQUE (name)
);
