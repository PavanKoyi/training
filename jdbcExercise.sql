CREATE TABLE address (
    id              BIGINT(20)      PRIMARY KEY		AUTO_INCREMENT,
    street          VARCHAR(100),
    city            VARCHAR(15),
    postal_code     INT(11)         NOT NULL
);


CREATE TABLE person (
    id              BIGINT(20)      PRIMARY KEY		AUTO_INCREMENT,
    `name`          VARCHAR(50),
    email           VARCHAR(100)    UNIQUE,
    address_id      BIGINT(20),
    birth_date      DATE,
    created_date    DATETIME,
                    KEY k_person_address (address_id),
                    CONSTRAINT fk_person_address
                    FOREIGN KEY (address_id) REFERENCES address (id) ON DELETE NO ACTION ON UPDATE NO ACTION
);
