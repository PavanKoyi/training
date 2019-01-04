CREATE TABLE service_address (
    id              BIGINT(20)      PRIMARY KEY		AUTO_INCREMENT,
    street          VARCHAR(100)		NOT NULL,
    city            VARCHAR(15)		NOT NULL,
    postal_code     INT(11)         NOT NULL
);


CREATE TABLE service_person (
    id              BIGINT(20)      PRIMARY KEY		AUTO_INCREMENT,
    first_name      VARCHAR(50)		NOT NULL,
    last_name		  VARCHAR(50)		NOT NULL,
    email           VARCHAR(100)    UNIQUE,
    address_id      BIGINT(20)		NOT NULL,
    birth_date      DATE				NOT NULL,
    created_date    DATE    			NOT NULL,
                    KEY k_service_person_service_address (address_id),
                    CONSTRAINT fk_service_person_service_address
                    FOREIGN KEY (address_id) REFERENCES service_address (id) ON DELETE NO ACTION ON UPDATE NO ACTION
);
