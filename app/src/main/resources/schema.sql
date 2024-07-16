DROP TABLE IF EXISTS urls;
DROP TABLE IF EXISTS url_checks;

CREATE TABLE urls (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    created_at TIMESTAMP
);

CREATE TABLE url_checks (
    id SERIAL PRIMARY KEY,
    url_id BIGINT REFERENCES urls (id),
    status_code INT,
    h1 VARCHAR(255) NOT NULL,
    title VARCHAR(255) NOT NULL,
    description TEXT,
    created_at TIMESTAMP
);